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
        "</tr>"
    );
}

function findVehicleByRegNumber(regNumber, after) {
    findOne("vehicle", regNumber, addVehicleRow, after);
}