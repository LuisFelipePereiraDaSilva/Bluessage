package com.example.bluessage.View.Chat;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bluessage.Controle.SendReceive;
import com.example.bluessage.MainActivity;
import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class TelaChat extends AppCompatActivity {

    private static String nomeUsuario;
    public static void setNomeUsuario(String nome) {
        nomeUsuario = nome;
    }
    public static final int STATE_LISTENING = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTION_FAILED = 4;
    public static final int STATE_MESSAGE_RECIVIED = 5;

    public static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public static SendReceive sendReceive;
    public static final String APP_NAME = "BTChat";
    public static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setTitle(nomeUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void enviarMensagem(View view){
        if(delay == true){
            Toast.makeText(getBaseContext(),"Chegou",Toast.LENGTH_LONG).show();
            System.out.println("Linha 49");
            EditText writeMsg = (EditText) findViewById(R.id.editTextMessage);
            String string = String.valueOf(writeMsg.getText());
            sendReceive.write(string.getBytes());
        }else {
            MainActivity.imprimir("Fazendo conexão");
        }
    }

    public static boolean delay = false;
    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what){
                case STATE_LISTENING:
                    //MainActivity.imprimir("Escutando");
                    //status.setText("Listenig");
                    break;
                case STATE_CONNECTING:
                    //MainActivity.imprimir("Conectandooooooooo");
                    delay=false;
                    //status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    //MainActivity.imprimir("Conectado");
                    delay = true;
                    //status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    MainActivity.imprimir("Falha na conexão");
                    //status.setText("Connection falied");
                    break;
                case STATE_MESSAGE_RECIVIED:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);
                    //msg_box.setText(tempMsg);
                    MainActivity.imprimir(tempMsg);
                    break;
            }
            return true;
        }
    });

}
