package com.example.demo;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Movie {
    public String id;
    public String title;
    @Field("released_at")
    public int releasedAt;
    public String genre;
    public String synopsis;
    public String country;
    public Director director;
    public List<Actor> actors;

    public Movie(String title) {
        this.title = title;
    }
}
