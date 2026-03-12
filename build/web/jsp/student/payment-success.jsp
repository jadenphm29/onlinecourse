<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Successful - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body { min-height:100vh; display:flex; flex-direction:column; background:#f0f2f5; }
        .success-wrap {
            flex:1; display:flex; align-items:center;
            justify-content:center; padding:40px 20px;
        }
        .success-card {
            background:#fff; border-radius:20px; padding:48px 40px;
            width:100%; max-width:440px; text-align:center;
            box-shadow:0 8px 32px rgba(0,0,0,0.10);
        }
        .success-icon { font-size:64px; margin-bottom:16px; }
        .success-card h2 {
            font-size:22px; font-weight:800;
            color:#065f46; margin-bottom:8px;
        }
        .success-card p { font-size:14px; color:#6b7280; margin-bottom:24px; }
        .course-name {
            font-size:16px; font-weight:700;
            color:#1a1a2e; margin-bottom:24px;
            padding:12px; background:#f0fdf4;
            border-radius:10px; border:1px solid #6ee7b7;
        }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>

<div class="success-wrap">
    <div class="success-card">
        <div class="success-icon">&#10004;&#65039;</div>
        <h2>Payment Successful!</h2>
        <p>Your enrollment has been confirmed.</p>
        <c:if test="${not empty course}">
            <div class="course-name">${course.name}</div>
        </c:if>
        <a href="${pageContext.request.contextPath}/myCourses"
           class="btn-approve" style="padding:12px 32px; font-size:15px;">
            &#128218; View My Courses
        </a>
    </div>
</div>

<%@ include file="/footer.jsp" %>
</body>
</html>