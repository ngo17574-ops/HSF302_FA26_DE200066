package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    long count();
    Optional<Student> findById(Long id);
    List<Student> findAllOrderByGpaDesc();                              // 7a
    Page<Student> findPage(int pageIndex, int size, String sortField);  // 7b
    Optional<Student> findByStudentCode(String studentCode);   // 8a
    boolean isEmailExisted(String email);                      // 8b
    long countActive();                                        // 8c
    List<Student> searchByName(String keyword);        // 9a
    List<Student> findByEmailDomain(String domain);    // 9b
    List<Student> findWithoutEmail();                  // 9c
    List<Student> findByGpaRange(double min, double max);   // 10a
    List<Student> findActiveByGender(Gender gender);        // 10b
    List<Student> findBornAfter(LocalDate date);            // 10c
    List<Student> findByDepartment(String deptCode);    // 11a
    long countByDepartment(String deptCode);            // 11b
    List<Student> findTop3ByGpa();                      // 11c
    // TODO 12
    List<Student> findGoodStudents(String deptCode, double minGpa);
    // TODO 13
    List<Student> searchByKeyword(String keyword);
    // TODO 15
    List<Student> findAboveAverageGpa();
}
