<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%) !important;
        }
        .login-main {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 20px;
        }
        .login-card {
            background: #fff;
            border-radius: 20px;
            padding: 44px 40px 36px;
            width: 100%;
            max-width: 400px;
            box-shadow: 0 24px 64px rgba(0,0,0,0.35), 0 4px 16px rgba(0,0,0,0.15);
            animation: slideUp 0.35s cubic-bezier(.22,.68,0,1.2);
        }
        @keyframes slideUp {
            from { opacity: 0; transform: translateY(28px) scale(0.97); }
            to   { opacity: 1; transform: translateY(0) scale(1); }
        }
        .login-logo { text-align: center; margin-bottom: 24px; }
        .logo-icon {
            width: 64px; height: 64px;
            border-radius: 18px;
            background: linear-gradient(135deg, #4f46e5, #7c3aed);
            display: inline-flex;
            align-items: center; justify-content: center;
            font-size: 30px; margin-bottom: 14px;
            box-shadow: 0 8px 24px rgba(79,70,229,0.38);
        }
        .login-card h2 {
            font-size: 23px; font-weight: 800;
            color: #1a1a2e; text-align: center; margin-bottom: 4px;
        }
        .login-sub {
            text-align: center; font-size: 13px;
            color: #6b7280; margin-bottom: 26px; display: block;
        }
        .form-group { margin-bottom: 16px; }
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
            background: linear-gradient(135deg, #4f46e5, #7c3aed);
            color: #fff; border: none; border-radius: 10px;
            font-size: 15px; font-weight: 700; cursor: pointer;
            margin-top: 4px; font-family: inherit;
            transition: opacity 0.2s, transform 0.1s;
            letter-spacing: 0.3px;
        }
        .btn-login-submit:hover { opacity: 0.90; transform: translateY(-1px); }
        .error-msg {
            background: #fee2e2; border: 1px solid #fca5a5;
            color: #991b1b; padding: 10px 13px; border-radius: 9px;
            font-size: 13px; margin-bottom: 18px;
            display: flex; align-items: center; gap: 6px;
        }
        .login-footer-text {
            text-align: center; margin-top: 22px;
            font-size: 13px; color: #6b7280;
        }
        .login-footer-text a {
            color: #4f46e5; font-weight: 600; text-decoration: none;
        }
        .login-footer-text a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="login-main">
    <div class="login-card">
        <div class="login-logo">
            <div class="logo-icon">&#127891;</div>
            <h2>Welcome Back</h2>
            <span class="login-sub">Sign in to your EduOnline account</span>
        </div>

        <c:if test="${not empty error}">
            <div class="error-msg">&#9888; ${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email"
                       placeholder="your@email.com" required
                       value="${param.email}"/>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password"
                       placeholder="••••••••" required/>
            </div>
            <button type="submit" class="btn-login-submit">
                Sign In &#8594;
            </button>
        </form>

        <div class="login-footer-text">
            Don't have an account?
            <a href="${pageContext.request.contextPath}/register">Create one now</a>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>