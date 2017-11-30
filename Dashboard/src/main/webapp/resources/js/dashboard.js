// INITIALIZATION

var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/"));
var statusText = $("#statusText");
var resultTable = $("#resultTable");
var loadedCargoes;
var searchField = $("#searchField");
var interruptBlock = $("#interruptBlock");
var detailsList = $("#detailsList");
$("#showButton").click(showOrder);
showAllOrders();

var socket = new WebSocket("ws://localhost:8080/dashboard/socket");
socket.onmessage = onMessage;


function refreshResultTable() {
    resultTable.empty();
}


// STATUS MESSAGE

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
    writeRedStatus("Wrong " + name + "! It is only allowed letters, digits, '-' and '_'");
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


// DISPLAY

function showAll(entity, action) {
    refreshResultTable();
    jQuery
        .ajax({url: contextPath + "/rest/" + entity})
        .done(function (result) {
            handleError(result, function (result) {
                for (var i = 0; i < result.length; i++) {
                    action(result[i]);
                }
            })
        })
}

function findOne(entity, param, action, after) {
    refreshResultTable();
    jQuery
        .ajax({url: contextPath + "/rest/" + entity + "/" + param})
        .done(function (result) {
            handleError(result, action);
            if (after != null) {
                after();
            }
        })
}


// VERIFICATIONS

var MAX_LONG = 9223372036854775807;

function isInteger(str) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isDigit(c)) {
            return false;
        }
    }
    return str.length > 0;
}

function isLongName(str) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isExtChar(c)) {
            return false;
        }
    }
    return (str.length > 0) && (str.length <= 255);
}

function isSimpleName(str) {
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isSimpleChar(c)) {
            return false;
        }
    }
    return (str.length > 0) && (str.length <= 45);
}

function isSimpleChar(char) {
    return isLetter(char) || isDigit(char) || isSimpleSymbol(char);
}

function isExtChar(char) {
    return isLetter(char) || isDigit(char) || isExtSymbol(char);
}

function isDigit(char) {
    return (char >= "0") && (char <= "9");
}

function isLetter(char) {
    return (char >= "a") && (char <= "z") || (char >= "A") && (char <= "Z");
}

function isSimpleSymbol(char) {
    var simpleSymbols = "_-";
    for (var i = 0; i < simpleSymbols.length; i++) {
        if (char == simpleSymbols.charAt(i)) {
            return true;
        }
    }
    return false;
}

function isExtSymbol(char) {
    var extSymbols = " ~!@\"#№$;%^:&?*()-_=+[{]}\\'|,<.>/";
    for (var i = 0; i < extSymbols.length; i++) {
        if (char == extSymbols.charAt(i)) {
            return true;
        }
    }
    return false;
}


// ORDER LIST

function addOrderRow(row) {
    resultTable.append(
        "<tr>" +
        "<td>" + row.id + "</td>" +
        "<td id='generic_capacity_" + row.id + "'></td>" +
        "<td id='generic_progress_" + row.id + "'>" + row.progress + " / " + row.total + "</td>" +
        "<td>" + (row.vehicle == null ? "" : row.vehicle.regNumber) + "</td>" +
        "<td>" +
        "<button id='generic_showButton_" + row.id + "' type='button'>Show</button> " +
        "</td>" +
        "</tr>"
    );
    $("#generic_showButton_" + row.id).click(function () {
        drawOrder(row);
    });
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
    interruptBlock.append("<button id='interruptButton' type='button'>Interrupt order</button>");
    $("#interruptButton").click(function () {
        interrupt(order.id);
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


// JMS

function onMessage(event) {
    writeGreenStatus(event.data);
    showAllOrders();
}