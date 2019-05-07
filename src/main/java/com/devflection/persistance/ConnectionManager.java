package com.devflection.persistance;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    private ConnectionManager(){
    }

    public static Connection connect() {
        Connection conn = null;

        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }

        if (conn!= null){
            System.out.println("Connection created successfully");
            return conn;
        } else {
            throw new IllegalStateException("Could not connect to HSQLDB");
        }
    }
}
