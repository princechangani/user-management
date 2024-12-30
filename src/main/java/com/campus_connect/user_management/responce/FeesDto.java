package com.campus_connect.user_management.responce;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeesDto {

    private Integer id;
    private Long enrollmentNo;
    private Integer semester;
    private String email;
    private Long feeAmount;
    private Boolean isSubmitted;
}