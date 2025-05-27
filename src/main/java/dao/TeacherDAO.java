package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import Model.Account;
import Model.Course;
import Model.Teacher;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private AccountDAO accountDAO = new AccountDAO();

    // Phương thức tạo tài khoản Teacher
    public Teacher createTeacher(String username, String password, String email, String phone, String avatar, 
                                boolean isActive, String name, String bio, String qualifications) throws Exception {
        try {
            // Kiểm tra xem username hoặc email đã tồn tại chưa
            Account existingAccount = accountDAO.findByUsernameOrEmail(username, email);
            if (existingAccount != null) {
                throw new Exception("Username or email already exists: " + username + " / " + email);
            }

            // Tạo đối tượng Teacher trực tiếp
            Teacher teacher = new Teacher();
            teacher.setUsername(username);
            teacher.setPassword(password);
            teacher.setEmail(email);
            teacher.setPhone(phone);
            teacher.setAvatar(avatar);
            teacher.setActive(isActive);
            teacher.setName(name);
            teacher.setBio(bio);
            teacher.setQualifications(qualifications);

            // Lưu Teacher vào database (tự động lưu vào cả accounts và teachers)
            boolean success = accountDAO.saveAccount(teacher);
            if (!success) {
                throw new Exception("Failed to save Teacher account.");
            }

            return teacher;
        } catch (Exception e) {
            throw new Exception("Error creating Teacher account: " + e.getMessage());
        }
    }

    // Phương thức kiểm tra xem tài khoản có phải Teacher không
    public boolean isTeacher(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            Teacher teacher = em.find(Teacher.class, accountId);
            return teacher != null;
        } catch (Exception e) {
            throw new Exception("Error checking if accountId " + accountId + " is Teacher: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Phương thức lấy thông tin Teacher theo accountId
    public Teacher getTeacherById(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            Teacher teacher = em.find(Teacher.class, accountId);
            if (teacher == null) {
                throw new Exception("No Teacher found for accountId: " + accountId);
            }
            return teacher;
        } catch (Exception e) {
            throw new Exception("Error getting Teacher for accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    
     public List<Teacher> getActiveTeachers() {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding active teachers");
            TypedQuery<Teacher> query = em.createQuery(
                "SELECT t FROM Teacher t WHERE t.isActive = :active", Teacher.class);
            query.setParameter("active", true);
            List<Teacher> teachers = query.getResultList();
            System.out.println("Found " + teachers.size() + " active teachers");
            if (teachers.isEmpty()) {
                System.out.println("No active teachers found. Check if 'is_active' = true exists in 'accounts' table.");
            } else {
                for (Teacher t : teachers) {
                    System.out.println("Active teacher: ID=" + t.getAccountId() + ", Name=" + t.getName() + ", isActive=" + t.isActive());
                }
            }
            return teachers;
        } catch (Exception e) {
            System.err.println("Error in getActiveTeachers: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<Teacher> getInactiveTeachers() {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding inactive teachers");
            TypedQuery<Teacher> query = em.createQuery(
                "SELECT t FROM Teacher t WHERE t.isActive = :active", Teacher.class);
            query.setParameter("active", false);
            List<Teacher> teachers = query.getResultList();
            System.out.println("Found " + teachers.size() + " inactive teachers");
            return teachers;
        } catch (Exception e) {
            System.err.println("Error in getInactiveTeachers: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
public List<Teacher> getAllTeachers(String searchName) {
        EntityManager em = factory.createEntityManager();
        try {
            
            String jpql = "SELECT t FROM Teacher t";
            if (searchName != null && !searchName.trim().isEmpty()) {
                jpql += " WHERE LOWER(t.name) LIKE :searchName";
            }
            TypedQuery<Teacher> query = em.createQuery(jpql, Teacher.class);
            if (searchName != null && !searchName.trim().isEmpty()) {
                query.setParameter("searchName", "%" + searchName.trim().toLowerCase() + "%");
            }
            List<Teacher> teachers = query.getResultList();
            System.out.println("Found " + teachers.size() + " teachers");
            return teachers;
        } catch (Exception e) {
            System.err.println("Error in getAllTeachers: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    // Phương thức lấy danh sách tất cả Teacher
    public List<Teacher> getAllTeachers() throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Teacher> result = em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
            em.getTransaction().commit();
            return result; // Không ném ngoại lệ nếu danh sách rỗng
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error getting all Teachers: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Phương thức xóa Teacher
    public boolean removeTeacher(Long accountId) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Teacher teacher = em.find(Teacher.class, accountId);
            if (teacher == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(teacher); // Xóa Teacher, JPA sẽ tự động xóa bản ghi trong cả accounts và teachers
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error removing Teacher with accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
     // Phương thức mới: tìm teacher theo name và active status
    public List<Teacher> findTeachersByNameAndStatus(String searchName, Boolean active) {
        EntityManager em = factory.createEntityManager();
        try {
           
            StringBuilder jpql = new StringBuilder("SELECT t FROM Teacher t WHERE 1=1");
            if (searchName != null && !searchName.trim().isEmpty()) {
                jpql.append(" AND LOWER(t.name) LIKE :searchName");
            }
            if (active != null) {
                jpql.append(" AND t.active = :active");
            }
            TypedQuery<Teacher> query = em.createQuery(jpql.toString(), Teacher.class);
            if (searchName != null && !searchName.trim().isEmpty()) {
                query.setParameter("searchName", "%" + searchName.trim().toLowerCase() + "%");
            }
            if (active != null) {
                query.setParameter("active", active);
            }
            List<Teacher> teachers = query.getResultList();
            System.out.println("Found " + teachers.size() + " teachers");
            return teachers;
        } catch (Exception e) {
            System.err.println("Error in findTeachersByNameAndStatus: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    // Các phương thức khác (findById, removeTeacher, updateTeacherStatus) giữ nguyên
    public Teacher findById(Long accountId) {
        EntityManager em = factory.createEntityManager();
        try {
           
            Teacher teacher = em.find(Teacher.class, accountId);
            if (teacher != null) {
                System.out.println("Found teacher: " + teacher.getName());
            } else {
                System.out.println("No teacher found for accountId: " + accountId);
            }
            return teacher;
        } catch (Exception e) {
            System.err.println("Error in findById: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    // Phương thức cập nhật trạng thái isActive của Teacher
    public boolean updateTeacherStatus(Long accountId, boolean isActive) throws Exception {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Teacher teacher = em.find(Teacher.class, accountId);
            if (teacher == null) {
                em.getTransaction().rollback();
                return false;
            }
            teacher.setActive(isActive);
            em.merge(teacher);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error updating status for Teacher with accountId " + accountId + ": " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    // Phương thức tìm Teacher dựa trên courseId
    public Teacher findTeacherByCourseId(Long courseId) {
        EntityManager em = factory.createEntityManager();
        try {
            // Truy vấn khóa học theo courseId
            Course course = em.find(Course.class, courseId);
            if (course != null) {
                // Lấy teacherId từ Course và tìm Teacher
                Long teacherId = course.getTeacher().getAccountId();
                // Truy vấn Teacher dựa trên teacherId
                Teacher teacher = em.find(Teacher.class, teacherId);
                return teacher;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    //zz
}