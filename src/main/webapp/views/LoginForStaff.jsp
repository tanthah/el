<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        body {
            background-color: #508bfc;
        }
        .container {
            height: 100vh;
        }
        .card {
            border-radius: 1rem;
        }
        .card-body {
            padding: 2rem;
            text-align: center;
        }
        .btn {
            border-radius: 1rem;
        }
        .form-control {
            border-radius: 1rem;
        }
        .form-outline {
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong">
                    <div class="card-body">
                        <h3 class="mb-5">Staff Login</h3>
                        <form action="<%= request.getContextPath() %>/stafflogin" method="post">
                            <div class="form-outline mb-4">
                                <input type="text" id="username" name="username" class="form-control form-control-lg" required />
                                <label class="form-label" for="username">Username</label>
                            </div>

                            <div class="form-outline mb-4">
                                <input type="password" id="password" name="password" class="form-control form-control-lg" required />
                                <label class="form-label" for="password">Password</label>
                            </div>

                            <!-- Uncomment and customize if you want to add a role dropdown -->
                            <!--
                            <div class="form-outline mb-4">
                                <label for="role">Role:</label><br>
                                <select id="role" name="role" class="form-control form-control-lg" required>
                                    <option value="teacher">Teacher</option>
                                    <option value="admin">Admin</option>
                                </select><br><br>
                            </div>
                            -->

                            <button type="submit" class="btn btn-primary btn-lg btn-block">Login</button>

                            <hr class="my-4">

                            <!-- If you want to add social login buttons, uncomment the following -->
                            <!--
                            <button class="btn btn-lg btn-block btn-primary" style="background-color: #dd4b39;" type="submit">
                                <i class="fab fa-google me-2"></i> Sign in with Google
                            </button>
                            <button class="btn btn-lg btn-block btn-primary mb-2" style="background-color: #3b5998;" type="submit">
                                <i class="fab fa-facebook-f me-2"></i> Sign in with Facebook
                            </button>
                            -->
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
