<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>eLearning Navbar</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7fa;
        }

        /* Navbar style */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 30px;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .navbar .logo {
            font-size: 24px;
            font-weight: 700;
            color: #00bcd4;
            display: flex;
            align-items: center;
        }

        .navbar .logo i {
            font-size: 28px;
            margin-right: 8px;
        }

        .navbar .nav-links {
            list-style: none;
            display: flex;
            gap: 10px;
            margin-right: 10px;
            flex-grow: 1;
            justify-content: flex-end;
        }

        .navbar .nav-links li {
            display: inline;
        }

        .navbar .nav-links li a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
            font-size: 16px;
            transition: color 0.3s ease;
        }

        .navbar .nav-links li a:hover {
            color: #00bcd4;
        }

        .navbar .nav-links li.dropdown {
            position: relative;
        }

        .navbar .nav-links li.dropdown .dropdown-menu {
            position: absolute;
            top: 100%;
            left: 0;
            background-color: white;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            min-width: 150px;
            display: none;
        }

        .navbar .nav-links li.dropdown:hover .dropdown-menu {
            display: block;
        }

        .navbar .nav-links li.dropdown .dropdown-menu li {
            padding: 10px;
            border-bottom: 1px solid #e0e0e0;
        }

        .navbar .nav-links li.dropdown .dropdown-menu li a {
            font-size: 14px;
            color: #333;
        }

        .navbar .nav-links li.dropdown .dropdown-menu li a:hover {
            color: #00bcd4;
        }

        .navbar .join-btn {
            background-color: #00bcd4;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 500;
            font-size: 16px;
        }

        .navbar .join-btn:hover {
            background-color: #0097a7;
        }

        .user-section {
            display: flex;
            align-items: center;
            gap: 10px;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <header class="navbar">
        <div class="logo">
            <i class="fa fa-book"></i> eLEARNING
        </div>
        <ul class="nav-links">
            <li><a href="#">HOME</a></li>
            <li><a href="#">ABOUT</a></li>
            <li><a href="#">COURSES</a></li>
            <li class="dropdown">
                <a href="#">PAGES <i class="fa fa-caret-down"></i></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Page 1</a></li>
                    <li><a href="#">Page 2</a></li>
                    <li><a href="#">Page 3</a></li>
                </ul>
            </li>
            <li><a href="#">CONTACT</a></li>
        </ul>
        <div class="user-section">
            <c:choose>
                <c:when test="${sessionScope.loggedInAccount != null}">
                    <button class="join-btn">
                        <a href="${pageContext.request.contextPath}/views/StudentDashboard.jsp" style="color: white; text-decoration: none;">
                            Student Dashboard
                        </a>
                    </button>
                    <button class="join-btn">
                        <a href="${pageContext.request.contextPath}/logout" style="color: white; text-decoration: none;">
                            Logout
                        </a>
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="join-btn">
                        <a href="${pageContext.request.contextPath}/views/Login.jsp" style="color: white; text-decoration: none;">
                            Join Now →
                        </a>
                    </button>
                </c:otherwise>
            </c:choose>
        </div>
    </header>

</body>
</html>