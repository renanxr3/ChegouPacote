package br.com.renaninfo.chegoupacote.vo;

import com.google.firebase.database.Exclude;

/**
 * Created by Renan on 27/03/2017.
 */

public class Entregador {

    private String idUsuario;
    private Usuario usuario;

    public Entregador() {

    }

    public Entregador(Usuario usuario) {
        this.usuario = usuario;
    }

    public Entregador(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Exclude
    public Usuario getUsuario() {
        return usuario;
    }

    @Exclude
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
