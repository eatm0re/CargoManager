var searchField = $("#searchField");
var editDriverForm = $("#editDriverForm");
var editDriverValue = $("#editDriverValue");
$("#findOneButton").click(findDriver);
$("#showAllButton").click(showAllDrivers);
showAllDrivers();


function showAllDrivers() {
    showAll("driver", addDriverRow);
}

function findDriver() {
    var persNumber = searchField.val();
    if (!isSimpleName(persNumber)) {
        writeWrongValueMessage("personal number");
        return;
    }
    findDriverByPersNumber(persNumber);
}