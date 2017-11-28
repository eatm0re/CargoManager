<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add city</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Add city</h1>
            <br/>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input id="nameField" type="text"/></td>
                </tr>
                <tr>
                    <td>Latitude:</td>
                    <td><input id="latitudeField" type="text"/></td>
                </tr>
                <tr>
                    <td>Longitude:</td>
                    <td><input id="longitudeField" type="text"/></td>
                </tr>
            </table>
            <button id="addButton" type="button">Add</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/city/util.js"/>"></script>
<script src="<c:url value="/resources/js/city/initEditFields.js"/>"></script>
<script src="<c:url value="/resources/js/city/add.js"/>"></script>

</body>

</html>