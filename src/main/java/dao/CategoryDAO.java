package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import Model.Category;
import java.util.List;

public class CategoryDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Private constructor to prevent instantiation
    private CategoryDAO() {
    }

    // Static inner class for lazy initialization (thread-safe)
    private static class SingletonHolder {
        private static final CategoryDAO INSTANCE = new CategoryDAO();
    }

    // Public method to get the singleton instance
    public static CategoryDAO getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean saveCategory(Category category) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (category.getCategoryId() == null) {
                em.persist(category);
            } else {
                em.merge(category);
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

    public Category findById(Long categoryId) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Category.class, categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Category> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public boolean deleteCategory(Long categoryId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Category category = em.find(Category.class, categoryId);
            if (category != null) {
                em.remove(category);
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

    public Category findByName(String name) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.name = :name", Category.class);
            query.setParameter("name", name);
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

    // Optional: Method to close EntityManagerFactory (call during application shutdown)
    public static void closeFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}