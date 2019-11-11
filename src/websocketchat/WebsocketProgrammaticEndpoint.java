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


public class WebsocketProgrammaticEndpoint extends Endpoint {
	private static final long serialVersionUID = 8566939988530771311L;
	HashMap<Session, String> usuarios = new HashMap<>(); 
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println(session.getRequestURI().toString()+" - Nova conexão estabelecida: " + session.getId());
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			@Override
			public void onMessage(String message) {
				handleMessage(message, session);
			}
		});
		usuarios.put(session, "anônimo");
		sendMessageToOtherSessions("Server: Mais alguém entrou na sala", session);
	}
	
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println(usuarios.get(session) + " Saiu "+session.getId());
		usuarios.remove(session);
		if(usuarios.size() < 1) {			
			enableWebsocketEndpointName(session.getRequestURI().toString());
		}
	}
	
	private void enableWebsocketEndpointName(String fullPath) {
		System.out.println("sem usuários");
		String endpoindName = fullPath.substring(fullPath.lastIndexOf("/")+1); 
		WebsocketManager.addAbleEndpointName(endpoindName);
	}
	
	public void handleMessage(String message, Session session) {
		message = checkNickWasChanged(message, session);
		sendMessageToOtherSessions(message, session);
	}
	
	private String checkNickWasChanged(String message, Session session) {
		String nick = getNickOnMessage(message);
		if(!usuarios.get(session).equals(nick)) {
			sendMessageToOtherSessions(usuarios.get(session)+" trocou de nick para -> "+ nick, session);
			usuarios.replace(session, nick);
 		}
		return message;
	}
	private String getNickOnMessage(String message) {
		try {
			String nick = (String)message.subSequence(0, message.indexOf(":"));
			return nick;
		}
		catch(StringIndexOutOfBoundsException e) {
			return "anônimo";
		}
	}
	
	private void sendMessageToOtherSessions (String message, Session session) {
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











