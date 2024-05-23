package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomSQL{

    Connection conn1 = null;
    public void createConnection(){

        try {
            String url1 = "jdbc:mysql://localhost:3306/Bank_Server";
            String user = "springstudent";
            String password = "springstudent";

            conn1 = DriverManager.getConnection(url1, user, password);

            if (conn1 != null) {
                System.out.println("Connected to the database test1");
            }

        }
        catch (Exception e){

        }
    }

    public void getDataFromDb(){

        try {
            String selectQuery = "SELECT * FROM BankDetails";
            Statement statement = conn1.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
            System.out.println("\nThe Available Data");

            while (resultSet.next()) {
                int id = resultSet.getInt("UserId");
                String userName = resultSet.getString("UserName");
                int balance = resultSet.getInt("Balance");
                String timestamp = resultSet.getString("Timestamp");

                System.out.println("ID: " + id + ", userName: " + userName + ", balance: " + balance
                        + ", timestamp " + timestamp);
            }

            statement.close();
        }

        catch (Exception e){
            System.out.println("getDataFromDb : " + e);
        }
    }

    public synchronized void updateDb(int bal){

        try {

            int originalBalance = getUserBalance();

            System.out.println("User balance is:" + originalBalance);
            System.out.println("To withdraw :" + bal);

            String sql = "UPDATE BankDetails " + "SET Balance = " + (originalBalance - bal) + " WHERE UserId = 1";
            Statement statement = conn1.prepareStatement(sql);

            statement.execute(sql);
            statement.close();
        }

        catch (Exception e){
            System.out.println("updateDb : " + e);
        }
    }

    public int getUserBalance(){

        int balance=0;

        try {

            String selectQuery = "SELECT Balance FROM BankDetails WHERE UserId = 1";
            Statement statement = conn1.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
//            System.out.println("\nThe Available Data");

            while (resultSet.next()) {
                balance = resultSet.getInt("Balance");
            }

            return balance;
        }

        catch (Exception e){
            System.out.println("getUserBalance : " + e);
        }

        finally {
            return balance;
        }

    }

}
