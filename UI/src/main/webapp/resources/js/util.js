// INITIALIZATION

var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/"));
var statusText = $("#statusText");
var resultTable = $("#resultTable");

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

function findOne(entity, param, action) {
    refreshResultTable();
    jQuery
        .ajax({url: contextPath + "/rest/" + entity + "/" + param})
        .done(function (result) {
            handleError(result, action);
        })
}


// VERIFICATIONS

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