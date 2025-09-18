package com.example.demo;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Actor {
    public String id;
    public String name;
    public String firstname;
    public LocalDate birthday;
    public String role;

    public Actor(String name) {
        this.name = name;
    }
}
