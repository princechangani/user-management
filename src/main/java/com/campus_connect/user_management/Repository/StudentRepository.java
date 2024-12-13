package com.campus_connect.user_management.Repository;

import com.campus_connect.user_management.DataEntity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
    Optional<Student> findByEnrollmentNumber(Long enrollmentNumber);

}
