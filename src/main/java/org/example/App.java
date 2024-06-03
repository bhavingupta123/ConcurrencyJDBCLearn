package org.example;


import org.example.threads.CustomThread;

public class App
{
    public static void main(String[] args)
    {
        CustomSQL customSQL = new CustomSQL();

        CustomThread customThread =  new CustomThread(customSQL);
        CustomThread customThread2 = new CustomThread(customSQL);
        CustomThread customThread3 = new CustomThread(customSQL);
        CustomThread customThread4 =  new CustomThread(customSQL);

        customThread.bal = 30;
        customThread.userId = 1;

        customThread2.bal = 50;
        customThread2.userId = 1;

        customThread4.bal = 10;
        customThread4.userId = 1;

        customThread3.bal = 20;
        customThread3.userId = 3;

        customThread.start();
        customThread2.start();
        customThread3.start();
        customThread4.start();

    }
}
