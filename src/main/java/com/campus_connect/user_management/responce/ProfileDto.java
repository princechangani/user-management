package com.campus_connect.user_management.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Integer id;
    private int semester;
    private String division;
    private String gradStartYear;
    private String gradEndYear;
    private String bearerToken;
    private String course;
    private Long enrollmentNo;
}
