package Controller;

import dao.TeacherDAO;
import Model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher-management")
public class TeacherManagementServlet extends HttpServlet {
    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String searchName = request.getParameter("searchName");

        if ("view".equals(action)) {
            // Xem chi tiết Teacher
            String accountIdStr = request.getParameter("accountId");
            try {
                Long accountId = Long.parseLong(accountIdStr);
                Teacher teacher = teacherDAO.findById(accountId);
                if (teacher != null) {
                    System.out.println("Found teacher with accountId: " + accountId);
                    request.setAttribute("teacher", teacher);
                    request.getRequestDispatcher("/views/teacher/ViewTeacher.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Teacher with accountId " + accountId + " not found.");
                    request.getRequestDispatcher("/views/teacher/TeacherManagement.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid account ID.");
                request.getRequestDispatcher("/views/teacher/TeacherManagement.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error loading teacher: " + e.getMessage());
                e.printStackTrace();
                request.getRequestDispatcher("/views/teacher/TeacherManagement.jsp").forward(request, response);
            }
        } else {
            // Hiển thị danh sách Teacher với bộ lọc
            try {
                List<Teacher> teachers;
                // Mặc định là all nếu action không được gửi
                String filterAction = action != null ? action : "all";
                if ("active".equals(filterAction)) {
                    teachers = teacherDAO.getActiveTeachers();
                } else if ("inactive".equals(filterAction)) {
                    teachers = teacherDAO.getInactiveTeachers();
                } else {
                    teachers = teacherDAO.getAllTeachers(searchName);
                }
                if (teachers == null) {
                    teachers = List.of(); // Đảm bảo teachers không null
                }
                System.out.println("Found " + teachers.size() + " teachers with searchName: " + searchName + ", action: " + filterAction);
                request.setAttribute("teachers", teachers);
                request.setAttribute("searchName", searchName);
                request.setAttribute("action", filterAction);
                request.getRequestDispatcher("/views/teacher/ListTeacher.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error loading teachers: " + e.getMessage());
                e.printStackTrace();
                request.getRequestDispatcher("/views/teacher/TeacherManagement.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String searchName = request.getParameter("searchName");
        String filterAction = request.getParameter("filterAction");

        if ("delete".equals(action)) {
            // Xóa Teacher
            String accountIdStr = request.getParameter("accountId");
            try {
                Long accountId = Long.parseLong(accountIdStr);
                boolean success = teacherDAO.removeTeacher(accountId);
                if (success) {
                    request.setAttribute("message", "Teacher with accountId " + accountId + " deleted successfully.");
                } else {
                    request.setAttribute("errorMessage", "Teacher with accountId " + accountId + " not found.");
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Failed to delete teacher: " + e.getMessage());
            }
        } else if ("updateStatus".equals(action)) {
            // Cập nhật trạng thái isActive
            String accountIdStr = request.getParameter("accountId");
            String isActiveStr = request.getParameter("isActive");
            try {
                Long accountId = Long.parseLong(accountIdStr);
                boolean isActive = Boolean.parseBoolean(isActiveStr);
                boolean success = teacherDAO.updateTeacherStatus(accountId, isActive);
                if (success) {
                    request.setAttribute("message", "Teacher status updated successfully for accountId " + accountId + ".");
                } else {
                    request.setAttribute("errorMessage", "Teacher with accountId " + accountId + " not found.");
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Failed to update teacher status: " + e.getMessage());
            }
        }

        // Tải lại danh sách Teacher với bộ lọc
        try {
            List<Teacher> teachers;
            // Mặc định là all nếu filterAction không được gửi
            filterAction = filterAction != null ? filterAction : "all";
            if ("active".equals(filterAction)) {
                teachers = teacherDAO.getActiveTeachers();
            } else if ("inactive".equals(filterAction)) {
                teachers = teacherDAO.getInactiveTeachers();
            } else {
                teachers = teacherDAO.getAllTeachers(searchName);
            }
            if (teachers == null) {
                teachers = List.of(); // Đảm bảo teachers không null
            }
            request.setAttribute("teachers", teachers);
            request.setAttribute("searchName", searchName);
            request.setAttribute("action", filterAction);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading teachers: " + e.getMessage());
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/teacher/ListTeacher.jsp").forward(request, response);
    }
}