package com.example.bluessage.Controle;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;

import com.example.bluessage.View.Chat.TelaChat;

import java.io.IOException;
import java.util.UUID;

public class ClientClass extends Thread{

    private BluetoothDevice device;
    private BluetoothSocket socket;

    public ClientClass(BluetoothDevice device1){
        device=device1;

        try {
            socket=device.createRfcommSocketToServiceRecord(TelaChat.MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        try {
            socket.connect();
            Message message = Message.obtain();
            message.what=TelaChat.STATE_CONNECTED;
            TelaChat.handler.sendMessage(message);
            TelaChat.sendReceive =new SendReceive(socket);
            TelaChat.sendReceive.start();
        } catch (IOException e) {
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=TelaChat.STATE_CONNECTION_FAILED;
            TelaChat.handler.sendMessage(message);
        }
    }
}