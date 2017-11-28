<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Cities list</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Cities list</h1>
            <br/>
            Find by name:
            <input id="searchField" type="text">
            <button id="findOneButton" type="button">Find</button>
            <button id="showAllButton" type="button">Show all</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/city/util.js"/>"></script>
<script src="<c:url value="/resources/js/city/list.js"/>"></script>

</body>

</html>