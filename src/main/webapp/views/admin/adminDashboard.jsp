<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
     <!-- Thêm meta tag để kiểm soát cache -->
    <meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background-color: #f0f2f5;
            color: #333;
            font-size: 14px;
        }

        .dashboard-container {
            display: flex;
            flex-direction: row;
            height: 100vh;
        }

        /* Sidebar */
        .sidebar {
            width: 250px;
            background-color: #ffffff;
            padding: 20px 0;
            border-right: 1px solid #e0e0e0;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100vh;
            position: fixed;
        }

        .sidebar nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar nav ul li a {
            display: flex;
            align-items: center;
            padding: 12px 20px;
            text-decoration: none;
            color: #444;
            font-size: 14px;
            transition: background-color 0.2s ease, color 0.2s ease;
        }

        .sidebar nav ul li a:hover {
            background-color: #e9ecef;
            color: #3747aD;
            font-weight: 500;
        }

        .sidebar nav ul li a .icon {
            margin-right: 12px;
            font-size: 18px;
        }

        /* Main content area */
        .main-content-wrapper {
            margin-left: 250px;
            width: 100%;
            height: 100%;
        }

        iframe {
            width: 100%;
            height: 100%;
            border: none;
        }

    </style>
</head>
<body>
    <%
        // Kiểm tra đăng nhập
        if (session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/views/LoginForStaff.jsp");
            return;
        }
    %>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <nav>
                <ul>
                    <li><a href="javascript:void(0)" onclick="loadPage('adminManagement')"><span class="icon">&#128187;</span> Tạo Admin Account</a></li>
                    <li><a href="javascript:void(0)" onclick="loadPage('employeeManagement')"><span class="icon">&#128221;</span> Quản lí nhân viên</a></li>
                    <li><a href="javascript:void(0)" onclick="loadPage('manageCourses')"><span class="icon">&#128218;</span> Quản lí khóa học</a></li>

                    <li><a href="<%= request.getContextPath() %>/logout"><span class="icon">&#128274;</span> Logout</a></li>
                </ul>
            </nav>
        </aside>

        <!-- Main content area (iframe) -->
        <div class="main-content-wrapper">
            <iframe id="contentFrame" src="about:blank" name="contentFrame"></iframe>
        </div>
    </div>

    <script>
        // Function to load pages into the iframe
        function loadPage(page) {
            var frame = document.getElementById('contentFrame');
            switch(page) {
                case 'adminManagement':
                    frame.src = "<%= request.getContextPath() %>/views/admin/AdminManagement.jsp";
                    break;
                case 'employeeManagement':
                    frame.src = "<%= request.getContextPath() %>/views/teacher/TeacherManagement.jsp";
                    break;
                case 'manageCourses':
    frame.src = "<%= request.getContextPath() %>/views/admin/managecourses.jsp";
    break;

                default:
                    frame.src = "about:blank";
            }
        }
    </script>
</body>
</html>
