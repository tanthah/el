<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Teacher</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 1rem;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }
        
        h2 {
            color: #508bfc;
            margin-bottom: 30px;
            text-align: center;
        }
        
        h3 {
            color: #508bfc;
            margin-top: 30px;
            margin-bottom: 20px;
        }
        
        .error {
            color: #dc3545;
            font-size: 14px;
            margin-top: 5px;
            display: block;
        }
        
        .success {
            color: #28a745;
            padding: 10px;
            background-color: #e8f5e9;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
        
        .form-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
            position: relative;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #555;
        }
        
        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }
        
        .form-group input:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #508bfc;
            box-shadow: 0 0 0 2px rgba(80, 139, 252, 0.2);
        }

        button {
            background-color: #508bfc;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            font-weight: 500;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        
        button:hover {
            background-color: #3a7bd5;
        }

        p a {
            text-decoration: none;
            color: #508bfc;
            font-size: 16px;
            transition: color 0.3s ease;
        }

        p a:hover {
            color: #3a7bd5;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create New Teacher</h2>

        <!-- Hiển thị thông báo thành công hoặc lỗi -->
        <c:if test="${not empty message}">
            <p class="success">${message}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <!-- Form tạo Teacher -->
        <div class="form-container">
            <h3>Create New Teacher</h3>
            <form action="${pageContext.request.contextPath}/create-teacher" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${param.username}">
                    <c:if test="${not empty errors.username}">
                        <span class="error">${errors.username}</span>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password">
                    <c:if test="${not empty errors.password}">
                        <span class="error">${errors.password}</span>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${param.email}">
                    <c:if test="${not empty errors.email}">
                        <span class="error">${errors.email}</span>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <input type="text" id="phone" name="phone" value="${param.phone}">
                </div>
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="${param.name}">
                    <c:if test="${not empty errors.name}">
                        <span class="error">${errors.name}</span>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="bio">Bio:</label>
                    <textarea id="bio" name="bio">${param.bio}</textarea>
                </div>
                <div class="form-group">
                    <label for="qualifications">Qualifications:</label>
                    <textarea id="qualifications" name="qualifications">${param.qualifications}</textarea>
                </div>
                <button type="submit">Create Teacher</button>
            </form>
        </div>

        
    </div>
</body>
</html>
