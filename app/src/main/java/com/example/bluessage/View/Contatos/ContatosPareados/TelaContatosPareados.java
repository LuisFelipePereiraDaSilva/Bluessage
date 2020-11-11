package com.example.bluessage.View.Contatos.ContatosPareados;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bluessage.Controle.ClientClass;
import com.example.bluessage.MainActivity;
import com.example.bluessage.R;
import com.example.bluessage.View.Chat.TelaChat;
import com.example.bluessage.View.Contatos.TelaContatos;

import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import info.androidhive.fontawesome.FontDrawable;

public class TelaContatosPareados extends Fragment {

    public static TelaContatosPareados newInstance() {
        return new TelaContatosPareados();
    }

    private LinearLayout layoutContatosPareados;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_contatos_pareados, container, false);

        layoutContatosPareados = root.findViewById(R.id.layoutContatosPareados);

        contatosPareados();
        return root;
    }

    private void contatosPareados() {
        if (MainActivity.getBluetoothConectado()) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    montarContato(deviceName, deviceHardwareAddress, "",device);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public LinearLayout montarLinearLayout(int width, int height, int orietacao, int marginBottom, int marginLeft, int marginRight, int paddingHorizontal) {
        LinearLayout.LayoutParams tamanhoLinearLayoutPrincipal = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoLinearLayoutPrincipal.width = width;
        tamanhoLinearLayoutPrincipal.height = height;
        tamanhoLinearLayoutPrincipal.bottomMargin = marginBottom;
        tamanhoLinearLayoutPrincipal.leftMargin = marginLeft;
        tamanhoLinearLayoutPrincipal.rightMargin = marginRight;
        LinearLayout linearLayoutPrincipal = new LinearLayout(getContext());
        linearLayoutPrincipal.setOrientation(orietacao);
        linearLayoutPrincipal.setLayoutParams(tamanhoLinearLayoutPrincipal);
        linearLayoutPrincipal.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        linearLayoutPrincipal.setGravity(Gravity.CENTER_VERTICAL);
        return linearLayoutPrincipal;
    }
    public ConstraintLayout montarImagemUsuario(String imagem) {
        LinearLayout.LayoutParams tamanhoLayout = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoLayout.width = 120;
        tamanhoLayout.height = 120;

        ConstraintLayout layout = new ConstraintLayout(getContext());
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

        View imgAux = null;

        if (imagem.equals("")) {
            FontDrawable drawable = new FontDrawable(getContext(), R.string.fa_user_solid, true, false);
            drawable.setTextColor(Color.rgb(255,255,255));

            ImageView img = new ImageView(getContext());
            img.setLayoutParams(tamanhoImagem);
            img.setImageDrawable(drawable);
            imgAux = img;
        } else {

        }

        layout.addView(imgAux);
        return layout;
    }
    public LinearLayout montarNomeUsuarioENomeTelefone(String nome, String mensagem) {
        LinearLayout layoutPrincipal = montarLinearLayout(LinearLayout.LayoutParams.FILL_PARENT, 180, LinearLayout.VERTICAL, 0,
                30,0,0);

        LinearLayout.LayoutParams tamanhoTextViewNome = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoTextViewNome.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tamanhoTextViewNome.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        tamanhoTextViewNome.bottomMargin = 10;

        TextView textViewNome = new TextView(getContext());
        textViewNome.setGravity(Gravity.LEFT);
        textViewNome.setTextSize(20);
        textViewNome.setTextColor(Color.rgb(0,0,0));
        textViewNome.setTypeface(null, Typeface.BOLD);
        textViewNome.setText(nome);
        textViewNome.setLayoutParams(tamanhoTextViewNome);
        textViewNome.setLines(1);

        LinearLayout.LayoutParams tamanhoTextViewMensagem = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoTextViewMensagem.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tamanhoTextViewMensagem.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        TextView textViewMensagem = new TextView(getContext());
        textViewMensagem.setGravity(Gravity.LEFT);
        textViewMensagem.setTextSize(16);
        textViewMensagem.setTextColor(Color.rgb(0,0,0));
        textViewMensagem.setText(mensagem);
        textViewMensagem.setLayoutParams(tamanhoTextViewMensagem);
        textViewMensagem.setLines(1);

        layoutPrincipal.addView(textViewNome);
        layoutPrincipal.addView(textViewMensagem);

        return layoutPrincipal;
    }
    public void montarContato(final String nomeUsuario, final String nomeTelefone, String imagem, final BluetoothDevice device) {

        LinearLayout linearLayoutPrincipal = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.VERTICAL, 10, 0, 0, 0);
        linearLayoutPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientClass clientClass = new ClientClass(device);
                clientClass.start();
                TelaChat.setNomeUsuario(nomeUsuario);
                TelaChat.setEnderecoMac(nomeTelefone);
                Intent in = new Intent(TelaContatos.telaContatos, TelaChat.class);
                startActivity(in);
                //status.setText("Connecteing");
            }
        });

        LinearLayout linearLayoutCorpo = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, 180, LinearLayout.HORIZONTAL, 0,
                0,0,25);
        linearLayoutCorpo.addView(montarImagemUsuario(imagem));
        linearLayoutCorpo.addView(montarNomeUsuarioENomeTelefone(nomeUsuario, nomeTelefone));

        linearLayoutPrincipal.addView(linearLayoutCorpo);

        layoutContatosPareados.addView(linearLayoutPrincipal);


    }
}
