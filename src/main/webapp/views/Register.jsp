<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký Tài Khoản</title>
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
        .error {
            color: red;
            font-size: 0.9em;
            display: block;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <section class="vh-100" style="background-color: #eee;">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-12 col-xl-11">
                    <div class="card text-black" style="border-radius: 25px;">
                        <div class="card-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng Ký</p>

                                    <!-- Server error message -->
                                    <% if (request.getAttribute("errorMessage") != null) { %>
                                        <div class="server-error"><%= request.getAttribute("errorMessage") %></div>
                                        <script>
                                            console.log("Error message: <%= request.getAttribute("errorMessage") %>");
                                        </script>
                                    <% } %>

                                    <form class="mx-1 mx-md-4" id="registerForm" action="<%= request.getContextPath() %>/register" method="POST">
                                        <!-- Username -->
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                            <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                <input type="text" id="username" name="username" class="form-control" required
                                                       value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"/>
                                                <label class="form-label" for="username">Tên đăng nhập</label>
                                                <span id="usernameError" class="error"></span>
                                            </div>
                                        </div>

                                        <!-- Email -->
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                            <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                <input type="email" id="email" name="email" class="form-control" required
                                                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"/>
                                                <label class="form-label" for="email">Email</label>
                                                <span id="emailError" class="error"></span>
                                            </div>
                                        </div>

                                        <!-- Password -->
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                            <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                <input type="password" id="password" name="password" class="form-control" required />
                                                <label class="form-label" for="password">Mật khẩu</label>
                                                <span id="passwordError" class="error"></span>
                                            </div>
                                        </div>

                                        <!-- Confirm Password -->
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                            <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required />
                                                <label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label>
                                                <span id="confirmPasswordError" class="error"></span>
                                            </div>
                                        </div>

                                        <!-- Phone -->
                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-phone fa-lg me-3 fa-fw"></i>
                                            <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                <input type="text" id="phone" name="phone" class="form-control"
                                                       value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>"/>
                                                <label class="form-label" for="phone">Số điện thoại</label>
                                                <span id="phoneError" class="error"></span>
                                            </div>
                                        </div>

                                            <div class="form-check d-flex justify-content-center mb-5">
                                                <input class="form-check-input me-2" type="checkbox" value="" id="terms" required />
                                                <label class="form-check-label" for="terms">
                                                    Tôi đồng ý với <a href="/el1/views/dieu_khoan_dich_vu.html" target="_blank">Điều khoản dịch vụ</a>
                                                </label>
                                            </div>

                                        <!-- Submit button -->
                                        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                            <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-lg">
                                                Đăng ký
                                            </button>
                                        </div>

                                        <!-- Link to login -->
                                        <div class="text-center">
                                            <p>Đã có tài khoản? <a href="/el1/views/Login.jsp">Đăng nhập</a></p>
                                        </div>
                                    </form>
                                </div>
                                <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                         class="img-fluid" alt="Sample image">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- MDB JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("registerForm").addEventListener("submit", function(event) {
                let isValid = true;

                // Lấy giá trị từ các trường
                const username = document.getElementById("username").value.trim();
                const password = document.getElementById("password").value.trim();
                const confirmPassword = document.getElementById("confirmPassword").value.trim();
                const email = document.getElementById("email").value.trim();
                const phone = document.getElementById("phone").value.trim();

                // Reset thông báo lỗi
                document.getElementById("usernameError").textContent = "";
                document.getElementById("passwordError").textContent = "";
                document.getElementById("confirmPasswordError").textContent = "";
                document.getElementById("emailError").textContent = "";
                document.getElementById("phoneError").textContent = "";

                // Kiểm tra tên đăng nhập
                if (username.length < 3) {
                    document.getElementById("usernameError").textContent = "Tên đăng nhập phải có ít nhất 3 ký tự";
                    isValid = false;
                }

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

                // Kiểm tra email
                const emailRegex = /^\S+@\S+\.\S+$/;
                if (!emailRegex.test(email)) {
                    document.getElementById("emailError").textContent = "Email không đúng định dạng";
                    isValid = false;
                }

                // Kiểm tra số điện thoại (nếu có)
                if (phone) {
                    const phoneRegex = /^\d{9,10}$/;
                    if (!phoneRegex.test(phone)) {
                        document.getElementById("phoneError").textContent = "Số điện thoại phải có 9-10 chữ số";
                        isValid = false;
                    }
                }

                // Ngăn submit nếu có lỗi
                if (!isValid) {
                    event.preventDefault();
                    console.log("Validation failed:", {
                        usernameError: document.getElementById("usernameError").textContent,
                        passwordError: document.getElementById("passwordError").textContent,
                        confirmPasswordError: document.getElementById("confirmPasswordError").textContent,
                        emailError: document.getElementById("emailError").textContent,
                        phoneError: document.getElementById("phoneError").textContent
                    });
                }
            });
        });
    </script>
</body>
</html>