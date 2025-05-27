<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Course - <c:out value="${course.title}"/></title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f8f9fa;
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        h2, h3, h4, h5, h6 {
            color: #0056b3; 
            margin-top: 1.5em;
            margin-bottom: 0.8em;
        }
        h2 { margin-top: 0; }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 6px;
            color: #555;
        }

        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1rem;
        }
        
        input[type="text"]:focus,
        input[type="number"]:focus,
        textarea:focus,
        select:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            outline: none;
        }

        textarea {
            resize: vertical;
            min-height: 80px; /* Adjusted min-height */
        }

        button, .button-link {
            padding: 10px 18px; /* Slightly adjusted padding */
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 0.9rem; /* Adjusted font size */
            font-weight: 500;
            text-decoration: none; 
            display: inline-block; 
            transition: background-color 0.2s ease-in-out, color 0.2s ease-in-out;
            margin-right: 8px; 
            margin-bottom: 8px; 
        }
        button:last-of-type, .button-link:last-of-type { /* Use last-of-type for better accuracy */
            margin-right: 0;
        }


        .btn-primary { background-color: #007bff; color: white; }
        .btn-primary:hover { background-color: #0056b3; }

        .btn-success { background-color: #28a745; color: white; }
        .btn-success:hover { background-color: #1e7e34; }
        
        .btn-info { background-color: #17a2b8; color: white; }
        .btn-info:hover { background-color: #117a8b; }

        .btn-danger, .delete-btn { background-color: #dc3545; color: white; } /* Merged .delete-btn */
        .btn-danger:hover, .delete-btn:hover { background-color: #c82333; }
        
        .btn-secondary { background-color: #6c757d; color: white; }
        .btn-secondary:hover { background-color: #545b62; }


        hr {
            margin: 25px 0; /* Adjusted margin */
            border: 0;
            border-top: 1px solid #dee2e6;
        }

        .course-section, .chapter-wrapper, .lesson-wrapper, .file-media-wrapper {
            padding: 15px;
            border: 1px solid #e0e0e0;
            border-radius: 6px;
            margin-bottom: 20px;
        }
        .course-section { background-color: #e9ecef; } /* Course info distinct */
        .chapter-wrapper { background-color: #f9f9f9; }
        .lesson-wrapper { background-color: #ffffff; margin-left: 20px; border-left: 3px solid #007bff;} /* Lessons indented and accented */
        .file-media-wrapper { background-color: #f7f7f7; margin-left: 20px; border-left: 3px solid #17a2b8;} /* File media indented and accented */


        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
        }
        .section-header h3, .section-header h4, .section-header h5, .section-header h6 {
            margin: 0;
        }

        .actions-group {
            display: flex;
            flex-wrap: wrap; 
            gap: 10px;
            margin-top: 15px;
        }
        
        /* Make forms inside actions-group behave as inline elements */
        .actions-group form {
            display: inline; /* Critical for original layout of delete buttons */
        }


        details {
            border: 1px solid #ddd;
            border-radius: 6px;
            margin-bottom: 15px;
            background-color: #fff; 
        }

        summary {
            padding: 10px 15px; /* Adjusted padding */
            font-weight: bold;
            cursor: pointer;
            background-color: #eef1f2;
            border-radius: 5px; /* Apply to all corners if details content is hidden initially */
            display: flex;
            justify-content: space-between;
            align-items: center;
            list-style-position: inside; 
        }
        summary:hover {
            background-color: #e2e6ea;
        }
        details[open] > summary {
            border-bottom: 1px solid #ddd;
            border-bottom-left-radius: 0;
            border-bottom-right-radius: 0;
        }

        .details-content {
            padding: 15px; /* Adjusted padding */
            border-top: 1px solid #ddd; 
        }
        details[open] > .details-content {
             background-color: #ffffff; 
        }
        
        .error {
            color: #721c24; 
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .success {
            color: #155724;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .thumbnail-preview-container {
            margin-top: 10px;
        }
        .thumbnail-preview {
            max-width: 120px; /* Adjusted size */
            max-height: 120px;
            border: 1px solid #ddd;
            padding: 4px;
            border-radius: 4px;
            display: block; 
        }
        .choose-image-link {
            display: inline-block;
            margin-top: 8px; 
            color: #007bff;
            text-decoration: none;
        }
        .choose-image-link:hover {
            text-decoration: underline;
        }

        /* Common style for add forms inside details */
        details.add-form-container > summary {
            font-weight: normal; /* Less prominent for "Add new..." */
            background-color: #f0f8ff; /* AliceBlue for add summaries */
        }
        details.add-form-container > summary:hover {
            background-color: #e6f3ff;
        }
        details.add-form-container > .details-content {
            background-color: #f8fcff; /* Lighter background for add form content */
        }
        details.add-form-container h5, details.add-form-container h6 { /* Specific heading style for add forms */
            margin-top: 0;
            color: #0069d9;
        }


    </style>
</head>
<body>
    <div class="container">
        <h2><c:out value="${not empty course.title ? 'Edit Course: ' : 'Create New Course'}"/> <c:out value="${course.title}"/></h2>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p class="success">${message}</p>
        </c:if>

        <%-- Course Details Form --%>
        <section class="course-section">
            <h3>Course Information</h3>
            <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                <input type="hidden" name="courseId" value="${course.courseId}"/>
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" value="${course.title}" required>
                </div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" id="price" name="price" value="${course.price}" step="0.01" min="0" required>
                </div>
                <div class="form-group">
                    <label for="thumbnail">Thumbnail URL:</label>
                    <input type="text" id="thumbnail" name="thumbnail" value="${course.thumbnail}" placeholder="Enter image path or URL">
                     <div class="thumbnail-preview-container">
                        <c:if test="${not empty course.thumbnail}">
                            <img src="${pageContext.request.contextPath}/${course.thumbnail}" alt="Preview" class="thumbnail-preview"/>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/select-image" target="_blank" class="choose-image-link button-link btn-secondary">Choose/Upload Image</a>
                    </div>
                </div>
                <div class="form-group">
                    <label for="categoryId">Category:</label>
                    <select id="categoryId" name="categoryId" required>
                        <option value="">-- Select Category --</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}" ${category.categoryId == course.category.categoryId ? 'selected' : ''}>
                                <c:out value="${category.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="descriptionContent">Description:</label>
                    <textarea id="descriptionContent" name="descriptionContent" rows="4" required>${course.description.content}</textarea>
                </div>
                <button type="submit" name="action" value="updateCourse" class="btn-primary">Save Course Details</button>
            </form>
        </section>

        <hr>

        <%-- Chapters Section --%>
        <section>
            <div class="section-header">
                 <h3>Course Chapters</h3>
            </div>

            <%-- Add New Chapter Form --%>
            <details class="add-form-container">
                <summary>+ Add New Chapter</summary>
                <div class="details-content">
                    <h5>New Chapter Details</h5>
                    <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                        <input type="hidden" name="courseId" value="${course.courseId}"/>
                        <div class="form-group">
                            <label for="chapterTitle_new">Chapter Title:</label>
                            <input type="text" id="chapterTitle_new" name="chapterTitle" required/>
                        </div>
                        <div class="form-group">
                            <label for="chapterOrder_new">Chapter Order:</label>
                            <input type="number" id="chapterOrder_new" name="chapterOrder" value="${course.chapters.size() + 1}" required min="0" step="1"/>
                        </div>
                        <button type="submit" name="action" value="addChapter" class="btn-success">Add Chapter</button>
                    </form>
                </div>
            </details>
            
            <c:forEach var="chapter" items="${course.chapters}" varStatus="chapterStatus">
                <details class="chapter-wrapper" <c:if test="${chapterStatus.first}">open</c:if>>
                    <summary>
                        <span>Chapter ${chapter.chapterOrder}: <c:out value="${chapter.title}"/></span>
                        <span>(Click to expand/collapse)</span>
                    </summary>
                    <div class="details-content">
                        <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                            <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                            <input type="hidden" name="courseId" value="${course.courseId}"/>
                            <div class="form-group">
                                <label for="chapterTitle_${chapter.chapterId}">Chapter Title:</label>
                                <input type="text" id="chapterTitle_${chapter.chapterId}" name="chapterTitle" value="${chapter.title}" required/>
                            </div>
                            <div class="form-group">
                                <label for="chapterOrder_${chapter.chapterId}">Chapter Order:</label>
                                <input type="number" id="chapterOrder_${chapter.chapterId}" name="chapterOrder" value="${chapter.chapterOrder}" required min="0" step="1"/>
                            </div>
                            <div class="actions-group">
                                <button type="submit" name="action" value="updateChapter" class="btn-primary">Update Chapter</button>
                                <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                    <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                    <button type="submit" class="delete-btn" name="action" value="delChapter" onclick="return confirm('Are you sure you want to delete chapter \'${chapter.title}\' and all its lessons?')">Delete Chapter</button>
                                </form>
                            </div>
                        </form>
                        <hr style="margin: 20px 0;">

                        <%-- Lessons Section within Chapter --%>
                        <div>
                             <h4 style="margin-bottom: 10px;">Lessons in "<c:out value="${chapter.title}"/>"</h4>

                            <%-- Add New Lesson Form for this Chapter --%>
                            <details class="add-form-container">
                                <summary>+ Add New Lesson to "<c:out value="${chapter.title}"/>"</summary>
                                <div class="details-content">
                                    <h5>New Lesson Details</h5>
                                    <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                        <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                        <%-- Re-instating courseId here if your backend needs it, though original addLesson didn't have it explicitly in its own hidden field --%>
                                        <input type="hidden" name="courseId" value="${course.courseId}"/> 
                                        <%-- UUID for new lessonId, as in original JSP structure for adding items --%>
                                        <input type="hidden" name="lessonId" value="<%= java.util.UUID.randomUUID().toString() %>"/>
                                        <div class="form-group">
                                            <label for="lessonTitle_new_${chapter.chapterId}">Lesson Title:</label>
                                            <input type="text" id="lessonTitle_new_${chapter.chapterId}" name="lessonTitle" required/>
                                        </div>
                                        <div class="form-group">
                                            <label for="lessonIndex_new_${chapter.chapterId}">Lesson Index:</label>
                                            <input type="number" id="lessonIndex_new_${chapter.chapterId}" name="lessonIndex" value="${chapter.lessons.size() + 1}" required min="0" step="1"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="lessonDescription_new_${chapter.chapterId}">Description:</label>
                                            <textarea id="lessonDescription_new_${chapter.chapterId}" name="lessonDescription" rows="3"></textarea>
                                        </div>
                                        <button type="submit" name="action" value="addLesson" class="btn-success">Add Lesson</button>
                                    </form>
                                </div>
                            </details>

                            <c:forEach var="lesson" items="${chapter.lessons}" varStatus="lessonStatus">
                                <details class="lesson-wrapper">
                                    <summary>
                                        <span>Lesson ${lesson.lessonIndex}: <c:out value="${lesson.title}"/></span>
                                        <span>(Click to expand/collapse)</span>
                                    </summary>
                                    <div class="details-content">
                                        <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                            <input type="hidden" name="lessonId" value="${lesson.lessonId}"/>
                                            <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                            <%-- Add courseId here if your updateLesson action needs it --%>
                                            <input type="hidden" name="courseId" value="${course.courseId}"/>
                                            <div class="form-group">
                                                <label for="lessonTitle_${lesson.lessonId}">Lesson Title:</label>
                                                <input type="text" id="lessonTitle_${lesson.lessonId}" name="lessonTitle" value="${lesson.title}" required/>
                                            </div>
                                            <div class="form-group">
                                                <label for="lessonIndex_${lesson.lessonId}">Index:</label>
                                                <input type="number" id="lessonIndex_${lesson.lessonId}" name="lessonIndex" value="${lesson.lessonIndex}" required min="0" step="1"/>
                                            </div>
                                            <div class="form-group">
                                                <label for="lessonDescription_${lesson.lessonId}">Description:</label>
                                                <textarea id="lessonDescription_${lesson.lessonId}" name="lessonDescription" rows="3">${lesson.description}</textarea>
                                            </div>
                                            <div class="actions-group">
                                                <button type="submit" name="action" value="updateLesson" class="btn-info">Update Lesson</button>
                                                <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                                    <input type="hidden" name="lessonId" value="${lesson.lessonId}"/>
                                                    <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                                    <button type="submit" class="delete-btn" name="action" value="deleteLesson" onclick="return confirm('Are you sure you want to delete lesson \'${lesson.title}\' and all its media?')">Delete Lesson</button>
                                                </form>
                                            </div>
                                        </form>
                                        <hr style="margin: 15px 0;">

                                        <%-- File Media Section within Lesson --%>
                                        <div>
                                            <h5 style="margin-bottom:10px;">Media Files in "<c:out value="${lesson.title}"/>"</h5>
                                            
                                            <%-- Add New File Media Form for this Lesson (MATCHING ORIGINAL STRUCTURE) --%>
                                            <details class="add-form-container">
                                                <summary>+ Add New File/Media to "<c:out value="${lesson.title}"/>"</summary>
                                                <div class="details-content">
                                                     <h6>New File/Media Details</h6>
                                                    <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                                        <input type="hidden" name="lessonId" value="${lesson.lessonId}"/>
                                                        <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                                        <%-- REINSTATED FROM ORIGINAL JSP FOR ADD FILE MEDIA --%>
                                                        <input type="hidden" name="fileId" value="<%= java.util.UUID.randomUUID().toString() %>"/>
                                                        <div class="form-group">
                                                            <%-- Using a more unique ID for the label 'for' attribute --%>
                                                            <label for="fileName_new_fm_${lesson.lessonId}">New File Name:</label>
                                                            <input type="text" id="fileName_new_fm_${lesson.lessonId}" name="fileName" required/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="fileType_new_fm_${lesson.lessonId}">File Type:</label>
                                                            <input type="text" id="fileType_new_fm_${lesson.lessonId}" name="fileType"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="fileUrl_new_fm_${lesson.lessonId}">File URL:</label>
                                                            <input type="text" id="fileUrl_new_fm_${lesson.lessonId}" name="fileUrl" required/>
                                                        </div>
                                                        <button type="submit" name="action" value="addFileMedia" class="btn-success">Add File Media</button>
                                                    </form>
                                                </div>
                                            </details>

                                            <c:forEach var="fileMedia" items="${lesson.fileMedias}" varStatus="fileStatus">
                                                <details class="file-media-wrapper">
                                                    <summary>
                                                        <span>${fileMedia.fileName} (Type: <c:out value="${fileMedia.fileType}"/>)</span>
                                                        <span>(Click to edit)</span>
                                                    </summary>
                                                    <div class="details-content">
                                                        <p>URL: <a href="${fileMedia.fileUrl}" target="_blank">${fileMedia.fileUrl}</a></p>
                                                        <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                                            <input type="hidden" name="fileMediaId" value="${fileMedia.id}"/>
                                                            <input type="hidden" name="lessonId" value="${lesson.lessonId}"/>
                                                            <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                                             <%-- Add courseId here if your updateFileMedia action needs it --%>
                                                            <input type="hidden" name="courseId" value="${course.courseId}"/>
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
                                                            <div class="actions-group">
                                                                <button type="submit" name="action" value="updateFileMedia" class="btn-info">Update File Media</button>
                                                                <form action="${pageContext.request.contextPath}/teacher/edit-course" method="post">
                                                                    <input type="hidden" name="fileMediaId" value="${fileMedia.id}"/>
                                                                    <input type="hidden" name="lessonId" value="${lesson.lessonId}"/>
                                                                    <input type="hidden" name="chapterId" value="${chapter.chapterId}"/>
                                                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                                                    <button type="submit" class="delete-btn" name="action" value="deleteFileMedia" onclick="return confirm('Are you sure you want to delete this file media: \'${fileMedia.fileName}\'?')">Delete File Media</button>
                                                                </form>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </details>
                                            </c:forEach>
                                            <c:if test="${empty lesson.fileMedias}">
                                                <p style="font-style: italic; margin-left: 20px;">No media files in this lesson yet.</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </details>
                            </c:forEach>
                            <c:if test="${empty chapter.lessons}">
                                <p style="font-style: italic;">No lessons in this chapter yet.</p>
                            </c:if>
                        </div>
                    </div>
                </details>
            </c:forEach>
            <c:if test="${empty course.chapters}">
                <p style="font-style: italic;">No chapters in this course yet. Add one using the form above.</p>
            </c:if>
        </section>
    </div>
</body>
</html>