<table>
    <thead>
    <tr>
        <th>Personal number</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Location</th>
        <th>Vehicle</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody id="changeTable">
    <tr>
        <td>
            <input id="persNumberField" type="text">
        </td>
        <td>
            <input id="firstNameField" type="text">
        </td>
        <td>
            <input id="lastNameField" type="text">
        </td>
        <td>
            <input id="locationField" type="text">
        </td>
        <td>
            <input id="vehicleField" type="text">
        </td>
        <td>
            <select id="statusField">
                <option id="statusREST" value="REST">REST</option>
                <option id="statusWORK" value="WORK">WORK</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <button id="loadButton" type="button">Load</button>
            <button id="applyButton" type="button">Apply</button>
        </td>
    </tr>
    </tbody>
</table>