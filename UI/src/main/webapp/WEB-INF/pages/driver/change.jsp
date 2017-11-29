<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Edit driver</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Edit driver</h1>
            <br/>
            <p>Type personal number and press "Load" button to load a driver record.</p>
            <c:import url="changeTable.jsp"/>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/driver/util.js"/>"></script>
<script src="<c:url value="/resources/js/driver/initEditFields.js"/>"></script>
<script src="<c:url value="/resources/js/driver/change.js"/>"></script>

<c:if test="${not empty persNumber}">
    <script>
        persNumberField.val("${persNumber}");
        loadDriverToChange();
    </script>
</c:if>

</body>

</html>