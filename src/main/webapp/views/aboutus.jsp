<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chào Mừng Đến Với eLEARNING</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
        }
        .container {
            width: 70%;
            margin: 0 auto;
            padding: 20px;
        }
        .about-section {
            display: flex;
            justify-content: space-between;
            padding: 40px 0;
            align-items: center;
        }
        .about-text {
            width: 50%;
            padding-right: 20px;
        }
        .about-text h2 {
            font-size: 2.5em;
            color: #2c3e50;
        }
        .about-text p {
            font-size: 1.2em;
            color: #7f8c8d;
            margin-bottom: 20px;
            line-height: 1.6em;
        }
        .about-image {
            width: 40%;
        }
        .about-image img {
            width: 100%;
            height: auto;
            border-radius: 10px;
        }
        .service-list {
            display: flex;
            margin-top: 30px;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        .service-item {
            background-color: #ffffff;
            border: 1px solid #ddd;
            padding: 20px;
            width: 30%;
            margin: 10px 0;
            border-radius: 5px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .service-item h3 {
            font-size: 1.5em;
            color: #2980b9;
        }
        .service-item p {
            color: #7f8c8d;
        }
        .read-more-btn {
            display: inline-block;
            background-color: #2980b9;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 30px;
        }
        .read-more-btn:hover {
            background-color: #3498db;
        }
        .back-to-top {
            position: fixed;
            bottom: 30px;
            right: 30px;
            background-color: #2980b9;
            color: white;
            padding: 15px;
            border-radius: 50%;
            text-align: center;
            font-size: 24px;
            cursor: pointer;
        }
        .back-to-top:hover {
            background-color: #3498db;
        }
    </style>
</head>
<body>

    <div class="container">
        <!-- About Us Section -->
        <div class="about-section">
            <div class="about-image">
                <img src="/el1/views/img/aboutus.jpg" alt="Hình ảnh sách" />
            </div>
            <div class="about-text">
                <h2>Chào Mừng Đến Với eLEARNING</h2>
                <p>Chúng tôi cung cấp các khóa học trực tuyến về các môn học như Toán, Lý, Hóa, Văn và nhiều môn học khác. Cùng đội ngũ giảng viên giàu kinh nghiệm, chúng tôi cam kết mang lại cho bạn những kiến thức bổ ích và dễ hiểu.</p>
                <p>Với chứng chỉ quốc tế, các khóa học của chúng tôi giúp bạn trang bị kiến thức vững vàng, đồng thời mở rộng cơ hội nghề nghiệp và học tập toàn cầu.</p>

                <!-- List of Services -->
                <div class="service-list">
                    <div class="service-item">
                        <h3>Giảng Viên Kinh Nghiệm</h3>
                        <p>Các giảng viên giàu kinh nghiệm, tận tâm sẽ giúp bạn tiếp thu bài học một cách dễ dàng.</p>
                    </div>
                    <div class="service-item">
                        <h3>Chứng Chỉ Quốc Tế</h3>
                        <p>Hoàn thành khóa học và nhận chứng chỉ quốc tế công nhận trình độ học vấn của bạn.</p>
                    </div>
                    <div class="service-item">
                        <h3>Khóa Học Trực Tuyến</h3>
                        <p>Học mọi lúc, mọi nơi với các khóa học trực tuyến linh hoạt, phù hợp với lịch trình của bạn.</p>
                    </div>
                </div>

                <!-- Read More Button -->
                <a href="<%= request.getContextPath() %>/teacher/auto-manage-courses" class="read-more-btn">Đọc Thêm</a>
                
               
            </div>
        </div>

        <!-- Back to Top Button -->
        <div class="back-to-top" onclick="window.scrollTo({top: 0, behavior: 'smooth'});">↑</div>
    </div>

</body>
</html>
