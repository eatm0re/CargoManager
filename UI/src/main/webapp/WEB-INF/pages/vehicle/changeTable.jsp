<table>
    <thead>
    <tr>
        <th>Registration number</th>
        <th>Capacity (in KG)</th>
        <th>Status</th>
        <th>Location</th>
        <th>Order ID</th>
    </tr>
    </thead>
    <tbody id="changeTable">
    <tr>
        <td>
            <input id="regNumberField" type="text">
        </td>
        <td>
            <input id="capacityField" type="text">
        </td>
        <td>
            <select id="statusField">
                <option id="statusOK" value="OK">OK</option>
                <option id="statusBROKEN" value="BROKEN">BROKEN</option>
            </select>
        </td>
        <td>
            <input id="locationField" type="text">
        </td>
        <td>
            <input id="orderField" type="text">
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