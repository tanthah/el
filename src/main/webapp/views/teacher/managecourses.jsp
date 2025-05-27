<%-- /views/teacher/managecourses.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Courses</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .create-btn, .show-btn {
            margin-bottom: 20px;
            padding: 10px 20px;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            display: inline-block;
        }
        .create-btn {
            background-color: #4CAF50;
        }
        .show-btn {
            background-color: #2196F3;
        }
    </style>
</head>
<body>
    <h2>Manage Courses</h2>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>

    <!-- Nút tạo khóa học mới -->
    <a href="${pageContext.request.contextPath}/teacher/create-course" class="create-btn">Create New Course</a>

    <!-- Nút hiển thị tất cả khóa học -->
    <a href="${pageContext.request.contextPath}/teacher/manage-courses?action=show" class="show-btn">Show All Courses</a>

    <!-- Danh sách khóa học (chỉ hiển thị khi showCourses = true) -->
    <c:if test="${showCourses}">
        <c:if test="${not empty courses}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Price</th>
                    <th>Thumbnail</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td>${course.courseId}</td>
                        <td>${course.title}</td>
                        <td>${course.price}</td>
                        <td>
                            <c:if test="${not empty course.thumbnail}">
                                <img src="${pageContext.request.contextPath}/${course.thumbnail}" alt="Thumbnail" width="50"/>
                            </c:if>
                        </td>
                        <td>${course.category.name}</td>
                        <td>${course.description.content}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/teacher/edit-course" method="get" style="display:inline;">
                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                    <button type="submit" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer;">Edit</button>
                            </form>

                            
                            <a href="${pageContext.request.contextPath}/teacher/manage-courses?action=delete&courseId=${course.courseId}" 
                               onclick="return confirm('Are you sure you want to delete this course?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty courses}">
            <p>No courses found.</p>
        </c:if>
    </c:if>
</body>
</html>