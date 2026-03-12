<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Student Registrations - EduOnline</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <%@ include file="/header.jsp" %>
        <div class="container">
            <div class="page-header">
                <h2>&#128101; Student Registrations</h2>
                <a href="${pageContext.request.contextPath}/myCourses"
                   class="btn-secondary">&#8592; Back</a>
            </div>

            <c:if test="${not empty param.error}">
                <div class="alert-error">&#9888; ${param.error}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty regList}">
                    <table class="student-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Student</th>
                                <th>Email</th>
                                <th>Date</th>
                                <th>Payment</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reg" items="${regList}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${reg.studentName}</td>
                                    <td>${reg.studentEmail}</td>
                                    <td>${reg.regDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reg.paymentStatus == 'PAID'}">
                                                <span class="badge badge-approved">&#10003; Paid</span>
                                            </c:when>
                                            <c:when test="${reg.paymentStatus == 'REFUNDED'}">
                                                <span class="badge badge-canceled">&#8635; Refunded</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-pending">&#9203; Unpaid</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reg.status == 'PENDING'}">
                                                <span class="badge badge-pending">Pending</span>
                                            </c:when>
                                            <c:when test="${reg.status == 'APPROVED'}">
                                                <span class="badge badge-approved">Approved</span>
                                            </c:when>
                                            <c:when test="${reg.status == 'REJECTED'}">
                                                <span class="badge badge-rejected">Rejected</span>
                                            </c:when>
                                            <c:when test="${reg.status == 'CANCELED'}">
                                                <span class="badge badge-canceled">Canceled</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${reg.status == 'PENDING'}">
                                            <c:choose>
                                                <c:when test="${reg.paymentStatus == 'PAID'}">
                                                    <form action="${pageContext.request.contextPath}/instructor/update-status"
                                                          method="post" style="display:inline;">
                                                        <input type="hidden" name="regId"    value="${reg.regId}"/>
                                                        <input type="hidden" name="courseId" value="${courseId}"/>
                                                        <input type="hidden" name="status"   value="APPROVED"/>
                                                        <button type="submit" class="btn-approve">&#10003; Approve</button>
                                                    </form>
                                                    <form action="${pageContext.request.contextPath}/instructor/update-status"
                                                          method="post" style="display:inline;">
                                                        <input type="hidden" name="regId"    value="${reg.regId}"/>
                                                        <input type="hidden" name="courseId" value="${courseId}"/>
                                                        <input type="hidden" name="status"   value="REJECTED"/>
                                                        <button type="submit" class="btn-reject">&#10007; Reject</button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="font-size:12px; color:#92400e; font-weight:600;">
                                                        &#9203; Awaiting payment
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>No registrations found for this course.</p>
                </c:otherwise>
            </c:choose>
        </div>
        <%@ include file="/footer.jsp" %>
    </body>
</html>