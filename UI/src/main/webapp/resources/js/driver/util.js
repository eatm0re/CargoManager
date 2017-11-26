function addDriverRow(row) {
    resultTable.append(
        "<tr>" +
        "<td>" + row.persNumber + "</td>" +
        "<td>" + row.firstName + "</td>" +
        "<td>" + row.lastName + "</td>" +
        "<td>" + row.status + "</td>" +
        "<td>" + (row.vehicle == null ? "" : row.vehicle.regNumber) + "</td>" +
        "<td>" + row.location.name + "</td>" +
        "<td>" + (row.workedThisMonthMs / 3600000) + "</td>" +
        "</tr>"
    );
}

function findDriverByPersNumber(persNumber) {
    findOne("driver", persNumber, addDriverRow);
}