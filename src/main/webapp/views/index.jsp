<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ - eLEARNING</title>
</head>
<body>

    <!-- Include Header -->
    <jsp:include page="Header.jsp" />

    <!-- Main Content -->
    <div class="main-content">
        <jsp:include page="aboutus.jsp" />
        
        <jsp:include page="courses.jsp" />
        
             
    </div>

    <!-- Include Footer -->
    <jsp:include page="Footer.jsp" />

</body>
</html>
