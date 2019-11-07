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

import websocketchat.OneEndpoint;

@Named("main")
@Singleton
public class MainController implements Serializable {

	private static final long serialVersionUID = -4814970080266121815L;
	
	@Inject
	ServletContext servletContext;
	
	List<Endpoint> lista = new ArrayList<Endpoint>();
	
	public void novoWebSocket() throws DeploymentException, IOException, URISyntaxException{
		ServerEndpointConfig build = ServerEndpointConfig.Builder.create(OneEndpoint.class, "/oi").build();
		ServerContainer serverContainer = (ServerContainer)servletContext.getAttribute("javax.websocket.server.ServerContainer");
		System.out.println("Rodou tudo: "+serverContainer.toString());
		serverContainer.addEndpoint(build);
	}
}
