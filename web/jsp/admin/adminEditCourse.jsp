<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Course - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <div class="page-header">
        <h2>&#9999; Edit Course</h2>
        <a href="${pageContext.request.contextPath}/admin/manageCourses"
           class="btn-secondary">&#8592; Back</a>
    </div>

    <div class="form-card" style="max-width:680px;">
        <form method="post" action="${pageContext.request.contextPath}/admin/editCourse">
            <input type="hidden" name="courseId" value="${course.courseId}"/>

            <div class="form-group">
                <label>Course Name *</label>
                <input type="text" name="name" value="${course.name}"
                       required style="width:100%;"/>
            </div>

            <div class="form-group">
                <label>Short Description</label>
                <textarea name="description" rows="3"
                          style="width:100%; resize:vertical;">${course.description}</textarea>
            </div>

            <div class="form-group">
                <label>&#128221; Detail Description</label>
                <textarea name="detailDescription" rows="6"
                          placeholder="What students will learn, prerequisites, course outline..."
                          style="width:100%; resize:vertical;">${course.detailDescription}</textarea>
            </div>

            <div style="display:grid; grid-template-columns:1fr 1fr; gap:16px;">
                <div class="form-group">
                    <label>Tuition Fee (VND) *</label>
                    <input type="number" name="tuitionFee" min="0" step="1000"
                           value="${course.tuitionFee}" required style="width:100%;"/>
                </div>
                <div class="form-group">
                    <label>Total Slots *</label>
                    <input type="number" name="totalSlots" min="1"
                           value="${course.totalSlots}" required style="width:100%;"/>
                </div>
            </div>

            <div style="display:grid; grid-template-columns:1fr 1fr; gap:16px;">
                <div class="form-group">
                    <label>Start Date *</label>
                    <input type="date" name="startDate" value="${course.startDate}"
                           required style="width:100%;"/>
                </div>
                <div class="form-group">
                    <label>End Date *</label>
                    <input type="date" name="endDate" value="${course.endDate}"
                           required style="width:100%;"/>
                </div>
            </div>

            <div class="form-group">
                <label>Schedule</label>
                <input type="text" name="scheduleText" value="${course.scheduleText}"
                       placeholder="Monday - Wednesday, 08:00 AM - 10:00 AM"
                       style="width:100%;"/>
            </div>

            <div style="display:grid; grid-template-columns:1fr 1fr; gap:16px;">
                <div class="form-group">
                    <label>Status</label>
                    <select name="status" style="width:100%;">
                        <option value="ACTIVE" ${course.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                        <option value="INACTIVE" ${course.status == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Instructor</label>
                    <select name="instructorId" style="width:100%;">
                        <option value="">— No Instructor —</option>
                        <c:forEach var="ins" items="${instructors}">
                            <option value="${ins.userId}"
                                ${ins.userId == course.instructorId ? 'selected' : ''}>
                                ${ins.fullName}
                                <c:if test="${ins.status == 'LOCKED'}"> [LOCKED]</c:if>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>&#128247; Thumbnail URL</label>
                <input type="text" name="thumbnailUrl"
                       value="${course.thumbnailUrl}"
                       placeholder="https://i.imgur.com/abc.jpg"
                       style="width:100%;"/>
                <c:if test="${not empty course.thumbnailUrl}">
                    <img src="${course.thumbnailUrl}" alt="preview"
                         style="margin-top:8px; width:100%; max-height:160px;
                                object-fit:cover; border-radius:8px;"/>
                </c:if>
                <small style="color:#6b7280;">Link ảnh trực tiếp từ imgur, cloudinary...</small>
            </div>

            <div class="form-group">
                <label>&#127910; Video URL (YouTube Embed)</label>
                <input type="text" name="videoUrl"
                       value="${course.videoUrl}"
                       placeholder="https://www.youtube.com/embed/xxxxxx"
                       style="width:100%;"/>
                <small style="color:#6b7280;">YouTube → Share → Embed → copy phần src="..."</small>
            </div>

            <div style="display:flex; gap:10px; margin-top:8px;">
                <button type="submit" class="btn-approve" style="padding:10px 28px;">
                    &#10003; Save Changes
                </button>
                <a href="${pageContext.request.contextPath}/admin/manageCourses"
                   class="btn-secondary" style="padding:10px 20px;">Cancel</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>