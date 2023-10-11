package com.studentregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentregister.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
