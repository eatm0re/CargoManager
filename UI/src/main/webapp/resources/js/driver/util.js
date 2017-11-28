function addDriverRow(row) {
    var workedThisMonthH = Math.floor(row.workedThisMonthMs / 3600000);
    var workedThisMonthM = Math.floor(((row.workedThisMonthMs / 3600000) % 1) * 60);
    resultTable.append(
        "<tr>" +
        "<td>" + row.persNumber + "</td>" +
        "<td>" + row.firstName + "</td>" +
        "<td>" + row.lastName + "</td>" +
        "<td>" + row.status + "</td>" +
        "<td>" + (row.vehicle == null ? "" : row.vehicle.regNumber) + "</td>" +
        "<td>" + row.location.name + "</td>" +
        "<td>" + workedThisMonthH + "h " + workedThisMonthM + "m</td>" +
        "</tr>"
    );
}

function findDriverByPersNumber(persNumber, after) {
    findOne("driver", persNumber, addDriverRow, after);
}