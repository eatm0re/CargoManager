<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add cargo</title>
    <c:import url="../tiles/stylesheets.jsp"/>
</head>

<body>

<div id="wrapper" class="toggled">

    <c:import url="../tiles/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <h1>Add cargo</h1>
            <br/>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input id="nameField" type="text" size="60"/></td>
                </tr>
                <tr>
                    <td>Weight (in KG):</td>
                    <td><input id="weightField" type="text" size="20"/></td>
                </tr>
                <tr>
                    <td>Location:</td>
                    <td><input id="locationField" type="text" size="20"/></td>
                </tr>
                <tr>
            </table>
            <button id="addButton" type="button">Add</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>
<script src="<c:url value="/resources/js/cargo/util.js"/>"></script>
<script src="<c:url value="/resources/js/cargo/initEditFields.js"/>"></script>
<script src="<c:url value="/resources/js/cargo/add.js"/>"></script>

</body>

</html>