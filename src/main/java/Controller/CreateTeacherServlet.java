package Controller;

import dao.TeacherDAO;
import Model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/create-teacher")
public class CreateTeacherServlet extends HttpServlet {
    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị form tạo Teacher (có thể tạo file JSP riêng nếu cần)
        request.getRequestDispatcher("/views/teacher/CreateTeacher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String avatar = "avata";
        String name = request.getParameter("name");
        String bio = request.getParameter("bio");
        String qualifications = request.getParameter("qualifications");
        boolean isActive = false;

        // Map để lưu lỗi validation
        Map<String, String> errors = new HashMap<>();

        // Validation cơ bản
        if (username == null || username.isEmpty()) {
            errors.put("username", "Username is required.");
        }
        if (password == null || password.isEmpty()) {
            errors.put("password", "Password is required.");
        }
        if (email == null || email.isEmpty()) {
            errors.put("email", "Email is required.");
        }
        if (name == null || name.isEmpty()) {
            errors.put("name", "Name is required.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("errorMessage", "Please fix the errors below.");
            request.getRequestDispatcher("/views/teacher/CreateTeacher.jsp").forward(request, response);
            return;
        }

        try {
            // Tạo Teacher mới
            Teacher teacher = teacherDAO.createTeacher(username, password, email, phone, avatar, isActive, name, bio, qualifications);
            request.setAttribute("message", "Teacher created successfully with accountId: " + teacher.getAccountId());
            request.getRequestDispatcher("/Done.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to create teacher: " + e.getMessage());
            request.getRequestDispatcher("/views/ERROR2.jsp").forward(request, response);
        }
    }
}