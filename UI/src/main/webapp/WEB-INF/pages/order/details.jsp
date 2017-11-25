<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Order details</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Order details</h1>
            Order number:
            <input id="orderIdField" type="text"/>
            <button type="button" onclick="showOrderDetails()">Show</button>
            <div id="statusText">Status</div>
            <div id="completeCheckpoint"></div>
            <ol id="detailsList">
            </ol>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>

</body>

</html>