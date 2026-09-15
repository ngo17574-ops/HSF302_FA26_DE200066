package fu.de200066.pojo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ràng buộc UNIQUE
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    // TODO 2.3: Inverse side - 1 phòng ban có nhiều nhân viên
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    // 1. Constructor
    public Department() {
    }

    // 2. Constructor
    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // 3. Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            this.employees.add(employee);
            employee.setDepartment(this); // Đồng bộ ngược lại cho Employee
        }
    }

    public void removeEmployee(Employee employee) {
        if (employee != null) {
            this.employees.remove(employee);
            employee.setDepartment(null); // Hủy tham chiếu của Employee
        }
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", totalEmployees=" + (employees != null ? employees.size() : 0) +
                '}';
    }
}