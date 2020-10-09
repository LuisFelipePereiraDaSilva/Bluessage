package com.example.bluessage.View.Contatos.ContatosNaoPareados;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluessage.MainActivity;
import com.example.bluessage.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import info.androidhive.fontawesome.FontDrawable;

public class TelaContatosNaoPareados extends Fragment {

    public static TelaContatosNaoPareados newInstance() {
        return new TelaContatosNaoPareados();
    }

    private LinearLayout layoutContatosNaoPareados;

    private ArrayList<String> dispositivos = new ArrayList<String>();
    private ArrayList<BluetoothDevice> disps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_contatos_nao_pareados, container, false);

        layoutContatosNaoPareados = root.findViewById(R.id.layoutContatosNaoPareados);

        montarContato(-1,"Dispositivo Teste");

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getContext().registerReceiver(receiver, filter);

        contatosNaoPareados();
        return root;
    }

    private void contatosNaoPareados() {
        if (MainActivity.getBluetoothConectado()) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            boolean result = bluetoothAdapter.startDiscovery();
            System.out.println(result);
            System.out.println();
        }
    }

    public boolean createBond(BluetoothDevice btDevice) throws Exception
    {
        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
        Method createBondMethod = class1.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                if(!dispositivos.contains(deviceHardwareAddress)) {
                    disps.add(device);
                    dispositivos.add(deviceHardwareAddress);
                    montarContato(disps.size() - 1, deviceName);
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        getContext().unregisterReceiver(receiver);
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
    public ConstraintLayout montarImagemUsuario() {
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

        FontDrawable drawable = new FontDrawable(getContext(), R.string.fa_user_solid, true, false);
        drawable.setTextColor(Color.rgb(255,255,255));

        ImageView img = new ImageView(getContext());
        img.setLayoutParams(tamanhoImagem);
        img.setImageDrawable(drawable);

        layout.addView(img);
        return layout;
    }
    public TextView montarNomeTelefone(String nome) {

        LinearLayout.LayoutParams tamanhoTextViewNome = new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
        tamanhoTextViewNome.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tamanhoTextViewNome.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        tamanhoTextViewNome.leftMargin = 30;

        TextView textViewNome = new TextView(getContext());
        textViewNome.setGravity(Gravity.LEFT);
        textViewNome.setTextSize(20);
        textViewNome.setTextColor(Color.rgb(0,0,0));
        textViewNome.setTypeface(null, Typeface.BOLD);
        textViewNome.setText(nome);
        textViewNome.setLayoutParams(tamanhoTextViewNome);
        textViewNome.setLines(1);

        return textViewNome;
    }
    public void montarContato(int id, String nomeUsuario) {

        LinearLayout linearLayoutPrincipal = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.VERTICAL, 0, 0, 0, 0);
        linearLayoutPrincipal.setId(id);
        linearLayoutPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() != -1) {
                    try {
                        createBond(disps.get(v.getId()));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        LinearLayout linearLayoutCorpo = montarLinearLayout(LinearLayout.LayoutParams.MATCH_PARENT, 180, LinearLayout.HORIZONTAL, 0,
                0,0,25);
        linearLayoutCorpo.addView(montarImagemUsuario());
        linearLayoutCorpo.addView(montarNomeTelefone(nomeUsuario));

        linearLayoutPrincipal.addView(linearLayoutCorpo);

        layoutContatosNaoPareados.addView(linearLayoutPrincipal);
    }
}
