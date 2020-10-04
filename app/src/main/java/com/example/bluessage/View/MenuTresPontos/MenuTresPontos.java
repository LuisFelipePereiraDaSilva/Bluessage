package com.example.bluessage.View.MenuTresPontos;

import android.content.Context;
import android.content.Intent;

import com.example.bluessage.R;
import com.example.bluessage.View.Perfil.TelaPerfil;
import com.example.bluessage.View.Sobre.TelaSobre;
import com.example.bluessage.View.TelaAjuda.TelaAjuda;

public class MenuTresPontos {

    // telaAberta = -1 -> nenhuma, 0 -> sobre, 1 -> ajuda, 2 -> perfil
    public static boolean abrirTelaSelecionada(Context context, int id, int telaAberta) {
        Intent in = null;
        if (telaAberta != 0 && id == R.id.action_sobre) {
            in = new Intent(context, TelaSobre.class);
        } else if (telaAberta != 1 && id == R.id.action_ajuda) {
            in = new Intent(context, TelaAjuda.class);
        } else if (telaAberta != 2 && id == R.id.action_perfil) {
            in = new Intent(context, TelaPerfil.class);
        } else {
            return false;
        }
        context.startActivity(in);
        return true;
    }
}
