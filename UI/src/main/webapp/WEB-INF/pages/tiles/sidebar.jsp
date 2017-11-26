<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Sidebar -->
<div id="sidebar-wrapper">
    <ul class="sidebar-nav">
        <li class="sidebar-brand">
            <a href="${pageContext.request.contextPath}/admin/">Main menu</a>
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
            <a href="${pageContext.request.contextPath}/admin/vehicle/search">Search</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/vehicle/add">Add</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/vehicle/edit">Edit</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/town">Cities</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/town/add">Add</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/order">Orders</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/order/details">Details</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/order/add">Add</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/admin/cargo">Cargoes</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/cargo/search">Search</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/cargo/add">Add</a>
        </li>
    </ul>
</div>