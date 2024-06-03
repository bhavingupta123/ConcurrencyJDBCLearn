package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomSQL{

    private static final ConcurrentHashMap<Integer, Lock> userLocks = new ConcurrentHashMap<>();

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

    public synchronized void updateDb(int bal, int userId){

        Lock lock = userLocks.computeIfAbsent(userId, k -> new ReentrantLock());
        lock.lock();

        try {

            int originalBalance = getUserBalance(userId);

            System.out.println("User balance for userId : " + userId + " is:" + originalBalance);
            System.out.println("To withdraw for userId :"  +  userId + " is:" + bal);

            if(originalBalance - bal >= 0){
                String sql = "UPDATE BankDetails " + "SET Balance = " + (originalBalance - bal) + " WHERE UserId = " + userId;
                Statement statement = conn1.prepareStatement(sql);

                statement.execute(sql);
                statement.close();
            }

            else {
                System.out.println("low balance hence not possible for userId : " + userId);
            }
        }

        catch (Exception e){
            System.out.println("updateDb : " + e);
        }

        finally {
            lock.unlock();
            userLocks.remove(userId, lock);
        }

    }

    public int getUserBalance(int userId){

        int balance=0;

        try {

            String selectQuery = "SELECT Balance FROM BankDetails WHERE UserId = " + userId + " FOR UPDATE";
            Statement statement = conn1.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);

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
