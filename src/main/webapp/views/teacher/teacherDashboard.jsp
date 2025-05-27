<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teacher Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        :root {
            --header-height: 60px;
            --navi-width: 220px;
        }

        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            display: flex;
            height: 100vh;
            background-color: #f4f7fa;
        }

        /* Header style */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 30px; /* Điều chỉnh padding, chiều cao sẽ kiểm soát không gian dọc */
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            width: 100%;
            z-index: 1000;
            box-sizing: border-box;
            height: var(--header-height); /* Sử dụng biến CSS cho chiều cao */
        }

        .header .logo {
            font-size: 24px;
            font-weight: 700;
            color: #00bcd4;
            display: flex;
            align-items: center;
        }

        .header .student-info {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-shrink: 0;
            white-space: nowrap;
        }

        .header .student-info i {
            font-size: 20px;
        }

        .header .student-info span {
            font-size: 16px;
        }

        /* Side navigation (Navi) */
        .navi {
            position: fixed;
            top: var(--header-height); /* Sử dụng biến CSS */
            left: 0;
            width: var(--navi-width); /* Sử dụng biến CSS */
            background-color: #f5f5f5;
            padding: 20px 0;
            height: calc(100vh - var(--header-height)); /* Chiều cao còn lại */
            box-shadow: 2px 0px 5px rgba(0, 0, 0, 0.1);
            overflow-y: auto;
            box-sizing: border-box;
        }

        .navi .nav-item a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 20px;
            cursor: pointer;
            font-weight: 500;
            transition: background-color 0.3s ease, color 0.3s ease;
            text-decoration: none;
            color: #333;
            font-size: 15px;
        }

        .navi .nav-item a:hover {
            background-color: #00bcd4;
            color: white;
        }

        .navi .nav-item a i {
            font-size: 18px;
            width: 20px;
            text-align: center;
        }

        .navi .logout a { /* Đã gộp vào .nav-item a, nhưng có thể tùy chỉnh thêm nếu cần */
            /* Các style cụ thể cho logout nếu muốn khác biệt với nav-item thường */
        }
        .navi .logout a:hover { /* Style hover riêng cho logout */
             background-color: #f44336 !important; /* Quan trọng để ghi đè nếu có style chung */
             color: white !important;
        }


        /* Main content area */
        .main-content-wrapper {
            margin-left: var(--navi-width); /* Sử dụng biến CSS */
            padding-top: var(--header-height); /* Sử dụng biến CSS */
            flex-grow: 1;
            height: calc(100vh - var(--header-height)); /* Chiều cao còn lại */
            overflow: hidden;
            box-sizing: border-box;
        }

        #contentFrame {
            width: 100%;
            height: 100%;
            border: none;
            background-color: #ffffff; /* Thay đổi màu nền iframe cho phù hợp hơn */
        }

    </style>
</head>
<body>
    <%
        if (session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/views/LoginForStaff.jsp");
            return;
        }
    %>
    <div class="header">
        <div class="logo">
            <i class="fa fa-book" style="margin-right: 8px;"></i> eLEARNING
        </div>
        <div class="student-info">
            <i class="fa fa-user"></i>
            <span>Teacher</span>
        </div>
    </div>

    <div class="navi">
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadContent('home')">
                <i class="fa fa-home"></i>
                <span class="text">Trang chủ</span>
            </a>
        </div>
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadContent('myCourses')">
                <i class="fa fa-book"></i>
                <span class="text">Khóa học của tôi</span>
            </a>
        </div>
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadContent('statistics')">
                <i class="fa fa-chart-bar"></i>
                <span class="text">Thống kê</span>
            </a>
        </div>
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadContent('notifications')">
                <i class="fa fa-bell"></i>
                <span class="text">Thông báo</span>
            </a>
        </div>
        <div class="nav-item logout"> <a href="<%= request.getContextPath() %>/logout">
                <i class="fa fa-sign-out-alt"></i>
                <span class="text">Đăng xuất</span>
            </a>
        </div>
    </div>

    <div class="main-content-wrapper">
        <iframe id="contentFrame" name="contentFrame" src="about:blank"></iframe>
    </div>
    
    <c:if test="${not empty error}">
        <p style="color: red; position: fixed; bottom: 10px; left: calc(var(--navi-width) + 20px); background-color: white; padding: 5px 10px; border-radius: 4px; box-shadow: 0 0 5px rgba(0,0,0,0.2);">${error}</p>
    </c:if>

    <script>
        function loadContent(pageKey) {
            var frame = document.getElementById('contentFrame');
            var contextPath = "<%= request.getContextPath() %>";
            var targetUrl = "about:blank"; 

            switch(pageKey) {
                case 'home':
                    targetUrl = contextPath + "/views/index.jsp"; // Điều chỉnh nếu cần
                    break;
                case 'myCourses':
                    targetUrl = contextPath + "/views/teacher/managecourses.jsp";
                    break;
                case 'statistics':
                    targetUrl = contextPath + "/views/teacher/statistics.jsp"; // THAY THẾ URL
                    break;
                case 'notifications':
                    targetUrl = contextPath + "/views/teacher/notifications.jsp"; // THAY THẾ URL
                    break;
                default:
                    // Trang chào mừng hoặc trang mặc định nào đó
                    // targetUrl = contextPath + "/views/teacher/welcomeDashboard.jsp"; 
                    break;
            }
            frame.src = targetUrl;
        }

        window.onload = function() {
            loadContent('welcomeDashboard'); // Tải trang chủ mặc định
        };
    </script>
</body>
</html>