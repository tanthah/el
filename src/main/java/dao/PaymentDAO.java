package dao;

import jakarta.persistence.*;
import Model.Payment;
import Model.Order;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // Lưu thanh toán
    public boolean savePayment(Payment payment) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (payment.getPaymentId() == null) {
                
                em.persist(payment);
            } else {
              
                em.merge(payment);
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

    // Tìm thanh toán theo ID
    public Payment findById(Long paymentId) {
        EntityManager em = factory.createEntityManager();
        try {
            
            Payment payment = em.find(Payment.class, paymentId);
            if (payment != null) {
                System.out.println("Found payment with ID: " + paymentId);
            } else {
                System.out.println("No payment found for ID: " + paymentId);
            }
            return payment;
        } catch (Exception e) {
            System.err.println("Error in findById: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Tìm tất cả thanh toán
    public List<Payment> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding all payments");
            TypedQuery<Payment> query = em.createQuery("SELECT p FROM Payment p", Payment.class);
            List<Payment> payments = query.getResultList();
            System.out.println("Found " + payments.size() + " payments");
            return payments;
        } catch (Exception e) {
            System.err.println("Error in findAll: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    // Xóa thanh toán
    public boolean deletePayment(Long paymentId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Payment payment = em.find(Payment.class, paymentId);
            if (payment != null) {
                System.out.println("Deleting payment with ID: " + paymentId);
                em.remove(payment);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("No payment found to delete for ID: " + paymentId);
                em.getTransaction().commit();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error in deletePayment: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Tìm thanh toán theo Order (Đơn hàng)
    public Payment findByOrderId(Long orderId) {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding payment for orderId: " + orderId);
            TypedQuery<Payment> query = em.createQuery("SELECT p FROM Payment p WHERE p.order.orderId = :orderId", Payment.class);
            query.setParameter("orderId", orderId);
            Payment payment = query.getSingleResult();
            System.out.println("Found payment for orderId: " + orderId);
            return payment;
        } catch (Exception e) {
            System.err.println("Error in findByOrderId: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
