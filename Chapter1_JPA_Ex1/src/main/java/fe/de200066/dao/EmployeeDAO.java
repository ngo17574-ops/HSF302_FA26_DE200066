package fe.de200066.dao;

import fe.de200066.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

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
}