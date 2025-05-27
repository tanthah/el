<%-- /views/error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2>Error</h2>
    <p style="color: red;">${error}</p>
    <a href="${pageContext.request.contextPath}/login">Back to Login</a>
</body>
</html>