package spring.boot.webflux.repository;

import reactor.core.publisher.Flux;
import spring.boot.webflux.model.Todo;

public interface MyRepository {
	Flux<Todo> findAll();
}
