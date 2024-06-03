package org.example.threads;

import org.example.CustomSQL;

public class CustomThread extends Thread{

    CustomSQL customSQL;

    public int bal;

    public int userId;

    public CustomThread(CustomSQL customSQL){
        this.customSQL = customSQL;
    }

    @Override
    public void run(){
        customSQL.createConnection();
        customSQL.updateDb(bal, userId);
//        customSQL.getDataFromDb();
    }
}
