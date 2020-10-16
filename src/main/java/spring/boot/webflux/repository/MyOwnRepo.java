package spring.boot.webflux.repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import spring.boot.webflux.model.Todo;

@Repository
public class MyOwnRepo implements MyRepository {

	 @Override
	    public Flux<Todo> findAll() {
	        //simulate data streaming every 1 second.
	        return Flux.interval(Duration.ofSeconds(1))
	                .onBackpressureDrop()
	                .map(this::generateTask)
	                .flatMapIterable(x -> x);
	    }

	    private List<Todo> generateTask(long interval) {

	    	Todo obj = new Todo(TaskGenerator.randomId(), TaskGenerator.randomTaskName(), TaskGenerator.randomStatus());
	        return Arrays.asList(obj);

	    }

}
