$("#addButton").click(addCargo);


function addCargo() {
    cargo.name = nameField.val();
    if (!isLongName(cargo.name)) {
        writeWrongValueMessage("cargo name");
        return;
    }
    cargo.weightKg = weightField.val();
    if (isNaN(cargo.weightKg) || cargo.weightKg < 0) {
        writeRedStatus("Cargo weight must be a non-negative number");
        return;
    }
    cargo.location.name = locationField.val();
    if (!isSimpleName(cargo.location.name)) {
        writeWrongValueMessage("city name");
        return;
    }
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/cargo",
        data: JSON.stringify(cargo)
    }).done(function (result) {
        handleError(result, function (id) {
            findCargoById(id, function () {
                writeGreenStatus("Cargo has successfully added!");
            });
        });
    })
}