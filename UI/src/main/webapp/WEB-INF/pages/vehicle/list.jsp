<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Vehicles list</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Vehicles list</h1>
            <br/>
            Find by registration number:
            <input id="searchField" type="text">
            <button id="findOneButton" type="button">Find</button>
            <button id="showAllButton" type="button">Show all</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/vehicle/util.js"/>"></script>
<script src="<c:url value="/resources/js/vehicle/list.js"/>"></script>

</body>

</html>