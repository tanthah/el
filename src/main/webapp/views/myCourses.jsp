<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Khóa học của tôi</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        :root {
            --primary-color-theme2: #007bff;
            --primary-color-dark-theme2: #0056b3;
            --text-color-theme2-main: #333;
            --text-color-theme2-title: #343a40;
            --text-color-theme2-light: #6c757d;
            --background-color-theme2: #f0f2f5;
            --card-background-theme2: #ffffff;
            --border-color-theme2: #dee2e6;
            --placeholder-bg-theme2: #e9ecef;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--background-color-theme2);
            margin: 0;
            padding: 0;
            color: var(--text-color-theme2-main);
            line-height: 1.6;
        }

        .page-wrapper {
            max-width: 1200px;
            margin: 25px auto;
            padding: 20px;
        }

        .page-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .page-header h1 {
            font-size: 2.2rem;
            font-weight: 600;
            color: #0056b3;
            margin: 0;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 15px;
            border-radius: 6px;
            text-align: center;
            margin: 0 auto 30px auto;
            max-width: 800px;
        }

        .course-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 25px;
        }

        .course-card {
            background-color: var(--card-background-theme2);
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.07);
            display: flex;
            flex-direction: column;
            overflow: hidden;
            transition: transform 0.25s ease, box-shadow 0.25s ease;
        }

        .course-card:hover {
            transform: translateY(-6px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }

        .course-card .thumbnail-wrapper {
            width: 100%;
            height: 200px;
            overflow: hidden;
            background-color: var(--placeholder-bg-theme2);
            position: relative;
        }

        .course-card .thumbnail-wrapper img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }

        .course-card .card-body {
            padding: 20px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .course-card .card-body h3 {
            margin: 0 0 10px 0;
            font-size: 1.3rem;
            font-weight: 600;
            color: var(--text-color-theme2-title);
            line-height: 1.4;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            min-height: calc(1.3rem * 1.4 * 2);
        }

        .course-card .card-body .price {
            font-size: 1.6rem;
            font-weight: 700;
            color: var(--primary-color-theme2);
            margin: 0 0 12px 0;
        }
        
        .course-card .card-body .access-btn {
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            font-size: 0.9rem;
            font-weight: 500;
            text-align: center;
            transition: background-color 0.2s ease;
            box-sizing: border-box;
            border: none;
            margin-top: auto;
            text-decoration: none;
            display: block;
            background-color: var(--primary-color-theme2);
            color: white;
        }
        .course-card .card-body .access-btn i {
            margin-right: 8px;
        }
        .course-card .card-body .access-btn:hover {
            background-color: var(--primary-color-dark-theme2);
        }

        .no-courses {
            text-align: center;
            font-size: 1.1rem;
            color: var(--text-color-theme2-light);
            padding: 50px 20px;
            background-color: var(--card-background-theme2);
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }

        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 40px;
            padding: 15px 0;
        }
        .pagination-list {
            list-style: none;
            display: flex;
            padding: 0;
            margin: 0;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.06);
            overflow: hidden;
        }
        .pagination-list li { margin: 0; }

        .pagination-list li a, .pagination-list li span {
            display: block;
            padding: 10px 18px;
            color: var(--primary-color-theme2);
            background-color: var(--card-background-theme2);
            border: 1px solid var(--border-color-theme2);
            border-left: none;
            text-decoration: none;
            transition: background-color 0.2s, color 0.2s;
            font-size: 0.95rem;
        }
        .pagination-list li:first-child a, .pagination-list li:first-child span {
            border-left: 1px solid var(--border-color-theme2);
        }
        .pagination-list li a:hover {
            background-color: #e9ecef;
            color: var(--primary-color-dark-theme2);
        }
        .pagination-list li.active span, .pagination-list li.active a {
            background-color: var(--primary-color-theme2);
            color: white;
            border-color: var(--primary-color-theme2);
            z-index: 1;
            position: relative;
            cursor: default;
        }
        .pagination-list li.disabled span,
        .pagination-list li.disabled a {
            color: #adb5bd;
            pointer-events: none;
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
    <div class="page-wrapper">
        <div class="page-header">
            <h1>Khóa học của tôi</h1>
        </div>

        <c:if test="${not empty error}">
            <div class="error-message"><c:out value="${error}"/></div>
        </c:if>

        <c:choose> <%-- MAIN CHOOSE - Mở --%>
            <c:when test="${empty purchasedCourses}">
                <p class="no-courses">Bạn chưa mua khóa học nào.</p>
            </c:when>
            <c:otherwise>
                <div class="course-container">
                    <c:forEach var="course" items="${purchasedCourses}">
                        <div class="course-card">
                            <div class="thumbnail-wrapper">
                                <c:choose> <%-- IMAGE CHOOSE - Mở --%>
                                    <c:when test="${not empty course.thumbnail}">
                                        <img src="<%= request.getContextPath() %>/${course.thumbnail}" 
                                             alt="<c:out value='${course.title}'/>" 
                                             loading="lazy"
                                             onerror="this.onerror=null; this.src='<%= request.getContextPath() %>/images/default-course-thumbnail.png'"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<%= request.getContextPath() %>/images/default-course-thumbnail.png" 
                                             alt="Ảnh mặc định khóa học" 
                                             loading="lazy" />
                                    </c:otherwise>
                                </c:choose> <%-- IMAGE CHOOSE - Đóng --%>
                            </div>
                            <div class="card-body">
                                <h3><c:out value="${course.title}"/></h3>
                                <p class="price">
                                    <fmt:formatNumber value="${course.price}" type="currency" currencyCode="VND" pattern="#,##0₫"/>
                                </p>
                                <a href="<%= request.getContextPath() %>/student/learn-course?courseId=${course.courseId}" class="access-btn">
                                    <i class="fas fa-play-circle"></i> Truy cập khóa học
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <%-- Phân trang --%>
                <c:if test="${totalPages > 1}">
                    <nav class="pagination-container" aria-label="Course navigation">
                        <ul class="pagination-list">
                            <%-- Nút Trước --%>
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <c:choose> <%-- PREV BUTTON CHOOSE - Mở --%>
                                    <c:when test="${currentPage == 1}">
                                        <span class="page-link">&laquo; Trước</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="page-link" href="<%= request.getContextPath() %>/student/mycourses?page=${currentPage - 1}">&laquo; Trước</a>
                                    </c:otherwise>
                                </c:choose> <%-- PREV BUTTON CHOOSE - Đóng --%>
                            </li>
                            
                            <%-- Các nút số trang --%>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <%-- Đơn giản hóa: dùng c:if thay vì c:choose lồng nhau ở đây --%>
                                    <c:if test="${i == currentPage}">
                                        <span class="page-link">${i}</span>
                                    </c:if>
                                    <c:if test="${i != currentPage}">
                                        <a class="page-link" href="<%= request.getContextPath() %>/student/mycourses?page=${i}">${i}</a>
                                    </c:if>
                                </li>
                            </c:forEach>

                            <%-- Nút Sau --%>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <c:choose> <%-- NEXT BUTTON CHOOSE - Mở --%>
                                    <c:when test="${currentPage == totalPages}">
                                        <span class="page-link">Sau &raquo;</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="page-link" href="<%= request.getContextPath() %>/student/mycourses?page=${currentPage + 1}">Sau &raquo;</a>
                                    </c:otherwise>
                                </c:choose> <%-- NEXT BUTTON CHOOSE - Đóng --%>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </c:otherwise>
        </c:choose> <%-- MAIN CHOOSE - Đóng --%>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // JavaScript hiện có của bạn
        });
    </script>
</body>
</html>