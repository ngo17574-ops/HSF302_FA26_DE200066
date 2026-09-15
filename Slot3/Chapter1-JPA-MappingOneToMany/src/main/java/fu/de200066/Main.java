package fu.de200066;

import fu.de200066.dao.DepartmentDAO;
import fu.de200066.pojo.Department;
import fu.de200066.pojo.Employee;
import fu.de200066.pojo.Gender;
import fu.de200066.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // 1. Gọi findAll() các Department (KHÔNG JOIN FETCH) -> Sinh ra 1 câu SQL SELECT

            List<Department> departments = departmentDAO.findAll(em);
            System.out.println("-> Đã load xong danh sách Department (1 Query)");
            // 2. Loop qua department.getEmployees() của từng phần tử -> Sinh ra N câu SQL SELECT
            System.out.println("-> Bắt đầu loop qua getEmployees() của từng phòng ban:");
            for (Department d : departments) {
                System.out.println("Phòng ban: " + d.getName() + " có " + d.getEmployees().size() + " nhân viên.");
            }
        } finally {
            em.close();
        }


    }
}