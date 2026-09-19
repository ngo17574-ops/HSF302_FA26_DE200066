package fu.de200066.dao;

import fu.de200066.pojo.Project;
import fu.de200066.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ProjectDAO {

    public void save(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(project);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Project findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public List<Project> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Project> findAllWithEmployees() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.employees", Project.class).getResultList();
        } finally {
            em.close();
        }
    }

    //  5.8
    public List<Object[]> getActiveEmployeeStatsByProject() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT p.projectName, COUNT(e), SUM(e.salary) " +
                          "FROM Project p JOIN p.employees e " +
                          "WHERE e.active = true " +
                          "GROUP BY p.projectName";
            return em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            em.close();
        }
    }
}