<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Footer</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
        }

        /* Footer styling */
        .footer {
            background-color: #1c1c1c;
            color: #fff;
            padding: 60px 30px;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }

        /* Column styles */
        .footer .footer-column {
            flex: 1;
            min-width: 250px;
            margin: 10px;
        }

        /* Quick Link section */
        .footer .footer-column h3 {
            font-size: 20px;
            margin-bottom: 20px;
        }

        .footer .footer-column ul {
            list-style-type: none;
            padding: 0;
        }

        .footer .footer-column ul li a {
            text-decoration: none;
            color: #fff;
            font-size: 14px;
            display: block;
            margin-bottom: 10px;
            transition: color 0.3s ease;
        }

        .footer .footer-column ul li a:hover {
            color: #00bcd4;
        }

        /* Contact section */
        .footer .footer-column .contact-info i {
            margin-right: 10px;
            font-size: 18px;
        }

        .footer .footer-column .contact-info p {
            margin-bottom: 10px;
            font-size: 14px;
        }

        /* Gallery section */
        .footer .footer-column .gallery img {
            width: 60px;
            height: 60px;
            margin: 5px;
            object-fit: cover;
            border-radius: 5px;
        }

        /* Newsletter section */
        .footer .footer-column .newsletter input[type="email"] {
            padding: 10px;
            width: 200px;
            border: none;
            border-radius: 5px;
            margin-right: 10px;
            font-size: 14px;
        }

        .footer .footer-column .newsletter button {
            background-color: #00bcd4;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        .footer .footer-column .newsletter button:hover {
            background-color: #0097a7;
        }

        /* Bottom section */
        .footer .footer-bottom {
            text-align: center;
            font-size: 12px;
            margin-top: 20px;
            border-top: 1px solid #444;
            padding-top: 10px;
        }

        .footer .footer-bottom a {
            color: #fff;
            text-decoration: none;
        }

        .footer .footer-bottom a:hover {
            color: #00bcd4;
        }

        /* Scroll-to-top button */
        .scroll-to-top {
            position: fixed;
            bottom: 20px;
            right: 30px;
            background-color: #00bcd4;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 50%;
            cursor: pointer;
        }

        .scroll-to-top:hover {
            background-color: #0097a7;
        }

    </style>
</head>
<body>

    <footer class="footer">
        <!-- Quick Link Column -->
        <div class="footer-column">
            <h3>Quick Link</h3>
            <ul>
                <li><a href="#">About Us</a></li>
                <li><a href="#">Contact Us</a></li>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Terms & Condition</a></li>
                <li><a href="#">FAQs & Help</a></li>
            </ul>
        </div>

        <!-- Contact Column -->
        <div class="footer-column">
            <h3>Contact</h3>
            <div class="contact-info">
                <p><i class="fas fa-map-marker-alt"></i> 123 Street, New York, USA</p>
                <p><i class="fas fa-phone"></i> +012 345 67890</p>
                <p><i class="fas fa-envelope"></i> info@example.com</p>
                <div>
                    <a href="#" class="social-icon"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-youtube"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-linkedin-in"></i></a>
                </div>
            </div>
        </div>

        <!-- Gallery Column -->
        <div class="footer-column">
            <h3>Gallery</h3>
            <div class="gallery">
                <img src="https://via.placeholder.com/60" alt="Gallery Image 1">
                <img src="https://via.placeholder.com/60" alt="Gallery Image 2">
                <img src="https://via.placeholder.com/60" alt="Gallery Image 3">
                <img src="https://via.placeholder.com/60" alt="Gallery Image 4">
                <img src="https://via.placeholder.com/60" alt="Gallery Image 5">
                <img src="https://via.placeholder.com/60" alt="Gallery Image 6">
            </div>
        </div>

        <!-- Newsletter Column -->
        <div class="footer-column">
            <h3>Newsletter</h3>
            <p>Dolor amet sit justo amet elitr clita ipsum elitr est.</p>
            <div class="newsletter">
                <input type="email" placeholder="Your email">
                <button type="button">SignUp</button>
            </div>
        </div>
    </footer>

    <!-- Footer Bottom -->
    <div class="footer-bottom">
        <p>&copy; 2025 Your Site Name, All Rights Reserved. Designed By <a href="#">HTML Codex</a></p>
        <p>Distributed By <a href="#">ThemeWagon</a></p>
    </div>

    <!-- Scroll to Top Button -->
    <button class="scroll-to-top" onclick="scrollToTop()">↑</button>

    <script>
        function scrollToTop() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }
    </script>

</body>
</html>
