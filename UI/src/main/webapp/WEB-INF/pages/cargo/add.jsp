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
                    <td><input id="weightKgField" type="text" size="15"/></td>
                </tr>
                <tr>
            </table>
            <button type="button" onclick="addCargo()">Add</button>
            <div id="statusText">Status</div>
            <c:import url="resultTable.jsp"/>
        </div>
    </div>

</div>

<c:import url="../tiles/scripts.jsp"/>

</body>

</html>