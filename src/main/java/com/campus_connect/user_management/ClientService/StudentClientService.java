package com.campus_connect.user_management.ClientService;

import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.Repository.StudentRepository;
import com.campus_connect.user_management.clientRepository.*;
import com.campus_connect.user_management.exception.UserNotFoundException;
import com.campus_connect.user_management.responce.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;



@Service
public class StudentClientService {



    private final StudentRepository studentRepository;
    private final StudentClient studentClient;


    public StudentClientService(StudentRepository studentRepository, StudentClient studentClient){
        this.studentRepository = studentRepository;

        this.studentClient = studentClient;
    }

    public StudentResponse getStudentResult(Long enrollmentNo) {
        return studentClient.getStudentResult(enrollmentNo);
    }



    public ResultDto saveStudentResult(Long enrollmentNo, ResultDto resultDto) {
        Optional<Student> student= Optional.of(studentRepository.findByEnrollmentNumber(enrollmentNo).orElseThrow(
                ()-> new UserNotFoundException("User not found for this Enrollment Number: " + enrollmentNo)
        ));
        resultDto.setEnrollmentNo(enrollmentNo);
        return studentClient.saveResult(resultDto);
    }


    public AttendanceDto saveStudentAttendance(Long enrollmentNo,AttendanceDto attendanceDto) {

        Optional<Student> student= Optional.of(studentRepository.findByEnrollmentNumber(enrollmentNo).orElseThrow(
                ()-> new UserNotFoundException("User not found for this Enrollment Number: " + enrollmentNo)
        ));

        attendanceDto.setEnrollmentNo(enrollmentNo);
        return studentClient.saveAttendance(attendanceDto);


    }

    public AttendanceDto getStudentsAttendance(Long enrollmentNo) {
        return studentClient.getAttendance(enrollmentNo);
    }

    public Map<Integer, Map<String,List<ScheduleDto>>> getStudentSchedule(Integer semester, String division) {
        return studentClient.getScheduleBySemAndDiv(semester,division);

    }

    public FeesDto saveStudentFees(FeesDto feesDto) {
        return studentClient.saveFees(feesDto);

    }
    public List<FeesDto> getStudentFees(Long enrollmentNo) {
        return studentClient.getStudentFees(enrollmentNo);
    }

}
