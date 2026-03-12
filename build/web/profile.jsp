<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - EduOnline</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
    <h2>My Profile</h2>

    <c:if test="${not empty success}">
        <div class="alert-success">✅ ${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert-error">⚠️ ${error}</div>
    </c:if>

    <div class="profile-grid">

        <!-- Personal Info -->
        <div class="form-card">
            <h3 style="margin:0 0 20px;">👤 Personal Information</h3>
            <form action="${pageContext.request.contextPath}/profile" method="post">
                <input type="hidden" name="action" value="updateProfile"/>
                <div class="form-group">
                    <label>Full Name *</label>
                    <input type="text" name="fullName"
                           value="${sessionScope.user.fullName}" required style="width:100%;"/>
                </div>
                <div class="form-group">
                    <label>Email (read-only)</label>
                    <input type="text" value="${sessionScope.user.email}" readonly
                           style="width:100%; background:#f3f4f6; color:#9ca3af; cursor:not-allowed;"/>
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <input type="text" name="phone"
                           value="${sessionScope.user.phone}"
                           placeholder="0901 234 567" style="width:100%;"/>
                </div>
                <div class="form-group">
                    <label>Address</label>
                    <input type="text" name="address"
                           value="${sessionScope.user.address}"
                           placeholder="Ho Chi Minh City" style="width:100%;"/>
                </div>
                <button type="submit" class="btn-approve"
                        style="width:100%; padding:11px; font-size:14px;">
                    💾 Save Changes
                </button>
            </form>
        </div>

        <!-- Change Password -->
        <div class="form-card">
            <h3 style="margin:0 0 20px;">🔒 Change Password</h3>
            <form action="${pageContext.request.contextPath}/profile" method="post">
                <input type="hidden" name="action" value="changePassword"/>
                <div class="form-group">
                    <label>Current Password</label>
                    <input type="password" name="oldPassword"
                           placeholder="••••••••" style="width:100%;"/>
                </div>
                <div class="form-group">
                    <label>New Password</label>
                    <input type="password" name="newPassword"
                           placeholder="••••••••" style="width:100%;"/>
                </div>
                <div class="form-group">
                    <label>Confirm New Password</label>
                    <input type="password" name="confirmPassword"
                           placeholder="••••••••" style="width:100%;"/>
                </div>
                <div style="height:14px;"></div>
                <button type="submit" class="btn-reject"
                        style="width:100%; padding:11px; font-size:14px;">
                    🔑 Update Password
                </button>
            </form>
        </div>

    </div>

    <!-- Account Info Bar -->
    <div class="profile-info-bar">
        <div class="form-card">
            <div style="display:flex; align-items:center; gap:16px;">
                <div class="profile-avatar-large">
                    <%= ((dto.User) session.getAttribute("user")).getFullName().charAt(0) %>
                </div>
                <div>
                    <div style="font-size:18px; font-weight:800; color:#1a1a2e;">
                        ${sessionScope.user.fullName}
                    </div>
                    <div style="font-size:13px; color:#6b7280; margin-top:3px;">
                        ${sessionScope.user.email}
                    </div>
                </div>
            </div>
            <div style="display:flex; gap:12px; flex-wrap:wrap; margin-left:auto;">
                <div class="profile-stat-chip">
                    <div class="chip-label">Role</div>
                    <div class="chip-value">${sessionScope.role}</div>
                </div>
                <div class="profile-stat-chip">
                    <div class="chip-label">Status</div>
                    <div style="margin-top:4px;">
                        <c:choose>
                            <c:when test="${sessionScope.user.status == 'ACTIVE'}">
                                <span class="badge badge-active">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-locked">Locked</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<%@ include file="footer.jsp" %>
</body>
</html>