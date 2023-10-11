package com.studentregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentregister.model.Student;

public interface StudentRespository extends JpaRepository<Student, Long>{

}
