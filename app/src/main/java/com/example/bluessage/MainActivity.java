package com.example.bluessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bluessage.View.Contatos.TelaContatos;
import com.example.bluessage.View.MenuTresPontos.MenuTresPontos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
