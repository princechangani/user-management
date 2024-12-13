package com.campus_connect.user_management.DTO;


public class StudentDto {

    private Long id;
    private  String email;
    private  String password;
    private String bearerToken;
    private String course;
    private String role;
    private int semester;
    private String division;
    private String gradStartYear;
    private String gradEndYear;

    private Long enrollmentNumber;





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSemester() {
        return semester;
    }

    public Long getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(Long enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getGradStartYear() {
        return gradStartYear;
    }

    public void setGradStartYear(String gradStartYear) {
        this.gradStartYear = gradStartYear;
    }

    public String getGradEndYear() {
        return gradEndYear;
    }

    public void setGradEndYear(String gradEndYear) {
        this.gradEndYear = gradEndYear;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
