package com.campus_connect.user_management.ClientService;

import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.Repository.StudentRepository;
import com.campus_connect.user_management.StudentClientRepository.AttendanceClient;
import com.campus_connect.user_management.StudentClientRepository.ResultClient;
import com.campus_connect.user_management.StudentClientRepository.StudentClient;
import com.campus_connect.user_management.exception.UserNotFoundException;
import com.campus_connect.user_management.responce.AttendanceDto;
import com.campus_connect.user_management.responce.ResultDto;
import com.campus_connect.user_management.responce.StudentResponse;
import com.campus_connect.user_management.service.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class StudentClientService {


private final AttendanceClient attendanceClient;
    private final ResultClient resultClient;
    private final StudentRepository studentRepository;

    public StudentClientService(AttendanceClient attendanceClient, ResultClient resultClient, StudentRepository studentRepository) {
        this.attendanceClient = attendanceClient;
        this.resultClient = resultClient;
        this.studentRepository = studentRepository;
    }

    public StudentResponse getStudentResult(Long enrollmentNo) {
        return resultClient.getStudentResult(enrollmentNo);
    }



    public ResultDto saveStudentResult(Long enrollmentNo, ResultDto resultDto) {
        Optional<Student> student= Optional.of(studentRepository.findByEnrollmentNumber(enrollmentNo).orElseThrow(
                ()-> new UserNotFoundException("User not found for this Enrollment Number: " + enrollmentNo)
        ));
        resultDto.setEnrollmentNo(enrollmentNo);
        return resultClient.saveResult(resultDto);
    }


    public AttendanceDto saveStudentAttendance(Long enrollmentNo,AttendanceDto attendanceDto) {

        Optional<Student> student= Optional.of(studentRepository.findByEnrollmentNumber(enrollmentNo).orElseThrow(
                ()-> new UserNotFoundException("User not found for this Enrollment Number: " + enrollmentNo)
        ));

        attendanceDto.setEnrollmentNo(enrollmentNo);
        return attendanceClient.saveAttendance(attendanceDto);


    }

    public AttendanceDto getStudentsAttendance(Long enrollmentNo) {
        return attendanceClient.getAttendance(enrollmentNo);
    }
}
