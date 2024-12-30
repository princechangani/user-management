package com.campus_connect.user_management.responce;

import java.time.LocalDate;


public class AttendanceDto {

    private Integer id;
    private String name;
    private LocalDate date;
    private Long enrollmentNo;
    private Double attendancePercent;
    private Integer absent;
    private Integer present;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(Long enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public Double getAttendancePercent() {
        return attendancePercent;
    }

    public void setAttendancePercent(Double attendancePercent) {
        this.attendancePercent = attendancePercent;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }
}
