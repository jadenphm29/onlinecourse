<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <h2>Manage Users</h2>

    <div style="margin-bottom:14px; display:flex; gap:10px; align-items:center;">
        <a href="${pageContext.request.contextPath}/admin/addUser"
           class="btn-approve">+ Add User</a>
        <a href="${pageContext.request.contextPath}/home">← Back to Home</a>
    </div>

    <form method="get"
          action="${pageContext.request.contextPath}/admin/manageUsers"
          style="margin-bottom:16px; display:flex; gap:8px; align-items:center; flex-wrap:wrap;">
        <input type="text" name="email" placeholder="Search by email..."
               value="${filterEmail}" style="padding:6px 10px; width:220px;"/>
        <select name="role" style="padding:6px 10px;">
            <option value="">All Roles</option>
            <option value="STUDENT"
                <c:if test="${filterRole == 'STUDENT'}">selected</c:if>>Student</option>
            <option value="INSTRUCTOR"
                <c:if test="${filterRole == 'INSTRUCTOR'}">selected</c:if>>Instructor</option>
            <option value="ADMIN"
                <c:if test="${filterRole == 'ADMIN'}">selected</c:if>>Admin</option>
        </select>
        <button type="submit">🔍 Search</button>
        <a href="${pageContext.request.contextPath}/admin/manageUsers">Reset</a>
    </form>

    <c:choose>
        <c:when test="${not empty users}">
            <table class="student-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${u.fullName}</td>
                            <td>${u.email}</td>
                            <td>${u.phone}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.role == 'ADMIN'}">
                                        <span class="badge badge-active">Admin</span>
                                    </c:when>
                                    <c:when test="${u.role == 'INSTRUCTOR'}">
                                        <span class="badge badge-pending">Instructor</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge">Student</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.status == 'ACTIVE'}">
                                        <span class="badge badge-active">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-locked">Locked</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/editUser?userId=${u.userId}">
                                    ✏ Edit
                                </a>
                                <c:if test="${u.role != 'ADMIN'}">
                                    &nbsp;|&nbsp;
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/admin/manageUsers"
                                          style="display:inline;">
                                        <input type="hidden" name="action" value="toggleStatus"/>
                                        <input type="hidden" name="userId" value="${u.userId}"/>
                                        <button type="submit"
                                                class="${u.status == 'ACTIVE' ? 'btn-reject' : 'btn-approve'}"
                                                style="padding:3px 8px; font-size:0.82rem;">
                                            ${u.status == 'ACTIVE' ? '🔒 Lock' : '🔓 Unlock'}
                                        </button>
                                    </form>
                                    <c:if test="${u.userId != sessionScope.user.userId}">
                                        &nbsp;|&nbsp;
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/admin/manageUsers"
                                              style="display:inline;"
                                              onsubmit="return confirm('Delete user: ${u.fullName}?');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="userId" value="${u.userId}"/>
                                            <button type="submit" class="btn-reject"
                                                    style="padding:3px 8px; font-size:0.82rem;">
                                                🗑 Delete
                                            </button>
                                        </form>
                                    </c:if>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No users found.</p>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>