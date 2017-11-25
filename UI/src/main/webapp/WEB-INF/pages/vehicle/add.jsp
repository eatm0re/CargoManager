<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add vehicle</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Add vehicle</h1>
            <br/>
            <table>
                <tr>
                    <td>Registration number:</td>
                    <td><input id="regNumberField" type="text"/></td>
                </tr>
                <tr>
                    <td>Capacity (in KG):</td>
                    <td><input id="capacityKgField" type="text"/></td>
                </tr>
                <tr>
                    <td>City:</td>
                    <td><input id="townField" type="text"/></td>
                </tr>
            </table>
            <button type="button" onclick="addVehicle()">Add</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>

</body>

</html>