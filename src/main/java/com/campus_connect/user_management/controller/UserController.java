package com.campus_connect.user_management.controller;


import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.responce.StudentResponse;
import com.campus_connect.user_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campus-connect/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto saveStudent(@RequestBody StudentDto studentDTO) {
        userService.saveStudent(studentDTO);
        return studentDTO;
    }



}