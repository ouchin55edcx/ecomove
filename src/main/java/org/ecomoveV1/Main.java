package org.ecomoveV1;

import org.ecomoveV1.config.DatabaseConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args){
        try {

            Connection connection = DatabaseConnection.getInstance().getConnection();

            System.out.println("connected ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}