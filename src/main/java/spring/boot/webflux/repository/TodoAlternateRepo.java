package spring.boot.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import spring.boot.webflux.model.Todo;

public interface TodoAlternateRepo  { //extends ReactiveCrudRepository<Todo, Long> {
	
	Flux<Todo> findAll();
	
}