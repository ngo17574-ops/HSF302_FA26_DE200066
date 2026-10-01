package com.hsf302.ch4.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, unique = true, length = 10)
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private LocalDate dob;

    private Double gpa;

    private boolean active;

    // Quan hệ ManyToOne với Department (Exercise 1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // =========================================================================
    // TODO 3: Owning side của quan hệ Many-To-Many với Course
    // =========================================================================
    @ManyToMany // Fetch mặc định là LAZY, KHÔNG dùng CascadeType.REMOVE hay ALL
    @JoinTable(
            name = "student_courses",                                    // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "student_id"),              // FK trỏ về bảng students (entity hiện tại)
            inverseJoinColumns = @JoinColumn(name = "course_id")         // FK trỏ về bảng courses (entity bên kia)
    )
    private Set<Course> courses = new HashSet<>();

    // ===== Helper Methods đồng bộ 2 chiều =====
    public void enroll(Course c) {
        courses.add(c);                 // Owning side -> Hibernate INSERT vào bảng student_courses
        c.getStudents().add(this);      // Inverse side -> giữ đối tượng Java đồng bộ trong cùng transaction
    }

    public void unenroll(Course c) {
        courses.remove(c);              // Owning side -> Hibernate DELETE khỏi bảng student_courses
        c.getStudents().remove(this);   // Inverse side -> giữ đối tượng Java đồng bộ
    }

    // Business key: equals/hashCode theo mã sinh viên studentCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student other)) return false;
        return studentCode != null && studentCode.equals(other.getStudentCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentCode);
    }

    @Override
    public String toString() {
        return String.format("%s | %-15s | %-20s | %.1f | %s",
                studentCode, fullName, email, gpa, active ? "active" : "inactive");
        // Giữ nguyên: KHÔNG in department và KHÔNG in courses để tránh LazyInitializationException
    }
}