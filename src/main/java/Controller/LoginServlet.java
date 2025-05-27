package Controller;

import Service.StudentAccountService;
import Model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private StudentAccountService studentAccountService;

    @Override
    public void init() throws ServletException {
        studentAccountService = new StudentAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validation
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng cung cấp tên người dùng và mật khẩu.");
            request.getRequestDispatcher("/views/Login.jsp").forward(request, response);
            return;
        }

        // Gọi service để đăng nhập
        Account[] accountHolder = new Account[1]; // Mảng để lưu tài khoản
        boolean success = studentAccountService.login(username, password, accountHolder);

        // Xử lý kết quả
        if (success) {
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInAccount", accountHolder[0]);
            response.sendRedirect(request.getContextPath() + "/views/StudentDashboard.jsp");
        } else {
            request.setAttribute("errorMessage", "Tên người dùng, mật khẩu không chính xác hoặc tài khoản không hợp lệ.");
            request.getRequestDispatcher("/views/Login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}