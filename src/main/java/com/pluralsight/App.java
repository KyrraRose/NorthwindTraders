package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    "root",
                    "yearup");

            PreparedStatement statement = connection.prepareStatement(query);


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
                System.out.printf("%-4d %-35s $%-7.2f %-6d%n", productID, name, price, stock);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }
}