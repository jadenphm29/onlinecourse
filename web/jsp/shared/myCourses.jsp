<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Courses - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>

<div class="container">

    <!-- ═══════════ STUDENT ═══════════ -->
    <c:if test="${sessionScope.role == 'STUDENT'}">
        <div class="page-header">
            <h2>&#127891; My Enrolled Courses</h2>
            <span style="color:#6b7280; font-size:14px;">
                ${fn:length(myCourses)} courses
            </span>
        </div>

        <c:choose>
            <c:when test="${not empty pagedCourses}">
                <div class="course-grid">
                    <c:forEach var="course" items="${pagedCourses}">
                        <div class="course-card">

                            <%-- Thumbnail --%>
                            <c:set var="isApproved" value="false"/>
                            <c:forEach var="reg" items="${myRegs}">
                                <c:if test="${reg.courseId == course.courseId
                                              and reg.status == 'APPROVED'}">
                                    <c:set var="isApproved" value="true"/>
                                </c:if>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${isApproved == 'true'}">
                                    <a href="${pageContext.request.contextPath}/student/learn?courseId=${course.courseId}">
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}">
                                </c:otherwise>
                            </c:choose>
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
                                        <div class="course-thumb-placeholder">
                                            <c:choose>
                                                <c:when test="${isApproved == 'true'}">&#9654;</c:when>
                                                <c:otherwise>&#127891;</c:otherwise>
                                            </c:choose>
                                        </div>
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
                                <span class="course-slots
                                    <c:choose>
                                        <c:when test='${course.enrolledCount >= course.totalSlots}'>slots-full</c:when>
                                        <c:when test='${course.totalSlots - course.enrolledCount <= 5}'>slots-near</c:when>
                                        <c:otherwise>slots-ok</c:otherwise>
                                    </c:choose>">
                                    ${course.enrolledCount}/${course.totalSlots}
                                </span>
                            </div>

                            <div class="course-card-body">
                                <h3>
                                    <c:choose>
                                        <c:when test="${isApproved == 'true'}">
                                            <a href="${pageContext.request.contextPath}/student/learn?courseId=${course.courseId}"
                                               style="color:#4ade80; text-decoration:none;">
                                                &#9654; ${course.name}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}"
                                               style="color:inherit; text-decoration:none;">
                                                ${course.name}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </h3>
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

                                <%-- Slot bar --%>
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

                                <%-- Registration status --%>
                                <c:forEach var="reg" items="${myRegs}">
                                    <c:if test="${reg.courseId == course.courseId}">
                                        <c:choose>
                                            <c:when test="${reg.status == 'PENDING' and reg.paymentStatus == 'UNPAID'}">
                                                <div class="reg-status-block pending" style="margin-top:10px;">
                                                    &#9203; Pending — Payment required
                                                </div>
                                            </c:when>
                                            <c:when test="${reg.status == 'PENDING' and reg.paymentStatus == 'PAID'}">
                                                <div class="reg-status-block pending" style="margin-top:10px;">
                                                    &#9203; Pending approval
                                                </div>
                                            </c:when>
                                            <c:when test="${reg.status == 'APPROVED'}">
                                                <div class="reg-status-block approved" style="margin-top:10px;">
                                                    &#10003; Approved — Click to start learning
                                                </div>
                                            </c:when>
                                            <c:when test="${reg.status == 'REJECTED' and reg.paymentStatus == 'REFUNDED'}">
                                                <div class="reg-status-block rejected" style="margin-top:10px;">
                                                    &#10007; Rejected — &#8635; Balance refunded
                                                </div>
                                            </c:when>
                                            <c:when test="${reg.status == 'REJECTED'}">
                                                <div class="reg-status-block rejected" style="margin-top:10px;">
                                                    &#10007; Rejected
                                                </div>
                                            </c:when>
                                            <c:when test="${reg.status == 'CANCELED'}">
                                                <div class="reg-status-block canceled" style="margin-top:10px;">
                                                    &#128683; Canceled
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </div>

                            <div class="course-card-footer">
                                <%-- Nút Start Learning nếu APPROVED --%>
                                <c:if test="${isApproved == 'true'}">
                                    <a href="${pageContext.request.contextPath}/student/learn?courseId=${course.courseId}"
                                       class="btn-approve"
                                       style="width:100%; text-align:center; padding:9px;
                                              display:block; margin-bottom:8px;
                                              background:linear-gradient(135deg,#10b981,#059669);">
                                        &#9654; Start Learning
                                    </a>
                                </c:if>

                                <%-- Nút Pay Now nếu PENDING + UNPAID --%>
                                <c:forEach var="reg" items="${myRegs}">
                                    <c:if test="${reg.courseId == course.courseId
                                                  and reg.status == 'PENDING'
                                                  and reg.paymentStatus == 'UNPAID'}">
                                        <a href="${pageContext.request.contextPath}/payment?regId=${reg.regId}"
                                           class="btn-approve"
                                           style="width:100%; text-align:center; padding:9px;
                                                  display:block; margin-bottom:8px;
                                                  background:linear-gradient(135deg,#f59e0b,#d97706);">
                                            &#128179; Pay Now
                                        </a>
                                    </c:if>
                                </c:forEach>

                                <%-- Nút Unenroll --%>
                                <form action="${pageContext.request.contextPath}/student/unenroll"
                                      method="post"
                                      onsubmit="return confirm('Unenroll from: ${course.name}?');">
                                    <input type="hidden" name="courseId" value="${course.courseId}"/>
                                    <button type="submit" class="btn-reject"
                                            style="width:100%; padding:9px;">
                                        &#128683; Unenroll
                                    </button>
                                </form>
                            </div>

                        </div>
                    </c:forEach>
                </div>

                <%-- Pagination --%>
                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/myCourses?page=${currentPage - 1}"
                               class="page-btn">&#8592; Prev</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="p">
                            <a href="${pageContext.request.contextPath}/myCourses?page=${p}"
                               class="page-btn ${p == currentPage ? 'page-btn-active' : ''}">${p}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="${pageContext.request.contextPath}/myCourses?page=${currentPage + 1}"
                               class="page-btn">Next &#8594;</a>
                        </c:if>
                    </div>
                </c:if>
            </c:when>

            <c:otherwise>
                <div style="text-align:center; padding:60px 0; color:#6b7280;">
                    <div style="font-size:48px; margin-bottom:16px;">&#128218;</div>
                    <p style="font-size:16px; font-weight:600;">No courses enrolled yet.</p>
                    <a href="${pageContext.request.contextPath}/courseList"
                       class="btn-approve" style="margin-top:16px; padding:10px 24px;">
                        Browse Courses
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <!-- ═══════════ INSTRUCTOR ═══════════ -->
    <c:if test="${sessionScope.role == 'INSTRUCTOR'}">
        <div class="page-header">
            <h2>&#127979; My Courses</h2>
            <span style="color:#6b7280; font-size:14px;">
                ${fn:length(myCourses)} courses
            </span>
        </div>

        <c:choose>
            <c:when test="${not empty pagedCourses}">
                <div class="course-grid">
                    <c:forEach var="course" items="${pagedCourses}">
                        <div class="course-card">

                            <%-- Thumbnail --%>
                            <a href="${pageContext.request.contextPath}/instructor/registrations?courseId=${course.courseId}">
                                <c:choose>
                                    <c:when test="${not empty course.thumbnailUrl}">
                                        <img src="${course.thumbnailUrl}"
                                             alt="${course.name}"
                                             class="course-thumb"
                                             onerror="this.style.display='none';
                                                      this.nextElementSibling.style.display='flex';"/>
                                        <div class="course-thumb-placeholder" style="display:none;">&#127979;</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="course-thumb-placeholder">&#127979;</div>
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
                                <span class="course-slots
                                    <c:choose>
                                        <c:when test='${course.enrolledCount >= course.totalSlots}'>slots-full</c:when>
                                        <c:when test='${course.totalSlots - course.enrolledCount <= 5}'>slots-near</c:when>
                                        <c:otherwise>slots-ok</c:otherwise>
                                    </c:choose>">
                                    ${course.enrolledCount}/${course.totalSlots}
                                </span>
                            </div>

                            <div class="course-card-body">
                                <h3>${course.name}</h3>
                                <p class="course-desc">${course.description}</p>
                                <div class="course-meta">
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

                            <div class="course-card-footer" style="display:flex; gap:8px;">
                                <a href="${pageContext.request.contextPath}/instructor/registrations?courseId=${course.courseId}"
                                   class="btn-approve"
                                   style="flex:1; text-align:center; padding:8px; font-size:12px;">
                                    &#128101; Students
                                </a>
                                <a href="${pageContext.request.contextPath}/instructor/edit-course?courseId=${course.courseId}"
                                   class="btn-secondary"
                                   style="flex:1; text-align:center; padding:8px; font-size:12px;">
                                    &#9999; Edit
                                </a>
                            </div>

                        </div>
                    </c:forEach>
                </div>

                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/myCourses?page=${currentPage - 1}"
                               class="page-btn">&#8592; Prev</a>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="p">
                            <a href="${pageContext.request.contextPath}/myCourses?page=${p}"
                               class="page-btn ${p == currentPage ? 'page-btn-active' : ''}">${p}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="${pageContext.request.contextPath}/myCourses?page=${currentPage + 1}"
                               class="page-btn">Next &#8594;</a>
                        </c:if>
                    </div>
                </c:if>
            </c:when>

            <c:otherwise>
                <div style="text-align:center; padding:60px 0; color:#6b7280;">
                    <div style="font-size:48px; margin-bottom:16px;">&#127979;</div>
                    <p style="font-size:16px; font-weight:600;">No courses assigned yet.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

</div>

<%@ include file="/footer.jsp" %>
</body>
</html>