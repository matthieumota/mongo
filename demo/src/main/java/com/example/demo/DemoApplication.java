package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	public MongoOperations mongoOps;
	@Autowired
	private MovieRepository movieRepository;

	public void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	@Override
    public void run(String... args) {
		testWithoutRepositories();
		// createMovie();

		System.out.println(movieRepository.findAll());
		movieRepository.findAll().forEach(
            m -> System.out.println("Trouvé: " + m.title)
        );
	}

	public void testWithoutRepositories() {
		// Movie movie = new Movie("Le parrain 2");
        // mongoOps.insert(movie);

        Movie firstMovie = mongoOps.findOne(new Query(), Movie.class);
        System.out.println(firstMovie.title);

        // mongoOps.dropCollection("movie");
	}

	public void createMovie() {
		var movie = new Movie("Le Parrain");
		movie.setReleased_at(1965);
		movie.setGenre( "Gangster");
		movie.setSynopsis("Un film");
		movie.setCountry("US");

		movie.director = new Director("Ford Coppola");
		movie.director.setFirstname("Francis");
		movie.director.setBirthday(LocalDate.of(1947, 10, 14));

		movie.actors = new ArrayList<Actor>();
		var actor = new Actor("Brando");
		actor.setFirstname("Marlon");
		actor.setBirthday(LocalDate.of(1924, 4, 3));
		actor.setRole("Don Vito Corleone");
		movie.actors.add(actor);
		var actor2 = new Actor("Pacino");
		actor2.setFirstname("Al");
		actor2.setBirthday(LocalDate.of(1940, 4, 25));
		actor2.setRole("Michael Corleone");
		movie.actors.add(actor2);

		movieRepository.insert(movie);
	}

}
