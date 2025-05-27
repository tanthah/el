<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Lại Mật Khẩu</title>
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
        <h2 class="text-center mb-4">Đặt Lại Mật Khẩu</h2>

        <!-- Server error message -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="server-error"><%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <!-- Success message -->
        <% if (request.getAttribute("successMessage") != null) { %>
            <div class="success-message"><%= request.getAttribute("successMessage") %></div>
            <div class="text-center">
                <p><a href="/el1/views/Login.jsp">Quay lại Đăng nhập</a></p>
            </div>
        <% } else if (request.getAttribute("tokenValid") != null && (Boolean) request.getAttribute("tokenValid")) { %>
            <form id="resetPasswordForm" action="<%= request.getContextPath() %>/reset-password" method="POST">
                <input type="hidden" name="token" value="<%= request.getParameter("token") %>"/>

                <!-- Password -->
                <div data-mdb-input-init class="form-outline mb-4">
                    <input type="password" id="password" name="password" class="form-control" required />
                    <label class="form-label" for="password">Mật khẩu mới</label>
                    <span id="passwordError" class="error"></span>
                </div>

                <!-- Confirm Password -->
                <div data-mdb-input-init class="form-outline mb-4">
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required />
                    <label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label>
                    <span id="confirmPasswordError" class="error"></span>
                </div>

                <!-- Submit button -->
                <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block mb-4 w-100">
                    Đặt Lại Mật Khẩu
                </button>
            </form>
        <% } else { %>
            <div class="text-center">
                <p>Liên kết đặt lại mật khẩu không hợp lệ hoặc đã hết hạn. Vui lòng <a href="<%= request.getContextPath() %>/forgot-password">thử lại</a>.</p>
            </div>
        <% } %>

        <div class="text-center">
            <p>Quay lại <a href="/el1/views/Login.jsp">Đăng nhập</a></p>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- MDB JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const form = document.getElementById("resetPasswordForm");
            if (form) {
                form.addEventListener("submit", function(event) {
                    let isValid = true;
                    const password = document.getElementById("password").value.trim();
                    const confirmPassword = document.getElementById("confirmPassword").value.trim();

                    document.getElementById("passwordError").textContent = "";
                    document.getElementById("confirmPasswordError").textContent = "";

                    // Kiểm tra mật khẩu
                    if (password.length < 6) {
                        document.getElementById("passwordError").textContent = "Mật khẩu phải có ít nhất 6 ký tự";
                        isValid = false;
                    }

                    // Kiểm tra xác nhận mật khẩu
                    if (confirmPassword !== password) {
                        document.getElementById("confirmPasswordError").textContent = "Xác nhận mật khẩu không khớp";
                        isValid = false;
                    }

                    if (!isValid) {
                        event.preventDefault();
                        console.log("Validation failed:", {
                            passwordError: document.getElementById("passwordError").textContent,
                            confirmPasswordError: document.getElementById("confirmPasswordError").textContent
                        });
                    }
                });
            }
        });
    </script>
</body>
</html>