package fu.de200066.dao;

import fu.de200066.pojo.Employee;
import fu.de200066.pojo.Project;
import fu.de200066.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {

    // 1. Lưu Employee vào DB
    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // 2. Tìm Employee theo ID
    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    // 5.6
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Tìm cả 2 entity trong cùng 1 Persistence Context (trạng thái Managed)
            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee != null && project != null) {
                // Gọi helper method đã viết ở TODO 5.5
                employee.assignToProject(project);
                // Do employee đang ở trạng thái Managed, cơ chế Dirty Checking của JPA
                // sẽ tự động nhận diện thay đổi và phát sinh câu lệnh INSERT vào bảng employee_project khi commit
            } else {
                System.out.println("Không tìm thấy Employee (ID: " + employeeId + ") hoặc Project (ID: " + projectId + ")");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findAllWithProjects() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.projects", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}