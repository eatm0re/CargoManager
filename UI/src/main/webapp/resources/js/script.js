var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/"));

function refreshResultTable() {
    jQuery("#resultTable").empty();
}

function refreshDetailsList() {
    $("#detailsList").empty();
}

var statusText = $("#statusText");

function writeStatus(message) {
    if (message.length > 0) {
        statusText.css("opacity", 1);
        statusText.text(message);
    } else {
        statusText.css("opacity", 0);
        statusText.text("Status");
    }
}

function writeRedStatus(message) {
    markStatusRed();
    writeStatus(message);
}

function writeGreenStatus(message) {
    markStatusGreen();
    writeStatus(message);
}

function writeWrongValueMessage(name) {
    writeRedStatus("Wrong '" + name + "' value! It is only allowed letters, digits and '-'.");
}

function markStatusRed() {
    statusText.css("color", "red");
}

function markStatusGreen() {
    statusText.css("color", "green");
}

function hideStatus() {
    writeStatus("");
}

function handleError(result, action) {
    hideStatus();
    if (result.code == 200) {
        action(result.body);
    } else {
        writeRedStatus(result.message);
        console.log(result.body);
    }
}

function findBy(entity, param, action) {
    jQuery
        .ajax({url: contextPath + "/rest/" + entity + "/" + param})
        .done(function (result) {
            handleError(result, function (result) {
                for (var i = 0; i < result.length; i++) {
                    action(result[i]);
                }
            })
        })
}

function findOneBy(entity, param, action) {
    jQuery
        .ajax({url: contextPath + "/rest/" + entity + "/" + param})
        .done(function (result) {
            handleError(result, action);
        })
}

function findByParam(entityPath, action, paramName, paramValue) {
    jQuery
        .ajax({url: contextPath + "/" + entityPath + "/" + paramName.toLowerCase() + "/" + paramValue})
        .done(function (result) {
            handleError(result, function (result) {
                for (var i = 0; i < result.length; i++) {
                    action(result[i]);
                }
            })
        })
}

function findOneByParam(entityPath, action, paramName, paramValue) {
    jQuery
        .ajax({url: contextPath + "/" + entityPath + "/" + paramName.toLowerCase() + "/" + paramValue})
        .done(function (result) {
            handleError(result, action);
        });
}

function isInteger(str) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isDigit(c)) {
            return false;
        }
    }
    return str.length > 0;
}

var MAX_INT = 2147483647;
var DEFAULT_MAX_LENGTH = 45;

function isPermittedExtString(str) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isPermittedExtChar(c)) {
            return false;
        }
    }
    return str.length > 0;
}

function isPermittedString(str) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isPermittedChar(c)) {
            return false;
        }
    }
    return str.length > 0;
}

function ensureLength(str, maxLength, name) {
    if (str.length > maxLength) {
        writeRedStatus("'" + name + "' value is too long. Max length allowed: " + maxLength + ".");
        return true;
    }
    return false;
}

function isPermittedChar(char) {
    return isLetter(char) || isDigit(char) || isPermittedSpecSymbol(char);
}

function isPermittedExtChar(char) {
    return isLetter(char) || isDigit(char) || isPermittedExtSymbol(char);
}

function isDigit(char) {
    return (char >= "0") && (char <= "9");
}

function isLetter(char) {
    return (char >= "a") && (char <= "z") || (char >= "A") && (char <= "Z");
}

function isPermittedSpecSymbol(char) {
    return char == "-";
}

function isPermittedExtSymbol(char) {
    var permittedExtSymbols = "-_ :;.()!/|";
    for (var i = 0; i < permittedExtSymbols.length; i++) {
        if (char == permittedExtSymbols.charAt(i)) {
            return true;
        }
    }
    return false;
}


// DRIVERS

function addDriverRow(row) {
    var table = jQuery("#resultTable");
    table.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td>" + row.persNumber + "</td>" +
        "<td>" + row.firstName + "</td>" +
        "<td>" + row.lastName + "</td>" +
        "<td>" + row.status + "</td>" +
        "<td>" + (row.vehicle == null ? "" : row.vehicle.regNumber) + "</td>" +
        "<td>" + row.location.name + "</td>" +
        "<td>" + (row.workedThisMonthMs / 3600000) + "</td>" +
        "</tr>"
    );
}

function showAllDrivers() {
    findBy("driver", "", addDriverRow);
}

function findDriver() {
    refreshResultTable();
    var param = $("#searchParamList").val();
    var value = $("#searchField").val();
    switch (param) {
        case "id":
            if (!isInteger(value)) {
                writeRedStatus("ID must be a positive integer!");
                break;
            }
            if (value > MAX_INT) {
                writeRedStatus("ID must not be more than is too big!");
                break;
            }
            findOneByParam("driver", addDriverRow, param, value);
            break;
        case "persNumber":
            if (!isPermittedString(value)) {
                writeWrongValueMessage("Personal number");
                break;
            }
            if (ensureLength(value, DEFAULT_MAX_LENGTH, "Personal number")) {
                break;
            }
            findOneByParam("driver", addDriverRow, param, value);
            break;
        default:
            var name = $("#option_" + param).attr("name");
            if (!isPermittedString(value)) {
                writeWrongValueMessage(name);
                break;
            }
            if (ensureLength(value, DEFAULT_MAX_LENGTH, name)) {
                break;
            }
            findByParam("driver", addDriverRow, param, value);
    }
}

function addDriver() {
    refreshResultTable();
    var persNumber = jQuery("#persNumberField").val();
    if (!isPermittedString(persNumber)) {
        writeWrongValueMessage("Personal number");
        return;
    }
    if (ensureLength(persNumber, DEFAULT_MAX_LENGTH, "Personal number")) {
        return;
    }
    var firstName = jQuery("#firstNameField").val();
    if (!isPermittedString(firstName)) {
        writeWrongValueMessage("First name");
        return;
    }
    if (ensureLength(firstName, DEFAULT_MAX_LENGTH, "First name")) {
        return;
    }
    var lastName = jQuery("#lastNameField").val();
    if (!isPermittedString(lastName)) {
        writeWrongValueMessage("Last name");
        return;
    }
    if (ensureLength(name, DEFAULT_MAX_LENGTH, "Last name")) {
        return;
    }
    var town = jQuery("#townField").val();
    if (!isPermittedString(town)) {
        writeWrongValueMessage("City");
        return;
    }
    if (ensureLength(town, DEFAULT_MAX_LENGTH, "City")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/driver/create?persnumber=" + persNumber +
        "&firstname=" + firstName +
        "&lastname=" + lastName +
        "&town=" + town
    }).done(function (result) {
        handleError(result, addDriverRow);
    })
}

function setDriverStatus(status) {
    $("#statusREST").removeAttr("selected");
    $("#statusSHIFT").removeAttr("selected");
    $("#statusWHEEL").removeAttr("selected");
    $("#status" + status).attr("selected", "true")
}

function loadDriverToChange() {
    var persNumber = $("#persNumberField").val();
    if (!isPermittedString(persNumber)) {
        writeWrongValueMessage("Personal number");
        return;
    }
    if (ensureLength(persNumber, DEFAULT_MAX_LENGTH, "Personal number")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/driver/persnumber/" + persNumber
    }).done(function (result) {
        handleError(result, function (result) {
            $("#firstNameField").val(result.firstName);
            $("#lastNameField").val(result.lastName);
            setDriverStatus(result.currStatus);
            $("#vehicleField").val(result.vehicle == null ? "" : result.vehicle.regNumber);
            $("#townField").val(result.town.name);
        })
    })
}

function applyDriverChanges() {
    refreshResultTable();
    var persNumber = $("#persNumberField").val();
    if (!isPermittedString(persNumber)) {
        writeWrongValueMessage("Personal number");
        return;
    }
    if (ensureLength(persNumber, DEFAULT_MAX_LENGTH, "Personal number")) {
        return;
    }
    var firstName = $("#firstNameField").val();
    if (!isPermittedString(firstName)) {
        writeWrongValueMessage("First name");
        return;
    }
    if (ensureLength(firstName, DEFAULT_MAX_LENGTH, "First name")) {
        return;
    }
    var lastName = $("#lastNameField").val();
    if (!isPermittedString(lastName)) {
        writeWrongValueMessage("Last name");
        return;
    }
    if (ensureLength(lastName, DEFAULT_MAX_LENGTH, "Last name")) {
        return;
    }
    var status = $("#statusField").val();
    var vehicle = $("#vehicleField").val();
    if (vehicle != "") {
        if (!isPermittedString(vehicle)) {
            writeWrongValueMessage("Vehicle");
            return;
        }
        if (ensureLength(vehicle, DEFAULT_MAX_LENGTH, "Vehicle")) {
            return;
        }
    }
    var town = $("#townField").val();
    if (!isPermittedString(town)) {
        writeWrongValueMessage("City");
        return;
    }
    if (ensureLength(town, DEFAULT_MAX_LENGTH, "City")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/driver/change?persnumber=" + persNumber +
        "&firstname=" + firstName +
        "&lastname=" + lastName +
        "&status=" + status +
        "&vehicle=" + vehicle +
        "&town=" + town
    }).done(function (result) {
        handleError(result, addDriverRow);
    })
}


// VEHICLES

function addVehicleRow(row) {
    var table = jQuery("#resultTable");
    table.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td>" + row.regNumber + "</td>" +
        "<td>" + row.capacityKg + "</td>" +
        "<td>" + row.status + "</td>" +
        "<td>" + (row.orderId == 0 ? "" : row.orderId) + "</td>" +
        "<td>" + row.town.name + "</td>" +
        "</tr>"
    );
}

function showAllVehicles() {
    findByParam("vehicle", addVehicleRow, "all", "");
}

function findVehicle() {
    refreshResultTable();
    var param = jQuery("#searchParamList").val();
    var value = jQuery("#searchField").val();
    switch (param) {
        case "id":
            if (!isInteger(value)) {
                writeRedStatus("ID must be a positive integer!");
                break;
            }
            if (value > MAX_INT) {
                writeRedStatus("ID must not be more than is too big!");
                break;
            }
            findOneByParam("vehicle", addVehicleRow, param, value);
            break;
        case "regNumber":
            if (!isPermittedString(value)) {
                writeWrongValueMessage("Registration number");
                break;
            }
            if (ensureLength(value, DEFAULT_MAX_LENGTH, "Registration number")) {
                break;
            }
            findOneByParam("vehicle", addVehicleRow, param, value);
            break;
        default:
            var name = $("#option_" + param).attr("name");
            if (!isPermittedString(value)) {
                writeWrongValueMessage(name);
                break;
            }
            if (ensureLength(value, DEFAULT_MAX_LENGTH, name)) {
                break;
            }
            findByParam("vehicle", addVehicleRow, param, value);
    }
}

function addVehicle() {
    refreshResultTable();
    var regNumber = jQuery("#regNumberField").val();
    if (!isPermittedString(regNumber)) {
        writeWrongValueMessage("Registration number");
        return;
    }
    if (ensureLength(regNumber, DEFAULT_MAX_LENGTH, "Registration number")) {
        return;
    }
    var capacityKg = jQuery("#capacityKgField").val();
    if (!isInteger(capacityKg)) {
        writeRedStatus("Capacity must be a non-negative integer!");
        return;
    }
    if (capacityKg > MAX_INT) {
        writeRedStatus("Capacity must not be more than is too big!");
        return;
    }
    var town = jQuery("#townField").val();
    if (!isPermittedString(town)) {
        writeWrongValueMessage("City");
        return;
    }
    if (ensureLength(town, DEFAULT_MAX_LENGTH, "City")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/vehicle/create?regnumber=" + regNumber +
        "&capacitykg=" + capacityKg +
        "&town=" + town
    }).done(function (result) {
        handleError(result, addVehicleRow);
    })
}

function setVehicleStatus(status) {
    $("#statusBROKEN").removeAttr("selected");
    $("#statusOK").removeAttr("selected");
    $("#status" + status).attr("selected", "true");
}

function loadVehicleToChange() {
    var regNumber = $("#regNumberField").val();
    if (!isPermittedString(regNumber)) {
        writeWrongValueMessage("Registration number");
        return;
    }
    if (ensureLength(regNumber, DEFAULT_MAX_LENGTH, "Registration number")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/vehicle/regnumber/" + regNumber
    }).done(function (result) {
        handleError(result, function (result) {
            $("#capacityKgField").val(result.capacityKg);
            setVehicleStatus(result.status);
            $("#orderField").val(result.orderId == 0 ? "" : result.orderId);
            $("#townField").val(result.town.name);
        })
    })
}

function applyVehicleChanges() {
    refreshResultTable();
    var regNumber = $("#regNumberField").val();
    if (!isPermittedString(regNumber)) {
        writeWrongValueMessage("Registration number");
        return;
    }
    if (ensureLength(regNumber, DEFAULT_MAX_LENGTH, "Registration number")) {
        return;
    }
    var capacityKg = $("#capacityKgField").val();
    if (!isInteger(capacityKg)) {
        writeRedStatus("Capacity must be a non-negative integer!");
        return;
    }
    if (capacityKg > MAX_INT) {
        writeRedStatus("Capacity must not be more than is too big!");
        return;
    }
    var status = $("#statusField").val();
    var order = $("#orderField").val();
    if (order != "") {
        if (!isInteger(order)) {
            writeRedStatus("Order ID must be a positive integer!");
            return;
        }
        if (order > MAX_INT) {
            writeRedStatus("Order ID must not be more than is too big!");
            return;
        }
    }
    var town = $("#townField").val();
    if (!isPermittedString(town)) {
        writeWrongValueMessage("City");
        return;
    }
    if (ensureLength(town, DEFAULT_MAX_LENGTH, "City")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/vehicle/change?regnumber=" + regNumber +
        "&capacitykg=" + capacityKg +
        "&status=" + status +
        "&orderid=" + (order == "" ? 0 : order) +
        "&town=" + town
    }).done(function (result) {
        handleError(result, addVehicleRow);
    })
}


// TOWNS

function addTownRow(row) {
    var table = jQuery("#resultTable");
    table.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td>" + row.name + "</td>" +
        "</tr>"
    );
}

function showAllTowns() {
    findByParam("town", addTownRow, "all", "");
}

function addTown() {
    refreshResultTable();
    var name = jQuery("#townNameField").val();
    if (!isPermittedString(name)) {
        writeWrongValueMessage("City name");
        return;
    }
    if (ensureLength(name, DEFAULT_MAX_LENGTH, "City")) {
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/town/create?name=" + name
    }).done(function (result) {
        handleError(result, addTownRow);
    })
}


// ORDERS

var orderRowCounter = 0;

function addOrderRow(row) {
    var table = jQuery("#resultTable");
    var currentRow = orderRowCounter++;
    table.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td id='generic_capacity_" + currentRow + "'></td>" +
        "<td>" + (row.completed ? "YES" : "NO") + "</td>" +
        "<td>" + (row.vehicle == null ? "" : row.vehicle.regNumber) + "</td>" +
        "</tr>"
    );
    jQuery
        .ajax({url: contextPath + "/order/capacityneeded/" + row.id})
        .done(function (result) {
            $("#generic_capacity_" + currentRow).append(result.data);
        })
}

function showAllOrders() {
    findByParam("order", addOrderRow, "all", "");
}

function addCheckpointToList(checkpoint) {
    var strToAppend = "<li>" + checkpoint.town.name + "<ol>";
    var tasks = checkpoint.tasks;
    var length = tasks.length;
    for (var i = 0; i < length; i++) {
        strToAppend += (
            "<li>" +
            (tasks[i].type == "LOADING" ? "Loading" : "Unloading") + " cargo #" +
            tasks[i].cargo.id + " (" +
            tasks[i].cargo.name + ", " +
            tasks[i].cargo.weightKg + "kg)" +
            "</li>"
        )
    }
    strToAppend += "</ol></li>";
    ($("#detailsList")).append(strToAppend);
}

function showOrderDetails() {
    refreshDetailsList();
    var completeCheckpoint = $("#completeCheckpoint");
    completeCheckpoint.empty();
    var id = $("#orderIdField").val();
    if (!isInteger(id)) {
        writeRedStatus("ID must be a positive integer!");
        return;
    }
    if (id > MAX_INT) {
        writeRedStatus("ID is too big!");
        return;
    }
    jQuery
        .ajax({url: contextPath + "/order/id/" + id})
        .done(function (result) {
            handleError(result, function (result) {
                completeCheckpoint.append("<button type='button' onclick='completeCheckpoint(" + id + ")'>Mark checkpoint completed</button>");
                if (result.completed) {
                    completeCheckpoint.empty();
                    completeCheckpoint.css("color", "green");
                    completeCheckpoint.append("Order completed!");
                } else {
                    var checkpoints = result.checkpoints;
                    var length = checkpoints.length;
                    for (var i = 0; i < length; i++) {
                        addCheckpointToList(checkpoints[i]);
                    }
                }
            })
        })
}

function completeCheckpoint(id) {
    var completeCheckpoint = $("#completeCheckpoint");
    jQuery
        .ajax({url: contextPath + "/order/checkpoint/" + id})
        .done(function (result) {
            handleError(result, function (result) {
                refreshDetailsList();
                jQuery
                    .ajax({url: contextPath + "/order/id/" + id})
                    .done(function (result) {
                        handleError(result, function (result) {
                            if (result.completed) {
                                completeCheckpoint.empty();
                                completeCheckpoint.css("color", "green");
                                completeCheckpoint.append("Order completed!");
                            } else {
                                var checkpoints = result.checkpoints;
                                var length = checkpoints.length;
                                for (var i = 0; i < length; i++) {
                                    addCheckpointToList(checkpoints[i]);
                                }
                            }
                        })
                    })
            })
        })
}

var checkpointCounter = 0;
var taskCounter = 0;
var checkpointByTask = [];

function addCheckpointToEditList() {
    $("#detailsList").append(
        "<li>" +
        "City: <input id='generic_town_" + checkpointCounter + "' type='text' size='38'/><br/><br/>" +
        "<ol id='generic_checkpoint_" + checkpointCounter + "'></ol>" +
        "<button type='button' onclick='addTaskToEditList(" + checkpointCounter + ")'>New task</button><br/><br/>" +
        "</li>"
    );
    addTaskToEditList(checkpointCounter++);
}

function addTaskToEditList(checkpoint) {
    $("#generic_checkpoint_" + checkpoint).append(
        "<li>" +
        "<select id='generic_type_" + taskCounter + "'>" +
        "<option value='LOADING'>Loading</option>" +
        "<option value='UNLOADING'>Unloading</option>" +
        "</select>" +
        " cargo #" +
        "<input id='generic_cargoId_" + taskCounter + "' type='text' size='15' onchange='showCargoInfo(" + taskCounter + ", this.value)'/>" +
        " <i id='generic_cargoInfo_" + taskCounter + "'></i><br/><br/>" +
        "</li>"
    );
    checkpointByTask.push(checkpoint);
    taskCounter++
}

function showCargoInfo(divId, cargoId) {
    jQuery
        .ajax({url: contextPath + "/cargo/id/" + cargoId})
        .done(function (result) {
            handleError(result, function (result) {
                var div = $("#generic_cargoInfo_" + divId);
                div.empty();
                div.append("(" + result.name + ", " + result.weightKg + "kg)");
            })
        })
}

function addOrder() {
    var checkpoints = [];
    for (var i = 0; i < checkpointCounter; i++) {
        var town = $("#generic_town_" + i).val();
        if (!isPermittedString(town)) {
            writeWrongValueMessage("City " + town);
            return;
        }
        if (ensureLength(town, DEFAULT_MAX_LENGTH, "City")) {
            return;
        }
        checkpoints.push({
            id: i,
            town: {name: town},
            tasks: []
        })
    }
    for (i = 0; i < taskCounter; i++) {
        var cargo = $("#generic_cargoId_" + i).val();
        if (!isInteger(cargo)) {
            writeRedStatus("Cargo ID must be a positive integer!");
            return;
        }
        if (cargo > MAX_INT) {
            writeRedStatus("Cargo ID is too big!");
            return;
        }
        checkpoints[checkpointByTask[i]].tasks.push({
            type: $("#generic_type_" + i).val(),
            cargo: {id: cargo}
        })
    }
    jQuery.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/order/create",
        data: JSON.stringify(checkpoints)
    }).done(function (result) {
        handleError(result, function (result) {
            writeGreenStatus("Order has successfully created with ID: " + result.id);
        })
    })
}


// CARGOES

function addCargoRow(row) {
    var table = jQuery("#resultTable");
    table.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td>" + row.name + "</td>" +
        "<td>" + row.weightKg + "</td>" +
        "</tr>"
    );
}

function showAllCargoes() {
    findByParam("cargo", addCargoRow, "all", "");
}

function findCargo() {
    refreshResultTable();
    var param = jQuery("#searchParamList").val();
    var value = jQuery("#searchField").val();
    switch (param) {
        case "id":
            if (!isInteger(value)) {
                writeRedStatus("ID must be a positive integer!");
                break;
            }
            if (value > MAX_INT) {
                writeRedStatus("ID is too big!");
                break;
            }
            findOneByParam("cargo", addCargoRow, param, value);
            break;
        default:
            var name = $("#option_" + param).attr("name");
            if (!isPermittedString(value)) {
                writeWrongValueMessage(name);
                break;
            }
            if (ensureLength(value, 255, name)) {
                break;
            }
            findByParam("cargo", addCargoRow, param, value);
    }
}

function addCargo() {
    refreshResultTable();
    var name = $("#nameField").val();
    if (!isPermittedExtString(name)) {
        writeRedStatus("Wrong cargo name!");
        return;
    }
    if (ensureLength(name, 255, "Cargo")) {
        return;
    }
    var weightKg = $("#weightKgField").val();
    if (isNaN(weightKg) || weightKg < 0) {
        writeRedStatus("Cargo weight must be a non-negative number!");
        return;
    }
    jQuery.ajax({
        url: contextPath +
        "/cargo/create?" +
        "name=" + name +
        "&weightkg=" + weightKg
    }).done(function (result) {
        handleError(result, addCargoRow);
    })
}