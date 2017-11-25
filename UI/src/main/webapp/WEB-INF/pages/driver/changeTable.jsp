<table>
    <thead>
    <tr>
        <th>Personal number</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Status</th>
        <th>Vehicle (reg. number)</th>
        <th>City</th>
    </tr>
    </thead>
    <tbody id="changeTable">
    <tr>
        <td>
            <input id="persNumberField" type="text" <%--size="10"--%>>
        </td>
        <td>
            <input id="firstNameField" type="text" <%--size="30"--%>>
        </td>
        <td>
            <input id="lastNameField" type="text" <%--size="30"--%>>
        </td>
        <td>
            <select id="statusField">
                <option id="statusREST" value="REST">REST</option>
                <option id="statusSHIFT" value="SHIFT">SHIFT</option>
                <option id="statusWHEEL" value="WHEEL">WHEEL</option>
            </select>
        </td>
        <td>
            <input id="vehicleField" type="text" <%--size="10"--%>>
        </td>
        <td>
            <input id="townField" type="text" <%--size="30"--%>>
        </td>
    </tr>
    <tr>
        <td>
            <button type="button" onclick="loadDriverToChange()">Load</button>
            <button type="button" onclick="applyDriverChanges()">Apply</button>
        </td>
    </tr>
    </tbody>
</table>