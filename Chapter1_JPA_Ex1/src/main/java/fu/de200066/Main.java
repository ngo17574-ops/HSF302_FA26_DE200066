package fu.de200066;

import fe.de200066.dao.EmployeeDAO;
import fe.de200066.pojo.Employee;
import fe.de200066.pojo.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU");
        EmployeeDAO dao = new EmployeeDAO(emf);

        // 1. Khởi tạo đối tượng -> Trạng thái: New / Transient (chưa có ID, chưa quản lý bởi JPA)
        Employee emp = new Employee(
                "Nguyen Van A",
                "anguyen@gmail.com",
                new BigDecimal("15000000.00"),
                Gender.MALE,
                LocalDate.of(2022, 5, 10),
                true
        );

        System.out.println("Trước khi save - ID: " + emp.getId()); // In ra: null

        // 2. Gọi save() -> Trong persist(): Managed -> Sau khi commit & close(): Detached
        dao.save(emp);

        // 3. Kiểm tra tiêu chí Checklist TODO 0.3
        System.out.println("Sau khi save - ID đã tự sinh: " + emp.getId());
        System.out.println("Thâm niên làm việc: " + emp.getYearsOfService() + " năm");

        emf.close();
    }
}