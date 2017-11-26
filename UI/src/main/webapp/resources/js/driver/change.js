var statusREST = $("#statusREST");
var statusWORK = $("#statusWORK");
$("#loadButton").click(loadDriverToChange);
$("#applyButton").click(applyDriverChanges);


function loadDriverToChange() {
    var persNumber = persNumberField.val();
    if (!isSimpleName(persNumber)) {
        writeWrongValueMessage("personal number");
        return;
    }
    jQuery.ajax({
        url: contextPath + "/rest/driver/" + persNumber
    }).done(function (result) {
        handleError(result, function (result) {
            firstNameField.val(result.firstName);
            lastNameField.val(result.lastName);
            locationField.val(result.location.name);
            vehicleField.val(result.vehicle == null ? "" : result.vehicle.regNumber);
            setDriverStatus(result.currStatus);
        })
    })
}

function applyDriverChanges() {
    driver.persNumber = persNumberField.val();
    if (!isSimpleName(driver.persNumber)) {
        writeWrongValueMessage("personal number");
        return;
    }
    driver.firstName = firstNameField.val();
    if (!isSimpleName(driver.firstName)) {
        writeWrongValueMessage("first name");
        return;
    }
    driver.lastName = lastNameField.val();
    if (!isSimpleName(driver.lastName)) {
        writeWrongValueMessage("last name");
        return;
    }
    driver.location.name = locationField.val();
    if (!isSimpleName(driver.location.name)) {
        writeWrongValueMessage("city name");
        return;
    }
    driver.vehicle.regNumber = vehicleField.val();
    if (driver.vehicle.regNumber != "") {
        if (!isSimpleName(driver.vehicle.regNumber)) {
            writeWrongValueMessage("vehicle registration number");
            return;
        }
    }
    driver.status = statusField.val();
    jQuery.ajax({
        type: "PUT",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/driver",
        data: JSON.stringify(driver)
    }).done(function (result) {
        handleError(result, function () {
            writeGreenStatus("Driver has successfully changed!");
            findDriverByPersNumber(driver.persNumber);
        });
    })
}

function setDriverStatus(status) {
    if (status == "WORK") {
        statusREST.removeAttr("selected");
        statusWORK.attr("selected", "true");
    } else {
        statusWORK.removeAttr("selected");
        statusREST.attr("selected", "true");
    }
}