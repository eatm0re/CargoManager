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
                    <td><input id="capacityField" type="text"/></td>
                </tr>
                <tr>
                    <td>Location:</td>
                    <td><input id="locationField" type="text"/></td>
                </tr>
            </table>
            <button id="addButton" type="button">Add</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/vehicle/util.js"/>"></script>
<script src="<c:url value="/resources/js/vehicle/initEditFields.js"/>"></script>
<script src="<c:url value="/resources/js/vehicle/add.js"/>"></script>

</body>

</html>