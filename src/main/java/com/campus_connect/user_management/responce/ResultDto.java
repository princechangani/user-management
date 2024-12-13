package com.campus_connect.user_management.responce;

import jakarta.persistence.Version;

import java.util.ArrayList;
import java.util.List;

public class ResultDto {

    private Integer id;
    private String studentName;
    @Version
    private Long enrollmentNo;
    private String branch;
    @Version
    private List<SubjectDto> subjects = new ArrayList<>();
    private String status;
    private Float spi;
    private Float cpi;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(Long enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDto> subjects) {
        this.subjects = subjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getSpi() {
        return spi;
    }

    public void setSpi(Float spi) {
        this.spi = spi;
    }

    public Float getCpi() {
        return cpi;
    }

    public void setCpi(Float cpi) {
        this.cpi = cpi;
    }


}
