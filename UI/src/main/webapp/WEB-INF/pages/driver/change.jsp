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

</body>

</html>