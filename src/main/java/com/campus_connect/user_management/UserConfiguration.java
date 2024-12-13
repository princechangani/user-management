package com.campus_connect.user_management;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;


@Configuration
public class UserConfiguration {


    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}

