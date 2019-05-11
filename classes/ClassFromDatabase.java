package com.devflection.example.db;

import java.util.stream.Stream;
import java.util.stream.Collectors;

public class ClassFromDatabase {

    public ClassFromDatabase() {
    }

    public String getMessage() {
        return Stream.of("Yay!", "You freed me from the confinement of the database!", "You get 3 wishes!")
                .collect(Collectors.joining("\n"));
    }
}