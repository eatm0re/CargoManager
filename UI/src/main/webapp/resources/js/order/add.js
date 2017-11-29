var checkpointCounter = 0;
var taskCounter = 0;
var checkpointByTask = [];
var detailsList = $("#detailsList");
var addOrderButton = $("#addOrderButton");
addOrderButton.click(addOrder);
$("#addCheckpointButton").click(addCheckpoint);
addCheckpoint();


function addCheckpoint() {
    detailsList.append(
        "<li>" +
        "City: <input id='generic_city_" + checkpointCounter + "' type='text' size='38'/><br/><br/>" +
        "<ol id='generic_checkpoint_" + checkpointCounter + "'></ol>" +
        "<button type='button' onclick='addTask(" + checkpointCounter + ")'>New task</button><br/><br/>" +
        "</li>"
    );
    addTask(checkpointCounter++);
}

function addTask(checkpoint) {
    $("#generic_checkpoint_" + checkpoint).append(
        "<li>" +
        " Cargo #" +
        "<input id='generic_cargoId_" + taskCounter + "' type='text' size='15' onchange='showCargoInfo(" + taskCounter + ", this.value)'/>" +
        " <i id='generic_cargoInfo_" + taskCounter + "'></i><br/><br/>" +
        "</li>"
    );
    checkpointByTask.push(checkpoint);
    taskCounter++
}

function showCargoInfo(divId, cargoId) {
    jQuery
        .ajax({url: contextPath + "/rest/cargo/" + cargoId})
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
        var city = $("#generic_city_" + i).val();
        if (!isSimpleName(city)) {
            writeWrongValueMessage("city name (" + city + ")");
            return;
        }
        checkpoints.push({
            city: {name: city},
            tasks: []
        })
    }
    for (i = 0; i < taskCounter; i++) {
        var cargo = $("#generic_cargoId_" + i).val();
        if (!isInteger(cargo)) {
            writeRedStatus("Cargo ID must be a positive integer!");
            return;
        }
        if (cargo > MAX_LONG) {
            writeRedStatus("Cargo ID is too big!");
            return;
        }
        checkpoints[checkpointByTask[i]].tasks.push({cargo: {id: cargo}})
    }
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/order",
        data: JSON.stringify({checkpoints: checkpoints})
    }).done(function (result) {
        handleError(result, function (result) {
            writeGreenStatus("Order has successfully created with ID: " + result);
        })
    })
}