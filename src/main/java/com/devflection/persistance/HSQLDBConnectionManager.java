package com.devflection.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLDBConnectionManager {

    private HSQLDBConnectionManager(){
    }

    public static Connection connect() {
        try {
            // Load the driver that is included in the HSQLDB jar
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e1) {
            throw new IllegalStateException("Could not load HSQLDB JDBCDriver.");
        }

        Connection connection;
        try {
            // Create the connection to the file based DB with the defaults
            connection = DriverManager.getConnection("jdbc:hsqldb:file:db/classesDB", "SA", "");
        }  catch (SQLException e) {
            throw new IllegalStateException("Could not connect to HSQLDB", e);
        }

        if (connection != null){
            return connection;
        } else {
            throw new IllegalStateException("Could not connect to HSQLDB");
        }
    }
}
