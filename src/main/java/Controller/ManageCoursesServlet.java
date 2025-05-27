package Controller;

import dao.CourseDAO;
import dao.AccountDAO;
import Model.Account;
import Model.Teacher;
import Model.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    @WebServlet("/teacher/manage-courses")
public class ManageCoursesServlet extends HttpServlet {
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
           CourseDAO courseDAO = new CourseDAO();

           
            List<Course> courses = courseDAO.findAll();
            if (courses == null) {
                courses = new ArrayList<>();
            }
            request.setAttribute("courses", courses);
            request.setAttribute("showCourses", true);
        request.getRequestDispatcher("/views/admin/showcourses.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy Account từ session
        Account account = (Account) session.getAttribute("account");
        AccountDAO accountDAO = new AccountDAO();

        // Kiểm tra xem account có phải là Teacher không
        if (!accountDAO.isTeacher(account.getAccountId())) {
            request.setAttribute("error", "You are not authorized to manage courses.");
            request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
            return;
        }

        // Kiểm tra action
        String action = request.getParameter("action");
        CourseDAO courseDAO = new CourseDAO();
        Long teacherId = account.getAccountId(); // Sử dụng accountId làm teacherId

        if ("delete".equals(action)) {
            // Xóa khóa học
            String courseIdStr = request.getParameter("courseId");
            try {
                Long courseId = Long.parseLong(courseIdStr);
               
                boolean deleted = courseDAO.deleteCourse(courseId);
                if (deleted) {
                    request.setAttribute("message", "Course deleted successfully.");
                } else {
                    request.setAttribute("error", "Failed to delete course. Course not found.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid course ID.");
            }

            // Lấy lại danh sách khóa học để hiển thị
            List<Course> courses = courseDAO.findByTeacherId(teacherId);
            if (courses == null) {
                courses = new ArrayList<>();
            }
            request.setAttribute("courses", courses);
            request.setAttribute("showCourses", true);
        } else if ("show".equals(action)) {
            // Hiển thị danh sách khóa học
            System.out.println("Finding courses for accountId: " + teacherId);
            List<Course> courses = courseDAO.findByTeacherId(teacherId);
            if (courses == null) {
                courses = new ArrayList<>();
            }
            request.setAttribute("courses", courses);
            request.setAttribute("showCourses", true);
        }

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("/views/teacher/managecourses.jsp").forward(request, response);
    }
}