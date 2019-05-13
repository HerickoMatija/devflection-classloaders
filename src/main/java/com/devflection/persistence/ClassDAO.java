package com.devflection.persistence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassDAO {

    private static final String DB_CONNECTION = "jdbc:hsqldb:file:db/classesDB";
    private static final String DB_USERNAME = "SA";
    private static final String DB_PASSWORD = "";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS classes (className VARCHAR(50) NOT NULL, data BLOB NOT NULL, PRIMARY KEY(className));";

    private static final String INSERT = "INSERT INTO classes(className, data) VALUES(?,?)";
    private static final String FIND_BY_NAME = "SELECT data FROM classes WHERE className = ?";

    private ClassDAO() {
    }

    /**
     * This method creates the table for storing classes in the HSQLDB and also inserts the test class file that is in this project.
     *
     * @throws IOException
     */
    public static void setup() throws IOException {
        createClassTable();

        byte[] classFromDB = getClass("com.devflection.example.db.ClassFromDatabase");

        if (classFromDB == null || classFromDB.length == 0) {

            byte[] bytes = Files.readAllBytes(Paths.get("classes/ClassFromDatabase.class"));

            insertClass("com.devflection.example.db.ClassFromDatabase", bytes);
        }
    }

    public static boolean createClassTable() {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println("Could not execute create table query.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean insertClass(String className, byte[] data) {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement insertClassPreparedStatement = connection.prepareStatement(INSERT);
            insertClassPreparedStatement.setString(1, className);
            insertClassPreparedStatement.setBinaryStream(2, new ByteArrayInputStream(data));
            insertClassPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not execute insert query.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static byte[] getClass(String className) {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement insertClassPreparedStatement = connection.prepareStatement(FIND_BY_NAME);
            insertClassPreparedStatement.setString(1, className);
            ResultSet resultSet = insertClassPreparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBytes(1);
            }
        } catch (SQLException e) {
            System.out.println("Could not execute query.");
            e.printStackTrace();
        }
        return new byte[0];
    }

}
