<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%) !important;
        }
        .register-overlay {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 20px;
        }
        .register-card {
            background: #fff;
            border-radius: 20px;
            padding: 40px 38px 32px;
            width: 100%;
            max-width: 460px;
            box-shadow: 0 24px 64px rgba(0,0,0,0.35), 0 4px 16px rgba(0,0,0,0.15);
            animation: slideUp 0.35s cubic-bezier(.22,.68,0,1.2);
            max-height: 92vh;
            overflow-y: auto;
        }
        @keyframes slideUp {
            from { opacity: 0; transform: translateY(28px) scale(0.97); }
            to   { opacity: 1; transform: translateY(0) scale(1); }
        }
        .register-card h2 {
            font-size: 23px; font-weight: 800;
            color: #1a1a2e; text-align: center; margin-bottom: 4px;
        }
        .register-sub {
            text-align: center; font-size: 13px;
            color: #6b7280; margin-bottom: 24px; display: block;
        }
        .form-group { margin-bottom: 15px; }
        .form-group label {
            display: block; font-size: 13px;
            font-weight: 600; color: #374151; margin-bottom: 6px;
        }
        .form-group input {
            width: 100%; padding: 11px 14px;
            border: 1.5px solid #e0e4ef;
            border-radius: 10px; font-size: 14px;
            color: #222; outline: none; background: #fafafa;
            transition: border-color 0.2s, box-shadow 0.2s;
            font-family: inherit; box-sizing: border-box;
        }
        .form-group input:focus {
            border-color: #6366f1;
            box-shadow: 0 0 0 3px rgba(99,102,241,0.13);
            background: #fff;
        }
        .btn-login-submit {
            width: 100%; padding: 13px;
            background: linear-gradient(135deg, #10b981, #059669);
            color: #fff; border: none; border-radius: 10px;
            font-size: 15px; font-weight: 700; cursor: pointer;
            margin-top: 4px; font-family: inherit;
            transition: opacity 0.2s, transform 0.1s;
            letter-spacing: 0.3px;
        }
        .btn-login-submit:hover { opacity: 0.90; transform: translateY(-1px); }
        .alert-error {
            background: #fee2e2; border: 1px solid #fca5a5;
            color: #991b1b; padding: 10px 13px; border-radius: 9px;
            font-size: 13px; margin-bottom: 16px;
            display: flex; align-items: center; gap: 6px;
        }
        .alert-success {
            background: #d1fae5; border: 1px solid #6ee7b7;
            color: #065f46; padding: 10px 13px; border-radius: 9px;
            font-size: 13px; margin-bottom: 16px;
            display: flex; align-items: center; gap: 6px;
        }
        .login-footer {
            text-align: center; margin-top: 20px;
            font-size: 13px; color: #6b7280;
        }
        .login-footer a {
            color: #4f46e5; font-weight: 600; text-decoration: none;
        }
        .login-footer a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="register-overlay">
    <div class="register-card">
        <h2>Create Account</h2>
        <span class="register-sub">Join EduOnline and start learning today</span>

        <c:if test="${not empty error}">
            <div class="alert-error">&#9888; ${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert-success">&#10003; ${success}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="form-group">
                <label>Full Name *</label>
                <input type="text" name="fullName"
                       placeholder="Nguyen Van A" required
                       value="${param.fullName}"/>
            </div>
            <div class="form-group">
                <label>Email Address *</label>
                <input type="email" name="email"
                       placeholder="your@email.com" required
                       value="${param.email}"/>
            </div>
            <div class="form-group">
                <label>Password *</label>
                <input type="password" name="password"
                       placeholder="At least 6 characters" required/>
            </div>
            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone"
                       placeholder="0901 234 567"
                       value="${param.phone}"/>
            </div>
            <div class="form-group">
                <label>Address</label>
                <input type="text" name="address"
                       placeholder="Ho Chi Minh City"
                       value="${param.address}"/>
            </div>
            <button type="submit" class="btn-login-submit">
                Create Account &#8594;
            </button>
        </form>

        <div class="login-footer">
            Already have an account?
            <a href="${pageContext.request.contextPath}/login">Sign in</a>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>