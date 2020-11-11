package com.example.bluessage.Dados;

import java.io.Serializable;
import java.util.Date;

public class Mensagem implements Serializable {
    private String usuarioDestino;
    private String texto;
    private Date date;
    private int status;
    public static enum STATUS {VISUALZIADA, ENTREGUE, ENVIADA};

    public Mensagem(String usuarioDestino, String texto, Date date) {
        this.usuarioDestino = usuarioDestino;
        this.texto = texto;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public String getTexto() {
        return texto;
    }
}
