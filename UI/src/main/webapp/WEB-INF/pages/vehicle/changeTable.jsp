<table>
    <thead>
    <tr>
        <th>Registration number</th>
        <th>Capacity (in KG)</th>
        <th>Status</th>
        <th>Order ID</th>
        <th>City</th>
    </tr>
    </thead>
    <tbody id="changeTable">
    <tr>
        <td>
            <input id="regNumberField" type="text">
        </td>
        <td>
            <input id="capacityKgField" type="text">
        </td>
        <td>
            <select id="statusField">
                <option id="statusOK" value="OK">OK</option>
                <option id="statusBROKEN" value="BROKEN">BROKEN</option>
            </select>
        </td>
        <td>
            <input id="orderField" type="text">
        </td>
        <td>
            <input id="townField" type="text">
        </td>
    </tr>
    <tr>
        <td>
            <button type="button" onclick="loadVehicleToChange()">Load</button>
            <button type="button" onclick="applyVehicleChanges()">Apply</button>
        </td>
    </tr>
    </tbody>
</table>