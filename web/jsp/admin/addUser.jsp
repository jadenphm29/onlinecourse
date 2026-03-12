<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add User</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container">
    <h2>Add New User</h2>
    <p><a href="${pageContext.request.contextPath}/admin/manageUsers">← Back to Manage Users</a></p>

    <c:if test="${not empty error}">
        <p style="color:red; font-weight:bold;">${error}</p>
    </c:if>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/addUser"
          style="max-width:420px;">
        <p>
            <label>Full Name: *</label><br/>
            <input type="text" name="fullName" required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Email: *</label><br/>
            <input type="email" name="email" required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Password: *</label><br/>
            <input type="password" name="password" required style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Phone:</label><br/>
            <input type="text" name="phone" style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Address:</label><br/>
            <input type="text" name="address" style="width:100%; padding:6px;"/>
        </p>
        <p>
            <label>Role: *</label><br/>
            <select name="role" style="width:100%; padding:6px;">
                <option value="STUDENT">Student</option>
                <option value="INSTRUCTOR">Instructor</option>
            </select>
        </p>
        <p>
            <button type="submit" class="btn-approve">✔ Add User</button>
            &nbsp;
            <a href="${pageContext.request.contextPath}/admin/manageUsers">Cancel</a>
        </p>
    </form>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>