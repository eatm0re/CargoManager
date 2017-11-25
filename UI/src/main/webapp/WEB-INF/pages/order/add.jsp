<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add order</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body onload="addCheckpointToEditList()">

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Add order</h1>
            <ol id="detailsList">
            </ol>
            <button type="button" onclick="addCheckpointToEditList()">New checkpoint</button>
            <br/><br/>
            <button type="button" onclick="addOrder()">Create order</button>
            <div id="statusText">Status</div>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>

</body>

</html>