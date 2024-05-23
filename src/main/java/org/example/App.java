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

        customThread.bal = 1;
        customThread2.bal = 2;
        customThread3.bal = 3;

        customThread.start();
        customThread2.start();
        customThread3.start();

    }
}
