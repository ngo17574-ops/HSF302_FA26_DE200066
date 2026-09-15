package fe.de200066.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate hireDate;

    private boolean active;

    @Transient
    private int yearsOfService;

    // Constructor tiện lợi không gồm ID (để DB tự sinh)
    public Employee(String fullName, String email, BigDecimal salary, Gender gender, LocalDate hireDate, boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.gender = gender;
        this.hireDate = hireDate;
        this.active = active;
    }

    // Method tính toán số năm làm việc (theo yêu cầu đề bài)
    public int getYearsOfService() {
        if (this.hireDate == null) return 0;
        return Period.between(this.hireDate, LocalDate.now()).getYears();
    }
}