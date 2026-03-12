<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Courses</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <h2>Manage Courses</h2>

    <div style="margin-bottom:14px; display:flex; gap:10px; align-items:center;">
        <a href="${pageContext.request.contextPath}/admin/addCourse"
           class="btn-approve">+ Add Course</a>
        <a href="${pageContext.request.contextPath}/home">← Back to Home</a>
    </div>

    <c:choose>
        <c:when test="${not empty courses}">
            <table class="student-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Course Name</th>
                        <th>Instructor</th>
                        <th>Fee (VND)</th>
                        <th>Slots</th>
                        <th>Start Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${courses}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${c.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty c.instructorName}">
                                        ${c.instructorName}
                                        <c:if test="${c.instructorStatus == 'LOCKED'}">
                                            <span class="badge badge-locked">Locked</span>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color:#aaa;">— None —</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <fmt:formatNumber value="${c.tuitionFee}"
                                                  type="number" groupingUsed="true"/>
                            </td>
                            <td>${c.enrolledCount} / ${c.totalSlots}</td>
                            <td>${c.startDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${c.status == 'ACTIVE'}">
                                        <span class="badge badge-active">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-inactive">Inactive</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/editCourse?courseId=${c.courseId}">
                                    ✏ Edit
                                </a>
                                &nbsp;|&nbsp;
                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/manageCourses"
                                      style="display:inline;"
                                      onsubmit="return confirm('Delete course: ${c.name}?');">
                                    <input type="hidden" name="action"   value="delete"/>
                                    <input type="hidden" name="courseId" value="${c.courseId}"/>
                                    <button type="submit" class="btn-reject"
                                            style="padding:3px 8px; font-size:0.82rem;">
                                        🗑 Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No courses found.</p>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>