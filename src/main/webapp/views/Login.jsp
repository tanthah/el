<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- MDBootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .form-container {
            width: 35%;
            margin: 80px auto;
        }
        @media (max-width: 768px) {
            .form-container {
                width: 90%;
            }
        }
        .error {
            color: red;
            font-size: 0.9em;
        }
        .server-error {
/*            color: red;
            font-weight: bold;
            margin-bottom: 15px;
            text-align: center;*/

    color: red;
    font-weight: bold;
    font-size: 1em; /* Đảm bảo kích thước chữ rõ ràng */
    margin-bottom: 15px;
    text-align: center;
    display: block; /* Đảm bảo hiển thị */
    visibility: visible; /* Đảm bảo không ẩn */
    opacity: 1; /* Đảm bảo không trong suốt */
    position: relative; /* Đảm bảo vị trí hợp lý */
    z-index: 10; /* Đảm bảo không bị che bởi phần tử khác */
    background-color: #ffe6e6; /* Nền nhạt để tăng độ tương phản */
    padding: 10px; /* Khoảng đệm để dễ nhìn */
    border: 1px solid red; /* Viền để nổi bật */

        }
        .success-message {
            color: green;
            font-weight: bold;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <!-- Server error message -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="server-error"><%= request.getAttribute("errorMessage") %></div>
        <% } %>
        <!-- Registration success message -->
        <% if ("true".equals(request.getParameter("registrationSuccess"))) { %>
            <div class="success-message">Đăng ký thành công! Vui lòng đăng nhập.</div>
        <% } %>
        <form id="loginForm" action="<%= request.getContextPath() %>/login" method="POST">
            <!-- Username input -->
            <div data-mdb-input-init class="form-outline mb-4">
                <input type="text" id="username" name="username" class="form-control" required
                       value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"/>
                <label class="form-label" for="username">Tên đăng nhập</label>
                <span id="usernameError" class="error"></span>
            </div>
            <!-- Password input -->
            <div data-mdb-input-init class="form-outline mb-4">
                <input type="password" id="password" name="password" class="form-control" required />
                <label class="form-label" for="password">Mật khẩu</label>
                <span id="passwordError" class="error"></span>
            </div>
            <!-- 2 column layout -->
            <div class="row mb-4">
                <div class="col d-flex justify-content-center">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="rememberMe" checked />
                        <label class="form-check-label" for="rememberMe"> Ghi nhớ tôi </label>
                    </div>
                </div>
                <div class="col text-end">
                    <a href="/el1/views/ForgotPassword.jsp">Quên mật khẩu?</a>
                </div>
            </div>
            <!-- Submit button -->
            <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4 w-100">
                Đăng nhập
            </button>
            <!-- Register buttons -->
            <div class="text-center">
                <p>Chưa có tài khoản? <a href="/el1/views/Register.jsp">Đăng ký</a></p>
                <p>hoặc đăng nhập với:</p>
                <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                    <i class="fab fa-facebook-f"></i>
                </button>
                <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                    <i class="fab fa-google"></i>
                </button>
                <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                    <i class="fab fa-twitter"></i>
                </button>
                <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1">
                    <i class="fab fa-github"></i>
                </button>
            </div>
        </form>
    </div>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- MDB JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Form validation
            document.getElementById("loginForm").addEventListener("submit", function(event) {
                let isValid = true;
                const username = document.getElementById("username").value.trim();
                const password = document.getElementById("password").value.trim();
                document.getElementById("usernameError").textContent = "";
                document.getElementById("passwordError").textContent = "";
                if (username.length < 3) {
                    document.getElementById("usernameError").textContent = "Tên đăng nhập phải có ít nhất 3 ký tự";
                    isValid = false;
                }
                if (password.length < 6) {
                    document.getElementById("passwordError").textContent = "Mật khẩu phải có ít nhất 6 ký tự";
                    isValid = false;
                }
                if (!isValid) {
                    event.preventDefault();
                }
            });
        });
    </script>
</body>
</html>