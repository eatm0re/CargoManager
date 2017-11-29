<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/logout" var="logoutUrl"/>

<!-- csrf for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<!-- Sidebar -->
<div id="sidebar-wrapper">
    <ul class="sidebar-nav">
        <li class="sidebar-brand">
            <a href="${pageContext.request.contextPath}/user">Main menu</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/user/order">Order</a>
        </li>
        <li class="sidebar-brand">
            <a href="javascript:$('#logoutForm').submit()">Logout</a>
        </li>
    </ul>
</div>