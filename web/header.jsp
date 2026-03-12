<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Auto-refresh balance từ DB cho STUDENT --%>
<%
    dto.User _u = (dto.User) session.getAttribute("user");
    if (_u != null && "STUDENT".equals(_u.getRole())) {
        try {
            dao.UserDAO _dao = new dao.UserDAO();
            double _bal = _dao.getBalance(_u.getUserId());
            _u.setBalance(_bal);
            session.setAttribute("user", _u);
        } catch (Exception _e) { /* ignore */ }
    }
%>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/home" class="brand">
        &#127891; <span>EduOnline</span>
    </a>
    <a href="${pageContext.request.contextPath}/home">Home</a>
    <c:if test="${not empty sessionScope.user and
                  (sessionScope.role == 'STUDENT' or sessionScope.role == 'ADMIN')}">
        <a href="${pageContext.request.contextPath}/courseList">Courses</a>
    </c:if>
    <c:if test="${not empty sessionScope.user and
                  (sessionScope.role == 'STUDENT' or sessionScope.role == 'INSTRUCTOR')}">
        <a href="${pageContext.request.contextPath}/myCourses">My Courses</a>
    </c:if>
    <c:if test="${not empty sessionScope.user and sessionScope.role == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/admin/manageUsers">Users</a>
        <a href="${pageContext.request.contextPath}/admin/manageCourses">Courses</a>
        <a href="${pageContext.request.contextPath}/admin/statistics">Statistics</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/about">About</a>

    <div class="login-container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">

                <%-- Wallet badge chỉ hiện với STUDENT --%>
                <c:if test="${sessionScope.role == 'STUDENT'}">
                    <a href="${pageContext.request.contextPath}/wallet"
                       class="wallet-pill">
                        &#128179;
                        <fmt:formatNumber value="${sessionScope.user.balance}"
                            type="number" groupingUsed="true"/> VND
                    </a>
                </c:if>

                <a href="${pageContext.request.contextPath}/profile" class="user-pill">
                    <div class="user-avatar">
                        <%= ((dto.User) session.getAttribute("user")).getFullName().charAt(0) %>
                    </div>
                    ${sessionScope.user.fullName}
                    &nbsp;·&nbsp;
                    <span style="color:#89b4fa; font-size:11px;">${sessionScope.role}</span>
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                    Logout
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="btn-login-nav">
                    Login
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</div>