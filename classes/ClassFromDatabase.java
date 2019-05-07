package com.devflection.example.db;


public ClassFromDatabase {

    public ClassFromDatabase() {
    }

    public String getMessage() {
        return Arrays.stream("Yay!", "You freed me from the confinement of the database!", "You get 3 wishes!")
            .stream(Collectors.joining("\n"));
    }
}