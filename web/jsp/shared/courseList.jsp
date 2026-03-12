<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">

    <div class="page-header">
        <h2>&#128218; All Courses</h2>
        <span style="color:#6b7280; font-size:14px;">
            ${totalCourses} courses available
        </span>
    </div>

    <div class="course-grid">
        <c:forEach var="course" items="${pagedCourses}">
            <div class="course-card">

                <%-- Thumbnail — chỉ hiện ảnh, không wrap link --%>
                <c:choose>
                    <c:when test="${not empty course.thumbnailUrl}">
                        <img src="${course.thumbnailUrl}"
                             alt="${course.name}"
                             class="course-thumb"
                             onerror="this.style.display='none';
                                      this.nextElementSibling.style.display='flex';"/>
                        <div class="course-thumb-placeholder" style="display:none;">&#127891;</div>
                    </c:when>
                    <c:otherwise>
                        <div class="course-thumb-placeholder">&#127891;</div>
                    </c:otherwise>
                </c:choose>

                <div class="course-card-top">
                    <c:choose>
                        <c:when test="${course.status == 'ACTIVE'}">
                            <span class="badge badge-active">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-inactive">Inactive</span>
                        </c:otherwise>
                    </c:choose>
                    <span class="course-slots
                        <c:choose>
                            <c:when test='${course.enrolledCount >= course.totalSlots}'>slots-full</c:when>
                            <c:when test='${course.totalSlots - course.enrolledCount <= 5}'>slots-near</c:when>
                            <c:otherwise>slots-ok</c:otherwise>
                        </c:choose>">
                        ${course.enrolledCount}/${course.totalSlots} slots
                    </span>
                </div>

                <div class="course-card-body">
                    <h3>${course.name}</h3>
                    <p class="course-desc">${course.description}</p>
                    <div class="course-meta">
                        <span>&#128100; ${course.instructorName}
                            <c:if test="${course.instructorStatus == 'LOCKED'}">
                                <span class="badge badge-locked" style="font-size:10px;">Locked</span>
                            </c:if>
                        </span>
                        <span>&#128197; ${course.scheduleText}</span>
                        <span>&#128198; ${course.startDate} &#8594; ${course.endDate}</span>
                        <span style="font-weight:700; color:#4f46e5;">
                            &#128176;
                            <fmt:formatNumber value="${course.tuitionFee}"
                                type="number" groupingUsed="true"/> VND
                        </span>
                    </div>
                    <div class="slot-bar-wrap" style="margin-top:10px;">
                        <div class="slot-bar-fill
                            <c:choose>
                                <c:when test='${course.enrolledCount >= course.totalSlots}'>full</c:when>
                                <c:when test='${course.totalSlots - course.enrolledCount <= 5}'>near</c:when>
                            </c:choose>"
                            style="width:${course.totalSlots > 0 ?
                                (course.enrolledCount * 100 / course.totalSlots) : 0}%;">
                        </div>
                    </div>
                </div>

                <div class="course-card-footer">

                    <%-- STUDENT --%>
                    <c:if test="${sessionScope.role == 'STUDENT'}">
                        <c:set var="enrolled"   value="false"/>
                        <c:set var="regStatus"  value=""/>
                        <c:set var="regId"      value=""/>
                        <c:set var="payStatus"  value=""/>
                        <c:forEach var="reg" items="${myRegistrations}">
                            <c:if test="${reg.courseId == course.courseId}">
                                <c:set var="enrolled"  value="true"/>
                                <c:set var="regStatus" value="${reg.status}"/>
                                <c:set var="regId"     value="${reg.regId}"/>
                                <c:set var="payStatus" value="${reg.paymentStatus}"/>
                            </c:if>
                        </c:forEach>

                        <c:choose>
                            <%-- Đã APPROVED → nút Start Learning --%>
                            <c:when test="${enrolled == 'true' and regStatus == 'APPROVED'}">
                                <a href="${pageContext.request.contextPath}/student/learn?courseId=${course.courseId}"
                                   class="btn-approve"
                                   style="width:100%; text-align:center; padding:9px; display:block;
                                          background:linear-gradient(135deg,#10b981,#059669);">
                                    &#9654; Start Learning
                                </a>
                            </c:when>

                            <%-- PENDING + UNPAID → nút Pay Now --%>
                            <c:when test="${enrolled == 'true' and regStatus == 'PENDING'
                                            and payStatus == 'UNPAID'}">
                                <div class="enrolled-indicator" style="margin-bottom:8px;">
                                    &#9203; Pending — Payment required
                                </div>
                                <a href="${pageContext.request.contextPath}/payment?regId=${regId}"
                                   class="btn-approve"
                                   style="width:100%; text-align:center; padding:9px; display:block;
                                          background:linear-gradient(135deg,#f59e0b,#d97706);">
                                    &#128179; Pay Now
                                </a>
                            </c:when>

                            <%-- PENDING + PAID → chờ duyệt --%>
                            <c:when test="${enrolled == 'true' and regStatus == 'PENDING'
                                            and payStatus == 'PAID'}">
                                <div class="enrolled-indicator">
                                    &#9203; Pending approval
                                    <span class="badge badge-pending">Pending</span>
                                </div>
                            </c:when>

                            <%-- Các trạng thái khác REJECTED/CANCELED → cho enroll lại --%>
                            <c:when test="${enrolled == 'true'
                                            and (regStatus == 'REJECTED' or regStatus == 'CANCELED')}">
                                <div class="enrolled-indicator" style="margin-bottom:8px;">
                                    <c:choose>
                                        <c:when test="${regStatus == 'REJECTED'}">
                                            <span class="badge badge-rejected">Rejected</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-canceled">Canceled</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <form action="${pageContext.request.contextPath}/student/enroll"
                                      method="post">
                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                    <button type="submit" class="btn-approve"
                                            style="width:100%; padding:9px;">
                                        &#128203; Enroll Again
                                    </button>
                                </form>
                            </c:when>

                            <%-- Chưa enroll --%>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/student/enroll"
                                      method="post">
                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                    <button type="submit" class="btn-approve"
                                            style="width:100%; padding:9px;"
                                            ${course.enrolledCount >= course.totalSlots ? 'disabled' : ''}>
                                        <c:choose>
                                            <c:when test="${course.enrolledCount >= course.totalSlots}">
                                                &#128683; Full
                                            </c:when>
                                            <c:otherwise>&#127891; Enroll Now</c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:if>

                    <%-- ADMIN --%>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <div style="display:flex; gap:8px;">
                            <a href="${pageContext.request.contextPath}/admin/editCourse?courseId=${course.courseId}"
                               class="btn-secondary" style="flex:1; text-align:center; padding:8px;">
                                &#9999; Edit
                            </a>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/manageCourses"
                                  style="flex:1;"
                                  onsubmit="return confirm('Delete: ${course.name}?');">
                                <input type="hidden" name="action" value="delete"/>
                                <input type="hidden" name="courseId" value="${course.courseId}"/>
                                <button type="submit" class="btn-reject" style="width:100%; padding:8px;">
                                    &#128465; Delete
                                </button>
                            </form>
                        </div>
                    </c:if>

                    <%-- GUEST --%>
                    <c:if test="${empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/login"
                           class="btn-approve" style="width:100%; text-align:center; padding:9px;">
                            &#128274; Login to Enroll
                        </a>
                    </c:if>

                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty pagedCourses}">
        <p style="color:#6b7280; text-align:center; padding:40px 0;">No courses available.</p>
    </c:if>

    <c:if test="${totalPages > 1}">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/courseList?page=${currentPage - 1}"
                   class="page-btn">&#8592; Prev</a>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="p">
                <a href="${pageContext.request.contextPath}/courseList?page=${p}"
                   class="page-btn ${p == currentPage ? 'page-btn-active' : ''}">${p}</a>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/courseList?page=${currentPage + 1}"
                   class="page-btn">Next &#8594;</a>
            </c:if>
        </div>
    </c:if>

</div>
<%@ include file="/footer.jsp" %>
</body>
</html>
