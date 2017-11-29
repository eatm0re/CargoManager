<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/logout" var="logoutUrl"/>

<!-- csrt for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

<!-- Sidebar -->
<div id="sidebar-wrapper">
    <ul class="sidebar-nav">
        <li class="sidebar-brand">
            <a href="${pageContext.request.contextPath}/admin">Main menu</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/driver">Drivers</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/driver/add">Add</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/driver/edit">Edit</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/vehicle">Vehicles</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/vehicle/add">Add</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/vehicle/edit">Edit</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/city">Cities</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/city/add">Add</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/order">Orders</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/order/add">Add</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/cargo">Cargoes</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/cargo/add">Add</a>
        </li>
        <li class="sidebar-brand">
            <a href="javascript:$('#logoutForm').submit()">Logout</a>
        </li>
    </ul>
</div>