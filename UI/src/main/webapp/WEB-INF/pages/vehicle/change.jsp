<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Edit vehicle</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Edit vehicle</h1>
            <br/>
            <p>Type registration number and press "Load" button to load a vehicle record.</p>
            <c:import url="changeTable.jsp"/>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/vehicle/util.js"/>"></script>
<script src="<c:url value="/resources/js/vehicle/initEditFields.js"/>"></script>
<script src="<c:url value="/resources/js/vehicle/change.js"/>"></script>

<c:if test="${not empty regNumber}">
    <script>
        regNumberField.val("${regNumber}");
        loadVehicleToChange();
    </script>
</c:if>

</body>

</html>