package com.srs.po;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 冯楚
 * @date 2018/6/8-19:12
 */
@Entity
@Table ( uniqueConstraints = @UniqueConstraint ( columnNames = { "studentSn" } ) )
public class Student implements Serializable {

    private Integer id;

    private String name;

    private String studentSn;

    private String major;

    private String degree;

    private List < Course > studyPlan;

    @JsonManagedReference ( value = "transcripts-student" )
    private List < Transcript > transcripts;

    private SysUser user;

    @Id
    @GeneratedValue ( strategy = GenerationType.SEQUENCE )
    public Integer getId ( ) {

        return id;
    }

    public void setId ( Integer id ) {

        this.id = id;
    }

    public String getName ( ) {

        return name;
    }

    public void setName ( String name ) {

        this.name = name;
    }

    public String getStudentSn ( ) {

        return studentSn;
    }

    public void setStudentSn ( String studentSn ) {

        this.studentSn = studentSn;
    }

    public String getMajor ( ) {

        return major;
    }

    public void setMajor ( String major ) {

        this.major = major;
    }

    public String getDegree ( ) {

        return degree;
    }

    public void setDegree ( String degree ) {

        this.degree = degree;
    }

    @ManyToMany ( cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY )
    @JoinColumn ( name = "studentId", referencedColumnName = "courseId" )
    public List < Course > getStudyPlan ( ) {

        return studyPlan;
    }

    public void setStudyPlan ( List < Course > studyPlan ) {

        this.studyPlan = studyPlan;
    }

    @OneToMany ( cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY, mappedBy = "student" )
    public List < Transcript > getTranscripts ( ) {

        return transcripts;
    }

    public void setTranscripts ( List < Transcript > transcripts ) {

        this.transcripts = transcripts;
    }

    @OneToOne ( cascade = { CascadeType.ALL }, fetch = FetchType.LAZY )
    @JoinColumn ( name = "userId", referencedColumnName = "id" )
    public SysUser getUser ( ) {

        return user;
    }

    public void setUser ( SysUser user ) {

        this.user = user;
    }

}
