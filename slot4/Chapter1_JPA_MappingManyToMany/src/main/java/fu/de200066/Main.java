package fu.de200066;

import fu.de200066.dao.EmployeeDAO;
import fu.de200066.dao.ProjectDAO;
import fu.de200066.pojo.Employee;
import fu.de200066.pojo.Gender;
import fu.de200066.pojo.Project;
import fu.de200066.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();



        // 1. Tạo 3 Employee theo đúng thông tin yêu cầu
        Employee emp1 = new Employee(
                "Phạm Minh Đức",
                "ducpm@fpt.edu.vn",
                new BigDecimal("25000000"),
                LocalDate.of(2023, 1, 15),
                Gender.MALE,
                true
        );

        Employee emp2 = new Employee(
                "Ngô Viết Bảo Huy",
                "huynvb@fpt.edu.vn",
                new BigDecimal("22000000"),
                LocalDate.of(2023, 6, 1),
                Gender.MALE,
                true
        );

        Employee emp3 = new Employee(
                "Dương Trong",
                "trongd@fpt.edu.vn",
                new BigDecimal("20000000"),
                LocalDate.of(2024, 2, 20),
                Gender.MALE,
                true
        );

        // 2. Tạo 2 Project
        Project prjA = new Project(
                "PRJ-A",
                "Dự án Website E-Commerce",
                new BigDecimal("150000000"),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );

        Project prjB = new Project(
                "PRJ-B",
                "Dự án Mobile Banking App",
                new BigDecimal("250000000"),
                LocalDate.of(2024, 3, 1),
                null // Chưa kết thúc
        );

        // 3. Lưu Employee và Project vào DB
        System.out.println("--- Đang lưu Nhân viên và Dự án vào CSDL ---");
        employeeDAO.save(emp1);
        employeeDAO.save(emp2);
        employeeDAO.save(emp3);

        projectDAO.save(prjA);
        projectDAO.save(prjB);
        System.out.println("-> Đã lưu thành công 3 nhân viên và 2 dự án!\n");

        // 4. Phân công chéo theo đề bài:
        // - NV1 (Phạm Minh Đức) -> Project A + Project B
        // - NV2 (Ngô Viết Bảo Huy) -> Project B
        // - NV3 (Dương Trong) -> Project A
        System.out.println("--- Đang phân công dự án ---");
        employeeDAO.assignEmployeeToProject(emp1.getId(), prjA.getId());
        employeeDAO.assignEmployeeToProject(emp1.getId(), prjB.getId());
        employeeDAO.assignEmployeeToProject(emp2.getId(), prjB.getId());
        employeeDAO.assignEmployeeToProject(emp3.getId(), prjA.getId());
        System.out.println("-> Phân công chéo hoàn tất!\n");

        // 5. Đọc từ CSDL và in danh sách Project của từng Nhân viên
        System.out.println("==================================================");
        System.out.println("DANH SÁCH DỰ ÁN CỦA TỪNG NHÂN VIÊN:");
        System.out.println("==================================================");
        List<Employee> employees = employeeDAO.findAllWithProjects();
        for (Employee emp : employees) {
            System.out.println("Nhân viên: " + emp.getFullName() + " | Email: " + emp.getEmail() + " | Lương: " + emp.getSalary());
            System.out.println("  -> Số dự án tham gia: " + emp.getProjects().size());
            for (Project p : emp.getProjects()) {
                System.out.println("     + [" + p.getProjectCode() + "] " + p.getProjectName());
            }
            System.out.println();
        }

        // 6. Đọc từ CSDL và in danh sách Nhân viên tham gia từng Dự án (Kiểm tra tính 2 chiều)
        System.out.println("==================================================");
        System.out.println("DANH SÁCH NHÂN VIÊN THAM GIA TỪNG DỰ ÁN (CHIỀU NGƯỢC LẠI):");
        System.out.println("==================================================");
        List<Project> projects = projectDAO.findAllWithEmployees();
        for (Project p : projects) {
            System.out.println("Dự án: [" + p.getProjectCode() + "] " + p.getProjectName());
            System.out.println("  -> Số nhân viên tham gia: " + p.getEmployees().size());
            for (Employee e : p.getEmployees()) {
                System.out.println("     + " + e.getFullName() + " (" + e.getEmail() + ")");
            }
            System.out.println();
        }

        // 7.
        //5.8

        List<Object[]> stats = projectDAO.getActiveEmployeeStatsByProject();
        for (Object[] row : stats) {
            String projectName = (String) row[0];
            Long count = (Long) row[1];
            BigDecimal totalSalary = (BigDecimal) row[2];
            System.out.printf("Dự án: %-26s | Số NV Active: %d | Tổng Lương: %,.2f VND%n",
                    projectName, count, totalSalary);
        }
        System.out.println();

        // Đóng EntityManagerFactory khi kết thúc chương trình
        JPAUtil.close();
    }
}