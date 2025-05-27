<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="dao.FileMediaDAO" %>
<%@ page import="dao.LessonDAO" %>
<%@ page import="Model.FileMedia" %>
<%@ page import="Model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="java.util.logging.Level" %>
<html>
<head>
    <title>File Media Manager</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="number"], textarea {
            width: 100%;
            max-width: 500px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            resize: vertical;
            min-height: 100px;
        }
        button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
        }
        button:hover {
            background-color: #45a049;
        }
        .delete-btn {
            background-color: #d9534f;
        }
        .delete-btn:hover {
            background-color: #c9302c;
        }
        hr {
            margin: 20px 0;
        }
        .file-media {
            margin-left: 20px;
            padding: 10px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
        .log {
            color: blue;
            font-family: monospace;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
    <h2>File Media Manager</h2>

    <%
        Logger logger = Logger.getLogger("FileMediaManagerJSP");
        FileMediaDAO fileMediaDAO = new FileMediaDAO();
        LessonDAO lessonDAO = new LessonDAO();
        String action = request.getParameter("action");
        StringBuilder logMessages = new StringBuilder();
        String errorMessage = null;
        String successMessage = null;

        try {
            logger.info("Processing action: " + action);
            logMessages.append("Action: ").append(action != null ? action : "null").append("\n");

            if ("addFileMedia".equals(action)) {
                String lessonId = request.getParameter("lessonId");
                String fileId = request.getParameter("fileId");
                String fileName = request.getParameter("fileName");
                String fileType = request.getParameter("fileType");
                String fileUrl = request.getParameter("fileUrl");

                logger.info("Add FileMedia - Lesson ID: " + lessonId + ", File ID: " + fileId + ", File Name: " + fileName + ", File Type: " + fileType + ", File URL: " + fileUrl);
                logMessages.append("Add FileMedia - Lesson ID: ").append(lessonId != null ? lessonId : "null")
                           .append(", File ID: ").append(fileId != null ? fileId : "null")
                           .append(", File Name: ").append(fileName != null ? fileName : "null")
                           .append(", File Type: ").append(fileType != null ? fileType : "null")
                           .append(", File URL: ").append(fileUrl != null ? fileUrl : "null").append("\n");

                if (lessonId == null || lessonId.trim().isEmpty()) {
                    errorMessage = "Lesson ID cannot be empty.";
                    logger.warning("Lesson ID is null or empty");
                    logMessages.append("Error: Lesson ID is null or empty\n");
                } else {
                    Lesson lesson = lessonDAO.findById(lessonId);
                    logger.info("Lesson found: " + (lesson != null ? lesson.getTitle() : "null"));
                    logMessages.append("Lesson found: ").append(lesson != null ? lesson.getTitle() : "null").append("\n");

                    if (lesson == null) {
                        errorMessage = "Lesson with ID " + lessonId + " does not exist.";
                        logger.warning("Lesson not found for ID: " + lessonId);
                        logMessages.append("Error: Lesson not found for ID: ").append(lessonId).append("\n");
                    } else {
                        FileMedia fileMedia = new FileMedia(fileId, fileName, fileType, fileUrl, lesson);
                        logger.info("Calling FileMediaDAO.saveFileMedia");
                        logMessages.append("Calling saveFileMedia\n");
                        boolean success = fileMediaDAO.saveFileMedia(fileMedia);
                        logger.info("Save FileMedia result: " + success);
                        logMessages.append("Save FileMedia result: ").append(success).append("\n");

                        if (success) {
                            successMessage = "File Media added successfully.";
                            logger.info("File Media added successfully");
                            logMessages.append("Success: File Media added\n");
                        } else {
                            errorMessage = "Failed to add file media.";
                            logger.warning("Failed to add file media");
                            logMessages.append("Error: Failed to add file media\n");
                        }
                    }
                }
            } else if ("updateFileMedia".equals(action)) {
                Long fileMediaId = Long.parseLong(request.getParameter("fileMediaId"));
                String lessonId = request.getParameter("lessonId");
                String fileName = request.getParameter("fileName");
                String fileType = request.getParameter("fileType");
                String fileUrl = request.getParameter("fileUrl");

                logger.info("Update FileMedia - File Media ID: " + fileMediaId + ", Lesson ID: " + lessonId + ", File Name: " + fileName + ", File Type: " + fileType + ", File URL: " + fileUrl);
                logMessages.append("Update FileMedia - File Media ID: ").append(fileMediaId)
                           .append(", Lesson ID: ").append(lessonId != null ? lessonId : "null")
                           .append(", File Name: ").append(fileName != null ? fileName : "null")
                           .append(", File Type: ").append(fileType != null ? fileType : "null")
                           .append(", File URL: ").append(fileUrl != null ? fileUrl : "null").append("\n");

                FileMedia fileMedia = fileMediaDAO.findById(fileMediaId);
                if (fileMedia != null) {
                    logger.info("Found FileMedia ID: " + fileMediaId);
                    logMessages.append("Found FileMedia ID: ").append(fileMediaId).append("\n");

                    if (lessonId == null || lessonId.trim().isEmpty()) {
                        errorMessage = "Lesson ID cannot be empty.";
                        logger.warning("Lesson ID is null or empty for update");
                        logMessages.append("Error: Lesson ID is null or empty\n");
                    } else {
                        Lesson lesson = lessonDAO.findById(lessonId);
                        logger.info("Lesson found for update: " + (lesson != null ? lesson.getTitle() : "null"));
                        logMessages.append("Lesson found for update: ").append(lesson != null ? lesson.getTitle() : "null").append("\n");

                        if (lesson == null) {
                            errorMessage = "Lesson with ID " + lessonId + " does not exist.";
                            logger.warning("Lesson not found for ID: " + lessonId);
                            logMessages.append("Error: Lesson not found for ID: ").append(lessonId).append("\n");
                        } else {
                            fileMedia.setFileName(fileName);
                            fileMedia.setFileType(fileType);
                            fileMedia.setFileUrl(fileUrl);
                            fileMedia.setLesson(lesson);

                            logger.info("Calling FileMediaDAO.saveFileMedia for update");
                            logMessages.append("Calling saveFileMedia for update\n");
                            boolean success = fileMediaDAO.saveFileMedia(fileMedia);
                            logger.info("Update FileMedia result: " + success);
                            logMessages.append("Update FileMedia result: ").append(success).append("\n");

                            if (success) {
                                successMessage = "File Media updated successfully.";
                                logger.info("File Media updated successfully");
                                logMessages.append("Success: File Media updated\n");
                            } else {
                                errorMessage = "Failed to update file media.";
                                logger.warning("Failed to update file media");
                                logMessages.append("Error: Failed to update file media\n");
                            }
                        }
                    }
                } else {
                    errorMessage = "File Media not found.";
                    logger.warning("File Media not found for ID: " + fileMediaId);
                    logMessages.append("Error: File Media not found for ID: ").append(fileMediaId).append("\n");
                }
            } else if ("deleteFileMedia".equals(action)) {
                Long fileMediaId = Long.parseLong(request.getParameter("fileMediaId"));
                logger.info("Delete FileMedia - File Media ID: " + fileMediaId);
                logMessages.append("Delete FileMedia - File Media ID: ").append(fileMediaId).append("\n");

                logger.info("Calling FileMediaDAO.deleteFileMedia");
                logMessages.append("Calling deleteFileMedia\n");
                boolean success = fileMediaDAO.deleteFileMedia(fileMediaId);
                logger.info("Delete FileMedia result: " + success);
                logMessages.append("Delete FileMedia result: ").append(success).append("\n");

                if (success) {
                    successMessage = "File Media deleted successfully.";
                    logger.info("File Media deleted successfully");
                    logMessages.append("Success: File Media deleted\n");
                } else {
                    errorMessage = "Failed to delete file media.";
                    logger.warning("Failed to delete file media");
                    logMessages.append("Error: Failed to delete file media\n");
                }
            }
        } catch (Exception e) {
            errorMessage = "Unexpected error: " + e.getMessage();
            logger.log(Level.SEVERE, "Unexpected error in FileMediaManagerJSP", e);
            logMessages.append("Unexpected error: ").append(e.getClass().getName()).append(": ").append(e.getMessage()).append("\n");
        }

        // Lấy danh sách FileMedia theo lessonId (nếu có)
        String searchLessonId = request.getParameter("searchLessonId");
        List<FileMedia> fileMedias = null;
        if (searchLessonId != null && !searchLessonId.trim().isEmpty()) {
            try {
                logger.info("Searching FileMedia by Lesson ID: " + searchLessonId);
                logMessages.append("Searching FileMedia by Lesson ID: ").append(searchLessonId).append("\n");
                fileMedias = fileMediaDAO.findByLessonId(searchLessonId);
                logger.info("Found " + (fileMedias != null ? fileMedias.size() : 0) + " FileMedia");
                logMessages.append("Found ").append(fileMedias != null ? fileMedias.size() : 0).append(" FileMedia\n");
            } catch (Exception e) {
                errorMessage = "Failed to load file media list: " + e.getMessage();
                logger.log(Level.SEVERE, "Error searching FileMedia by Lesson ID: " + searchLessonId, e);
                logMessages.append("Error searching FileMedia: ").append(e.getClass().getName()).append(": ").append(e.getMessage()).append("\n");
            }
        }
    %>

    <c:if test="<%= errorMessage != null %>">
        <p class="error"><%= errorMessage %></p>
    </c:if>
    <c:if test="<%= successMessage != null %>">
        <p class="success"><%= successMessage %></p>
    </c:if>
    <c:if test="<%= logMessages.length() > 0 %>">
        <p class="log"><%= logMessages.toString() %></p>
    </c:if>

    <h3>Add New File Media</h3>
    <form action="fileMediaManager.jsp" method="post">
        <input type="hidden" name="fileId" value="<%= UUID.randomUUID().toString() %>"/>
        <div class="form-group">
            <label for="lessonId">Lesson ID:</label>
            <input type="text" id="lessonId" name="lessonId" required/>
        </div>
        <div class="form-group">
            <label for="fileName">File Name:</label>
            <input type="text" id="fileName" name="fileName" required/>
        </div>
        <div class="form-group">
            <label for="fileType">File Type:</label>
            <input type="text" id="fileType" name="fileType"/>
        </div>
        <div class="form-group">
            <label for="fileUrl">File URL:</label>
            <input type="text" id="fileUrl" name="fileUrl" required/>
        </div>
        <button type="submit" name="action" value="addFileMedia">Add File Media</button>
    </form>

    <hr>

    <h3>Search File Media by Lesson ID</h3>
    <form action="fileMediaManager.jsp" method="get">
        <div class="form-group">
            <label for="searchLessonId">Lesson ID:</label>
            <input type="text" id="searchLessonId" name="searchLessonId" value="<%= searchLessonId != null ? searchLessonId : "" %>"/>
        </div>
        <button type="submit">Search</button>
    </form>

    <c:if test="<%= fileMedias != null %>">
        <h3>File Media List</h3>
        <c:forEach var="fileMedia" items="<%= fileMedias %>">
            <div class="file-media">
                <p>File: ${fileMedia.fileName} (Type: ${fileMedia.fileType})</p>
                <p>URL: <a href="${fileMedia.fileUrl}" target="_blank">${fileMedia.fileUrl}</a></p>
                <p>Lesson ID: ${fileMedia.lesson.lessonId}</p>

                <form action="fileMediaManager.jsp" method="post" style="display:inline;">
                    <input type="hidden" name="fileMediaId" value="${fileMedia.id}"/>
                    <div class="form-group">
                        <label for="lessonId_${fileMedia.id}">Lesson ID:</label>
                        <input type="text" id="lessonId_${fileMedia.id}" name="lessonId" value="${fileMedia.lesson.lessonId}" required/>
                    </div>
                    <div class="form-group">
                        <label for="fileName_${fileMedia.id}">File Name:</label>
                        <input type="text" id="fileName_${fileMedia.id}" name="fileName" value="${fileMedia.fileName}" required/>
                    </div>
                    <div class="form-group">
                        <label for="fileType_${fileMedia.id}">File Type:</label>
                        <input type="text" id="fileType_${fileMedia.id}" name="fileType" value="${fileMedia.fileType}"/>
                    </div>
                    <div class="form-group">
                        <label for="fileUrl_${fileMedia.id}">File URL:</label>
                        <input type="text" id="fileUrl_${fileMedia.id}" name="fileUrl" value="${fileMedia.fileUrl}" required/>
                    </div>
                    <button type="submit" name="action" value="updateFileMedia">Update File Media</button>
                </form>

                <form action="fileMediaManager.jsp" method="post" style="display:inline;">
                    <input type="hidden" name="fileMediaId" value="${fileMedia.id}"/>
                    <button type="submit" class="delete-btn" name="action" value="deleteFileMedia" onclick="return confirm('Are you sure you want to delete this file media?')">Delete File Media</button>
                </form>
            </div>
        </c:forEach>
    </c:if>

</body>
</html>