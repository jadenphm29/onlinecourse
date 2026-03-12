<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">

    <%-- Helper macro card thumbnail --%>

    <!-- ===== GUEST ===== -->
    <c:if test="${empty sessionScope.user}">
        <div class="page-header">
            <h2>&#127891; Welcome to EduOnline</h2>
            <a href="${pageContext.request.contextPath}/login"
               class="btn-approve" style="padding:9px 20px;">Login to Enroll</a>
        </div>
        <div class="course-grid">
            <c:forEach var="course" items="${courses}">
                <div class="course-card">
                    <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}">
                        <c:choose>
                            <c:when test="${not empty course.thumbnailUrl}">
                                <img src="${course.thumbnailUrl}" alt="${course.name}"
                                     class="course-thumb"
                                     onerror="this.style.display='none';this.nextElementSibling.style.display='flex';"/>
                                <div class="course-thumb-placeholder" style="display:none;">&#127891;</div>
                            </c:when>
                            <c:otherwise>
                                <div class="course-thumb-placeholder">&#127891;</div>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <div class="course-card-top">
                        <c:choose>
                            <c:when test="${course.status == 'ACTIVE'}">
                                <span class="badge badge-active">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-inactive">Inactive</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="course-card-body">
                        <h3>
                            <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}"
                               style="color:inherit; text-decoration:none;">${course.name}</a>
                        </h3>
                        <p class="course-desc">${course.description}</p>
                        <div class="course-meta">
                            <span>&#128197; ${course.scheduleText}</span>
                            <span style="font-weight:700; color:#4f46e5;">&#128176;
                                <fmt:formatNumber value="${course.tuitionFee}"
                                    type="number" groupingUsed="true"/> VND
                            </span>
                        </div>
                    </div>
                    <div class="course-card-footer">
                        <a href="${pageContext.request.contextPath}/login"
                           class="btn-approve" style="width:100%; text-align:center; padding:9px;">
                            &#128274; Login to Enroll
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${empty courses}"><p>No courses available.</p></c:if>
    </c:if>

    <!-- ===== STUDENT ===== -->
    <c:if test="${not empty sessionScope.user and sessionScope.role == 'STUDENT'}">
        <div class="page-header">
            <h2>Welcome, ${sessionScope.user.fullName}
                <c:choose>
                    <c:when test="${sessionScope.user.status == 'ACTIVE'}">
                        <span class="badge badge-active">Active</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge-locked">Locked</span>
                    </c:otherwise>
                </c:choose>
            </h2>
            <a href="${pageContext.request.contextPath}/courseList"
               class="btn-secondary" style="padding:9px 20px;">View All Courses</a>
        </div>

        <div class="course-grid">
            <c:forEach var="course" items="${courses}">
                <div class="course-card">
                    <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}">
                        <c:choose>
                            <c:when test="${not empty course.thumbnailUrl}">
                                <img src="${course.thumbnailUrl}" alt="${course.name}"
                                     class="course-thumb"
                                     onerror="this.style.display='none';this.nextElementSibling.style.display='flex';"/>
                                <div class="course-thumb-placeholder" style="display:none;">&#127891;</div>
                            </c:when>
                            <c:otherwise>
                                <div class="course-thumb-placeholder">&#127891;</div>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <div class="course-card-top">
                        <c:choose>
                            <c:when test="${course.status == 'ACTIVE'}">
                                <span class="badge badge-active">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-inactive">Inactive</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="course-card-body">
                        <h3>
                            <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}"
                               style="color:inherit; text-decoration:none;">${course.name}</a>
                        </h3>
                        <p class="course-desc">${course.description}</p>
                        <div class="course-meta">
                            <span>&#128100; ${course.instructorName}</span>
                            <span>&#128197; ${course.scheduleText}</span>
                            <span style="font-weight:700; color:#4f46e5;">&#128176;
                                <fmt:formatNumber value="${course.tuitionFee}"
                                    type="number" groupingUsed="true"/> VND
                            </span>
                        </div>
                    </div>
                    <div class="course-card-footer">
                        <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}"
                           class="btn-secondary" style="width:100%; text-align:center; padding:9px;">
                            &#128065; View Details
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${empty courses}"><p>No courses available.</p></c:if>
    </c:if>

    <!-- ===== INSTRUCTOR ===== -->
    <c:if test="${not empty sessionScope.user and sessionScope.role == 'INSTRUCTOR'}">
        <h2>Welcome, ${sessionScope.user.fullName}
            <span class="badge badge-active">Instructor</span>
        </h2>
        <div class="stats-grid">
            <div class="stat-box">
                <div class="stat-number">${instructorTotalCourses}</div>
                <div class="stat-label">My Courses</div>
            </div>
            <div class="stat-box">
                <div class="stat-number">${instructorTotalStudents}</div>
                <div class="stat-label">Students</div>
            </div>
            <div class="stat-box">
                <div class="stat-number">${instructorPendingCount}</div>
                <div class="stat-label">Pending</div>
            </div>
            <div class="stat-box">
                <div class="stat-number">${instructorApprovedCount}</div>
                <div class="stat-label">Approved</div>
            </div>
        </div>

        <h3>Your Courses</h3>
        <div class="course-grid">
            <c:forEach var="course" items="${courses}">
                <div class="course-card">
                    <a href="${pageContext.request.contextPath}/myCourses">
                        <c:choose>
                            <c:when test="${not empty course.thumbnailUrl}">
                                <img src="${course.thumbnailUrl}" alt="${course.name}"
                                     class="course-thumb"
                                     onerror="this.style.display='none';this.nextElementSibling.style.display='flex';"/>
                                <div class="course-thumb-placeholder" style="display:none;">&#127891;</div>
                            </c:when>
                            <c:otherwise>
                                <div class="course-thumb-placeholder">&#127891;</div>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <div class="course-card-top">
                        <c:choose>
                            <c:when test="${course.status == 'ACTIVE'}">
                                <span class="badge badge-active">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-inactive">Inactive</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="course-card-body">
                        <h3>${course.name}</h3>
                        <p class="course-desc">${course.description}</p>
                        <div class="course-meta">
                            <span>&#128197; ${course.scheduleText}</span>
                        </div>
                    </div>
                    <div class="course-card-footer">
                        <a href="${pageContext.request.contextPath}/myCourses"
                           class="btn-secondary" style="width:100%; text-align:center; padding:9px;">
                            &#9999; Manage
                        </a>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty courses}"><p>No courses yet.</p></c:if>
        </div>

        <h3>My Students</h3>
        <c:choose>
            <c:when test="${not empty instructorStudents}">
                <table class="student-table">
                    <thead>
                        <tr>
                            <th>#</th><th>Student</th><th>Email</th>
                            <th>Course</th><th>Date</th><th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" items="${instructorStudents}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${row.studentName}</td>
                                <td>${row.studentEmail}</td>
                                <td>${row.courseName}</td>
                                <td>${row.regDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${row.status=='PENDING'}">
                                            <span class="badge badge-pending">Pending</span>
                                        </c:when>
                                        <c:when test="${row.status=='APPROVED'}">
                                            <span class="badge badge-approved">Approved</span>
                                        </c:when>
                                        <c:when test="${row.status=='REJECTED'}">
                                            <span class="badge badge-rejected">Rejected</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-canceled">Canceled</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise><p>No students enrolled yet.</p></c:otherwise>
        </c:choose>
    </c:if>

    <!-- ===== ADMIN ===== -->
    <c:if test="${not empty sessionScope.user and sessionScope.role == 'ADMIN'}">
        <h2>Welcome, Administrator ${sessionScope.user.fullName}</h2>
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
        <h3>Management</h3>
        <div style="display:flex; gap:12px; flex-wrap:wrap;">
            <a href="${pageContext.request.contextPath}/admin/manageUsers"
               class="btn-approve" style="padding:11px 22px;">&#128101; Manage Users</a>
            <a href="${pageContext.request.contextPath}/admin/manageCourses"
               class="btn-approve" style="padding:11px 22px;">&#128218; Manage Courses</a>
            <a href="${pageContext.request.contextPath}/admin/statistics"
               class="btn-approve" style="padding:11px 22px;">&#128202; Statistics</a>
        </div>
    </c:if>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>