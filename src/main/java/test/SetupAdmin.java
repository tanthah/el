/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import Model.Admin;
import dao.AccountDAO;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SetupAdmin {
    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU"; // Thay bằng tên Persistence Unit của bạn
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123456";
    private static final String ADMIN_EMAIL = "monnkkey2004@gmail.com";

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            // Khởi tạo EntityManagerFactory
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = emf.createEntityManager();

            // Kiểm tra xem tài khoản admin đã tồn tại chưa
            Long count = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.username = :username", Long.class)
                          .setParameter("username", ADMIN_USERNAME)
                          .getSingleResult();
            if (count > 0) {
                System.out.println("Tài khoản admin đã tồn tại!");
                return;
            }

            // Mã hóa mật khẩu
            String hashedPassword = BCrypt.hashpw(ADMIN_PASSWORD, BCrypt.gensalt());

            // Tạo đối tượng Admin
            Admin admin = new Admin(
                ADMIN_USERNAME,    // username
                hashedPassword,    // password (đã mã hóa)
                ADMIN_EMAIL,       // email
                null,              // phone
                null,              // avatar
                true               // isActive
            );

            // Lưu vào cơ sở dữ liệu
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();

            System.out.println("Tạo tài khoản admin thành công!");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Lỗi khi tạo tài khoản admin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}