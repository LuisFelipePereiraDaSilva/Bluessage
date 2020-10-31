package com.example.bluessage.View.Chat;

import android.os.Bundle;

import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;

public class TelaChat extends AppCompatActivity {

    private static String nomeUsuario;
    public static void setNomeUsuario(String nome) {
        nomeUsuario = nome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setTitle(nomeUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
