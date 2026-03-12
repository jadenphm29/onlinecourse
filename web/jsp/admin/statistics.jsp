<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Statistics</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <h2>System Statistics</h2>
    <p><a href="${pageContext.request.contextPath}/home">← Back to Home</a></p>

    <div class="stats-grid">
        <div class="stat-box">
            <div class="stat-number">${totalStudents}</div>
            <div class="stat-label">Students</div>
        </div>
        <div class="stat-box">
            <div class="stat-number">${totalInstructors}</div>
            <div class="stat-label">Instructors</div>
        </div>
        <div class="stat-box">
            <div class="stat-number">${totalCourses}</div>
            <div class="stat-label">Courses</div>
        </div>
        <div class="stat-box">
            <div class="stat-number">${totalRegistrations}</div>
            <div class="stat-label">Registrations</div>
        </div>
    </div>

    <h3>Registrations per Course</h3>
    <c:choose>
        <c:when test="${not empty perCourse}">
            <table class="student-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Course Name</th>
                        <th>Total Registrations</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${perCourse}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${row.courseName}</td>
                            <td>${row.enrolledCount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No data available.</p>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>