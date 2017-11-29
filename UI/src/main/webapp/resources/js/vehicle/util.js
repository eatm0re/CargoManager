function addVehicleRow(row) {
    resultTable.append(
        "<tr>" +
        "<td>" + row.regNumber + "</td>" +
        "<td>" + row.capacityKg + "</td>" +
        "<td>" + row.status + "</td>" +
        "<td>" + row.location.name + "</td>" +
        "<td>" + (row.drivers.length == 0 ? "" : row.drivers.length) + "</td>" +
        "<td>" + (row.order == null ? "" : row.order.id) + "</td>" +
        "<td>" + (row.cargoes.length == 0 ? "" : row.cargoes.length) + "</td>" +
        "<td>" +
        "<button id='generic_editButton_" + row.id + "' type='button'>Edit</button> " +
        "</td>" +
        "</tr>"
    );
    $("#generic_editButton_" + row.id).click(function () {
        editVehicleValue.val(row.regNumber);
        editVehicleForm.submit();
    });
}

function findVehicleByRegNumber(regNumber, after) {
    findOne("vehicle", regNumber, addVehicleRow, after);
}