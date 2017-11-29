<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>CargoManager</title>
    <c:import url="tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Welcome, ${pageContext.request.userPrincipal.name}!</h1>
        </div>
    </div>

</div>

<c:import url="tiles/scripts.jsp"/>

</body>

</html>
