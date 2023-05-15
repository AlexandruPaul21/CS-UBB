package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/alexandrusirbu/teme-lab-AlexandruPaul21/TestMPP2023.db");
    }
}