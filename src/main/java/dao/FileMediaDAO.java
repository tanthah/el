package dao;

import Model.FileMedia;
import Model.Lesson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileMediaDAO {
    private static final Logger logger = Logger.getLogger(FileMediaDAO.class.getName());
    private EntityManagerFactory emf;

    public FileMediaDAO() {
        this.emf = Persistence.createEntityManagerFactory("MyWebAppPU");
    }

    public boolean saveFileMedia(FileMedia fileMedia) {
        logger.info("Saving FileMedia - File ID: " + fileMedia.getFileId() + ", Lesson ID: " + (fileMedia.getLesson() != null ? fileMedia.getLesson().getLessonId() : "null"));
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (fileMedia.getLesson() == null || fileMedia.getLesson().getLessonId() == null) {
                logger.warning("Lesson or Lesson ID is null for FileMedia: " + fileMedia.getFileId());
                throw new IllegalArgumentException("Lesson ID cannot be null");
            }

            Lesson lesson = em.find(Lesson.class, fileMedia.getLesson().getLessonId());
            if (lesson == null) {
                logger.warning("Lesson not found for ID: " + fileMedia.getLesson().getLessonId());
                throw new IllegalArgumentException("Lesson with ID " + fileMedia.getLesson().getLessonId() + " does not exist");
            }

            fileMedia.setLesson(lesson);
            if (fileMedia.getId() == null) {
                logger.info("Persisting new FileMedia: " + fileMedia.getFileId());
                em.persist(fileMedia);
            } else {
                logger.info("Merging existing FileMedia: " + fileMedia.getId());
                em.merge(fileMedia);
            }
            tx.commit();
            logger.info("FileMedia saved successfully: " + fileMedia.getFileId());
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in saveFileMedia: " + e.getMessage(), e);
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public FileMedia findById(Long id) {
        logger.info("Finding FileMedia by ID: " + id);
        EntityManager em = emf.createEntityManager();
        try {
            FileMedia fileMedia = em.find(FileMedia.class, id);
            logger.info("FileMedia found: " + (fileMedia != null ? fileMedia.getFileId() : "null"));
            return fileMedia;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding FileMedia by ID: " + id, e);
            return null;
        } finally {
            em.close();
        }
    }

    public List<FileMedia> findByLessonId(String lessonId) {
        logger.info("Finding FileMedia by Lesson ID: " + lessonId);
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FileMedia> query = em.createQuery(
                "SELECT fm FROM FileMedia fm WHERE fm.lesson.lessonId = :lessonId", FileMedia.class);
            query.setParameter("lessonId", lessonId);
            List<FileMedia> result = query.getResultList();
            logger.info("Found " + result.size() + " FileMedia for Lesson ID: " + lessonId);
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding FileMedia by Lesson ID: " + lessonId, e);
            return null;
        } finally {
            em.close();
        }
    }

    public boolean deleteFileMedia(Long id) {
        logger.info("Deleting FileMedia by ID: " + id);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            FileMedia fileMedia = em.find(FileMedia.class, id);
            if (fileMedia != null) {
                logger.info("FileMedia found for deletion: " + fileMedia.getFileId());
                em.remove(fileMedia);
                tx.commit();
                logger.info("FileMedia deleted successfully: " + id);
                return true;
            } else {
                logger.warning("FileMedia not found for ID: " + id);
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting FileMedia by ID: " + id, e);
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean existsByFileId(String fileId) {
        logger.info("Checking if File ID exists: " + fileId);
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(fm) FROM FileMedia fm WHERE fm.fileId = :fileId", Long.class);
            query.setParameter("fileId", fileId);
            boolean exists = query.getSingleResult() > 0;
            logger.info("File ID exists: " + exists);
            return exists;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking File ID: " + fileId, e);
            return false;
        } finally {
            em.close();
        }
    }
}