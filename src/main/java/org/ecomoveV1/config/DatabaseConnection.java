package org.ecomoveV1.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final static String DB_URL = "jdbc:postgresql://localhost:5432/ecomove";
    private final static String USER = "ecomoveuser";
    private final static String PASS = "ouchin55edcx";


    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Database Connected ");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
