<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            /* Style cho Header */
            .header {
                background-color: white;
                padding: 20px 40px;
                display: flex;
                justify-content: center;
                align-items: center;
                position: fixed;
                width: 100%;
                top: 0;
                left: 0;
                z-index: 1000;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }

            /* Phần button điều hướng */
            .nav-buttons {
                display: flex;
                gap: 20px;
            }

            .nav-button {
                color: #333;
                text-decoration: none;
                font-weight: 500;
                padding: 8px 0;
                position: relative;
                transition: color 0.3s ease;
                background: none;
                border: none;
                font-size: 16px;
                cursor: pointer;
            }

            .nav-button:hover {
                color: #4CAF50;
            }

            .nav-button::after {
                content: '';
                position: absolute;
                width: 0;
                height: 2px;
                bottom: 0;
                left: 0;
                background-color: #4CAF50;
                transition: width 0.3s ease;
            }

            .nav-button:hover::after {
                width: 100%;
            }

            /* Style cho content phía dưới header */
            .content {
                margin-top: 80px;
                padding: 20px;
            }

            iframe {
                width: 100%;
                height: 600px;
                border: none;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <div class="header">
            <div class="nav-buttons">
                <button class="nav-button" onclick="loadPage('category')">Category</button>
                <button class="nav-button" onclick="loadPage('course')">Course</button>
            </div>
        </div>

        <!-- Content Area -->
        <div class="content">
            <iframe id="contentFrame" src="about:blank" name="contentFrame"></iframe>
        </div>

        <script>
            // Function to load the appropriate page into iframe
            function loadPage(page) {
                var frame = document.getElementById('contentFrame');
                switch(page) {
                    case 'category':
                        frame.src = "<%= request.getContextPath() %>/views/admin/managecategories.jsp";
                        break;
                    case 'course':
                        frame.src = "<%= request.getContextPath() %>/views/admin/showcourses.jsp";
                        break;
                    default:
                        frame.src = "about:blank";
                }
            }
        </script>
    </body>
</html>