function addCityRow(row) {
    resultTable.append(
        "<tr>" +
        "<td>" + row.name + "</td>" +
        "<td>" + row.latitude + "</td>" +
        "<td>" + row.longitude + "</td>" +
        "</tr>"
    );
}

function findCityByName(name, after) {
    findOne("city", name, addCityRow, after);
}