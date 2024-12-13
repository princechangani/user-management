package com.campus_connect.user_management.responce;

import java.time.LocalDate;


public class AttendanceDto {

    private Integer id;
    private String name;
    private LocalDate date;
    private Long enrollmentNo;
    private float attendancePercent;

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

    public float getAttendancePercent() {
        return attendancePercent;
    }

    public void setAttendancePercent(float attendancePercent) {
        this.attendancePercent = attendancePercent;
    }
}
