<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6; /* Màu nền xám nhẹ nhàng */
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            color: #333;
        }

        .container {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 700px; /* Giới hạn chiều rộng container */
            margin-top: 20px;
        }

        .page-title {
            text-align: center;
            color: #2c3e50; /* Màu xanh đậm */
            font-size: 2.2em;
            margin-bottom: 30px; /* Tương tự mb-4 */
            font-weight: 600;
        }

        .section {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #e0e0e0; /* Đường kẻ phân cách nhẹ */
        }
        .section:last-of-type { /* Bỏ đường kẻ cho section cuối */
            border-bottom: none;
            margin-bottom: 0; /* Giảm margin cho section cuối trước form */
        }


        .section-title {
            font-size: 1.6em;
            color: #3498db; /* Màu xanh dương cho tiêu đề section */
            margin-top: 0;
            margin-bottom: 15px;
            padding-bottom: 5px;
            border-bottom: 2px solid #3498db;
            display: inline-block;
        }

        .detail-item {
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            font-size: 1em;
            line-height: 1.6;
        }
        
        .detail-item:nth-child(even) { /* Tạo hiệu ứng vằn nhẹ cho dễ đọc */
            /* background-color: #f9f9f9; */ /* Tùy chọn: nếu muốn nền xen kẽ */
        }

        .detail-label {
            font-weight: 600;
            color: #555;
            margin-right: 10px;
        }

        .detail-value {
            color: #333;
            text-align: right;
        }

        .confirm-payment-form {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #e0e0e0;
        }

        .confirm-button {
            background-color: #2ecc71; /* Màu xanh lá cây cho xác nhận */
            color: white;
            padding: 12px 30px;
            font-size: 1.1em;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.1s ease;
            box-shadow: 0 2px 5px rgba(0,0,0,0.15);
        }

        .confirm-button:hover {
            background-color: #27ae60; /* Màu xanh lá cây đậm hơn khi hover */
        }
        
        .confirm-button:active {
            transform: translateY(1px); /* Hiệu ứng nhấn nút */
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        
        /* Responsive adjustments */
        @media (max-width: 600px) {
            .container {
                padding: 20px;
                margin-top: 10px;
            }
            .page-title {
                font-size: 1.8em;
                margin-bottom: 20px;
            }
            .section-title {
                font-size: 1.4em;
            }
            .detail-item {
                flex-direction: column; /* Xếp chồng label và value trên màn hình nhỏ */
                align-items: flex-start;
                padding: 10px 0;
            }
            .detail-value {
                text-align: left;
                margin-top: 4px;
            }
            .confirm-button {
                width: 100%; /* Nút chiếm toàn bộ chiều rộng */
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    
    <div class="container">
        <h1 class="page-title">Order Confirmation</h1>

        <div class="section">
            <h3 class="section-title">Course Details</h3>
            <div class="detail-item">
                <span class="detail-label">Course Title:</span>
                <span class="detail-value"><c:out value="${course.title}"/></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Price:</span>
                <span class="detail-value"><c:out value="${course.price}"/> VND</span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Category:</span>
                <span class="detail-value"><c:out value="${course.category.name}"/></span>
            </div>
        </div>

        <div class="section">
            <h3 class="section-title">Order Details</h3>
            <div class="detail-item">
                <span class="detail-label">Order ID:</span>
                <span class="detail-value"><c:out value="${order.orderId}"/></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Order Date:</span>
                <span class="detail-value"><c:out value="${order.orderDate}"/></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Total Price:</span>
                <span class="detail-value"><c:out value="${order.totalPrice}"/> VND</span>
            </div>
        </div>

        <div class="section">
            <h3 class="section-title">Payment Details</h3>
            <div class="detail-item">
                <span class="detail-label">Payment ID:</span>
                <span class="detail-value"><c:out value="${payment.paymentId}"/></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Amount:</span>
                <span class="detail-value"><c:out value="${payment.amount}"/> VND</span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Payment Date:</span>
                <span class="detail-value"><c:out value="${payment.paymentDate}"/></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Payment Method:</span>
                <span class="detail-value"><c:out value="${payment.paymentMethod}"/></span>
            </div>
            <div class="detail-item">
                <span class="detail-label">Status:</span>
                <span class="detail-value"><c:out value="${payment.status}"/></span>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/buycourse" method="post" class="confirm-payment-form">
            <input type="hidden" name="paymentId" value="${payment.paymentId}"/>
            <button type="submit" class="confirm-button">Confirm Payment</button>
        </form>
    </div>

</body>
</html>