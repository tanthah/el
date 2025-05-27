<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h2>Error1</h2>
    
    <!-- Hiển thị thông báo lỗi nếu có -->
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>
    
    <!-- Mặc định hiển thị thông báo chung nếu không có lỗi cụ thể -->
    <c:if test="${empty error}">
        <p style="color: red">An unexpected error occurred.</p>
    </c:if>
    
    <p><a href="<%= request.getContextPath() %>/staff_login.jsp">Back to Login</a></p>
</body>
</html>