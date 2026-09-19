package fu.de200066.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "active")
    private boolean active = true;

//5.2
    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String fullName, String email, BigDecimal salary, LocalDate hireDate, Gender gender) {
        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
        this.gender = gender;
        this.active = true;
    }

    public Employee(String fullName, String email, BigDecimal salary, LocalDate hireDate, Gender gender, boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
        this.gender = gender;
        this.active = active;
    }

    //5.4
    // Lý do không dùng id:
    // 1. Khi entity ở trạng thái transient (chưa persist), id là null.
    // 2. Nếu thêm vào Set trước khi persist, sau khi persist DB sinh ra id mới
    //    sẽ làm thay đổi hashCode, phá vỡ cấu trúc của HashSet (bucket lookup bị sai).
    // Do đó sử dụng email (business key bất biến, unique) để đảm bảo an toàn.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(email, employee.email);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
    // 5.5
    public void assignToProject(Project p) {
        if (p != null) {
            this.projects.add(p);
            p.getEmployees().add(this);
        }
    }

    // 5.9: Helper method gỡ khỏi dự án, xóa ở cả 2 phía in-memory
    public void unassignFromProject(Project p) {
        if (p != null) {
            this.projects.remove(p);
            p.getEmployees().remove(this);
        }
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Set<Project> getProjects() { return projects; }
    public void setProjects(Set<Project> projects) { this.projects = projects; }
}