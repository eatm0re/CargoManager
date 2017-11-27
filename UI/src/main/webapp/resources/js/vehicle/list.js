var searchField = $("#searchField");
$("#findOneButton").click(findVehicle);
$("#showAllButton").click(showAllVehicles);
showAllVehicles();


function showAllVehicles() {
    showAll("vehicle", addVehicleRow)
}

function findVehicle() {
    var regNumber = searchField.val();
    if (!isSimpleName(regNumber)) {
        writeWrongValueMessage("registration number");
        return;
    }
    findVehicleByRegNumber(regNumber);
}