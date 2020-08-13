var stompClient = null;
var token = "eyJraWQiOiJTampFckRIRHFKRUw3djVwRWhoQVJ6Sk9SMmtJc2t5Q0hEdW1QcVwvalBsdz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxaTl1NmM1ZzFzOXFzNGY4N2w4bjd1aWU2cyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYWdnXC9hbGwtbWlnaHR5IiwiYXV0aF90aW1lIjoxNTk2NjQ2NDQ3LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV8xTHZGTEFlZnIiLCJleHAiOjE1OTY2NTAwNDcsImlhdCI6MTU5NjY0NjQ0NywidmVyc2lvbiI6MiwianRpIjoiZjI1ZjU2NTMtZDFiOS00YzQ3LWFkYjctZWMxMGNkMTcyNjZlIiwiY2xpZW50X2lkIjoiMWk5dTZjNWcxczlxczRmODdsOG43dWllNnMifQ.W-z4KMLuEJpkp0aC2ZvnwJQHR0jyLiPNyQY_VvGFX2qNVIiuHwXHSPygzRYwbfIDEFI_vwwQTSCTsD3cz6lBTyUevPRA84lY03m46EilBkOytRtkAWDuCbvDat8xWn4NvTAOdmiWJfkviahR3cZu6miG1_ZkoZ-arx3YkhTkVxAwhRnedOfKrD9t2zsfRRohQhu06H4ePHedUx3w0bGrDflHALZ90pF3Wnb84EF2zigshDs84H891U8zcPj47yPwz8mkv5D_xWa53HmgXBAQM9e0xfuZcF77nS9HCtLluC1KK5TqXLTFMu5s7fkXWHwehw_OX09h5P0N0QwHPbyeXw";
function connect() {
    stompClient = new window.StompJs.Client({
        webSocketFactory: function () {
            return new WebSocket("ws://localhost:8090/iotecha-wss");
        }
    });
    stompClient.onConnect = ({"X-Authorization": "Bearer " + token}, function (frame) {
            frameHandler(frame)
    });

    stompClient.onWebsocketClose = function () {
        onSocketClose();
    };

    stompClient.activate();
}

function onSocketClose() {
    if (stompClient !== null) {
        stompClient.deactivate();
    }
    setConnected(false);
    console.log("Socket was closed. Setting connected to false!")
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    
    $("#messages").html("");
}

function frameHandler(frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', function (message) {
        showMessage(message.body);
    });
}

function showMessage(message) {
    var msg = JSON.parse(message);
    $("#response").val(message);
}

function sendMessage() {
    stompClient.publish({
        destination:$("#destination").val(),
        body: $("#inputJson").val()
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.deactivate();
    }
    setConnected(false);
    console.log("Disconnected");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
    $("document").ready(function () {
        disconnect();
    });
});