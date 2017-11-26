$("#addDriverButton").click(addDriver);


function addDriver() {
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
    jQuery.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/driver",
        data: JSON.stringify(driver)
    }).done(function (result) {
        handleError(result, function () {
            writeGreenStatus("Driver has successfully added!");
            findDriverByPersNumber(driver.persNumber);
        });
    })
}