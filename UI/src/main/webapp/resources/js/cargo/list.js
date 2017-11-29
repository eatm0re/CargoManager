var searchField = $("#searchField");
$("#findOneButton").click(findCargo);
$("#showAllButton").click(showAllCargoes);
showAllCargoes();


function showAllCargoes() {
    showAll("cargo", addCargoRow)
}

function findCargo() {
    var id = searchField.val();
    if (!isInteger(id)) {
        writeRedStatus("Cargo ID must be a positive integer");
        return;
    }
    findCargoById(id)
}