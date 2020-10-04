package com.example.bluessage.View.TelaAjuda;

import android.os.Bundle;

import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;

public class TelaAjuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        getSupportActionBar().setTitle("Ajuda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
