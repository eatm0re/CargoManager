<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Search cargoes</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Search cargoes</h1>
            <br/>
            Find by
            <select id="searchParamList">
                <option id="option_id" value="id" name="ID">ID</option>
                <option id="option_name" value="name" name="Name">name</option>
            </select>
            :
            <input id="searchField" type="text">
            <button type="button" onclick="findCargo()">Show</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>

</body>

</html>