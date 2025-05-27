package dao;

import jakarta.persistence.*;
import Model.Order;
import Model.Course;
import Model.Student;
import Model.Payment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu đơn hàng
    public boolean saveOrder(Order order) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (order.getOrderId() == null) {
                System.out.println("Persisting new order");
                em.persist(order);
            } else {
                System.out.println("Merging existing order");
                em.merge(order);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error in saveOrder: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Tìm đơn hàng theo ID
    public Order findById(Long orderId) {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding order by ID: " + orderId);
            Order order = em.find(Order.class, orderId);
            if (order != null) {
                System.out.println("Found order with ID: " + orderId);
            } else {
                System.out.println("No order found for ID: " + orderId);
            }
            return order;
        } catch (Exception e) {
            System.err.println("Error in findById: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Tìm tất cả đơn hàng
    public List<Order> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding all orders");
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
            List<Order> orders = query.getResultList();
            System.out.println("Found " + orders.size() + " orders");
            return orders;
        } catch (Exception e) {
            System.err.println("Error in findAll: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    // Xóa đơn hàng
    public boolean deleteOrder(Long orderId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Order order = em.find(Order.class, orderId);
            if (order != null) {
                System.out.println("Deleting order with ID: " + orderId);
                em.remove(order);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("No order found to delete for ID: " + orderId);
                em.getTransaction().commit();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error in deleteOrder: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Tìm đơn hàng theo Student (Sinh viên)
    public List<Order> findByStudentId(Long studentId) {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding orders for studentId: " + studentId);
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.student.accountId = :studentId", Order.class);
            query.setParameter("studentId", studentId);
            List<Order> orders = query.getResultList();
            System.out.println("Found " + orders.size() + " orders for studentId: " + studentId);
            return orders;
        } catch (Exception e) {
            System.err.println("Error in findByStudentId: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    // Tìm đơn hàng theo Course (Khóa học)
    public List<Order> findByCourseId(Long courseId) {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding orders for courseId: " + courseId);
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.course.courseId = :courseId", Order.class);
            query.setParameter("courseId", courseId);
            List<Order> orders = query.getResultList();
            System.out.println("Found " + orders.size() + " orders for courseId: " + courseId);
            return orders;
        } catch (Exception e) {
            System.err.println("Error in findByCourseId: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
    
    public List<Order> findOrderByAccountId(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding orders for accountId: " + accountId);
            TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o WHERE o.student.accountId = :accountId", Order.class);
            query.setParameter("accountId", accountId);
            List<Order> orders = query.getResultList();
            System.out.println("Found " + orders.size() + " orders for accountId: " + accountId);
            return orders;
        } catch (Exception e) {
            System.err.println("Error in findOrderByAccountId for accountId: " + accountId + ", Error: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
 
    

}
