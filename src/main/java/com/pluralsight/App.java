package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("\nWhat do you want to do?\n\t[1] Display all products\n\t[2] Display all customers\n\t[0] Exit\n----\nSelect an Option: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> displayProducts(args);
                    case 2 -> displayCustomers(args);
                    case 0 -> exit();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }while(true);



    }
    public static void displayProducts(String[] args){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String query = "SELECT * FROM products";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    args[0],
                    args[1]);

            statement = connection.prepareStatement(query);


            // 2. Execute your query
            results = statement.executeQuery(query);
            // process the results
            System.out.println("Id   Name                                Price   Stock");
            System.out.println("---- ----------------------------------- ------- ------");
            while (results.next()) {
                System.out.printf("%-4d %-35s $%-7.2f %-6d%n",
                        results.getInt("ProductID"),
                        results.getString("ProductName"),
                        results.getDouble("UnitPrice"),
                        results.getInt("UnitsInStock"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection!= null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void displayCustomers(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String query = "SELECT * FROM customers WHERE CompanyName !='IT' ORDER BY Country";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    args[0],
                    args[1]);

           statement = connection.prepareStatement(query);

            results = statement.executeQuery(query);

            System.out.println("Contact Name                   Company Name                               City            Country       Phone #");
            System.out.println("------------------------------ ------------------------------------------ --------------- ------------- ------------");
            while (results.next()) {
                System.out.printf("%-30s %-42s %-15s %-13s %-12s%n",
                        results.getString("ContactName"),
                        results.getString("CompanyName"),
                        results.getString("City"),
                        results.getString("Country"),
                        results.getString("Phone"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection!= null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void exit(){
        System.out.println("Exiting!");
        System.exit(0);
    }
}