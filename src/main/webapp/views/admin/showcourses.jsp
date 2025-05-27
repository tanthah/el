<%-- /views/teacher/managecourses.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Courses</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        h2 {
            text-align: center;
            margin-top: 20px;
        }
        .courses-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            gap: 20px;
            padding: 20px;
        }
        .course-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 280px;
            padding: 20px;
            text-align: center;
            overflow: hidden;
        }
        .course-card img {
            width: 100%;
            border-radius: 8px;
            object-fit: cover;
        }
        .course-card h3 {
            margin: 10px 0;
            font-size: 18px;
            color: #333;
        }
        .course-card .price {
            font-size: 22px;
            font-weight: bold;
            color: #2196F3;
            margin: 10px 0;
        }
        .course-card .rating {
            color: #ffb400;
            margin: 10px 0;
        }
        .course-card .description {
            font-size: 14px;
            color: #777;
            margin: 10px 0;
        }
        .course-card .actions button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
        }
        .actions button:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
    <h2>Manage Courses</h2>
    
    <!-- Hiển thị thông báo lỗi hoặc thành công -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p class="success">${message}</p>
    </c:if>

    <!-- Nút hiển thị tất cả khóa học -->
    <form action="${pageContext.request.contextPath}/teacher/manage-courses" method="Post">
        <input type="hidden" name="action" value="showall">
        <button type="submit" class="show-btn">Show All Courses</button>
    </form>

   <!-- Danh sách khóa học (chỉ hiển thị khi showCourses = true) -->
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
                            <button type="button">Join Now</button>
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
