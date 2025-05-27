package Controller;

import Service.StudentAccountService;
import DTO.PasswordResetResultDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    private StudentAccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new StudentAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        // Gọi service để xác thực token
        PasswordResetResultDTO result = accountService.validateResetToken(token);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("tokenValid", result.isTokenValid());
        } else {
            request.setAttribute("errorMessage", result.getErrorMessage());
        }

        request.getRequestDispatcher("/views/ResetPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Gọi service để đặt lại mật khẩu
        PasswordResetResultDTO result = accountService.resetPassword(token, password, confirmPassword);

        // Xử lý kết quả
        if (result.isSuccess()) {
            request.setAttribute("successMessage", result.getMessage());
        } else {
            request.setAttribute("errorMessage", result.getErrorMessage());
            if (result.isTokenValid()) {
                request.setAttribute("tokenValid", true);
            }
        }

        request.getRequestDispatcher("/views/ResetPassword.jsp").forward(request, response);
    }
}