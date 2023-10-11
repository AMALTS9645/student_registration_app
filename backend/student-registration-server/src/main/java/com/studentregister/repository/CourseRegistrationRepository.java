package com.studentregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentregister.model.CourseRegistration;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

}
