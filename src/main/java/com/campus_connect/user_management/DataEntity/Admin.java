package com.campus_connect.user_management.DataEntity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Table(name = "Admin", uniqueConstraints = {@UniqueConstraint(columnNames = { "email"})})
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String role;
    private  String email;
    private  String password;
    private String bearerToken;
   /* @ElementCollection
    @CollectionTable(name = "Admin_Authorities", joinColumns = @JoinColumn(name = "admin_id"))
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, Object> authorities;*/




    private Long lastOTP;


    public Long getLastOTP() {
        return lastOTP;
    }

    public void setLastOTP(Long lastOTP) {
        this.lastOTP = lastOTP;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

/*
    public Map<String, Object> getAuthorities() {

        return authorities;
    }

    public void setAuthorities(Map<String, Object> authorities) {
        this.authorities = authorities;
    }*/
}

