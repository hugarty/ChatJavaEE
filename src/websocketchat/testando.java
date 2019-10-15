package websocketchat;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class testando {
	
	@OnOpen
	public void entrouAlguem(Session session) {
		System.out.println("OI");
		try {
			session.getBasicRemote().sendText("OI");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void saiuAlguem() {
		System.out.println("Saiu ");
	}
	
	@OnMessage
	public void recebeuMensagem(String message, Session session) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}











