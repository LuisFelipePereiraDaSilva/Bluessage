package com.example.bluessage.View.Chat;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluessage.Controle.Criptografia;
import com.example.bluessage.Controle.SendReceive;
import com.example.bluessage.Dados.DadosChat;
import com.example.bluessage.Dados.Mensagem;
import com.example.bluessage.MainActivity;
import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import info.androidhive.fontawesome.FontDrawable;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TelaChat extends AppCompatActivity {

    private static String nomeUsuario;
    public static void setNomeUsuario(String nome) {
        nomeUsuario = nome;
    }

    private static String enderecoMac;
    public static void setEnderecoMac(String endereco) {
        enderecoMac = endereco;
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
    private EditText editText;
    private TextView textView;
    private DadosChat dados;
    private ArrayList<Mensagem> mensagems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        telaChat = this;

        getSupportActionBar().setTitle(nomeUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutMensagens = (LinearLayout) findViewById(R.id.layoutChat);
        textView = (TextView) findViewById(R.id.editTextMessageAux);
        editText = (EditText) findViewById(R.id.editTextMessage);
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().equals("")) {
                    textView.setText("Digite uma Mensagem...");
                } else {
                    textView.setText("");
                }
            }
        });

        dados = new DadosChat(enderecoMac);

        carregarMensagens();
    }

    public void carregarMensagens() {
        try {
            mensagems = dados.lerMessagens(this);
            for (int i = 0; i < mensagems.size(); i++) {
                montarMensagem(mensagems.get(i).getTexto(), mensagems.get(i).getUsuarioDestino().equals("") ? false : true);
            }
        } catch (Exception e) {
        }
    }

    public void salvarMensagem(String texto, Boolean recebida) {
        try {
            Mensagem mensagem = new Mensagem(recebida ? nomeUsuario : "", texto, new Date());
            mensagems.add(mensagem);
            dados.salvarMessagens(this, mensagems);
        } catch (Exception e) {
            MainActivity.imprimir("Error ao salvar mensagens");
        }
    }

    public void enviarMensagem(View view){
        if(delay == true){
            String string = String.valueOf(editText.getText());
            montarMensagem(string, false);
            salvarMensagem(string, false);
            sendReceive.write(Criptografia.criptografar(string).getBytes());
            editText.setText("");
        }else {
            MainActivity.imprimir("Nenhum conexão foi estabelecida");
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
                    MainActivity.imprimir("Não foi possível se conectar a esse dispositivo");
                    break;
                case STATE_MESSAGE_RECIVIED:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg = new String(readBuff,0,msg.arg1);

                    if (TelaChat.telaChat != null) {
                        TelaChat.telaChat.salvarMensagem(tempMsg, true);
                        TelaChat.telaChat.montarMensagem(Criptografia.descriptografar(tempMsg), true);
                    }
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
