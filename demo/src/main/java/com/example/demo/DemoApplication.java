package com.example.demo;

import java.time.LocalDateTime;
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
	@Autowired
	private MovieAggregationRepository movieAggregationRepository;

	public void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	@Override
    public void run(String... args) {
		// testWithoutRepositories();
		// createMovie();
		// getYear();
		// getMoviesBetween70and00();
		// getMoviesFr();
		getMoviesCrimes();

		// System.out.println(movieAggregationRepository.getMovies());

		// System.out.println(movieRepository.findAll());
		// movieRepository.findAll().forEach(
        //     m -> System.out.println("Trouvé: " + m.title)
        // );
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
		movie.setReleasedAt(1965);
		movie.setGenre( "Gangster");
		movie.setSynopsis("Un film");
		movie.setCountry("US");

		movie.director = new Director("Ford Coppola");
		movie.director.setFirstname("Francis");
		movie.director.setBirthday(LocalDateTime.of(1947, 10, 14, 0, 0));

		movie.actors = new ArrayList<Actor>();
		var actor = new Actor("Brando");
		actor.setFirstname("Marlon");
		actor.setBirthday(LocalDateTime.of(1924, 4, 3, 0, 0));
		actor.setRole("Don Vito Corleone");
		movie.actors.add(actor);
		var actor2 = new Actor("Pacino");
		actor2.setFirstname("Al");
		actor2.setBirthday(LocalDateTime.of(1940, 4, 25, 0, 0));
		actor2.setRole("Michael Corleone");
		movie.actors.add(actor2);

		movieRepository.insert(movie);
	}

	public void getYear() {
		var movie = movieRepository.findByTitle("Le Parrain");
		System.out.println(movie.getReleasedAt());
	}

	public void getMoviesBetween70and00() {
		var movies = movieRepository.findByReleasedAtBetweenOrderByTitleAsc(1970, 2000);
		System.out.println(movies);
	}

	public void getMoviesFr() {
		var movies = movieRepository.findByCountry("FR");
		System.out.println(movies);
	}

	public void getMoviesCrimes() {
		var movies = movieRepository.findByGenre("Crime");
		System.out.println(movies);
	}

}
