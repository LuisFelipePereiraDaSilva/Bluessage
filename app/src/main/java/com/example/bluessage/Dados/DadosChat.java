package com.example.bluessage.Dados;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DadosChat {

    private String path;

    public DadosChat(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void salvarMessagens(Context context, ArrayList<Mensagem> mensagens) throws Exception {
        FileOutputStream file = context.openFileOutput(this.getPath(), Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(file);

        for(int i = 0; i <  mensagens.size(); i++){
            os.writeObject(mensagens.get(i));
        }
        os.close();
    }

    public ArrayList<Mensagem> lerMessagens(Context context) throws Exception {

        ArrayList<Mensagem> mensagens = new ArrayList<Mensagem>();
        FileInputStream file;
        file = context.openFileInput(this.getPath());

        ObjectInputStream is = new ObjectInputStream(file);
        Mensagem mensagem;
        try {
            while ((mensagem = (Mensagem) is.readObject()) != null) {
                mensagens.add(mensagem);
            }
            is.close();
        }catch (Exception e){}

        return mensagens;
    }
}
