<%-- /views/teacher/managecourses.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Danh sách Khóa học</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f2f5;
            color: #333;
            line-height: 1.6;
        }

        .page-wrapper {
            max-width: 1200px;
            margin: 25px auto;
            padding: 20px;
        }

        .page-title {
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.2rem;
            font-weight: 600;
            color: #0056b3;
        }

        .filter-bar-wrapper {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            margin-bottom: 30px;
        }

        .search-sort-form {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 15px;
        }
        .search-sort-form input[type="text"] {
            padding: 10px 15px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 1rem;
            flex-grow: 1;
            min-width: 200px;
        }
        .search-sort-form select[name="sortOrder"] {
            padding: 10px 15px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 1rem;
            background-color: #fff;
        }
        .search-sort-form button[type="submit"] {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.2s;
        }
        .search-sort-form button[type="submit"]:hover {
            background-color: #0056b3;
        }
        .search-sort-form input[type="text"]:focus,
        .search-sort-form select[name="sortOrder"]:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 0.15rem rgba(0,123,255,.2);
        }

        .courses-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 25px;
        }
        .course-card {
            background-color: #ffffff;
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
            background-color: #e9ecef;
        }
        .course-card .thumbnail-wrapper img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .course-card .card-body {
            padding: 20px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }
        .course-card .course-title {
            margin: 0 0 10px 0;
            font-size: 1.3rem;
            font-weight: 600;
            color: #343a40;
            line-height: 1.4;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            min-height: calc(1.3rem * 1.4 * 2);
        }
        .course-card .course-price {
            font-size: 1.6rem;
            font-weight: 700;
            color: #007bff;
            margin: 0 0 12px 0;
        }
        .course-card .course-description {
            font-size: 0.9rem;
            color: #5a6268;
            line-height: 1.5;
            margin: 0 0 15px 0;
            flex-grow: 1;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            min-height: calc(0.9rem * 1.5 * 3);
        }

        /* Styling cho phần chứa các nút hành động */
        .course-card .card-actions-footer {
            margin-top: auto; /* Đẩy các nút xuống cuối thẻ */
            display: flex; /* Sắp xếp các form nút trên một hàng */
            gap: 10px; /* Khoảng cách giữa các form nút */
        }
        .course-card .card-actions-footer form {
            flex: 1; /* Chia đều không gian cho các form nút */
            display: inline; /* GIỮ NGUYÊN display:inline như bản gốc */
        }

        /* Styling chung cho các nút trong card-actions-footer */
        .course-card .card-actions-footer button {
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%; /* Nút chiếm toàn bộ chiều rộng của form cha */
            font-size: 0.9rem;
            font-weight: 500;
            text-align: center;
            transition: background-color 0.2s ease;
            box-sizing: border-box;
            border: none; /* Loại bỏ viền mặc định của button */
        }

        /* Styling riêng cho nút "Read More" (hoặc nút phụ) */
        .course-card .card-actions-footer .btn-readmore {
            background-color: #6c757d; /* Màu xám cho nút phụ */
            color: white;
        }
        .course-card .card-actions-footer .btn-readmore:hover {
            background-color: #545b62;
        }

        /* Styling riêng cho nút "Join Now" (hoặc nút chính) */
        .course-card .card-actions-footer .btn-joinnow {
            background-color: #007bff; /* Màu xanh cho nút chính */
            color: white;
        }
        .course-card .card-actions-footer .btn-joinnow:hover {
            background-color: #0056b3;
        }


        .no-courses-found {
            text-align: center;
            font-size: 1.1rem;
            color: #6c757d;
            padding: 50px 20px;
            background-color: #fff;
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
            color: #007bff;
            background-color: #fff;
            border: 1px solid #dee2e6;
            border-left: none;
            text-decoration: none;
            transition: background-color 0.2s, color 0.2s;
            font-size: 0.95rem;
        }
        .pagination-list li:first-child a, .pagination-list li:first-child span {
             border-left: 1px solid #dee2e6;
        }
        .pagination-list li a:hover {
            background-color: #e9ecef;
            color: #0056b3;
        }
        .pagination-list li.active span {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
            z-index: 1;
            position: relative;
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
        <h2 class="page-title">DANH SÁCH KHÓA HỌC</h2>
        
        <div class="filter-bar-wrapper">
            <form action="${pageContext.request.contextPath}/BrowseCourseServlet" method="post" class="search-sort-form">
                <input type="hidden" name="page" value="1" />
                <input type="text" name="searchKeyword" placeholder="Nhập từ khóa tìm kiếm..." 
                       value="<c:out value="${currentSearchKeyword}"/>" >
                <select name="sortOrder" onchange="this.form.submit()">
                    <option value="asc" ${empty currentSortOrder || currentSortOrder == 'asc' ? 'selected' : ''}>Giá tăng dần</option>
                    <option value="desc" ${currentSortOrder == 'desc' ? 'selected' : ''}>Giá giảm dần</option>
                </select>
                <button type="submit">Tìm kiếm</button>
            </form>
        </div>

        <c:choose>
            <c:when test="${not empty courses}">
                <div class="courses-grid">
                    <c:forEach var="course" items="${courses}">
                        <div class="course-card">
                            <div class="thumbnail-wrapper">
                                <c:choose>
                                    <c:when test="${not empty course.thumbnail}">
                                        <img src="${pageContext.request.contextPath}/${course.thumbnail}" alt="<c:out value="${course.title}"/>" />
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/images/default-course-thumbnail.png" alt="Ảnh mặc định" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="card-body">
                                <h3 class="course-title"><c:out value="${course.title}"/></h3>
                                <div class="course-price">
                                    <fmt:setLocale value="vi_VN"/>
                                    <fmt:formatNumber value="${course.price}" type="currency" currencySymbol="₫"/>
                                </div>
                                <div class="course-description">
                                    <c:out value="${course.description.content}"/>
                                </div>
                                <div class="card-actions-footer">
                                    <%-- ===================================================================== --%>
                                    <%-- KHÔI PHỤC FORM "READ MORE" VỀ ĐÚNG NHƯ TRONG JSP GỐC BẠN CUNG CẤP --%>
                                    <%-- Chỉ thêm class CSS vào button --%>
                                    <%-- ===================================================================== --%>
                                    <form action="${pageContext.request.contextPath}/student/readmore" method="post" style="display:inline;">
                                        <input type="hidden" name="courseTitle" value="${course.title}">
                                        <%-- NẾU SERVLET /student/readmore của bạn cần courseId, bạn cần đảm bảo
                                             input này có trong code gốc hoặc thêm vào nếu logic yêu cầu.
                                             Trong code gốc đầu tiên bạn cung cấp, chỉ có courseTitle cho Read More.
                                        --%>
                                        <%-- <input type="hidden" name="courseId" value="${course.courseId}"> --%>
                                        <button type="submit" class="btn-readmore">Read More</button>
                                    </form>

                                    <%-- ===================================================================== --%>
                                    <%-- FORM "JOIN NOW" GIỮ NGUYÊN NHƯ TRONG JSP GỐC BẠN CUNG CẤP --%>
                                    <%-- Chỉ thêm class CSS vào button --%>
                                    <%-- ===================================================================== --%>
                                    <form action="${pageContext.request.contextPath}/student/join-course" method="post" 
                                          onsubmit="return confirmPurchase('<c:out value="${course.title}"/>');" style="display:inline;">
                                        <input type="hidden" name="courseId" value="${course.courseId}">
                                        <button type="submit" class="btn-joinnow">Join Now</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-courses-found">Không tìm thấy khóa học nào phù hợp với điều kiện tìm kiếm.</p>
            </c:otherwise>
        </c:choose>

        <%-- Phân trang --%>
        <c:if test="${not empty courses && totalPages > 1}">
            <nav class="pagination-container" aria-label="Course navigation">
                <ul class="pagination-list">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <c:choose>
                            <c:when test="${currentPage == 1}">
                                <span class="page-link">&laquo; Trước</span>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="${pageContext.request.contextPath}/BrowseCourseServlet?page=${currentPage - 1}&searchKeyword=<c:out value="${currentSearchKeyword}"/>&sortOrder=<c:out value="${currentSortOrder}"/>">&laquo; Trước</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <c:forEach var="i" begin="1" end="${totalPages}">
                         <c:if test="${i == currentPage || i == currentPage - 1 || i == currentPage + 1 || i == 1 || i == totalPages || (totalPages <= 5) || 
                                       (currentPage <= 3 && i <= 5) || (currentPage >= totalPages - 2 && i >= totalPages - 4) }">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/BrowseCourseServlet?page=${i}&searchKeyword=<c:out value="${currentSearchKeyword}"/>&sortOrder=<c:out value="${currentSortOrder}"/>">${i}</a>
                            </li>
                         </c:if>
                         <c:if test="${ (i == 2 && currentPage > 4 && totalPages > 5) || (i == totalPages - 1 && currentPage < totalPages - 3 && totalPages > 5) }">
                            <li class="page-item disabled"><span class="page-link">...</span></li>
                         </c:if>
                    </c:forEach>
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                         <c:choose>
                            <c:when test="${currentPage == totalPages}">
                                <span class="page-link">Sau &raquo;</span>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="${pageContext.request.contextPath}/BrowseCourseServlet?page=${currentPage + 1}&searchKeyword=<c:out value="${currentSearchKeyword}"/>&sortOrder=<c:out value="${currentSortOrder}"/>">Sau &raquo;</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>

    <script>
        function confirmPurchase(courseTitle) {
            return confirm(`Bạn có chắc chắn muốn tham gia khóa học: '${courseTitle}' không?`);
        }
    </script>
</body>
</html>