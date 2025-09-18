package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

@Repository
public class MovieAggregationRepository {
    @Autowired
    private MongoOperations mongoOps;

    public List<Movie> getMovies() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group("country").count().as("count"),
            Aggregation.project("count").and("_id")
        );

        AggregationResults<Movie> results = mongoOps.aggregate(aggregation, "movie", Movie.class);

        return results.getMappedResults();
    }
}
