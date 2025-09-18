package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie findByTitle(String title);

    // findBy -- ReleasedAt -- Between -- Order By -- Title -- Asc
    List<Movie> findByReleasedAtBetweenOrderByTitleAsc(int start, int end);

    List<Movie> findByCountry(String country);

    List<Movie> findByGenre(String genre);

    @Aggregation({
        "{ $match: { country: ?0 } }",
        "{ $replaceWith: '$director' }"
    })
    List<Director> findDirectors(String country);

    List<Movie> findByActorsFirstnameAndActorsName(String firstname, String name);

    List<Movie> findByDirectorFirstnameAndDirectorName(String firstname, String name);

    @Aggregation({
        "{ $group: { _id: $country, count: { $sum: 1 } } }",
        "{ $sort: { count: -1 } }"
    })
    List<Map<String, String>> countMoviesByCountry();

    @Aggregation({
        "{ $unwind: $actors }",
        "{ $match: { 'actors.role': ?0 } }"
    })
    List<Map<String, String>> findActorByRole(String role);

    @Aggregation({
        "{ $unwind: $actors }",
        "{ $group: { _id: $title, actors: { $sum: 1 } } }",
    })
    List<Map<String, String>> countActorsByMovie();

    @Aggregation({
        "{ $unwind: $actors }",
        "{ $group: { _id: $title, actors: { $sum: 1 } } }",
        "{ $group: { _id: null, average: { $avg: $actors } } }",
    })
    List<Map<String, String>> avgActorsByMovie();
}
