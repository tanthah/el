package dao;

import jakarta.persistence.*;
import Model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu sinh viên
    public boolean saveStudent(Student student) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (student.getAccountId() == null) {
                em.persist(student);
            } else {
                em.merge(student);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Tìm sinh viên theo ID
    public Student findById(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Student.class, accountId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Tìm tất cả sinh viên
    public List<Student> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    // Xóa sinh viên
    public boolean deleteStudent(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, accountId);
            if (student != null) {
                em.remove(student);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Tìm sinh viên theo username
    public Student findByUsername(String username) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.username = :username", Student.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Tìm sinh viên theo email
    public Student findByEmail(String email) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Tìm sinh viên theo số điện thoại
    public Student findByPhone(String phone) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.phone = :phone", Student.class);
            query.setParameter("phone", phone);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
