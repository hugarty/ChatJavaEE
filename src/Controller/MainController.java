package Controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import websocketchat.WebsocketManager;
import websocketchat.WebsocketProgrammaticEndpoint;

@Named("main")
public class MainController implements Serializable {

	private static final long serialVersionUID = -4814970080266121815L;
	
	@Inject
	WebsocketManager websocketManager;
	
	public String novoWebSocket() throws DeploymentException, IOException, URISyntaxException{
		String endpointName = websocketManager.getAWebsocketServerEndpoint();
		if(endpointName != null) {
			return "index?faces-redirect=true&x="+endpointName ;			
		}
		return "index?faces-redirect=true";
	}
	
}
