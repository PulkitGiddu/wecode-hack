package com.hsbc.bookit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConManager {
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection
                    ("jdbc:mysql://localhost/bookitdb","root","Summerinlove@09");
        } catch (SQLException e) {
            System.out.println("Connection Error:" + e.getMessage());
        }
        return null;
    }
}
