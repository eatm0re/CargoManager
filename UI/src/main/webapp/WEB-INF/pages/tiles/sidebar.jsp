<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Sidebar -->
<div id="sidebar-wrapper">
    <ul class="sidebar-nav">
        <li class="sidebar-brand">
            <a href="${pageContext.request.contextPath}/">Main menu</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/driver">Drivers</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/driver/search">Search</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/driver/add">Add</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/driver/edit">Edit</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/vehicle">Vehicles</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/vehicle/search">Search</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/vehicle/add">Add</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/vehicle/edit">Edit</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/town">Cities</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/town/add">Add</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/order">Orders</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/order/details">Details</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/order/add">Add</a>
        </li>
        <li class="sidebar-h1">
            <a href="${pageContext.request.contextPath}/cargo">Cargoes</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/cargo/search">Search</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/cargo/add">Add</a>
        </li>
    </ul>
</div>