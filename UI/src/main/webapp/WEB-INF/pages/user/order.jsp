<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Current order</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar-user.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Current order</h1>
            <br/>
            <div id="interruptBlock"></div>
            <br/><br/>
            <div id="statusText">Status</div>
            <ol id="detailsList"></ol>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/user/order.js"/>"></script>

</body>

</html>