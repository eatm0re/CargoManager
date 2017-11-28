var searchField = $("#searchField");
$("#findOneButton").click(findCity);
$("#showAllButton").click(showAllCities);
showAllCities();


function showAllCities() {
    showAll("city", addCityRow);
}

function findCity() {
    var name = searchField.val();
    if (!isSimpleName(name)) {
        writeWrongValueMessage("city name");
        return;
    }
    findCityByName(name);
}