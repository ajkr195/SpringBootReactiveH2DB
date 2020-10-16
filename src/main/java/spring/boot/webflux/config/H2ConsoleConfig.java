package spring.boot.webflux.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class H2ConsoleConfig {

	@Value("${webclientexample.postsapi.h2-console-webport}")
	Integer h2ConsoleWebport;

	@Value("${webclientexample.postsapi.h2-console-tcpport}")
	Integer h2ConsoleTcpPort;

	Logger log = (Logger) LoggerFactory.getLogger(H2ConsoleConfig.class);

	private org.h2.tools.Server webServer;

	private org.h2.tools.Server tcpServer;

	@EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
	public void start() throws java.sql.SQLException {
		log.info("starting h2 console at port " + h2ConsoleWebport);
		this.webServer = org.h2.tools.Server.createWebServer("-webPort", h2ConsoleWebport.toString(), "-tcpAllowOthers")
				.start();
		this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", h2ConsoleTcpPort.toString(), "-tcpAllowOthers")
				.start();
	}

	@EventListener(org.springframework.context.event.ContextClosedEvent.class)
	public void stop() {
		this.tcpServer.stop();
		this.webServer.stop();
	}

}