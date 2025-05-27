<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- MDBootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .server-error {
            color: red;
            font-weight: bold;
            font-size: 1em;
            margin-bottom: 15px;
            text-align: center;
            display: block !important;
            visibility: visible !important;
            opacity: 1 !important;
            position: relative;
            z-index: 100;
            background-color: #ffe6e6;
            padding: 10px;
            border: 1px solid red;
        }
        .success-message {
            color: green;
            font-weight: bold;
            font-size: 1em;
            margin-bottom: 15px;
            text-align: center;
            background-color: #e6ffe6;
            padding: 10px;
            border: 1px solid green;
        }
        .error {
            color: red;
            font-size: 0.9em;
            display: block;
            margin-top: 5px;
        }
        .form-container {
            width: 35%;
            margin: 80px auto;
        }
        @media (max-width: 768px) {
            .form-container {
                width: 90%;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2 class="text-center mb-4">Quên Mật Khẩu</h2>

        <!-- Server error message -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="server-error"><%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <!-- Success message -->
        <% if (request.getAttribute("successMessage") != null) { %>
            <div class="success-message"><%= request.getAttribute("successMessage") %></div>
        <% } %>

        <form id="forgotPasswordForm" action="<%= request.getContextPath() %>/forgot-password" method="POST">
            <!-- Email -->
            <div data-mdb-input-init class="form-outline mb-4">
                <input type="email" id="email" name="email" class="form-control" required
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"/>
                <label class="form-label" for="email">Email</label>
                <span id="emailError" class="error"></span>
            </div>

            <!-- Submit button -->
            <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4 w-100">
                Gửi Liên Kết Đặt Lại
            </button>

            <!-- Link to login -->
            <div class="text-center">
                <p>Quay lại <a href="<%= request.getContextPath() %>/views/Login.jsp">Đăng nhập</a></p>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- MDB JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("forgotPasswordForm").addEventListener("submit", function(event) {
                let isValid = true;
                const email = document.getElementById("email").value.trim();
                document.getElementById("emailError").textContent = "";

                // Kiểm tra email
                const emailRegex = /^\S+@\S+\.\S+$/;
                if (!emailRegex.test(email)) {
                    document.getElementById("emailError").textContent = "Email không đúng định dạng";
                    isValid = false;
                }

                if (!isValid) {
                    event.preventDefault();
                    console.log("Validation failed: Email không đúng định dạng");
                }
            });
        });
    </script>
</body>
</html>