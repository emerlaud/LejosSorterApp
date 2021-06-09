package fr.gems.lejos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.os.Build;
import android.util.Log;

public class BT_Comm {
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    BluetoothAdapter localAdapter;
    BluetoothSocket socket_ev3_1, socket_nxt2;
    boolean success=false;
    private boolean btPermission=false;
    private boolean alertReplied=false;

    public void reply(){this.alertReplied = true;}
    public void setBtPermission(boolean btPermission) {
        this.btPermission = btPermission;
    }

    public boolean initBT(){
        localAdapter=BluetoothAdapter.getDefaultAdapter();
        return localAdapter.isEnabled();
    }

    //connect to both NXTs
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public  boolean connectToEV3(String macAdd){

        BluetoothDevice ev3_1 = localAdapter.getRemoteDevice(macAdd);
        //try to connect to the nxt
        try {
            socket_ev3_1 = ev3_1.createRfcommSocketToServiceRecord(UUID
                    .fromString(SPP_UUID));

            socket_ev3_1.connect();

            success = true;

        } catch (IOException e) {
            Log.d("Bluetooth","Err: Device not found or cannot connect " + macAdd);
            success=false;


        }
        return success;

    }


    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public void writeMessage(byte msg) throws InterruptedException{
        BluetoothSocket connSock;


            connSock= socket_ev3_1;

        if(connSock!=null){
            try {

                OutputStreamWriter out=new OutputStreamWriter(connSock.getOutputStream());
                out.write(msg);
                out.flush();

                Thread.sleep(1000);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            //Error
        }
    }

    // Note: Not needed for the current program
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public int readMessage(String nxt){
        BluetoothSocket connSock;
        int n;
        //Swith nxt socket
        if(nxt.equals("nxt2")){
            connSock=socket_nxt2;
        }else if(nxt.equals("nxt1")){
            connSock= socket_ev3_1;
        }else{
            connSock=null;
        }

        if(connSock!=null){
            try {

                InputStreamReader in=new InputStreamReader(connSock.getInputStream());
                n=in.read();

                return n;


            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }else{
            //Error
            return -1;
        }

    }

}