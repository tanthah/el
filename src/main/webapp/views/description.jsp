<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Khóa Học: ${course.title}</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            color: #333;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
        }

        .page-container {
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 900px; /* Giới hạn chiều rộng tối đa */
            overflow: hidden; /* Để bo góc hoạt động đúng với các thành phần con */
        }

        .course-header {
            background-color: #0056b3; /* Màu xanh dương đậm hơn */
            color: white;
            padding: 30px 40px;
            text-align: center;
        }

        .course-header h1 {
            margin: 0;
            font-size: 2.2em;
            font-weight: 600;
        }

        .course-content-wrapper {
            padding: 30px 40px;
        }
        
        .course-main-details {
            display: flex;
            gap: 30px;
            margin-bottom: 30px;
            padding-bottom: 30px;
            border-bottom: 1px solid #e0e0e0;
        }

        .course-thumbnail-container {
            flex-shrink: 0; /* Không co lại */
            width: 300px; /* Kích thước cố định cho ảnh */
        }

        .course-thumbnail-container img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        .course-info {
            flex-grow: 1; /* Phát triển để lấp đầy không gian */
        }

        .course-info h2 { /* Tiêu đề khóa học trong phần info */
            font-size: 1.8em;
            color: #222;
            margin-top: 0;
            margin-bottom: 15px;
        }

        .course-price {
            font-size: 2em;
            font-weight: 700;
            color: #d9534f; /* Màu đỏ cam cho giá */
            margin-bottom: 25px;
        }
        
        /* === FORM VÀ NÚT MUA NGAY === */
        .buy-action-form {
            margin-top: 20px;
        }

        .buy-now-button {
            display: inline-block; /* Cho phép padding và margin */
            background-color: #5cb85c; /* Màu xanh lá cây */
            color: white;
            padding: 12px 25px;
            font-size: 1.1em;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            text-decoration: none; /* Trong trường hợp dùng thẻ <a> */
            cursor: pointer;
            transition: background-color 0.2s ease-in-out, transform 0.1s ease;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .buy-now-button:hover {
            background-color: #4cae4c; /* Màu xanh lá cây đậm hơn */
        }
        
        .buy-now-button:active { /* Hiệu ứng khi nhấn nút */
            transform: translateY(1px);
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
        }
        /* === KẾT THÚC FORM VÀ NÚT MUA NGAY === */

        .content-section {
            margin-bottom: 25px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            border: 1px solid #e7e7e7;
        }

        .content-section h3 {
            font-size: 1.4em;
            color: #0056b3;
            margin-top: 0;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #d0d0d0;
        }

        .content-section p,
        .content-section ul {
            font-size: 1em;
            color: #555;
            line-height: 1.7;
        }

        .content-section ul {
            list-style-position: inside;
            padding-left: 0; /* Bỏ padding mặc định nếu dùng list-style-position: inside */
        }
        
        .content-section ul li {
            margin-bottom: 8px;
        }

        .empty-state {
            color: #777;
            font-style: italic;
            padding: 10px 0;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .course-main-details {
                flex-direction: column;
            }
            .course-thumbnail-container {
                width: 100%; /* Ảnh chiếm toàn bộ chiều rộng */
                margin-bottom: 20px;
            }
            .course-header {
                padding: 20px;
            }
            .course-header h1 {
                font-size: 1.8em;
            }
            .course-content-wrapper {
                padding: 20px;
            }
            .course-info h2 {
                font-size: 1.6em;
            }
            .course-price {
                font-size: 1.7em;
            }
        }

    </style>
</head>
<body>

    <div class="page-container">
        <header class="course-header">
            <h1>${course.title}</h1>
        </header>

        <div class="course-content-wrapper">
            <section class="course-main-details">
                <div class="course-thumbnail-container">
                    <img src="${pageContext.request.contextPath}/${course.thumbnail}" alt="Thumbnail khóa học ${course.title}" />
                </div>
                <div class="course-info">
                    <h2>Thông tin chi tiết</h2>
                    <p class="course-price">$${course.price}</p>
                    
                    <form action="${pageContext.request.contextPath}/buycourse" method="get" class="buy-action-form">
                        <input type="hidden" name="courseId" value="${course.courseId}" />
                        <button type="submit" class="buy-now-button">Mua Ngay</button>
                    </form>
                    </div>
            </section>

            <c:if test="${not empty description}">
                <section class="content-section course-description">
                    <h3>Mô Tả Khóa Học</h3>
                    <p><strong>Nội Dung:</strong> ${description.content}</p>
                </section>
            </c:if>

            <section class="content-section schedule-info">
                <h3>Lịch học trong tuần</h3>
                <c:choose>
                    <c:when test="${not empty applicableDays}">
                        <ul>
                            <c:forEach var="day" items="${applicableDays}">
                                <li>${day}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <p class="empty-state">Không có lịch học áp dụng cho khóa học này.</p>
                    </c:otherwise>
                </c:choose>
            </section>

            <section class="content-section teacher-details">
                <h3>Thông Tin Giáo Viên</h3>
                <c:choose>
                    <c:when test="${not empty teacher}">
                        <p><strong>Tên Giáo Viên:</strong> ${teacher.name}</p>
                        <p><strong>Tiểu Sử:</strong> ${teacher.bio}</p>
                        <p><strong>Bằng Cấp:</strong> ${teacher.qualifications}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="empty-state">Không tìm thấy thông tin giáo viên cho khóa học này.</p>
                    </c:otherwise>
                </c:choose>
            </section>
        </div>
    </div>

</body>
</html>