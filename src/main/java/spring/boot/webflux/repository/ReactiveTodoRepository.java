package spring.boot.webflux.repository;

import org.springframework.stereotype.Repository;

import spring.boot.webflux.model.Todo;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReactiveTodoRepository implements TodoAlternateRepo {

    private static List<Todo> todo = new ArrayList<>();

    static {
        todo.add(new  Todo(100l, "My new Task 1 !", true));
        todo.add(new  Todo(101l, "My new Task 2 !", true));
        todo.add(new  Todo(102l, "My new Task 3 !", true));
        todo.add(new  Todo(103l, "My new Task 4 !", true));
        todo.add(new  Todo(104l, "My new Task 5 !", true));
    }

    @Override
    public Flux<Todo> findAll() {
    	
        //simulate stream data with 2 seconds delay.
        return Flux.fromIterable(todo).delayElements(Duration.ofMillis(400)); //.log();
        //return Flux.fromIterable(todo).delayElements(Duration.ofSeconds(1));
    }
    
    

}