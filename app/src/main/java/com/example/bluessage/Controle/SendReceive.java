package com.example.bluessage.Controle;

import android.bluetooth.BluetoothSocket;

import com.example.bluessage.View.Chat.TelaChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SendReceive extends Thread{
    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SendReceive(BluetoothSocket socket){
        bluetoothSocket = socket;
        InputStream tempIn = null;
        OutputStream tempOut = null;

        try {
            tempIn=bluetoothSocket.getInputStream();
            tempOut=bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = tempIn;
        outputStream = tempOut;
        System.out.println("\n\n\n\n");
        System.out.println(outputStream);
    }
    public void run(){
        byte[] buffer = new byte[1024];
        int bytes;

        while (true){
            try {
                bytes=inputStream.read(buffer);
                TelaChat.handler.obtainMessage(TelaChat.STATE_MESSAGE_RECIVIED,bytes,-1,buffer).sendToTarget();
                System.out.println("\n\n\n dale 40");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(byte[] bytes){
        try {
            System.out.println("\n\n\n dale 49-");
            outputStream.write(bytes);
            System.out.println("\n\n\n dale 49");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}