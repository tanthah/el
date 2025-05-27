package Util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/teacher-management", "/create-teacher", "/admin-dashboard", "/teacher-dashboard"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần khởi tạo
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Thêm header chống cache vào mọi response
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);

        // Kiểm tra session
        HttpSession session = httpRequest.getSession(false);
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Cho phép truy cập các trang đăng nhập và logout mà không cần session
        if (uri.endsWith("/stafflogin") || uri.endsWith("/logout") || uri.endsWith("/Login.jsp") || uri.endsWith("/LoginForStaff.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // Nếu không có session, chuyển hướng về trang đăng nhập
        if (session == null || session.getAttribute("account") == null) {
            httpResponse.sendRedirect(contextPath + "/views/LoginForStaff.jsp");
            return;
        }

        // Nếu có session, tiếp tục xử lý request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần dọn dẹp
    }
}