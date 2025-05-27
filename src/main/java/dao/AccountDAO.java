package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import Model.Account; // Import lớp Account của bạn
import Model.Admin;
import Model.Student;
import Model.Teacher;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class AccountDAO {

    // Tên persistence unit được định nghĩa trong META-INF/persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU"; // Thay thế bằng tên persistence unit của bạn
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public Account findByUsernameAndPassword(String username, String password) {
        EntityManager em = factory.createEntityManager();
        Account account = null;
        try {
            // **Lưu ý:** Trong thực tế, so sánh mật khẩu đã băm!
            TypedQuery<Account> query = em.createQuery(
                "SELECT a FROM Account a WHERE a.username = :username AND a.password = :password AND a.isActive = true", Account.class);
            query.setParameter("username", username);
            query.setParameter("password", password); // Cần băm mật khẩu thực tế
            account = query.getSingleResult();
        } catch (NoResultException e) {
            // Không tìm thấy tài khoản phù hợp
            account = null;
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            // Xử lý các lỗi khác khi truy vấn
        } finally {
            em.close();
        }
        return account;
    }

    public Account findByUsernameOrEmail(String username, String email) {
         EntityManager em = factory.createEntityManager();
        Account account = null;
        try {
            TypedQuery<Account> query = em.createQuery(
                "SELECT a FROM Account a WHERE a.username = :username OR a.email = :email", Account.class);
            query.setParameter("username", username);
            query.setParameter("email", email);
             // Sử dụng getResultList() và kiểm tra kích thước để tránh NoResultException
            // hoặc sử dụng getSingleResult() và bắt NoResultException
            account = query.getSingleResult();

        } catch (NoResultException e) {
            // Không tìm thấy tài khoản trùng lặp
             account = null;
        }
        catch (Exception e) {
             e.printStackTrace(); // Log lỗi
             // Xử lý các lỗi khác
        }
        finally {
             em.close();
        }
        return account;
    }


    public boolean saveAccount(Account account) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            // **Lưu ý:** Băm mật khẩu trước khi lưu!
            em.persist(account); // JPA sẽ biết đây là Student thông qua Inheritance/PrimaryKeyJoinColumn
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); // Log lỗi, ví dụ: unique constraint violation
            return false;
        } finally {
            em.close();
        }
    }
    
    public Account findById(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Account.class, accountId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    public boolean updateAccount(Account account) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(account);
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

        public boolean updatePasswordByEmail(String email, String newPlainPassword) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery<Account> query = em.createQuery(
                "SELECT a FROM Account a WHERE a.email = :email AND a.isActive = true", Account.class);
            query.setParameter("email", email);

            Account accountToUpdate;
            try {
                accountToUpdate = query.getSingleResult();
            } catch (NoResultException e) {
                accountToUpdate = null; // Không tìm thấy tài khoản phù hợp
            }

            if (accountToUpdate != null) {
                // GÁN TRỰC TIẾP MẬT KHẨU MỚI (KHÔNG BĂM)
                // LƯU Ý: Đây là một lỗ hổng bảo mật nghiêm trọng.
                accountToUpdate.setPassword(newPlainPassword);

                em.merge(accountToUpdate);
                em.getTransaction().commit();
                return true; // Cập nhật thành công
            } else {
                // Không tìm thấy tài khoản với email này hoặc tài khoản không hoạt động
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                return false; // Cập nhật thất bại
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); // Ghi lại thông tin lỗi
            return false; // Cập nhật thất bại do lỗi
        } finally {
            em.close();
        }
    }
        
    public boolean isTeacher(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            Teacher teacher = em.find(Teacher.class, accountId);
            return teacher != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean isAdmin(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            Admin admin = em.find(Admin.class, accountId);
            return admin != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    // Phương thức mới để tạo tài khoản
    public Account createAccount(String username, String password, String email, String phone, String avatar, boolean isActive) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Kiểm tra xem username hoặc email đã tồn tại chưa
            Account existingAccount = findByUsernameOrEmail(username, email);
            if (existingAccount != null) {
                throw new Exception("Username or email already exists: " + username + " / " + email);
            }

            // Tạo tài khoản mới
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); // Băm mật khẩu bằng BCrypt
            account.setEmail(email);
            account.setPhone(phone);
            account.setAvatar(avatar);
            account.setActive(isActive);

            em.persist(account);
            em.getTransaction().commit();
            return account;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error creating account: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    // Thêm phương thức để lấy Teacher từ Account
    public Teacher getTeacherByAccountId(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Teacher.class, accountId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
     public Student getStudentByAccountId(Long accountId) {
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
    // 
}