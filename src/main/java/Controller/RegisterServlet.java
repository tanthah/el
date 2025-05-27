package Controller;

import Service.StudentAccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private StudentAccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new StudentAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy và trim dữ liệu từ form
        String username = request.getParameter("username") != null ? request.getParameter("username").trim() : null;
        String password = request.getParameter("password") != null ? request.getParameter("password").trim() : null;
        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : null;
        String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";

        // Map để lưu lỗi validation
        Map<String, String> errors = new HashMap<>();

        // Validation
        if (username == null || username.isEmpty()) {
            errors.put("username", "Tên đăng nhập là bắt buộc");
        } else if (username.length() < 3) {
            errors.put("username", "Tên đăng nhập phải có ít nhất 3 ký tự");
        }

        if (password == null || password.isEmpty()) {
            errors.put("password", "Mật khẩu là bắt buộc");
        } else if (password.length() < 6) {
            errors.put("password", "Mật khẩu phải có ít nhất 6 ký tự");
        }

        if (email == null || email.isEmpty()) {
            errors.put("email", "Email là bắt buộc");
        } else if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            errors.put("email", "Email không đúng định dạng");
        }

        if (!phone.isEmpty() && !phone.matches("^\\d{10,11}$")) {
            errors.put("phone", "Số điện thoại phải có 10-11 chữ số");
        }

        // Nếu có lỗi validation, gửi lại form
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("errorMessage", "Vui lòng sửa các lỗi sau:");
            request.getRequestDispatcher("/views/Register.jsp").forward(request, response);
            return;
        }

        // Gọi service để đăng ký
        boolean success = accountService.registerStudent(username, password, email, phone);

        // Xử lý kết quả
        if (success) {
            response.sendRedirect(request.getContextPath() + "/views/Login.jsp");
        } else {
            request.setAttribute("errorMessage", "Đăng ký thất bại. Tên đăng nhập hoặc email có thể đã tồn tại.");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/views/Register.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}