package Controller;

import Service.CourseService;
import DTO.JoinCourseResultDTO;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/student/join-course")
public class JoinCourseServlet extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/views/Login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        String courseIdStr = request.getParameter("courseId");

        // Gọi service để tham gia khóa học
        JoinCourseResultDTO result = courseService.joinCourse(account.getAccountId(), courseIdStr);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
        } else {
            request.setAttribute("error", result.getErrorMessage());
        }

        request.getRequestDispatcher("/views/courses.jsp").forward(request, response);
    }
}