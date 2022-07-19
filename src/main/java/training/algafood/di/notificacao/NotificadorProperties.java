package training.algafood.di.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "notificador.email")
public class NotificadorProperties {

	private String hostServer;
	private Integer portServer = 25;
	
	public String getHostServer() {
		return hostServer;
	}
	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}
	public Integer getPortServer() {
		return portServer;
	}
	public void setPortServer(Integer portServer) {
		this.portServer = portServer;
	}
	
	
}
