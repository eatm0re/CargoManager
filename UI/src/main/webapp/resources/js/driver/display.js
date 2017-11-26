function addDriverRow(row) {
    var table = $("#resultTable");
    table.append(
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

function showAllDrivers() {
    showAll("driver", addDriverRow);
}

function findDriver() {
    refreshResultTable();
    var value = $("#searchField").val();
    if (!isSimpleName(value)) {
        writeWrongValueMessage("personal number");
        return;
    }
    findOne("driver", value, addDriverRow);
}