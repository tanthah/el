<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Học: ${course.title}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        h2, h3, h4, h5 {
            color: #333;
        }
        .course-section {
            margin-bottom: 20px;
        }
        .thumbnail-preview {
            max-width: 200px;
            max-height: 200px;
            margin-top: 10px;
            border-radius: 4px;
        }
        hr {
            border: 0;
            border-top: 1px solid #ddd;
            margin: 20px 0;
        }
        details {
            margin: 10px 0;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #fafafa;
        }
        details summary {
            cursor: pointer;
            font-weight: bold;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        details summary span {
            display: inline-block;
        }
        .details-content {
            padding: 15px;
            background-color: #fff;
            border-top: 1px solid #ddd;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group p {
            margin: 0;
            padding: 8px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        a {
            color: #0066cc;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .empty-message {
            font-style: italic;
            color: #666;
            margin-left: 20px;
        }
        .video-container {
            margin-bottom: 15px;
        }
        .video-container iframe {
            width: 100%;
            max-width: 560px;
            height: 315px;
            border: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Học Khóa học: <c:out value="${course.title}"/></h2>

        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>

        <%-- Thông tin khóa học --%>
        <section class="course-section">
            <h3>Thông tin khóa học</h3>
            <div class="form-group">
                <label>Tiêu đề:</label>
                <p><c:out value="${course.title}"/></p>
            </div>
           
       
            
          
        </section>

        <hr>

        <%-- Phần các chương --%>
        <section>
            <h3>Các chương trong khóa học</h3>
            
            <c:forEach var="chapter" items="${course.chapters}" varStatus="chapterStatus">
                <details class="chapter-wrapper" <c:if test="${chapterStatus.first}">open</c:if>>
                    <summary>
                        <span>Chương ${chapter.chapterOrder}: <c:out value="${chapter.title}"/></span>
                        <span>(Nhấn để mở/đóng)</span>
                    </summary>
                    <div class="details-content">
                        <div class="form-group">
                            <label>Tiêu đề chương:</label>
                            <p><c:out value="${chapter.title}"/></p>
                        </div>
                        <div class="form-group">
                            <label>Thứ tự chương:</label>
                            <p><c:out value="${chapter.chapterOrder}"/></p>
                        </div>
                        <hr style="margin: 20px 0;">

                        <%-- Phần các bài học trong chương --%>
                        <div>
                            <h4 style="margin-bottom: 10px;">Các bài học trong "<c:out value="${chapter.title}"/>"</h4>

                            <c:forEach var="lesson" items="${chapter.lessons}" varStatus="lessonStatus">
                                <details class="lesson-wrapper">
                                    <summary>
                                        <span>Bài học ${lesson.lessonIndex}: <c:out value="${lesson.title}"/></span>
                                        <span>(Nhấn để mở/đóng)</span>
                                    </summary>
                                    <div class="details-content">
                                        <div class="form-group">
                                            <label>Tiêu đề bài học:</label>
                                            <p><c:out value="${lesson.title}"/></p>
                                        </div>
                                        <div class="form-group">
                                            <label>Thứ tự:</label>
                                            <p><c:out value="${lesson.lessonIndex}"/></p>
                                        </div>
                                        <div class="form-group">
                                            <label>Mô tả:</label>
                                            <p><c:out value="${lesson.description}" default="Không có mô tả."/></p>
                                        </div>
                                        <hr style="margin: 15px 0;">

                                        <%-- Phần Media Files trong bài học --%>
                                        <div>
                                            <h5 style="margin-bottom: 10px;">Tệp phương tiện trong "<c:out value="${lesson.title}"/>"</h5>
                                            
                                            <%-- Hiển thị video YouTube cho file .mp4 --%>
                                            <c:forEach var="fileMedia" items="${lesson.fileMedias}" varStatus="fileStatus">
                                                <c:if test="${fileMedia.fileType == '.mp4' && fileStatus.first}">
                                                    <div class="video-container">
                                                        <%
                                                            String youtubeUrl = ((Model.FileMedia)pageContext.getAttribute("fileMedia")).getFileUrl();
                                                            String videoId = null;
                                                            if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
                                                                if (youtubeUrl.contains("v=")) {
                                                                    videoId = youtubeUrl.substring(youtubeUrl.indexOf("v=") + 2);
                                                                    if (videoId.contains("&")) {
                                                                        videoId = videoId.substring(0, videoId.indexOf("&"));
                                                                    }
                                                                }
                                                            }
                                                        %>
                                                        <% if (videoId != null) { %>
                                                            <iframe width="560" height="315" src="https://www.youtube.com/embed/<%= videoId %>" 
                                                                    title="Video YouTube" frameborder="0" 
                                                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                                                    allowfullscreen></iframe>
                                                        <% } else { %>
                                                            <p style="color: red;">URL YouTube không hợp lệ cho tệp: <c:out value="${fileMedia.fileName}"/></p>
                                                        <% } %>
                                                    </div>
                                                </c:if>
                                            </c:forEach>

                                            <%-- Liệt kê tất cả FileMedia --%>
                                            <c:forEach var="fileMedia" items="${lesson.fileMedias}" varStatus="fileStatus">
                                                <details class="file-media-wrapper">
                                                    <summary>
                                                        <span><c:out value="${fileMedia.fileName}"/> (Loại: <c:out value="${fileMedia.fileType}"/>)</span>
                                                        <span>(Nhấn để xem chi tiết)</span>
                                                    </summary>
                                                    <div class="details-content">
                                                        <div class="form-group">
                                                            <label>Tên tệp:</label>
                                                            <p><c:out value="${fileMedia.fileName}"/></p>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Loại tệp:</label>
                                                            <p><c:out value="${fileMedia.fileType}" default="Không xác định"/></p>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>URL tệp:</label>
                                                            <p><a href="${fileMedia.fileUrl}" target="_blank"><c:out value="${fileMedia.fileUrl}"/></a></p>
                                                        </div>
                                                    </div>
                                                </details>
                                            </c:forEach>
                                            <c:if test="${empty lesson.fileMedias}">
                                                <p class="empty-message">Chưa có tệp phương tiện trong bài học này.</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </details>
                            </c:forEach>
                            <c:if test="${empty chapter.lessons}">
                                <p class="empty-message">Chưa có bài học trong chương này.</p>
                            </c:if>
                        </div>
                    </div>
                </details>
            </c:forEach>
            <c:if test="${empty course.chapters}">
                <p class="empty-message">Chưa có chương trong khóa học này.</p>
            </c:if>
        </section>
    </div>
</body>
</html>