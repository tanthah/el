package Controller;

import Service.StudentAccountService;
import DTO.PasswordResetResultDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private StudentAccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new StudentAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/ForgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                         request.getContextPath();

        // Gọi service để yêu cầu đặt lại mật khẩu
        PasswordResetResultDTO result = accountService.requestPasswordReset(email, baseUrl);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("successMessage", result.getMessage());
        } else {
            request.setAttribute("errorMessage", result.getErrorMessage());
            request.setAttribute("email", email);
        }

        request.getRequestDispatcher("/views/ForgotPassword.jsp").forward(request, response);
    }
}