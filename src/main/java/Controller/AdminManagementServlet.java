package Controller;

import Model.Account;
import Model.Admin;
import dao.AccountDAO;
import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminManagementServlet", urlPatterns = {"/admin-management"})
public class AdminManagementServlet extends HttpServlet {
    private AdminDAO adDAO;
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        adDAO = new AdminDAO();
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách tất cả Admin để hiển thị
            List<Admin> admins = adDAO.getAllAdmins();
            request.setAttribute("admins", admins);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading admins: " + e.getMessage());
        }
        request.getRequestDispatcher("/views/AdminManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Tạo Admin mới
            String username = request.getParameter("username") != null ? request.getParameter("username").trim() : null;
            String password = request.getParameter("password") != null ? request.getParameter("password").trim() : null;

            // Đặt giá trị mặc định cho các trường không nhập
            String email = username + "@gmail.com"; // Email mặc định dựa trên username
            String phone = ""; // Phone để trống
            String avatar = null; // Avatar không bắt buộc
            boolean isActive = true; // Mặc định active

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

            // Nếu có lỗi validation
            if (!errors.isEmpty()) {
                try {
                    List<Admin> admins = adDAO.getAllAdmins();
                    request.setAttribute("admins", admins);
                } catch (Exception e) {
                    request.setAttribute("errorMessage", "Error loading admins: " + e.getMessage());
                }
                request.setAttribute("errors", errors);
                request.setAttribute("errorMessage", "Please fix the errors below:");
                request.getRequestDispatcher("/views/AdminManagement.jsp").forward(request, response);
                return;
            }

            try {
                // Tạo tài khoản Admin mới
                Admin admin = adDAO.createAdmin(username, password, email, phone, avatar, isActive);
                request.setAttribute("message", "Admin created successfully with accountId: " + admin.getAccountId());
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Failed to create admin: " + e.getMessage());
            }
        } else if ("delete".equals(action)) {
            // Xóa Admin
            String accountIdStr = request.getParameter("accountId");
            try {
                Long accountId = Long.parseLong(accountIdStr);
                boolean success = adDAO.removeAdmin(accountId);
                if (success) {
                    request.setAttribute("message", "Admin with accountId " + accountId + " deleted successfully.");
                } else {
                    request.setAttribute("errorMessage", "Admin with accountId " + accountId + " not found.");
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Failed to delete admin: " + e.getMessage());
            }
        }

        // Tải lại danh sách Admin sau khi thực hiện hành động
        try {
            List<Admin> admins = adDAO.getAllAdmins();
            request.setAttribute("admins", admins);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading admins: " + e.getMessage());
        }
        request.getRequestDispatcher("/views/AdminManagement.jsp").forward(request, response);
    }
}