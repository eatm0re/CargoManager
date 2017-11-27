$("#addButton").click(addVehicle);


function addVehicle() {
    vehicle.regNumber = regNumberField.val();
    if (!isSimpleName(vehicle.regNumber)) {
        writeWrongValueMessage("registration number");
        return;
    }
    vehicle.capacityKg = capacityField.val();
    if (!isInteger(vehicle.capacityKg) || vehicle.capacityKg < 0) {
        writeRedStatus("Capacity must be a non-negative integer!");
        return;
    }
    vehicle.location.name = locationField.val();
    if (!isSimpleName(vehicle.location.name)) {
        writeWrongValueMessage("city name");
        return;
    }
    jQuery.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/vehicle",
        data: JSON.stringify(vehicle)
    }).done(function (result) {
        handleError(result, function () {
            findVehicleByRegNumber(vehicle.regNumber, function () {
                writeGreenStatus("Vehicle has successfully added!");
            });
        });
    })
}