package com.example.bluessage.View.Chat;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluessage.Controle.Criptografia;
import com.example.bluessage.Controle.SendReceive;
import com.example.bluessage.MainActivity;
import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import info.androidhive.fontawesome.FontDrawable;

import java.util.ArrayList;
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

    private static TelaChat telaChat;

    private LinearLayout linearLayoutMensagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        telaChat = this;

        getSupportActionBar().setTitle(nomeUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutMensagens = (LinearLayout) findViewById(R.id.layoutChat);
    }

    public void enviarMensagem(View view){
        EditText writeMsg = (EditText) findViewById(R.id.editTextMessage);
        String string = String.valueOf(writeMsg.getText());
        montarMensagem(string, false);
        sendReceive.write(Criptografia.criptografar(string).getBytes());
    }

    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what){
                case STATE_LISTENING:
                    //status.setText("Listenig");
                    break;
                case STATE_CONNECTING:
                    //status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    //status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    MainActivity.imprimir("Não foi possível se conectar a esse dispositivo");
                    break;
                case STATE_MESSAGE_RECIVIED:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);
                    //msg_box.setText(tempMsg);
                    if (TelaChat.telaChat != null)
                        TelaChat.telaChat.montarMensagem(Criptografia.descriptografar(tempMsg), true);
                    break;
            }
            return true;
        }
    });

    public LinearLayout montarLinearLayout(int width, int height, int orietacao, int marginBottom, int marginLeft, int marginRight, int paddingHorizontal) {
        LinearLayout.LayoutParams tamanhoLinearLayoutPrincipal = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoLinearLayoutPrincipal.width = width;
        tamanhoLinearLayoutPrincipal.height = height;
        tamanhoLinearLayoutPrincipal.bottomMargin = marginBottom;
        tamanhoLinearLayoutPrincipal.leftMargin = marginLeft;
        tamanhoLinearLayoutPrincipal.rightMargin = marginRight;
        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        linearLayoutPrincipal.setOrientation(orietacao);
        linearLayoutPrincipal.setLayoutParams(tamanhoLinearLayoutPrincipal);
        linearLayoutPrincipal.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_VERTICAL);
        return linearLayoutPrincipal;
    }
    public ConstraintLayout montarImagemUsuario() {
        LinearLayout.LayoutParams tamanhoLayout = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoLayout.width = 120;
        tamanhoLayout.height = 120;

        ConstraintLayout layout = new ConstraintLayout(this);
        layout.setBackgroundResource(R.drawable.elipse);
        layout.setLayoutParams(tamanhoLayout);

        ConstraintLayout.LayoutParams tamanhoImagem = new ConstraintLayout.LayoutParams
                ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT,(int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
        tamanhoImagem.width = 48;
        tamanhoImagem.height = 70;
        tamanhoImagem.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        tamanhoImagem.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        tamanhoImagem.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        tamanhoImagem.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        FontDrawable drawable = new FontDrawable(this, R.string.fa_user_solid, true, false);
        drawable.setTextColor(Color.rgb(255,255,255));

        ImageView img = new ImageView(this);
        img.setLayoutParams(tamanhoImagem);
        img.setImageDrawable(drawable);

        layout.addView(img);
        return layout;
    }
    public TextView montarMensagemAux(String mensagem, Boolean recebida) {

        LinearLayout.LayoutParams tamanhoTextViewNome = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoTextViewNome.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        tamanhoTextViewNome.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        tamanhoTextViewNome.leftMargin = recebida ? 30 : 200;
        tamanhoTextViewNome.rightMargin = recebida ? 200 : 30;

        TextView textViewNome = new TextView(this);
        textViewNome.setGravity(recebida ? Gravity.LEFT : Gravity.RIGHT);
        textViewNome.setTextSize(16);
        textViewNome.setTextColor(Color.rgb(0,0,0));
        textViewNome.setText(mensagem);
        textViewNome.setLayoutParams(tamanhoTextViewNome);
        textViewNome.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        return textViewNome;
    }
    public void montarMensagem(String mensagem, Boolean recebida) {

        LinearLayout linearLayoutPrincipal = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.VERTICAL, 0, 0, 0, 0);

        LinearLayout linearLayoutCorpo = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, 150, LinearLayout.HORIZONTAL, 0,
                0,0,25);

        if (recebida) {
            linearLayoutCorpo.addView(montarImagemUsuario());
            linearLayoutCorpo.addView(montarMensagemAux(mensagem, recebida));
        } else {
            linearLayoutCorpo.addView(montarMensagemAux(mensagem, recebida));
            linearLayoutCorpo.addView(montarImagemUsuario());
            linearLayoutCorpo.setGravity(Gravity.RIGHT);
        }

        linearLayoutPrincipal.addView(linearLayoutCorpo);

        linearLayoutMensagens.addView(linearLayoutPrincipal);
    }
}
