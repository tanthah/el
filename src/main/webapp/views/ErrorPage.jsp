<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-danger">An Error Occurred</h1>
        <div class="alert alert-danger mt-3">
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    <c:out value="${errorMessage}"/>
                </c:when>
                <c:otherwise>
                    Something went wrong. Please try again later or contact support.
                </c:otherwise>
            </c:choose>
        </div>
        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Back to Home</a>
            <a href="${pageContext.request.contextPath}/views/Login.jsp" class="btn btn-success">Login Again</a>
        </div>
    </div>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>