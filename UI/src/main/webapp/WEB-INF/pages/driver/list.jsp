<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Drivers list</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body onload="showAllDrivers()">

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Drivers list</h1>
            <br/>
            Find by personal number:
            <input id="searchField" type="text">
            <button type="button" onclick="findDriver()">Find</button>
            <button type="button" onclick="showAllDrivers()">Show all</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/driver/display.js"/>"></script>

</body>

</html>