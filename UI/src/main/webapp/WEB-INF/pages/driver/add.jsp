<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add driver</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Add driver</h1>
            <br/>
            <table>
                <tr>
                    <td>Personal number:</td>
                    <td><input id="persNumberField" type="text"/></td>
                </tr>
                <tr>
                    <td>First name:</td>
                    <td><input id="firstNameField" type="text"/></td>
                </tr>
                <tr>
                    <td>Last name:</td>
                    <td><input id="lastNameField" type="text"/></td>
                </tr>
                <tr>
                    <td>Location:</td>
                    <td><input id="locationField" type="text"/></td>
                </tr>
            </table>
            <button id="addDriverButton" type="button">Add</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/driver/util.js"/>"></script>
<script src="<c:url value="/resources/js/driver/initEditFields.js"/>"></script>
<script src="<c:url value="/resources/js/driver/add.js"/>"></script>

</body>

</html>