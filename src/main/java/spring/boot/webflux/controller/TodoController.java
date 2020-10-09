package spring.boot.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import spring.boot.webflux.repository.TodoRepository;

@RestController
@RequestMapping("/api")
class TodoController {
    private TodoRepository repository;

    @Autowired
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    Flux<Todo> getAll() {
        return repository.findAll();
    }

    @PostMapping("/todo")
    Mono<Todo> addTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @PutMapping("/todo")
    Mono<Todo> updateTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @DeleteMapping("/todo/{id}")
    Mono<Void> deleteById(@PathVariable("id") Long id) {
        return repository.deleteById(id);
    }
}