<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Search vehicles</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Search vehicles</h1>
            <br/>
            Find by
            <select id="searchParamList">
                <option id="option_id" value="id" name="ID">ID</option>
                <option id="option_regNumber" value="regNumber" name="Registration number">registration number</option>
                <option id="option_town" value="town" name="City">town</option>
            </select>
            :
            <input id="searchField" type="text">
            <button type="button" onclick="findVehicle()">Show</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>

</body>

</html>