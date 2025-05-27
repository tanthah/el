package Controller;

import Service.CourseService;
import DTO.CourseSearchResultDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BrowseCourseServlet", urlPatterns = {"/BrowseCourseServlet"})
public class BrowseCourseServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        super.init();
        courseService = new CourseService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");
        String sortOrder = request.getParameter("sortOrder");

        // Gọi service để tìm kiếm khóa học
        CourseSearchResultDTO result = courseService.searchCoursesByTitleSortedByPrice(searchKeyword, sortOrder);

        // Gửi kết quả đến JSP
        request.setAttribute("courses", result.getCourses());
        request.setAttribute("showCourses", result.isShowCourses());
        if (!result.isSuccess()) {
            request.setAttribute("errorMessage", result.getErrorMessage());
        }
        request.getRequestDispatcher("/views/courses.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gọi service để lấy tất cả khóa học
        CourseSearchResultDTO result = courseService.getAllCourses();

        // Gửi kết quả đến JSP
        request.setAttribute("courses", result.getCourses());
        request.setAttribute("showCourses", result.isShowCourses());
        if (!result.isSuccess()) {
            request.setAttribute("errorMessage", result.getErrorMessage());
        }
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }
}