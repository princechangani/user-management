package com.campus_connect.user_management.ClientService;

import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.Repository.StudentRepository;
import com.campus_connect.user_management.StudentClientRepository.*;
import com.campus_connect.user_management.exception.UserNotFoundException;
import com.campus_connect.user_management.responce.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class StudentClientService {


private final AttendanceClient attendanceClient;
private final FeesClient feesClient;
    private final ResultClient resultClient;
    private final StudentRepository studentRepository;
    private final ScheduleClient scheduleClient;

    public StudentClientService(AttendanceClient attendanceClient, FeesClient feesClient, ResultClient resultClient, StudentRepository studentRepository, ScheduleClient scheduleClient) {
        this.attendanceClient = attendanceClient;
        this.feesClient = feesClient;
        this.resultClient = resultClient;
        this.studentRepository = studentRepository;
        this.scheduleClient = scheduleClient;
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

    public List<ScheduleDto> getStudentSchedule(Integer semester, String division) {
        return scheduleClient.getScheduleBySemAndDiv(semester,division);

    }

    public FeesDto saveStudentFees(FeesDto feesDto) {
        return feesClient.saveFees(feesDto);

    }
    public List<FeesDto> getStudentFees(Long enrollmentNo) {
        return feesClient.getStudentFees(enrollmentNo);
    }

}
