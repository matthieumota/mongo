package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie findByTitle(String title);

    // findBy -- ReleasedAt -- Between -- Order By -- Title -- Asc
    List<Movie> findByReleasedAtBetweenOrderByTitleAsc(int start, int end);

    List<Movie> findByCountry(String country);

    List<Movie> findByGenre(String genre);
}
