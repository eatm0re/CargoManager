var statusBROKEN = $("#statusBROKEN");
var statusOK = $("#statusOK");
$("#loadButton").click(loadVehicleToChange);
$("#applyButton").click(applyVehicleChanges);


function loadVehicleToChange() {
    var regNumber = regNumberField.val();
    if (!isSimpleName(regNumber)) {
        writeWrongValueMessage("registration number");
        return;
    }
    jQuery.ajax({
        url: contextPath + "/rest/vehicle/" + regNumber
    }).done(function (result) {
        handleError(result, function (result) {
            capacityField.val(result.capacityKg);
            setVehicleStatus(result.status);
            locationField.val(result.location.name);
            orderField.val(result.order == null ? "" : result.order.id);
        })
    })
}

function applyVehicleChanges() {
    vehicle.regNumber = regNumberField.val();
    if (!isSimpleName(vehicle.regNumber)) {
        writeWrongValueMessage("registration number");
        return;
    }
    vehicle.capacityKg = capacityField.val();
    if (!isInteger(vehicle.capacityKg)) {
        writeRedStatus("Capacity must be a positive integer!");
        return;
    }
    vehicle.status = statusField.val();
    vehicle.order.id = orderField.val();
    if (vehicle.order.id != "" && !isInteger(vehicle.order.id)) {
        writeRedStatus("Order ID must be a positive integer!");
        return;
    }
    vehicle.location.name = locationField.val();
    if (!isSimpleName(vehicle.location.name)) {
        writeWrongValueMessage("city name");
        return;
    }
    jQuery.ajax({
        type: "PUT",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/vehicle",
        data: JSON.stringify(vehicle)
    }).done(function (result) {
        handleError(result, function () {
            findVehicleByRegNumber(vehicle.regNumber, function () {
                writeGreenStatus("Vehicle has successfully changed!");
            });
        });
    })
}

function setVehicleStatus(status) {
    if (status == "OK") {
        statusBROKEN.removeAttr("selected");
        statusOK.attr("selected", "true");
    } else {
        statusOK.removeAttr("selected");
        statusBROKEN.attr("selected", "true");
    }
}