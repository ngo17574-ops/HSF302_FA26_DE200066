package fu.de200066.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ràng buộc UNIQUE theo yêu cầu đề bài
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    // 1. Constructor không tham số (Bắt buộc theo chuẩn JPA specification)
    public Department() {
    }

    // 2. Constructor có tham số tiện dụng cho việc khởi tạo
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

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}