package com.example.bluessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import info.androidhive.fontawesome.FontDrawable;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluessage.Controle.ServerClass;
import com.example.bluessage.View.Chat.TelaChat;
import com.example.bluessage.View.Contatos.TelaContatos;
import com.example.bluessage.View.MenuTresPontos.MenuTresPontos;

public class MainActivity extends AppCompatActivity {

    private static boolean bluetoothConecado = false;
    public static boolean getBluetoothConectado(){
        return bluetoothConecado;
    }

    private LinearLayout layoutConversas;
    private final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dale = this;

        layoutConversas = (LinearLayout) findViewById(R.id.layoutConversas);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Seu dispositivo não suporta Bluetooth!", Toast.LENGTH_LONG).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                bluetoothConecado = true;
                iniciarServer();
            }
        }


        requestPermissions();
    }

    private void iniciarServer(){
        ServerClass serverClass = new ServerClass();
        serverClass.start();
    }

    private static MainActivity dale;
    public static void imprimir(String messagem){
        Toast.makeText(dale,messagem,Toast.LENGTH_LONG).show();
    }

    private int androidVersion; //define at top of code as a variable
    private void requestPermissions(){
        androidVersion = Build.VERSION.SDK_INT;
        if (androidVersion >= 23){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    }, 1);
        }

        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
        startActivity(discoverableIntent);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bluetoothConecado = true;
                iniciarServer();
            } else {
                Toast.makeText(this, "Seu bluetooth não foi ativado", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (MenuTresPontos.abrirTelaSelecionada(MainActivity.this, item.getItemId(), -1))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void clickBotaoContatos(View view) {
        Intent in = new Intent(MainActivity.this, TelaContatos.class);
        startActivity(in);
    }

    public LinearLayout montarLinearLayout(int width, int height, int orietacao, int marginTop, int marginLeft, int marginRight, int paddingHorizontal) {
        LinearLayout.LayoutParams tamanhoLinearLayoutPrincipal = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoLinearLayoutPrincipal.width = width;
        tamanhoLinearLayoutPrincipal.height = height;
        tamanhoLinearLayoutPrincipal.topMargin = marginTop;
        tamanhoLinearLayoutPrincipal.leftMargin = marginLeft;
        tamanhoLinearLayoutPrincipal.rightMargin = marginRight;
        LinearLayout linearLayoutPrincipal = new LinearLayout((getBaseContext()));
        linearLayoutPrincipal.setOrientation(orietacao);
        linearLayoutPrincipal.setLayoutParams(tamanhoLinearLayoutPrincipal);
        linearLayoutPrincipal.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_VERTICAL);
        return linearLayoutPrincipal;
    }
    public ConstraintLayout montarImagemUsuario(String imagem) {
        LinearLayout.LayoutParams tamanhoLayout = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoLayout.width = 160;
        tamanhoLayout.height = 160;

        ConstraintLayout layout = new ConstraintLayout(getBaseContext());
        layout.setBackgroundResource(R.drawable.elipse);
        layout.setLayoutParams(tamanhoLayout);

        ConstraintLayout.LayoutParams tamanhoImagem = new ConstraintLayout.LayoutParams
                ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT,(int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
        tamanhoImagem.width = 58;
        tamanhoImagem.height = 80;
        tamanhoImagem.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        tamanhoImagem.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        tamanhoImagem.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        tamanhoImagem.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        View imgAux = null;

        if (imagem.equals("")) {
            FontDrawable drawable = new FontDrawable(getBaseContext(), R.string.fa_user_solid, true, false);
            drawable.setTextColor(Color.rgb(255,255,255));

            ImageView img = new ImageView(getBaseContext());
            img.setLayoutParams(tamanhoImagem);
            img.setImageDrawable(drawable);
            imgAux = img;
        } else {

        }

        layout.addView(imgAux);
        return layout;
    }
    public LinearLayout montarNomeEMensagem(String nome, String mensagem) {
        LinearLayout layoutPrincipal = montarLinearLayout(LinearLayout.LayoutParams.FILL_PARENT, 200, LinearLayout.VERTICAL, 0,
                30,0,0);

        LinearLayout.LayoutParams tamanhoTextViewNome = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoTextViewNome.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tamanhoTextViewNome.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        tamanhoTextViewNome.bottomMargin = 10;

        TextView textViewNome = new TextView(getBaseContext());
        textViewNome.setGravity(Gravity.LEFT);
        textViewNome.setTextSize(22);
        textViewNome.setTextColor(Color.rgb(0,0,0));
        textViewNome.setTypeface(null, Typeface.BOLD);
        textViewNome.setText(nome);
        textViewNome.setLayoutParams(tamanhoTextViewNome);
        textViewNome.setLines(1);

        LinearLayout linearLayoutMesagem = montarLinearLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.HORIZONTAL, 0, 0,0, 0);

        LinearLayout.LayoutParams tamanhoIconeMensagem = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoIconeMensagem.width = 40;
        tamanhoIconeMensagem.height = 40;
        tamanhoIconeMensagem.rightMargin = 20;
        tamanhoIconeMensagem.gravity = Gravity.BOTTOM;
        tamanhoIconeMensagem.bottomMargin = 2;

        FontDrawable drawable = new FontDrawable(getBaseContext(), R.string.fa_check_solid, true, false);
        drawable.setTextColor(Color.argb(80,0,0,0));

        ImageView icone = new ImageView(getBaseContext());
        icone.setLayoutParams(tamanhoIconeMensagem);
        icone.setImageDrawable(drawable);

        linearLayoutMesagem.addView(icone);

        LinearLayout.LayoutParams tamanhoTextViewMensagem = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoTextViewMensagem.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tamanhoTextViewMensagem.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        TextView textViewMensagem = new TextView(getBaseContext());
        textViewMensagem.setGravity(Gravity.LEFT);
        textViewMensagem.setTextSize(18);
        textViewMensagem.setTextColor(Color.rgb(0,0,0));
        textViewMensagem.setText(mensagem);
        textViewMensagem.setLayoutParams(tamanhoTextViewMensagem);
        textViewMensagem.setLines(1);

        linearLayoutMesagem.addView(textViewMensagem);

        layoutPrincipal.addView(textViewNome);
        layoutPrincipal.addView(linearLayoutMesagem);

        return layoutPrincipal;
    }
    public void montarConversa(final String nomeUsuario, String ultimaConversa, String imagem) {

        LinearLayout linearLayoutPrincipal = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.VERTICAL, 10, 0, 0, 0);

        LinearLayout linearLayoutCorpo = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, 200, LinearLayout.HORIZONTAL, 0,
                0,0,25);
        linearLayoutCorpo.addView(montarImagemUsuario(imagem));
        linearLayoutCorpo.addView(montarNomeEMensagem(nomeUsuario, ultimaConversa));

        LinearLayout linearLayoutLayoutRodape = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, 5, LinearLayout.VERTICAL,
                0,190,30,0);
        linearLayoutLayoutRodape.setBackgroundColor(Color.argb(99,0,0,0));

        linearLayoutPrincipal.addView(linearLayoutCorpo);
        linearLayoutPrincipal.addView(linearLayoutLayoutRodape);

        linearLayoutPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelaChat.setNomeUsuario(nomeUsuario);
                Intent in = new Intent(MainActivity.this, TelaChat.class);
                startActivity(in);
            }
        });

        layoutConversas.addView(linearLayoutPrincipal);
    }
}
