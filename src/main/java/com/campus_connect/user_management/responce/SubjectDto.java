package com.campus_connect.user_management.responce;


import java.util.Map;

public class SubjectDto {
        private Integer id;
        private String subjectName;
        private Integer score;
        private Integer resultId; // To link the subject to a specific result
        private Map<String, String> theoryGrade;
        private Map<String, String> practiceGrade;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getResultId() {
            return resultId;
        }

        public void setResultId(Integer resultId) {
            this.resultId = resultId;
        }

        public Map<String, String> getTheoryGrade() {
            return theoryGrade;
        }

        public void setTheoryGrade(Map<String, String> theoryGrade) {
            this.theoryGrade = theoryGrade;
        }

        public Map<String, String> getPracticeGrade() {
            return practiceGrade;
        }

        public void setPracticeGrade(Map<String, String> practiceGrade) {
            this.practiceGrade = practiceGrade;
        }
    }


