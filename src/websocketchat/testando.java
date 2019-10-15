package websocketchat;

import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ooooi")
public class testando {
	@OnOpen
	public void entrouAlguem () {
		System.out.println("eae");
	}
}
