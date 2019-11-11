package websocketchat;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
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
	
	static Queue<String> ableEndpointName = new LinkedList<String>(); 
	
	public String getAWebsocketServerEndpoint() {
		String websocketServerEndpoint = ableEndpointName.poll();
		if(websocketServerEndpoint != null) {
			return websocketServerEndpoint;
		}
		websocketServerEndpoint = deployNewEndpoint();
		return websocketServerEndpoint;
	}
	
	private String deployNewEndpoint () {
		String websocketServerEndpoint = getNewWebsocketEndpointName();
		ServerEndpointConfig build = ServerEndpointConfig
				.Builder
				.create(WebsocketProgrammaticEndpoint.class, "/"+websocketServerEndpoint )
				.build();
		try {
			ServerContainer serverContainer = getServerContainer();
			serverContainer.addEndpoint(build);
		} catch (DeploymentException e) {
			e.printStackTrace();
			return null;
		}
		return websocketServerEndpoint;
	}

	private ServerContainer getServerContainer() {
		return (ServerContainer)servletContext.getAttribute("javax.websocket.server.ServerContainer");
	}
	
	private String getNewWebsocketEndpointName () {
		String endPointName = Instant.now().toString();
		return ((Integer) endPointName.hashCode()).toString();
	}
	
	public static  void addAbleEndpointName (String endpointName) {
		System.out.println("Adicionou endpointname: "+endpointName);
		ableEndpointName.add(endpointName);
	}
}

















