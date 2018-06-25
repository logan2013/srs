package com.srs.specification;

import com.srs.po.course.Course;
import com.srs.po.transcript.Transcript;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudyPlanSpecification implements Specification < Transcript > {

    @Override
    public boolean isSatisfiedBy ( Transcript transcript ) {

        List < Course > studyPlan = transcript.getStudent ( ).getStudyPlan ( );
        Course          course    = transcript.getSection ( ).getCourse ( );
        return studyPlan.stream ( ).anyMatch ( c -> c.getId ( ).equals ( course.getId ( ) ) );
    }
}