
var webSocket = new WebSocket("ws://localhost:8080/websocketchat/chat");
	    
webSocket.onopen = function() {
    console.log("connection opened");
};

webSocket.onmessage = function (message) {
	console.log("Server: "+message.data)
};


const handlerOnclick = () => {
	const input = document.getElementById("input");
	console.log(input.value)
	webSocket.send(input.value);
	addMessageOnScreen(input.value);
	input.value = "";
}

const addMessageOnScreen = (message) => {
	let span = document.createElement("span");
	span.textContent= message;
	document.getElementById("mensagens").appendChild(span); 
}

