package websocketchat;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


public class testando extends Endpoint {
	private static final long serialVersionUID = 8566939988530771311L;
	HashMap<Session, String> usuarios = new HashMap<>();
	
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println(session.getRequestURI().toString()+": Nova conexão: " + session.getId());
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			@Override
			public void onMessage(String message) {
				recebeuMensagem(message, session);
			}
		});
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
	
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println(usuarios.get(session) + " Saiu "+session.getId());
		usuarios.remove(session);
		if(usuarios.size() < 1) {			
			enableEndpointName(session.getRequestURI().toString());
		}
	}
	
	private void enableEndpointName(String fullPath) {
		System.out.println("sem usuários");
		String endpoindName = fullPath.substring(fullPath.lastIndexOf("/")+1); 
		WebsocketManager.addAbleEndpointName(endpoindName);
	}
	
	public void recebeuMensagem(String message, Session session) {
		try {
			for(Session s: session.getOpenSessions()) {
				if(s != session)
					s.getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void onError(Session session, Throwable thr) {
		super.onError(session, thr);
	}
}











