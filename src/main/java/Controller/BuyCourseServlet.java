package Controller;

import Service.CourseService;
import DTO.OrderPaymentResultDTO;
import Model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/buycourse")
public class BuyCourseServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInAccount") == null) {
            System.out.println("doGet: Không có session hoặc chưa đăng nhập");
            response.sendRedirect(request.getContextPath() + "/views/Login.jsp");
            return;
        }

        Student loggedInStudent = (Student) session.getAttribute("loggedInAccount");
        String courseIdStr = request.getParameter("courseId");

        // Gọi service để tạo đơn hàng và thanh toán
        OrderPaymentResultDTO result = courseService.createOrderAndPayment(loggedInStudent.getAccountId(), courseIdStr);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("order", result.getOrder());
            request.setAttribute("payment", result.getPayment());
            request.setAttribute("course", result.getCourse());
            request.getRequestDispatcher("/views/OrderConfirmation.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", result.getErrorMessage());
            request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long paymentId = Long.parseLong(request.getParameter("paymentId"));

            // Gọi service để cập nhật trạng thái thanh toán
            OrderPaymentResultDTO result = courseService.updatePaymentStatus(paymentId);

            // Xử lý kết quả
            if (result.isSuccess()) {
                response.sendRedirect(request.getContextPath() + "/views/index.jsp");
            } else {
                request.setAttribute("errorMessage", result.getErrorMessage());
                request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Định dạng ID thanh toán không hợp lệ: " + request.getParameter("paymentId"));
            request.getRequestDispatcher("/views/ErrorPage.jsp").forward(request, response);
        }
    }
}