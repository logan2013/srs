package com.srs.service.transcript;

import com.srs.dao.SectionRepository;
import com.srs.dao.SysUserRepository;
import com.srs.dao.TranscriptRepository;
import com.srs.domain.TranscriptCatalog;
import com.srs.po.Section;
import com.srs.po.Student;
import com.srs.po.Transcript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TranscriptServiceImpl implements TranscriptService {

    @Autowired
    TranscriptRepository transcriptRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SectionRepository sectionRepository;

    private final TranscriptCatalog catalog = TranscriptCatalog.getInstance ( );

    @Override
    public String findAllByStudentJson ( Student student ) {

        return catalog.getTranscriptJsonForStudent ( transcriptRepository.getTranscriptsByStudentEquals ( student ) );
    }

    /**
     * 选课的场景类似秒杀，除了waitingList的实现外，还需要注意"超卖"问题 因为高并发，所以显然不能直接加锁. 经典的解决方案是带标记地更新库存容量
     * 因为本身涉及waitingList，所以我这里的解决方案是只要过了选课校验就保存当前记录。然后返回等待结果同步的提示信息，前端刷新数据，此时此条选课记录的index如果小于容量，则选课成功
     * 否则直接视为在waitingList中，当前面有人退选时，记录删除，则后面的记录自然往上补齐。选课通道关闭时统一清除冗余的记录，并通知选课成功的人付费
     */
    @Override
    public String chooseOneSection ( Student student , Section section ) {

        Transcript transcript = new Transcript ( section , student );
        String     s          = transcript.canChoose ( );
        if ( s == null ) {
            transcriptRepository.save ( transcript );
            return "{\"msg\":\"操作成功，等待同步结果\"}";
        } else {
            return s;
        }
    }

    @Override
    public boolean unChooseOneSection ( Integer id ) {

        transcriptRepository.deleteById ( id );
        return true;
    }

    @Override
    public String getTranscriptsBySectionId ( Integer sectionId ) {

        Optional < Section > byId = sectionRepository.findById ( sectionId );
        Section              section;
        if ( byId.isPresent ( ) ) {
            section = byId.get ( );
            List < Transcript > transcripts = section.getTranscripts ( );
            if ( transcripts.size ( ) > section.getCapacity ( ) ) {
                transcripts = transcripts.subList ( 0 , section.getCapacity ( ) - 1 );
            }
            return catalog.getTranscriptJsonForTeacher ( transcripts );
        } else {
            return null;
        }
    }

    @Override
    public String updateTranscripts ( List < Transcript > transcripts ) {

        List < Transcript > saveEd = transcriptRepository.saveAll ( transcripts );
        return catalog.getTranscriptJsonForTeacher ( saveEd );
    }
}
