package com.example.bluessage.View.Perfil;

import android.os.Bundle;

import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;

public class TelaPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

