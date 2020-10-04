package com.example.bluessage.View.Sobre;

import android.os.Bundle;

import com.example.bluessage.R;

import androidx.appcompat.app.AppCompatActivity;

public class TelaSobre  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        getSupportActionBar().setTitle("Sobre");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
