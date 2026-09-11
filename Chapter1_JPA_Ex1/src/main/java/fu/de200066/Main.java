package fu.de200066;

import fe.de200066.dao.EmployeeDAO;
import fe.de200066.pojo.Employee;
import fe.de200066.pojo.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU");
        EmployeeDAO dao = new EmployeeDAO(emf);

        // 1. Test findById với ID tồn tại
        Long searchId = 1L;
        Employee emp = dao.findById(searchId);
        if (emp != null) {
            System.out.println("Tìm thấy nhân viên ID " + searchId + ": " + emp);
            System.out.println("-> Họ tên: " + emp.getFullName() + ", Lương: " + emp.getSalary());
        } else {
            System.out.println("Không tìm thấy nhân viên ID " + searchId);
        }
        // 2. Test findById với ID không tồn tại (kiểm tra xem có trả về null không)
        Long notExistId = 999L;
        Employee notFoundEmp = dao.findById(notExistId);
        System.out.println("Tìm ID " + notExistId + " (kỳ vọng null): " + notFoundEmp);
        // 3. Test findAll()
        System.out.println("\n--- Danh sách tất cả nhân viên ---");
        List<Employee> list = dao.findAll();
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("Tổng số nhân viên: " + list.size());


        emf.close();
    }
}