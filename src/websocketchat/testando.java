package websocketchat;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class testando {
	
	@OnOpen
	public void entrouAlguem(Session session) {
		System.out.println("oi " + session.getId());
		try {
			for(Session s: session.getOpenSessions()) {
				if(s != session)
					s.getBasicRemote().sendText("Server: Mais alguém entrou na sala");
			}
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
		if(verificaMensagem(message)) {
			try {
				for(Session s: session.getOpenSessions()) {
					if(s != session)
						s.getBasicRemote().sendText(message);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@OnError
	public void ocorreuErro(Throwable e) {
		e.printStackTrace();
	}
	
	private boolean verificaMensagem(String message) {
		if(message.isBlank())
			return false;
		
		return true;
	}
}











