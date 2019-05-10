package com.devflection.persistance;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassDAO {

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS classes (className VARCHAR(50) NOT NULL, data BLOB NOT NULL, PRIMARY KEY(className));";

    private static final String INSERT = "INSERT INTO classes(className, data) VALUES(?,?)";
    private static final String FIND_BY_NAME = "SELECT data FROM classes WHERE className = ?";

    private Connection connection;

    public ClassDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createClassTable() {
        try {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println("Could not execute create table query.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertClassToDB(String className, byte[] data) {
        try {
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

    public byte[] getClassFromDB(String className) {
        try {
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
