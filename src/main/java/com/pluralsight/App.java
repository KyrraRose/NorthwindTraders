package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    private static Connection connection = null;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        loadConnection(args[0],args[1]);
        run();

    }
    public static void run(){
        do {
                mainMenu();
                menuSelection();
                pressEnter();

        }while(true);
    }
    public static void mainMenu(){
        System.out.print("\nWhat do you want to do?\n\t[1] Display all products\n\t[2] Display all customers\n\t[3] Display Categories\n\t[0] Exit\n---------------------------\n");
    }
    public static void menuSelection(){
        switch (getUserChoice("Select an Option: ")) {
            case 1 -> displayProducts();
            case 2 -> displayCustomers();
            case 3 -> {displayCategories();productsByCat(getUserChoice("\nWhat category of products would you like to see?\nCategory ID: "));}
            case 0 -> exit();
            default -> System.out.println("Input not recognized. Please try again.");
        }
    }
    public static void displayProducts(){

        String query = "SELECT * FROM products";

        try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery(query);) {

            System.out.println("\nId   Name                                Price   Stock");
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
        }
    }
    public static void displayCustomers() {


        String query = "SELECT * FROM customers WHERE CompanyName !='IT' ORDER BY Country";

        try (PreparedStatement statement = connection.prepareStatement(query);

            ResultSet results = statement.executeQuery(query);){

            System.out.println("\nContact Name                   Company Name                               City            Country       Phone #");
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
        }

    }
    public static void displayCategories() {


        String query = "SELECT * FROM categories ORDER BY CategoryName";

        try (PreparedStatement statement = connection.prepareStatement(query);

             ResultSet results = statement.executeQuery(query);){

            System.out.println("\nID    Category ");
            System.out.println("--- ------------------");
            while (results.next()) {
                System.out.printf("%-3d %-14s%n",
                        results.getInt("CategoryID"),
                        results.getString("CategoryName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    public static void productsByCat(int choice){

        String query = "SELECT * FROM products WHERE CategoryID = ?";
        ResultSet results = null;

        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, choice);
            results = statement.executeQuery();


            System.out.println("\nId   Name                                Price   Stock");
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
        }finally{
            if (results != null){
                try{
                results.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean validateCat(int choice){
        String query = "SELECT COUNT(CategoryID) FROM products";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery();) {




            System.out.println("\nId   Name                                Price   Stock");
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
        }
    }
    public static void loadConnection(String user,String pass){
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    user,
                    pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getUserChoice(String prompt) {
        System.out.print(prompt);

        boolean notChosen = true;
        int option = -1;

        do {
            try {
                option = scanner.nextInt();
                scanner.nextLine();

                if (option != -1) notChosen = false;

            } catch (Exception e) {
                System.out.print("Invalid Type Entered. " + prompt);
                scanner.nextLine();
            }
        } while (notChosen);

        return option;
    }
    public static void pressEnter(){
        System.out.println("---------------------\n\nPress [ENTER] to continue..");
        scanner.nextLine();
        clearScreen();
    }
    public static void clearScreen() {
        for (int i = 0; i < 60; i++) {
            System.out.println();
        }
    }
    public static void exit(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exiting!");
        System.exit(0);
    }
}