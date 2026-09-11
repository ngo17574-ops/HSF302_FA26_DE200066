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
        System.out.println("TODO 4");
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

        System.out.println("TODO5");
        // --- 1. Test findByEmail ---
        // Trường hợp 1: Email CÓ tồn tại trong DB
        String existEmail = "anguyen@gmail.com";
        Employee emp1 = dao.findByEmail(existEmail);
        System.out.println("1. Tìm email tồn tại (" + existEmail + "):");
        System.out.println("-> Kết quả: " + emp1);
        // Trường hợp 2: Email KHÔNG tồn tại trong DB (kỳ vọng trả về null, không bị ném exception)
        String notExistEmail = "khongtontai@gmail.com";
        Employee emp2 = dao.findByEmail(notExistEmail);
        System.out.println("\n2. Tìm email không tồn tại (" + notExistEmail + "):");
        System.out.println("-> Kết quả (kỳ vọng null): " + emp2);
        // --- 2. Test findBySalaryGreaterThan ---
        // Mức lương 10.000.000 (sẽ tìm thấy nhân viên A lương 15tr)
        BigDecimal threshold = new BigDecimal("10000000.00");
        System.out.println("\n3. Danh sách nhân viên có lương > " + threshold + ":");
        List<Employee> highSalaryEmps = dao.findBySalaryGreaterThan(threshold);
        for (Employee e : highSalaryEmps) {
            System.out.println("-> " + e.getFullName() + " - Lương: " + e.getSalary());
        }
        // Mức lương 50.000.000 (trường hợp rỗng - danh sách rỗng, không bị lỗi)
        BigDecimal highThreshold = new BigDecimal("50000000.00");
        System.out.println("\n4. Danh sách nhân viên có lương > " + highThreshold + " (kỳ vọng danh sách rỗng):");
        List<Employee> noEmps = dao.findBySalaryGreaterThan(highThreshold);
        System.out.println("-> Số lượng tìm thấy: " + noEmps.size());



        emf.close();
    }
}