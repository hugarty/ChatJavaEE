package websocketchat;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class testando {

	HashMap<Session, String> usuarios = new HashMap<>();
	
	@OnOpen
	public void entrouAlguem(Session session) {
		System.out.println("oi " + session.getId());
		usuarios.put(session, "");
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
	public void saiuAlguem(Session session) {
		System.out.println(usuarios.get(session) + " Saiu");
		usuarios.remove(session);
	}
	
	@OnMessage
	public void recebeuMensagem(String message, Session session) {
		if(verificaMensagem(message)) {
			String nick = getNick(message);
			if(usuarios.get(session) != nick){
				usuarios.replace(session, nick);
			}
			try {
				for(Session s: session.getOpenSessions()) {
					s.getBasicRemote().sendText(usuarios.get(session) + " -> " + nick);
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

	private String getNick (String message){
		if(message.contains(":"))
			return message.split(":")[0];
		return "";
	}
}











