<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Drivers list</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Drivers list</h1>
            <br/>
            Find by personal number:
            <input id="searchField" type="text">
            <button id="findOneButton" type="button">Find</button>
            <button id="showAllButton" type="button">Show all</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/driver/util.js"/>"></script>
<script src="<c:url value="/resources/js/driver/list.js"/>"></script>

</body>

</html>