package dao;

import Model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import Model.Admin;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class AdminDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private AccountDAO accountDAO = new AccountDAO();

   
    // Phương thức tạo tài khoản Admin
    public Admin createAdmin(String username, String password, String email, String phone, String avatar, boolean isActive) throws Exception {
        try {
            // Kiểm tra xem username hoặc email đã tồn tại chưa
            Account existingAccount = accountDAO.findByUsernameOrEmail(username, email);
            if (existingAccount != null) {
                throw new Exception("Username or email already exists: " + username + " / " + email);
            }

            // Tạo đối tượng Admin trực tiếp
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password); // Băm mật khẩu
            admin.setEmail(email);
            admin.setPhone(phone);
            admin.setAvatar(avatar);
            admin.setActive(isActive);

            // Lưu Admin vào database (tự động lưu vào cả accounts và admins)
            boolean success = accountDAO.saveAccount(admin);
            if (!success) {
                throw new Exception("Failed to save Admin account.");
            }

            return admin;
        } catch (Exception e) {
            throw new Exception("Error creating Admin account: " + e.getMessage());
        }
    }

    public boolean findAdminById(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT a.accountId FROM Admin a WHERE a.accountId = :accountId", Long.class);
            query.setParameter("accountId", accountId);
            List<Long> result = query.getResultList();
            if (result.isEmpty()) {
                throw new Exception("No Admin found for accountId " + accountId + " using JPQL query.");
            }
            return !result.isEmpty();
        } catch (Exception e) {
            throw new Exception("Error finding Admin for accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public Admin getAdminById(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            Admin admin = em.find(Admin.class, accountId);
            if (admin == null) {
                throw new Exception("No Admin found for accountId: " + accountId);
            }
            return admin;
        } catch (Exception e) {
            throw new Exception("Error getting Admin for accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean addAdmin(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Admin admin = new Admin();
            admin.setAccountId(accountId);
            em.persist(admin);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error adding Admin for accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean removeAdmin(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Admin admin = em.find(Admin.class, accountId);
            if (admin != null) {
                em.remove(admin);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error removing Admin for accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Admin> getAllAdmins() throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Admin> result = em.createQuery("SELECT a FROM Admin a", Admin.class).getResultList();
            em.getTransaction().commit();
            if (result.isEmpty()) {
                throw new Exception("No Admins found in the database.");
            }
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error getting all Admins: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Long> getAllAdminIds() throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Long> query = em.createQuery("SELECT a.accountId FROM Admin a", Long.class);
            List<Long> result = query.getResultList();
            em.getTransaction().commit();
            if (result.isEmpty()) {
                throw new Exception("No Admin IDs found in the database.");
            }
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error getting all Admin IDs: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}