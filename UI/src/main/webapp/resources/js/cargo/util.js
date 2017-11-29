function addCargoRow(row) {
    resultTable.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td>" + row.name + "</td>" +
        "<td>" + row.weightKg + "</td>" +
        "<td>" + row.location.name + "</td>" +
        "</tr>"
    );
}

function findCargoById(id, after) {
    findOne("cargo", id, addCargoRow, after)
}