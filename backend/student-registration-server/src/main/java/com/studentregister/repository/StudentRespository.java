package com.studentregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentregister.model.Student;

@Repository
public interface StudentRespository extends JpaRepository<Student, Long>{

}
