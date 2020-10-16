package spring.boot.webflux.config;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflux.model.Todo;
import spring.boot.webflux.repository.TaskGenerator;

@Configuration
public class WebSocketConfiguration {

	@Bean
	WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	WebSocketHandler webSocketHandler() {
		return new WebSocketHandler() {

			@Override
			public Mono<Void> handle(WebSocketSession session) {

				Flux<WebSocketMessage> generate = Flux
						.<Todo>generate(sink -> sink.next(new Todo(TaskGenerator.randomId(), TaskGenerator.randomTaskName(), TaskGenerator.randomStatus())))
						.map(todo -> session.textMessage("id: " + todo.getId()+ " description: " + todo.getText()  +  " completedStatus: " + todo.isCompleted() )).delayElements(Duration.ofSeconds(1))
						.doFinally(signalType -> System.out.println("Goodbye from WebSocket..!!"));
				return session.send(generate);
			}
		};
	}
	
	@Bean
	HandlerMapping handlerMapping() {
		SimpleUrlHandlerMapping simpleUrlHandlerMapping=new SimpleUrlHandlerMapping();
		simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/websocket/todo",webSocketHandler()));
		simpleUrlHandlerMapping.setOrder(10);
		
		return simpleUrlHandlerMapping;
	}

}