$("#addButton").click(addCity);


function addCity() {
    city.name = nameField.val();
    if (!isSimpleName(city.name)) {
        writeWrongValueMessage("city name");
        return;
    }
    city.latitude = latitudeField.val();
    if (isNaN(city.latitude)) {
        writeRedStatus("Latitude value must be a number");
        return;
    }
    city.longitude = longitudeField.val();
    if (isNaN(city.longitude)) {
        writeRedStatus("Longitude value must be a number");
        return;
    }
    jQuery.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/city",
        data: JSON.stringify(city)
    }).done(function (result) {
        handleError(result, function () {
            findCityByName(city.name, function () {
                writeGreenStatus("City has successfully added!");
            })
        });
    })
}