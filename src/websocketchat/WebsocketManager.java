package websocketchat;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

@Named
@Singleton
public class WebsocketManager implements Serializable{
	private static final long serialVersionUID = -4752045905522271008L;

	@Inject
	ServletContext servletContext;
	
	List<ServerEndpointConfig> endpointList = new LinkedList<ServerEndpointConfig>();
	
	public String buildNewWebsocketServer() {
		String websocketServerEndpoint = getWebsocketEndpointName();
		ServerEndpointConfig build = ServerEndpointConfig
				.Builder
				.create(testando.class, "/"+websocketServerEndpoint )
				.build();
		try {
			ServerContainer serverContainer = getServerContainer();
			serverContainer.addEndpoint(build);
			endpointList.add(build);
		} catch (DeploymentException e) {
			e.printStackTrace();
			return null;
		}
		return websocketServerEndpoint;
	}

	private ServerContainer getServerContainer() {
		return (ServerContainer)servletContext.getAttribute("javax.websocket.server.ServerContainer");
	}
	
	private String getWebsocketEndpointName () {
		return "1";
	}
}

















