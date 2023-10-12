//package com.studentregister.model;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class CourseRegistration {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(targetEntity = Student.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "os_fk", referencedColumnName = "id")
//    private Student student;
//
//    @ManyToOne(targetEntity = Course.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "oc_fk", referencedColumnName = "id")
//    private Course course;
//    
//    @CreatedBy
//    private String createdBy;
//
//    @CreatedDate
//    private LocalDateTime createdDate;
//
//    @LastModifiedBy
//    private String lastModifiedBy;
//
//    @LastModifiedDate
//    private LocalDateTime lastModifiedDate;
//}
