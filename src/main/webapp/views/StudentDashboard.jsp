<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
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
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 30px;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            width: 100%;
            z-index: 1000;
            box-sizing: border-box;
            height: var(--header-height);
        }
        .header .logo {
            font-size: 24px;
            font-weight: 700;
            color: #00bcd4;
            display: flex;
            align-items: center;
        }
        .header .logo i {
            margin-right: 8px;
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
        .navi {
            position: fixed;
            top: var(--header-height);
            left: 0;
            width: var(--navi-width);
            background-color: #f5f5f5;
            padding: 20px 0;
            height: calc(100vh - var(--header-height));
            box-shadow: 2px 0px 5px rgba(0, 0, 0, 0.1);
            overflow-y: auto;
            box-sizing: border-box;
        }
        .navi .nav-item a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 20px;
            text-decoration: none;
            color: #333;
            font-size: 15px;
            font-weight: 500;
            transition: background-color 0.3s ease, color 0.3s ease;
            cursor: pointer;
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
        .navi .logout a:hover {
            background-color: #f44336 !important;
            color: white !important;
        }
        .main-content-wrapper {
            margin-left: var(--navi-width);
            padding-top: var(--header-height);
            flex-grow: 1;
            height: calc(100vh - var(--header-height));
            overflow: hidden;
            box-sizing: border-box;
        }
        #contentFrame {
            width: 100%;
            height: 100%;
            border: none;
            background-color: #ffffff;
        }
    </style>
</head>
<body>
    <%
        if (session.getAttribute("loggedInAccount") == null) {
            response.sendRedirect(request.getContextPath() + "/views/LoginForStudent.jsp");
            return;
        }
    %>

    <div class="header">
        <div class="logo">
            <i class="fa fa-book"></i> eLEARNING
        </div>
        <div class="student-info">
            <i class="fa fa-user"></i>
            <span>${loggedInAccount.username}</span>
        </div>
    </div>

    <div class="navi">
        <div class="nav-item">
            <a href="<%= request.getContextPath() %>/views/index.jsp">
                <i class="fa fa-home"></i>
                <span class="text">Trang chủ</span>
            </a>
        </div>
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadStudentPage('myCourses')">
                <i class="fa fa-book"></i>
                <span class="text">Khóa học của tôi</span>
            </a>
        </div>
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadStudentPage('statistics')">
                <i class="fa fa-chart-bar"></i>
                <span class="text">Thống kê</span>
            </a>
        </div>
        <div class="nav-item">
            <a href="javascript:void(0)" onclick="loadStudentPage('notifications')">
                <i class="fa fa-bell"></i>
                <span class="text">Thông báo</span>
            </a>
        </div>
        <div class="nav-item logout">
            <a href="<%= request.getContextPath() %>/logout">
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
        function loadStudentPage(pageKey) {
            const frame = document.getElementById('contentFrame');
            const contextPath = "<%= request.getContextPath() %>";
            let targetUrl = "about:blank";

            switch(pageKey) {
                case 'welcomeDashboard':
                    targetUrl = contextPath + "/views/welcome_dashboard.html";
                    break;
                case 'myCourses':
                    targetUrl = contextPath + "/student/mycourses";
                    break;
                case 'statistics':
                    targetUrl = contextPath + "/views/student/studentStatistics.jsp";
                    break;
                case 'notifications':
                    targetUrl = contextPath + "/views/student/studentNotifications.jsp";
                    break;
                default:
                    targetUrl = contextPath + "/views/student/welcomeStudentDashboard.jsp";
                    break;
            }
            frame.src = targetUrl;
        }

        window.onload = function() {
            loadStudentPage('welcomeDashboard');
        };
    </script>
</body>
</html>