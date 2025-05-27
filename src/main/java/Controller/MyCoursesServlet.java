package Controller;

import Service.CourseService;
import DTO.PurchasedCoursesResultDTO;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/student/mycourses")
public class MyCoursesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MyCoursesServlet.class.getName());
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInAccount") == null) {
            
            response.sendRedirect(request.getContextPath() + "/views/LoginForStudent.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("loggedInAccount");
        Long accountId = account.getAccountId();
        

        // Gọi service để lấy danh sách khóa học đã mua
        PurchasedCoursesResultDTO result = courseService.getPurchasedCourses(accountId);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("purchasedCourses", result.getPurchasedCourses());
            request.getRequestDispatcher("/views/myCourses.jsp").forward(request, response);
        } else {
            request.setAttribute("error", result.getErrorMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}