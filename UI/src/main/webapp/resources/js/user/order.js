var loadedCargoes;
var persNumber = "LZX321098";
var interruptBlock = $("#interruptBlock");
var detailsList = $("#detailsList");
showCurrentOrder();


function showCurrentOrder() {
    $.ajax({url: contextPath + "/rest/driver/" + persNumber}).done(function (result) {
        handleError(result, function (driver) {
            if (driver.vehicle == null) {
                writeGreenStatus("You are not assigned to any vehicle");
                return;
            }
            var regNumber = driver.vehicle.regNumber;
            $.ajax({url: contextPath + "/rest/vehicle/" + regNumber}).done(function (result) {
                handleError(result, function (vehicle) {
                    if (vehicle.order == null) {
                        writeGreenStatus("You are not assigned to any order");
                        return;
                    }
                    var id = vehicle.order.id;
                    $.ajax({url: "/rest/order/" + id}).done(function (result) {
                        handleError(result, drawOrder)
                    })
                })
            })
        })
    })
}

function progressReport(order) {
    $.ajax({
        type: "PATCH",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/order/" + order.id
    }).done(function (result) {
        handleError(result, function () {
            writeGreenStatus("Progress has successfully registered");
            order.progress++;
            drawOrder(order);
        })
    })
}

function addOrderRow(row) {
    resultTable.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td id='generic_capacity_" + row.id + "'></td>" +
        "<td id='generic_progress_" + row.id + "'>" + row.progress + " / " + row.total + "</td>" +
        "<td>" + (row.vehicle == null ? "" : row.vehicle.regNumber) + "</td>" +
        "<td>" +
        "<button id='generic_showButton_" + row.id + "' type='button'>Show</button> " +
        "<button id='generic_interruptButton_" + row.id + "' type='button'>Interrupt</button>" +
        "</td>" +
        "</tr>"
    );
    $("#generic_showButton_" + row.id).click(function () {
        drawOrder(row);
    });
    if (row.progress == row.total) {
        var progressRow = $("#generic_progress_" + currentRow);
        progressRow.css("color", "green");
        progressRow.css("font-weight", "bold");
        $("#generic_interruptButton_" + row.id).prop("disabled", "true");
    } else {
        $("#generic_interruptButton_" + row.id).click(function () {
            interrupt(row.id)
        });
    }
    $.ajax({url: contextPath + "/rest/order/" + row.id + "/capacity"})
        .done(function (result) {
            $("#generic_capacity_" + row.id).append(result.body);
        })
}

function showAllOrders() {
    showAll("order", addOrderRow);
}

function showOrder() {
    var id = searchField.val();
    if (!isInteger(id)) {
        writeRedStatus("ID must be a positive integer");
        return;
    }
    if (id > MAX_LONG) {
        writeRedStatus("Order ID id too big");
        return;
    }
    $.ajax({url: contextPath + "/rest/order/" + id})
        .done(function (result) {
            handleError(result, drawOrder)
        })
}

function drawOrder(order) {
    interruptBlock.empty();
    detailsList.empty();
    loadedCargoes = [];
    interruptBlock.append(
        "<button id='interruptButton' type='button'>Interrupt order</button> " +
        "<button id='progressButton' type='button'>Report on progress</button>"
    );
    $("#interruptButton").click(function () {
        interrupt(order.id);
    });
    $("#progressButton").click(function () {
        progressReport(order);
    });
    var checkpoints = order.checkpoints;
    for (var i = 0; i < checkpoints.length; i++) {
        drawCheckpoint(checkpoints[i], i < order.progress);
    }
    var completedCheckpoints = $(".completedCheckpoint");
    completedCheckpoints.css("color", "green");
    completedCheckpoints.css("font-weight", "bold");
}

function drawCheckpoint(checkpoint, completed) {
    var res = "<li><div" + (completed ? " class='completedCheckpoint'>" : ">") + checkpoint.city.name + "</div><ul>";
    var tasks = checkpoint.tasks;
    for (var i = 0; i < tasks.length; i++) {
        res += "<li>";
        var cargo = tasks[i].cargo;
        if (containsCargo(cargo.id)) {
            res += "UNLOADING";
        } else {
            res += "LOADING";
        }
        res += " cargo #" + cargo.id + " (" + cargo.name + ", " + cargo.weightKg + "kg)</li>";
    }
    res += "</ul></li>";
    detailsList.append(res);
}

function containsCargo(id) {
    for (var i = 0; i < loadedCargoes.length; i++) {
        if (id == loadedCargoes[i]) {
            return true;
        }
    }
    loadedCargoes.push(id);
    return false;
}

function interrupt(id) {
    $.ajax({
        type: "PUT",
        contentType: "application/json; charset=UTF-8",
        url: contextPath + "/rest/order/" + id
    }).done(function (result) {
        handleError(result, function () {
            writeGreenStatus("Order #" + id + " has successfully interrupted");
        })
    })
}