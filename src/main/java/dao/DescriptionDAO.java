package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import Model.Description;
import ENum.ScheduleDay;
import java.util.List;
import java.util.Set;

public class DescriptionDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public boolean saveDescription(Description description) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (description.getDescriptionId() == null) {
                em.persist(description);
            } else {
                em.merge(description);
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

    public Description findById(Long descriptionId) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Description.class, descriptionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Description> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Description> query = em.createQuery("SELECT d FROM Description d", Description.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public boolean deleteDescription(Long descriptionId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Description description = em.find(Description.class, descriptionId);
            if (description != null) {
                em.remove(description);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
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

    public boolean updateApplicableDays(Long descriptionId, Set<ScheduleDay> applicableDays) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Description description = em.find(Description.class, descriptionId);
            if (description != null) {
                description.getApplicableDays().clear();
                description.getApplicableDays().addAll(applicableDays);
                em.merge(description);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
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

    public Description findByCourseId(Long courseId) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Description> query = em.createQuery(
                "SELECT d FROM Description d WHERE d.course.courseId = :courseId", Description.class);
            query.setParameter("courseId", courseId);
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
    
     // Lấy ScheduleDay cho Description từ bảng description_schedule
    public Set<ScheduleDay> getScheduleDay(Long descriptionId) {
        EntityManager em = factory.createEntityManager();
        try {
            // Truy vấn các ScheduleDay liên kết với descriptionId
            Description description = em.find(Description.class, descriptionId);
            if (description != null) {
                return description.getApplicableDays(); // Trả về tập hợp ScheduleDay
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    // THêm zô
}