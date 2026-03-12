<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Course</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <h2>Add New Course</h2>
    <p><a href="${pageContext.request.contextPath}/admin/manageCourses">← Back to Manage Courses</a></p>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/addCourse"
          style="max-width:480px;">
        <p>
            <label>Course Name: *</label><br/>
            <input type="text" name="name" required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Description:</label><br/>
            <textarea name="description" rows="3"
                      style="width:100%; padding:6px;"></textarea>
        </p>
        <p>
            <label>Tuition Fee (VND): *</label><br/>
            <input type="number" name="tuitionFee" min="0" step="1000"
                   required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Start Date: *</label><br/>
            <input type="date" name="startDate" required style="padding:6px;"/>
        </p>
        <p>
            <label>End Date: *</label><br/>
            <input type="date" name="endDate" required style="padding:6px;"/>
        </p>
        <p>
            <label>Schedule:</label><br/>
            <input type="text" name="scheduleText"
                   placeholder="Mon - Wed, 08:00 AM - 10:00 AM"
                   style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Total Slots: *</label><br/>
            <input type="number" name="totalSlots" min="1" value="30"
                   required style="width:120px; padding:6px;"/>
        </p>
        <p>
            <label>Status:</label><br/>
            <select name="status" style="padding:6px;">
                <option value="ACTIVE">Active</option>
                <option value="INACTIVE">Inactive</option>
            </select>
        </p>
        <p>
            <label>Instructor:</label><br/>
            <select name="instructorId" style="width:100%; padding:6px;">
                <option value="">— No Instructor —</option>
                <c:forEach var="ins" items="${instructors}">
                    <option value="${ins.userId}">
                        ${ins.fullName} (${ins.email})
                        <c:if test="${ins.status == 'LOCKED'}"> [LOCKED]</c:if>
                    </option>
                </c:forEach>
            </select>
        </p>
        <p>
            <button type="submit" class="btn-approve">✔ Add Course</button>
            &nbsp;
            <a href="${pageContext.request.contextPath}/admin/manageCourses">Cancel</a>
        </p>
    </form>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>