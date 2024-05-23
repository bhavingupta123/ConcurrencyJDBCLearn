package org.example.threads;

import org.example.CustomSQL;

public class CustomThread extends Thread{

    CustomSQL customSQL;

    public int bal;

    public CustomThread(CustomSQL customSQL){
        this.customSQL = customSQL;
    }

    @Override
    public void run(){
        customSQL.createConnection();
        customSQL.updateDb(bal);
        customSQL.getDataFromDb();
    }
}
