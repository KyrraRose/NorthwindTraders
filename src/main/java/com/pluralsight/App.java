package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 1. open a connection to the database
        // use the database URL to point to the correct database
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");


        // create statement
        // the statement is tied to the open connection
        Statement statement = connection.createStatement();
        // define your query
        //product id
        //- product name
        //- unit price
        //- units in stock

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";
        // 2. Execute your query
        ResultSet results = statement.executeQuery(query);
        // process the results
        System.out.println("Id   Name                                Price   Stock");
        System.out.println("---- ----------------------------------- ------- ------");
        while (results.next()) {
            int productID = results.getInt("ProductID");
            String name = results.getString("ProductName");
            double price = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");
            System.out.printf("%-4d %-35s $%-7.2f %-6d%n",productID,name,price,stock);
        }

        // 3. Close the connection
        connection.close();


    }
}