package org.ecomoveV1.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ecomove";
    private static final String USER = "ecomoveuser";
    private static final String PASS = "ouchin55edcx";


    private static DatabaseConnection instance ;
    private final Connection connection ;

    private DatabaseConnection(){
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance(){
        if (instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }


    public void closeConnection(){
        try {
            if (connection !=null && !connection.isClosed()){
                connection.close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}