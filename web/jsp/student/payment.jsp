<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background: #f0f2f5;
        }
        .payment-wrap {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 20px;
        }
        .payment-card {
            background: #fff;
            border-radius: 20px;
            padding: 40px 38px;
            width: 100%;
            max-width: 460px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.10);
            border: 1px solid #e8ecf5;
        }
        .payment-card h2 {
            font-size: 22px; font-weight: 800;
            color: #1a1a2e; margin-bottom: 24px;
            text-align: center;
        }
        .payment-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 0;
            border-bottom: 1px solid #f0f2f5;
            font-size: 14px;
        }
        .payment-row:last-of-type { border-bottom: none; }
        .payment-label { color: #6b7280; font-weight: 600; }
        .payment-value { color: #1a1a2e; font-weight: 700; }
        .payment-fee   { color: #4f46e5; font-size: 18px; font-weight: 900; }
        .balance-ok    { color: #10b981; }
        .balance-low   { color: #ef4444; }
        .btn-pay {
            width: 100%; padding: 14px;
            background: linear-gradient(135deg, #4f46e5, #7c3aed);
            color: #fff; border: none; border-radius: 12px;
            font-size: 16px; font-weight: 700; cursor: pointer;
            margin-top: 24px; font-family: inherit;
            transition: opacity 0.2s, transform 0.1s;
        }
        .btn-pay:hover { opacity: 0.90; transform: translateY(-1px); }
        .btn-pay:disabled {
            background: #d1d5db; cursor: not-allowed;
            transform: none; opacity: 1;
        }
        .btn-cancel {
            display: block; text-align: center;
            margin-top: 12px; font-size: 13px;
            color: #6b7280; text-decoration: none;
        }
        .btn-cancel:hover { color: #ef4444; text-decoration: none; }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>

<div class="payment-wrap">
    <div class="payment-card">
        <h2>&#128179; Course Payment</h2>

        <c:if test="${not empty error}">
            <div class="alert-error" style="margin-bottom:16px;">
                &#9888; ${error}
            </div>
        </c:if>

        <div class="payment-row">
            <span class="payment-label">Course</span>
            <span class="payment-value">${course.name}</span>
        </div>
        <div class="payment-row">
            <span class="payment-label">Instructor</span>
            <span class="payment-value">${course.instructorName}</span>
        </div>
        <div class="payment-row">
            <span class="payment-label">Schedule</span>
            <span class="payment-value">${course.scheduleText}</span>
        </div>
        <div class="payment-row">
            <span class="payment-label">Tuition Fee</span>
            <span class="payment-fee">
                <fmt:formatNumber value="${course.tuitionFee}"
                    type="number" groupingUsed="true"/> VND
            </span>
        </div>
        <div class="payment-row">
            <span class="payment-label">Your Balance</span>
            <c:choose>
                <c:when test="${balance >= course.tuitionFee}">
                    <span class="payment-value balance-ok">
                        <fmt:formatNumber value="${balance}"
                            type="number" groupingUsed="true"/> VND
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="payment-value balance-low">
                        <fmt:formatNumber value="${balance}"
                            type="number" groupingUsed="true"/> VND
                    </span>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="payment-row">
            <span class="payment-label">After Payment</span>
            <span class="payment-value">
                <fmt:formatNumber value="${balance - course.tuitionFee}"
                    type="number" groupingUsed="true"/> VND
            </span>
        </div>

        <form method="post"
              action="${pageContext.request.contextPath}/payment">
            <input type="hidden" name="regId" value="${regId}"/>
            <c:choose>
                <c:when test="${balance < course.tuitionFee}">
                    <button type="submit" class="btn-pay" disabled>
                        &#10060; Insufficient Balance
                    </button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn-pay">
                        &#9989; Confirm Payment
                    </button>
                </c:otherwise>
            </c:choose>
        </form>

        <a href="${pageContext.request.contextPath}/myCourses"
           class="btn-cancel">&#8592; Cancel &amp; Go Back</a>
    </div>
</div>

<%@ include file="/footer.jsp" %>
</body>
</html>