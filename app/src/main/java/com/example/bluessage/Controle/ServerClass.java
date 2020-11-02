package com.example.bluessage.Controle;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Message;

import com.example.bluessage.View.Chat.TelaChat;

import java.io.IOException;

public class ServerClass extends Thread{
    private BluetoothServerSocket serverSocket;

    public ServerClass(){
        try {
            serverSocket = TelaChat.bluetoothAdapter.listenUsingRfcommWithServiceRecord(TelaChat.APP_NAME,TelaChat.MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        BluetoothSocket socket = null;

        while (socket==null){
            try {
                Message message = Message.obtain();
                message.what=TelaChat.STATE_CONNECTING;
                TelaChat.handler.sendMessage(message);
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what=TelaChat.STATE_CONNECTION_FAILED;
                TelaChat.handler.sendMessage(message);
            }

            if (socket!=null){
                Message message = Message.obtain();
                message.what=TelaChat.STATE_CONNECTED;
                TelaChat.handler.sendMessage(message);

                TelaChat.sendReceive = new SendReceive(socket);
                TelaChat.sendReceive.start();;

                break;
            }
        }
    }

}