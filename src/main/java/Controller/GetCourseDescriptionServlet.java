package Controller;

import DTO.CourseDetailsDTO;
import Service.*;
import DTO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/readmore")
public class GetCourseDescriptionServlet extends HttpServlet {
    private CourseDetailsFacade courseDetailsFacade;

    @Override
    public void init() throws ServletException {
        super.init();
        // Khởi tạo Facade
        courseDetailsFacade = new CourseDetailsFacade();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy courseTitle từ form
        String courseTitle = request.getParameter("courseTitle");

        // Sử dụng Facade để lấy chi tiết khóa học
        CourseDetailsDTO courseDetails = courseDetailsFacade.getCourseDetailsByTitle(courseTitle);

        if (courseDetails != null) {
            // Đưa thông tin vào request để chuyển tiếp đến JSP
            request.setAttribute("course", courseDetails.getCourse());
            request.setAttribute("description", courseDetails.getDescription());
            request.setAttribute("applicableDays", courseDetails.getApplicableDays());
            request.setAttribute("teacher", courseDetails.getTeacher());

            // Chuyển tiếp đến description.jsp để hiển thị
            request.getRequestDispatcher("/views/description.jsp").forward(request, response);
        } else {
            // Nếu không tìm thấy khóa học, chuyển về trang lỗi
            request.setAttribute("error", "Course not found.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}