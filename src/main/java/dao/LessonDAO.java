package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import Model.Lesson;
import Model.Chapter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LessonDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static final Logger logger = Logger.getLogger(LessonDAO.class.getName());

    /**
     * Lưu hoặc cập nhật lesson vào cơ sở dữ liệu.
     * @param lesson Lesson cần lưu.
     * @return true nếu lưu thành công, false nếu thất bại.
     */
    public boolean saveLesson(Lesson lesson) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (lesson.getLessonId() == null || lesson.getLessonId().isEmpty()) {
                throw new IllegalArgumentException("Lesson ID must be provided");
            }
            if (lesson.getChapter() == null || lesson.getChapter().getChapterId() == null) {
                throw new IllegalArgumentException("Chapter must be provided");
            }
            // Kiểm tra Chapter tồn tại
            Chapter chapter = em.find(Chapter.class, lesson.getChapter().getChapterId());
            if (chapter == null) {
                throw new IllegalArgumentException("Chapter with ID " + lesson.getChapter().getChapterId() + " does not exist");
            }
            lesson.setChapter(chapter); // Gán Chapter thực sự từ DB
            if (em.find(Lesson.class, lesson.getLessonId()) == null) {
                em.persist(lesson);
            } else {
                em.merge(lesson);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.severe("Error in saveLesson: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Tìm lesson theo ID.
     * @param lessonId ID của lesson cần tìm.
     * @return Lesson nếu tìm thấy, null nếu không tìm thấy.
     */
    public Lesson findById(String lessonId) {
        EntityManager em = factory.createEntityManager();
        try {
            Lesson lesson = em.find(Lesson.class, lessonId);
            return lesson;
        } catch (Exception e) {
            logger.severe("Error in findById: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Lấy tất cả lesson trong cơ sở dữ liệu.
     * @return Danh sách tất cả lesson.
     */
    public List<Lesson> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Lesson> query = em.createQuery("SELECT l FROM Lesson l", Lesson.class);
            List<Lesson> lessons = query.getResultList();
            return lessons;
        } catch (Exception e) {
            logger.severe("Error in findAll: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    /**
     * Xóa lesson theo ID.
     * @param lessonId ID của lesson cần xóa.
     * @return true nếu xóa thành công, false nếu thất bại hoặc không tìm thấy.
     */
    public boolean deleteLesson(String lessonId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Lesson lesson = em.find(Lesson.class, lessonId);
            if (lesson != null) {
                em.remove(lesson);
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
            logger.severe("Error in deleteLesson: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Tìm tất cả lesson thuộc một chapter.
     * @param chapterId ID của chapter.
     * @return Danh sách lesson thuộc chapter, sắp xếp theo lessonIndex.
     */
    public List<Lesson> findByChapterId(Long chapterId) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Lesson> query = em.createQuery(
                "SELECT l FROM Lesson l WHERE l.chapter.chapterId = :chapterId ORDER BY l.lessonIndex ASC", Lesson.class);
            query.setParameter("chapterId", chapterId);
            List<Lesson> lessons = query.getResultList();
            return lessons;
        } catch (Exception e) {
            logger.severe("Error in findByChapterId: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}