package com.campus_connect.user_management.responce;

import jakarta.persistence.Version;

import java.util.List;

public class StudentResponse {
    private int id;
    private String studentName;

    @Version
    private Long enrollmentNo;
    private String branch;
    private List<SubjectResponse> subjects;
    private String status;
    private double spi;
    private double cpi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<SubjectResponse> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponse> subjects) {
        this.subjects = subjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSpi() {
        return spi;
    }

    public void setSpi(double spi) {
        this.spi = spi;
    }

    public double getCpi() {
        return cpi;
    }

    public void setCpi(double cpi) {
        this.cpi = cpi;
    }

    public static class Subject {
        private int id;
        private String subjectName;
        private int score;
        private int resultId;
        private TheoryGrade theoryGrade;
        private PracticeGrade practiceGrade;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getResultId() {
            return resultId;
        }

        public void setResultId(int resultId) {
            this.resultId = resultId;
        }

        public TheoryGrade getTheoryGrade() {
            return theoryGrade;
        }

        public void setTheoryGrade(TheoryGrade theoryGrade) {
            this.theoryGrade = theoryGrade;
        }

        public PracticeGrade getPracticeGrade() {
            return practiceGrade;
        }

        public void setPracticeGrade(PracticeGrade practiceGrade) {
            this.practiceGrade = practiceGrade;
        }

        public static class TheoryGrade {
            private String Midterm;
            private String Final;

            public String getMidterm() {
                return Midterm;
            }

            public void setMidterm(String midterm) {
                Midterm = midterm;
            }

            public String getFinal() {
                return Final;
            }

            public void setFinal(String aFinal) {
                Final = aFinal;
            }
        }

        public static class PracticeGrade {
            private String Lab1;
            private String Lab2;

            public String getLab1() {
                return Lab1;
            }

            public void setLab1(String lab1) {
                Lab1 = lab1;
            }

            public String getLab2() {
                return Lab2;
            }

            public void setLab2(String lab2) {
                Lab2 = lab2;
            }
        }
    }
}