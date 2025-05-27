<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hiển thị video YouTube</title>
</head>
<body>
    <h2>Nhập URL video YouTube</h2>

    <form method="post">
        <label for="youtubeUrl">URL YouTube:</label>
        <input type="text" id="youtubeUrl" name="youtubeUrl" placeholder="Ví dụ: https://www.youtube.com/watch?v=7kO_ALcwNAw" size="50" required>
        <button type="submit">Xem Video</button>
    </form>

    <br>

    <%
        String youtubeUrl = request.getParameter("youtubeUrl");
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            // Lấy ID video từ URL YouTube
            String videoId = null;
            if (youtubeUrl.contains("v=")) {
                videoId = youtubeUrl.substring(youtubeUrl.indexOf("v=") + 2);
                if (videoId.contains("&")) {
                    videoId = videoId.substring(0, videoId.indexOf("&"));
                }
            }

            // Nếu có ID video, hiển thị iframe
            if (videoId != null) {
    %>
                <h3>Video YouTube</h3>
                <iframe width="560" height="315" src="https://www.youtube.com/embed/<%= videoId %>" frameborder="0" allowfullscreen></iframe>
    <%
            } else {
    %>
                <p style="color: red;">URL YouTube không hợp lệ.</p>
    <%
            }
        }
    %>
</body>
</html>
