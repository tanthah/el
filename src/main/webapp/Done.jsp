<%-- /views/teacher/ListTeacher.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Teachers</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            overflow-x: auto;
            display: block;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
            white-space: nowrap;
            max-width: 200px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        th {
            background-color: #f2f2f2;
            position: sticky;
            top: 0;
        }
        .actions {
            display: flex;
            gap: 10px;
            flex-wrap: nowrap;
        }
        td:nth-child(8), td:nth-child(9) {
            max-width: 300px;
            overflow-x: auto;
            white-space: normal;
        }
        .table-container {
            max-height: 400px;
            overflow-y: auto;
        }
        .back-link {
            margin-top: 20px;
            display: inline-block;
            padding: 10px 20px;
            background-color: #666;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .back-link:hover {
            background-color: #555;
        }
        .action-button {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            color: white;
            display: inline-block;
        }
        .view-button {
            background-color: #2196F3;
        }
        .view-button:hover {
            background-color: #1976D2;
        }
        .delete-button {
            background-color: #f44336;
        }
        .delete-button:hover {
            background-color: #d32f2f;
        }
        .status-button {
            background-color: #4CAF50;
        }
        .status-button:hover {
            background-color: #45a049;
        }
        .btn-light {
            background-color: #f0f0f0;
            color: #333;
            border: 1px solid #ccc;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease, border-color 0.3s ease;
            text-align: center;
        }
        .btn-light:hover {
            background-color: #e0e0e0;
            border-color: #bbb;
        }
        .btn-light:focus {
            outline: none;
            border-color: #888;
        }
        .filter-form {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .filter-form input[type="text"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 200px;
        }
        .filter-form select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .filter-form button {
            padding: 8px 16px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filter-form button:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
    
    <!-- Hiển thị thông báo lỗi hoặc thành công -->
<!-- courses.jsp -->
<c:if test="${showCourses}">
    <c:if test="${not empty courses}">
        <div class="courses-container">
            <c:forEach var="course" items="${courses}">
                <div class="course-card">
                    <img src="${pageContext.request.contextPath}/${course.thumbnail}" alt="Thumbnail" />
                    <h3>${course.title}</h3>
                    <div class="price">$${course.price}</div>
                    <div class="description">${course.description.content}</div>
                    <div class="actions">
                        <button type="button">Read More</button>
                        <form action="${pageContext.request.contextPath}/student/join-course" method="post" onsubmit="return confirmPurchase('${course.title}');">
                            <input type="hidden" name="courseId" value="${course.courseId}">
                            <button type="submit">Join Now</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${empty courses}">
        <p>No courses found.</p>
    </c:if>
</c:if>
    
</body>
</html>