<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Orders list</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>All orders</h1>
            <br/>
            Show order by number:
            <input id="searchField" type="text">
            <button id="showButton" type="button">Show</button>
            <div id="statusText">Status</div>
            <ol id="detailsList">
            </ol>
            <div id="interruptBlock"></div>
            <br/><br/>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/order/list.js"/>"></script>

</body>

</html>