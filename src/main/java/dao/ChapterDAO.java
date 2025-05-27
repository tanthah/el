package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import Model.Chapter;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyWebAppPU";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    /**
     * Lưu hoặc cập nhật chapter vào cơ sở dữ liệu.
     * @param chapter Chapter cần lưu.
     * @return true nếu lưu thành công, false nếu thất bại.
     */
    public boolean saveChapter(Chapter chapter) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (chapter.getChapterId() == null) {
                System.out.println("Persisting new chapter: " + chapter.getTitle());
                em.persist(chapter);
            } else {
                System.out.println("Merging existing chapter: " + chapter.getChapterId());
                em.merge(chapter);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error in saveChapter: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

   
    public Chapter findById(Long chapterId) {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding chapter by ID: " + chapterId);
            Chapter chapter = em.find(Chapter.class, chapterId);
            if (chapter != null) {
                System.out.println("Found chapter: " + chapter.getTitle());
            } else {
                System.out.println("No chapter found for ID: " + chapterId);
            }
            return chapter;
        } catch (Exception e) {
            System.err.println("Error in findById: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    
    public List<Chapter> findAll() {
        EntityManager em = factory.createEntityManager();
        try {
            System.out.println("Finding all chapters");
            TypedQuery<Chapter> query = em.createQuery("SELECT c FROM Chapter c", Chapter.class);
            List<Chapter> chapters = query.getResultList();
            System.out.println("Found " + chapters.size() + " chapters");
            return chapters;
        } catch (Exception e) {
            System.err.println("Error in findAll: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

  
    public boolean deleteChapter(Long chapterId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Chapter chapter = em.find(Chapter.class, chapterId);
            if (chapter != null) {
                System.out.println("Deleting chapter: " + chapter.getTitle());
                em.remove(chapter);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("No chapter found to delete for ID: " + chapterId);
                em.getTransaction().commit();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error in deleteChapter: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

  
    public List<Chapter> findByCourseId(Long courseId) {
        EntityManager em = factory.createEntityManager();
        try {
           
            TypedQuery<Chapter> query = em.createQuery(
                "SELECT c FROM Chapter c WHERE c.course.courseId = :courseId ORDER BY c.chapterOrder ASC", Chapter.class);
            query.setParameter("courseId", courseId);
            List<Chapter> chapters = query.getResultList();
           
            return chapters;
        } catch (Exception e) {
            
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}