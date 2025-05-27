package dao;

import Model.PasswordResetToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class PasswordResetTokenDAO {
    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public void saveToken(String email, String token, LocalDateTime expiryDate) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            // Xóa token cũ nếu tồn tại
            em.createQuery("DELETE FROM PasswordResetToken t WHERE t.email = :email")
              .setParameter("email", email)
              .executeUpdate();
            // Lưu token mới
            PasswordResetToken prt = new PasswordResetToken(email, token, expiryDate);
            em.persist(prt);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public PasswordResetToken findToken(String token) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<PasswordResetToken> query = em.createQuery(
                "SELECT t FROM PasswordResetToken t WHERE t.token = :token", PasswordResetToken.class);
            query.setParameter("token", token);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void deleteToken(String token) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM PasswordResetToken t WHERE t.token = :token")
              .setParameter("token", token)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    
     public List<PasswordResetToken> findAllToken() {
        EntityManager em = factory.createEntityManager();
        try {
            // Tạo truy vấn JPQL để chọn tất cả các đối tượng PasswordResetToken
            TypedQuery<PasswordResetToken> query = em.createQuery(
                "SELECT t FROM PasswordResetToken t", PasswordResetToken.class);
            // Thực thi truy vấn và trả về danh sách kết quả
            return query.getResultList();
        } catch (Exception e) {
            // Xử lý lỗi nếu có, ví dụ in ra console
            e.printStackTrace();
            // Trả về danh sách rỗng trong trường hợp có lỗi
            return java.util.Collections.emptyList();
        } finally {
            // Luôn đóng EntityManager
            em.close();
        }
    }
    
}