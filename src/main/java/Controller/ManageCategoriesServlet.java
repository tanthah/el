/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;



import dao.AccountDAO;
import dao.CategoryDAO;
import Model.Account;
import Model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-categories")
public class ManageCategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền admin
        Account account = (Account) session.getAttribute("account");
        AccountDAO accountDAO = new AccountDAO();
        if (!accountDAO.isAdmin(account.getAccountId())) {
            request.setAttribute("error", "You are not authorized to manage categories.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }

        // Không lấy danh sách Category khi vào trang lần đầu
        // Chỉ gửi đến JSP mà không đặt categories
        request.getRequestDispatcher("/views/admin/managecategories.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền admin
        Account account = (Account) session.getAttribute("account");
        AccountDAO accountDAO = new AccountDAO();
        if (!accountDAO.isAdmin(account.getAccountId())) {
            request.setAttribute("error", "You are not authorized to manage categories.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }

        // Lấy action từ form
        String action = request.getParameter("action");
        CategoryDAO categoryDAO = CategoryDAO.getInstance(); // Use Singleton

        if ("create".equals(action)) {
            // Xử lý tạo Category mới
            String name = request.getParameter("name");
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Category name is required.");
            } else {
                // Kiểm tra tên trùng lặp
                Category existingCategory = categoryDAO.findByName(name);
                if (existingCategory != null) {
                    request.setAttribute("error", "Category name already exists.");
                } else {
                    Category category = new Category();
                    category.setName(name);
                    // Description là tùy chọn, có thể để null hoặc lấy từ form nếu cần
                    // category.setDescription(request.getParameter("description"));

                    boolean saved = categoryDAO.saveCategory(category);
                    if (saved) {
                        request.setAttribute("message", "Category created successfully!");
                    } else {
                        request.setAttribute("error", "Failed to create category.");
                    }
                }
            }
        } else if ("delete".equals(action)) {
            // Xử lý xóa Category
            String categoryIdStr = request.getParameter("categoryId");
            try {
                Long categoryId = Long.parseLong(categoryIdStr);
                Category category = categoryDAO.findById(categoryId);
                if (category == null) {
                    request.setAttribute("error", "Category not found.");
                } else {
                    // Kiểm tra xem Category có Course liên quan không
                    if (!category.getCourses().isEmpty()) {
                        request.setAttribute("error", "Cannot delete category with associated courses.");
                    } else {
                        boolean deleted = categoryDAO.deleteCategory(categoryId);
                        if (deleted) {
                            request.setAttribute("message", "Category deleted successfully!");
                        } else {
                            request.setAttribute("error", "Failed to delete category.");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid category ID.");
            }
        } else if ("show".equals(action)) {
            // Xử lý hiển thị danh sách Category
            List<Category> categories = categoryDAO.findAll();
            request.setAttribute("categories", categories);
            request.setAttribute("showCategories", true); // Đặt cờ để hiển thị bảng
        } else {
            request.setAttribute("error", "Invalid action.");
        }

        // Chuyển tiếp về JSP
        request.getRequestDispatcher("/views/admin/managecategories.jsp").forward(request, response);
    }
}