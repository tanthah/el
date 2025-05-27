package Controller;

import DTO.ChapterAddResultDTO;
import DTO.CourseUpdateResultDTO;
import Model.Category;
import Model.Chapter;
import Model.Course;
import Model.Lesson;
import Model.FileMedia;
import Service.CourseService;
import dao.CategoryDAO;
import dao.ChapterDAO;
import dao.CourseDAO;
import dao.LessonDAO;
import dao.FileMediaDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebServlet("/teacher/edit-course")
public class EditCourseServlet extends HttpServlet {
    
    private CourseService courseService = new CourseService();

    private CourseDAO courseDAO = new CourseDAO();
    private CategoryDAO categoryDAO = CategoryDAO.getInstance();
    private ChapterDAO chapterDAO = new ChapterDAO();
    private LessonDAO lessonDAO = new LessonDAO();
    private FileMediaDAO fileMediaDAO = new FileMediaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long courseId = Long.parseLong(request.getParameter("courseId"));
                     
        Course course = courseService.getCourseWithDetails(courseId);
        if (course != null) {
            // Nếu tìm thấy khóa học, lấy thêm danh sách categories
            List<Category> categories = courseService.getAllCategories();

            // Đặt course và categories vào request attributes để JSP có thể truy cập
            request.setAttribute("course", course);
            request.setAttribute("categories", categories);

           
            request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
        } else {
            
            response.sendRedirect(request.getContextPath() + "/teacher/manage-courses?error=CourseNotFound&id=" + courseId);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("updateCourse".equals(action)) {
            
                // Lấy tham số
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            String title = request.getParameter("title");
            String priceParam = request.getParameter("price");
            String thumbnail = request.getParameter("thumbnail");
            Long categoryId = Long.parseLong(request.getParameter("categoryId"));
            String descriptionContent = request.getParameter("descriptionContent");

            // Gọi service để cập nhật khóa học
            CourseUpdateResultDTO result = courseService.updateCourse(courseId, title, priceParam, thumbnail, categoryId, descriptionContent);

            // Xử lý kết quả
            if (result.isSuccess()) {
                request.setAttribute("course", result.getCourse());
                request.setAttribute("categories", courseService.getAllCategories());
                request.setAttribute("message", result.getMessage());
                request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
            } else {
                request.setAttribute("error", result.getErrorMessage());
                if (result.getErrorMessage().equals("Không tìm thấy khóa học.")) {
                    response.sendRedirect("/teacher/manage-courses");
                } else {
                    request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
                }
            }
            
        } else if ("addChapter".equals(action)) {
            addChapter(request, response);
        } else if ("updateChapter".equals(action)) {
            updateChapter(request, response);
        } else if ("delChapter".equals(action)) {
            deleteChapter(request, response);
        } else if ("addLesson".equals(action)) {
            addLesson(request, response);
        } else if ("updateLesson".equals(action)) {
            updateLesson(request, response);
        } else if ("deleteLesson".equals(action)) {
            deleteLesson(request, response);
        } else if ("addFileMedia".equals(action)) {
            addFileMedia(request, response);
        } else if ("updateFileMedia".equals(action)) {
            updateFileMedia(request, response);
        } else if ("deleteFileMedia".equals(action)) {
            deleteFileMedia(request, response);
        }
    }

    private void addChapter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CourseService courseService = new CourseService();

    // Lấy tham số
    Long courseId = Long.parseLong(request.getParameter("courseId"));
    String title = request.getParameter("chapterTitle");
    Integer chapterOrder = Integer.parseInt(request.getParameter("chapterOrder"));

    // Gọi service để thêm chương
    ChapterAddResultDTO result = courseService.addChapter(courseId, title, chapterOrder);

    // Xử lý kết quả
    if (result.isSuccess()) {
        request.setAttribute("course", result.getCourse());
        request.setAttribute("categories", courseService.getAllCategories());
        request.setAttribute("message", result.getMessage());
    } else {
        request.setAttribute("course", courseService.getCourseWithDetails(courseId));
        request.setAttribute("categories", courseService.getAllCategories());
        request.setAttribute("error", result.getErrorMessage());
    }

    request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
}

   private void updateChapter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CourseService courseService = new CourseService();

    // Lấy tham số
    Long chapterId = Long.parseLong(request.getParameter("chapterId"));
    String title = request.getParameter("chapterTitle");
    Integer chapterOrder = Integer.parseInt(request.getParameter("chapterOrder"));

    // Gọi service để cập nhật chương
    ChapterAddResultDTO result = courseService.updateChapter(chapterId, title, chapterOrder);

    // Xử lý kết quả
    if (result.isSuccess()) {
        request.setAttribute("message", result.getMessage());
    } else {
        request.setAttribute("error", result.getErrorMessage());
    }

    request.setAttribute("course", result.getCourse());
    request.setAttribute("categories", courseService.getAllCategories());
    request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
}

    private void deleteChapter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseService courseService = new CourseService();

        // Lấy tham số
        Long chapterId = Long.parseLong(request.getParameter("chapterId"));
        Long courseId = Long.parseLong(request.getParameter("courseId"));

        // Gọi service để xóa chương
        ChapterAddResultDTO result = courseService.deleteChapter(chapterId, courseId);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
        } else {
            request.setAttribute("error", result.getErrorMessage());
        }

        request.setAttribute("course", result.getCourse());
        request.setAttribute("categories", courseService.getAllCategories());
        request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
    }

        private void addLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseService courseService = new CourseService();

        // Lấy tham số
        Long chapterId = Long.parseLong(request.getParameter("chapterId"));
        String lessonId = request.getParameter("lessonId");
        String title = request.getParameter("lessonTitle");
        Integer lessonIndex = Integer.parseInt(request.getParameter("lessonIndex"));
        String description = request.getParameter("lessonDescription");

        // Gọi service để thêm bài học
        ChapterAddResultDTO result = courseService.addLesson(chapterId, lessonId, title, lessonIndex, description);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
        } else {
            request.setAttribute("error", result.getErrorMessage());
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            request.setAttribute("course", courseService.getCourseWithDetails(courseId));
        }

        request.setAttribute("course", result.getCourse());
        request.setAttribute("categories", courseService.getAllCategories());
        request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
}

    private void updateLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseService courseService = new CourseService();

        // Lấy tham số
        String lessonId = request.getParameter("lessonId");
        String title = request.getParameter("lessonTitle");
        Integer lessonIndex = Integer.parseInt(request.getParameter("lessonIndex"));
        String description = request.getParameter("lessonDescription");
        Long chapterId = Long.parseLong(request.getParameter("chapterId"));

        // Gọi service để cập nhật bài học
        ChapterAddResultDTO result = courseService.updateLesson(lessonId, title, lessonIndex, description, chapterId);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
        } else {
            request.setAttribute("error", result.getErrorMessage());
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            request.setAttribute("course", courseService.getCourseWithDetails(courseId));
        }

        request.setAttribute("course", result.getCourse());
        request.setAttribute("categories", courseService.getAllCategories());
        request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
    }

        private void deleteLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseService courseService = new CourseService();

        // Lấy tham số
        String lessonId = request.getParameter("lessonId");
        Long chapterId = Long.parseLong(request.getParameter("chapterId"));

        // Gọi service để xóa bài học
        ChapterAddResultDTO result = courseService.deleteLesson(lessonId, chapterId);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
        } else {
            request.setAttribute("error", result.getErrorMessage());
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            request.setAttribute("course", courseService.getCourseWithDetails(courseId));
        }

        request.setAttribute("course", result.getCourse());
        request.setAttribute("categories", courseService.getAllCategories());
        request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
    }
   
        private void addFileMedia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CourseService courseService = new CourseService();

    // Lấy tham số
    String lessonId = request.getParameter("lessonId");
    Long chapterId = Long.parseLong(request.getParameter("chapterId"));
    String fileId = request.getParameter("fileId");
    String fileName = request.getParameter("fileName");
    String fileType = request.getParameter("fileType");
    String fileUrl = request.getParameter("fileUrl");

    // Gọi service để thêm file media
    ChapterAddResultDTO result = courseService.addFileMedia(lessonId, chapterId, fileId, fileName, fileType, fileUrl);

    // Xử lý kết quả
    if (result.isSuccess()) {
        request.setAttribute("message", result.getMessage());
    } else {
        request.setAttribute("error", result.getErrorMessage());
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        request.setAttribute("course", courseService.getCourseWithDetails(courseId));
    }

    request.setAttribute("course", result.getCourse());
    request.setAttribute("categories", courseService.getAllCategories());
    request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
}

    private void updateFileMedia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CourseService courseService = new CourseService();

    // Lấy tham số
    Long fileMediaId = Long.parseLong(request.getParameter("fileMediaId"));
    String lessonId = request.getParameter("lessonId");
    Long chapterId = Long.parseLong(request.getParameter("chapterId"));
    String fileName = request.getParameter("fileName");
    String fileType = request.getParameter("fileType");
    String fileUrl = request.getParameter("fileUrl");

    // Gọi service để cập nhật file media
    ChapterAddResultDTO result = courseService.updateFileMedia(fileMediaId, lessonId, chapterId, fileName, fileType, fileUrl);

    // Xử lý kết quả
    if (result.isSuccess()) {
        request.setAttribute("message", result.getMessage());
    } else {
        request.setAttribute("error", result.getErrorMessage());
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        request.setAttribute("course", courseService.getCourseWithDetails(courseId));
    }

    request.setAttribute("course", result.getCourse());
    request.setAttribute("categories", courseService.getAllCategories());
    request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
}

    private void deleteFileMedia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CourseService courseService = new CourseService();

    // Lấy tham số
    Long fileMediaId = Long.parseLong(request.getParameter("fileMediaId"));
    String lessonId = request.getParameter("lessonId");
    Long chapterId = Long.parseLong(request.getParameter("chapterId"));

    // Gọi service để xóa file media
    ChapterAddResultDTO result = courseService.deleteFileMedia(fileMediaId, lessonId, chapterId);

    // Xử lý kết quả
    if (result.isSuccess()) {
        request.setAttribute("message", result.getMessage());
    } else {
        request.setAttribute("error", result.getErrorMessage());
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        request.setAttribute("course", courseService.getCourseWithDetails(courseId));
    }

    request.setAttribute("course", result.getCourse());
    request.setAttribute("categories", courseService.getAllCategories());
    request.getRequestDispatcher("/views/teacher/editCourse.jsp").forward(request, response);
}
}