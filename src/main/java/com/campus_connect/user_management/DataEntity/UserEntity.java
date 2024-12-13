package com.campus_connect.user_management.DataEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = { "email"})})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String role;
    private  String email;
    private  String password;
    private String bearerToken;
}
