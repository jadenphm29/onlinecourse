<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <h2>Edit User: ${editUser.fullName}</h2>
    <p><a href="${pageContext.request.contextPath}/admin/manageUsers">← Back to Manage Users</a></p>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/editUser"
          style="max-width:420px;">
        <input type="hidden" name="userId" value="${editUser.userId}"/>
        <p>
            <label>Full Name: *</label><br/>
            <input type="text" name="fullName"
                   value="${editUser.fullName}" required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Email: *</label><br/>
            <input type="email" name="email"
                   value="${editUser.email}" required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Phone:</label><br/>
            <input type="text" name="phone"
                   value="${editUser.phone}" style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Address:</label><br/>
            <input type="text" name="address"
                   value="${editUser.address}" style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Role: *</label><br/>
            <select name="role" style="width:100%; padding:6px;">
                <option value="STUDENT"
                    <c:if test="${editUser.role == 'STUDENT'}">selected</c:if>>Student</option>
                <option value="INSTRUCTOR"
                    <c:if test="${editUser.role == 'INSTRUCTOR'}">selected</c:if>>Instructor</option>
                <option value="ADMIN"
                    <c:if test="${editUser.role == 'ADMIN'}">selected</c:if>>Admin</option>
            </select>
        </p>
        <p>
            <label>Status: *</label><br/>
            <select name="status" style="width:100%; padding:6px;">
                <option value="ACTIVE"
                    <c:if test="${editUser.status == 'ACTIVE'}">selected</c:if>>Active</option>
                <option value="LOCKED"
                    <c:if test="${editUser.status == 'LOCKED'}">selected</c:if>>Locked</option>
            </select>
        </p>
        <p>
            <button type="submit" class="btn-approve">✔ Save Changes</button>
            &nbsp;
            <a href="${pageContext.request.contextPath}/admin/manageUsers">Cancel</a>
        </p>
    </form>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>