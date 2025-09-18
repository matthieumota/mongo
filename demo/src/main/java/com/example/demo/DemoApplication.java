package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoClients;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	public MongoOperations mongoOps;
	@Autowired
	private MovieRepository movieRepository;

	public void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	@Override
    public void run(String... args) {
		test();

		System.out.println(movieRepository.findAll());
		movieRepository.findAll().forEach(
            m -> System.out.println("Trouvé: " + m.title)
        );
	}

	public void test() {
		mongoOps = new MongoTemplate(MongoClients.create(), "tp");

		Movie movie = new Movie("Le parrain 2");
        mongoOps.insert(movie);

        Movie firstMovie = mongoOps.findOne(new Query(), Movie.class);
        System.out.println(firstMovie.title);

        // mongoOps.dropCollection("movie");
	} 

}
