package Controller;

import Service.StaffAccountService;
import DTO.LoginResultDTO;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/stafflogin")
public class StaffLoginServlet extends HttpServlet {
    
    private StaffAccountService staffAccountService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo StaffAccountService
        staffAccountService = new StaffAccountService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đảm bảo encoding của request và response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Gọi service để đăng nhập
        LoginResultDTO result = staffAccountService.login(username, password);

        // Xử lý kết quả
        if (result.isSuccess()) {
            // Lưu tài khoản vào session
            HttpSession session = request.getSession();
            session.setAttribute("account", result.getAccount());

            // Điều hướng dựa trên vai trò
            String role = result.getRole();
            if ("teacher".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/views/teacher/teacherDashboard.jsp");
            } else if ("admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/views/admin/adminDashboard.jsp");
            }
        } else {
            request.setAttribute("error", result.getErrorMessage());
            request.getRequestDispatcher("/views/ERROR2.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu truy cập bằng GET, chuyển hướng đến trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/views/LoginForStaff.jsp");
    }
}