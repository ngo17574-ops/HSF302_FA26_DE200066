package fe.de200066.dao;

import fe.de200066.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private final EntityManagerFactory emf;

    public EmployeeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void save(Employee e) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();               // 1. Mở transaction
            em.persist(e);            // 2. Chuyển entity sang trạng thái Managed và xếp hàng insert
            tx.commit();              // 3. Thực thi câu lệnh SQL INSERT xuống DB
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();        // 4. Rollback nếu có lỗi
            }
            throw ex;
        } finally {
            em.close();               // 5. Luôn đóng EntityManager
        }
    }
    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close(); // Luôn đóng EntityManager
        }
    }
    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            // "Employee" ở đây là tên Entity class trong Java, KHÔNG phải tên bảng trong SQL
            String jpql = "SELECT e FROM Employee e";
            return em.createQuery(jpql, Employee.class).getResultList();
        } finally {
            em.close();
        }
    }
    public Employee findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.email = :email";
            List<Employee> results = em.createQuery(jpql, Employee.class)
                    .setParameter("email", email) // Dùng tham số hóa chống Injection
                    .getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    public List<Employee> findBySalaryGreaterThan(BigDecimal minSalary) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.salary > :minSalary";
            return em.createQuery(jpql, Employee.class)
                    .setParameter("minSalary", minSalary)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public List<Employee> findByActive(boolean active) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.active = :active";
            return em.createQuery(jpql, Employee.class)
                    .setParameter("active", active)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}