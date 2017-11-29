<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add order</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Add order</h1>
            <ol id="detailsList">
            </ol>
            <button id="addCheckpointButton" type="button">New checkpoint</button>
            <br/><br/>
            <button id="addOrderButton" type="button">Create order</button>
            <div id="statusText">Status</div>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/order/add.js"/>"></script>

</body>

</html>