package spring.boot.webflux.controller;

import java.time.Duration;
import java.time.Instant;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflux.model.Todo;
import spring.boot.webflux.repository.MyRepository;
import spring.boot.webflux.repository.TaskGenerator;
import spring.boot.webflux.repository.TodoRepository;

@RestController
@RequestMapping("/api")
class TodoController {

	Logger logger = (Logger) LoggerFactory.getLogger(TodoController.class);

	private TodoRepository repository;

	@Autowired
	private MyRepository myRepository;

	@Autowired
	public TodoController(TodoRepository repository) {
		this.repository = repository;
	}

	@GetMapping(value = "/todo/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Todo> getAllLive() {
		logger.info("FindingAllTodos...Live...");
		return myRepository.findAll();
		// return repository.findAll();
	}

	@GetMapping(value = "/todos/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Publisher<Todo> sseGreetings() {
		Flux<Todo> delayElements = Flux.<Todo>generate(sink -> sink
				.next(new Todo(TaskGenerator.randomId(), TaskGenerator.randomTaskName(), TaskGenerator.randomStatus())))
				.delayElements(Duration.ofSeconds(1));
		return delayElements;
	}

	@GetMapping("/hellotodo")
	Publisher<Todo> todoPublisher() {
		Flux<Todo> todoFlux = Flux.<Todo>generate(sink -> sink
				.next(new Todo(TaskGenerator.randomId(), TaskGenerator.randomTaskName(), TaskGenerator.randomStatus())))
				.take(10);
		return todoFlux;
	}

	@GetMapping(value = "/todo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Todo> getAll() {
		logger.info("FindingAllTodos...");
		return repository.findAll().log();
		// return repository.findAll();
	}

	@GetMapping("/todos")
	Flux<Todo> getAllTodos() {
		logger.info("FindingAllTodos...");
		return repository.findAll().log();
		// return repository.findAll();
	}

	@PostMapping("/todo")
	Mono<Todo> addTodo(@RequestBody Todo todo) {
		logger.info("Creating New Todo...");
		return repository.save(todo).log();
		// return repository.save(todo);
	}

	@PutMapping("/todo")
	Mono<Todo> updateTodo(@RequestBody Todo todo) {
		logger.info("Updating Todo with ID: {}", todo.getId());
		return repository.save(todo).log();
		// return repository.save(todo);
	}

	@DeleteMapping("/todo/{id}")
	Mono<Void> deleteById(@PathVariable("id") Long id) {
		logger.info("Deleting Todo with ID: {}", id);
		return repository.deleteById(id).log();
		// return repository.deleteById(id);
	}
}