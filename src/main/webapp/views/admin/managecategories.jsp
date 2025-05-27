<%-- /views/admin/managecategories.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Categories</title>
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
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            padding: 8px;
            width: 300px;
        }
        .create-btn, .delete-btn, .show-btn {
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .create-btn {
            background-color: #4CAF50;
            color: white;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
        }
        .show-btn {
            background-color: #2196F3;
            color: white;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <h2>Manage Categories</h2>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>

    <!-- Form tạo Category mới -->
    <form action="${pageContext.request.contextPath}/admin/manage-categories" method="post">
        <input type="hidden" name="action" value="create">
        <div class="form-group">
            <label for="name">Category Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <button type="submit" class="create-btn">Create</button>
    </form>

    <!-- Nút hiển thị tất cả Category -->
    <form action="${pageContext.request.contextPath}/admin/manage-categories" method="post">
        <input type="hidden" name="action" value="show">
        <button type="submit" class="show-btn">Show All Categories</button>
    </form>

    <!-- Danh sách Category (chỉ hiển thị khi showCategories = true) -->
    <c:if test="${showCategories}">
        <c:if test="${not empty categories}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.categoryId}</td>
                        <td>${category.name}</td>
                        <td>${category.description}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/manage-categories" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="categoryId" value="${category.categoryId}">
                                <button type="submit" class="delete-btn" onclick="return confirm('Are you sure you want to delete this category?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty categories}">
            <p>No categories found.</p>
        </c:if>
    </c:if>
</body>
</html>