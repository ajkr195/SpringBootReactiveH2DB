package spring.boot.webflux;


import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.core.DatabaseClient;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;
import spring.boot.webflux.model.Todo;
import spring.boot.webflux.repository.TodoRepository;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}

	@Bean
	public CommandLineRunner demo(TodoRepository repository) {

		return (args) -> {
			// save a few todos
			repository.saveAll(Arrays.asList(
					new Todo(null, "My first Todo Task !", true), 
					new Todo(null, "My second Todo Task !", false),
					new Todo(null, "My second Todo Task !", false), 
					new Todo(null, "My third Todo Task !", true),
					new  Todo(null, "My forth Todo Task !", true),
		            new  Todo(null, "My fifth Todo Task !", false),
		            new  Todo(null, "My sixth Todo Task !", true),
		            new  Todo(null, "My seventh Todo Task !", true),
		            new  Todo(null, "My eighth Todo Task !", false),
		            new  Todo(null, "My nineth Todo Task !", false)))
					.blockLast(Duration.ofSeconds(10));

			// fetch all todos
			log.info("Todos found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(todo -> {
				log.info(todo.toString());
			}).blockLast(Duration.ofSeconds(10));

			log.info("");

			// fetch an individual todo by ID
			repository.findById(1L).doOnNext(todo -> {
				log.info("Todo found with findById(1L):");
				log.info("--------------------------------");
				log.info(todo.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));

			// fetch todos by text
			log.info("Todo found with text('My second Todo Task !'):");
			log.info("--------------------------------------------");
			repository.findByText("My second Todo Task !").doOnNext(bauer -> {
				log.info(bauer.toString());
			}).blockLast(Duration.ofSeconds(10));
			;
			log.info("");
		};
	}

}