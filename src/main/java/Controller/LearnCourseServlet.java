package Controller;

import Service.CourseService;
import DTO.CourseDetailsResultDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/learn-course")
public class LearnCourseServlet extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseIdStr = request.getParameter("courseId");

        // Gọi service để lấy thông tin khóa học
        CourseDetailsResultDTO result = courseService.getCourseWithChaptersAndLessons(courseIdStr);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("course", result.getCourse());
            request.getRequestDispatcher("/views/Learnpage.jsp").forward(request, response);
        } else {
            request.setAttribute("error", result.getErrorMessage());
            response.sendRedirect(request.getContextPath() + "/student/courses");
        }
    }
}