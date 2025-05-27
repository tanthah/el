package Controller;

import Service.CourseService;
import DTO.CreateCourseResultDTO;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/teacher/create-course")
public class CreateCourseServlet extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Account account = (Account) session.getAttribute("account");

        // Gọi service để lấy danh sách danh mục
        CreateCourseResultDTO result = courseService.getAllCategories(account.getAccountId());

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("categories", result.getCategories());
            request.getRequestDispatcher("/views/teacher/createcourse.jsp").forward(request, response);
        } else {
            request.setAttribute("error", result.getErrorMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        String title = request.getParameter("title");
        String priceStr = request.getParameter("price");
        String thumbnail = request.getParameter("thumbnail");
        String categoryIdStr = request.getParameter("categoryId");
        String descriptionContent = request.getParameter("descriptionContent");
        String[] applicableDaysArray = request.getParameterValues("applicableDays");

        // Gọi service để tạo khóa học
        CreateCourseResultDTO result = courseService.createCourse(
                account.getAccountId(), title, priceStr, thumbnail, categoryIdStr, descriptionContent, applicableDaysArray);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
            response.sendRedirect(request.getContextPath() + "/teacher/manage-courses");
        } else {
            request.setAttribute("error", result.getErrorMessage());
            request.setAttribute("categories", result.getCategories());
            request.getRequestDispatcher("/views/teacher/createcourse.jsp").forward(request, response);
        }
    }
}