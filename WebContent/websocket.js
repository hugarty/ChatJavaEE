
var webSocket = new WebSocket(`ws://localhost:8080/websocketchat/${websocketPath}`);
	    
webSocket.onopen = function() {
    console.log("connection openee");
};

webSocket.onmessage = function (message) {
	addMessageOnScreen(message.data);
};

const handlerOnclick = () => {
	const input = document.getElementById("input");
	const nick = document.getElementById("nick");
	webSocket.send(`${nick.value}:${input.value}`);
	addMessageOnScreen(input.value);
	input.value = "";
}

const addMessageOnScreen = (message) => {
	let span = document.createElement("span");
	span.textContent= message;
	document.getElementById("mensagens").appendChild(span); 
}

