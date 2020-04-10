package br.com.renaninfo.chegoupacote.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 27/03/2017.
 */

public class Usuario {

    private String id;
    private NivelUsuario nivel;
    private String email;
    private String nome;
    private List<Unidade> unidades;

    public boolean isNivelAdmin() {
        return NivelUsuario.ADMIN.equals(this.getNivel());
    }

    public boolean isNivelEntregador() {
        return NivelUsuario.ENTREGADOR.equals(this.getNivel());
    }

    public boolean isNivelUsuario() {
        return NivelUsuario.USUARIO.equals(this.getNivel());
    }

    public boolean canBeUsuario() {
        return false;
    }

    public enum NivelUsuario {
        USUARIO, ENTREGADOR, ADMIN
    }

    public Usuario() {
        unidades = new ArrayList<>();
    }

    public Usuario(String id, NivelUsuario nivel, String email, String nome, List<Unidade> listaUnidades) {
        this.id = id;
        this.nivel = nivel;
        this.email = email;
        this.nome = nome;
        this.unidades = listaUnidades;
    }

    public Usuario(String id, String email, String nome) {
        this.id = id;
        this.email = email;
        this.nome = nome;
    }

    public static NivelUsuario parseNivelUsuario(String sNivel) {
        switch (sNivel) {
            case "U":
            case "USUARIO": return NivelUsuario.USUARIO;
            case "A":
            case "ADMIN": return NivelUsuario.ADMIN;
            case "E":
            case "ENTREGADOR": return NivelUsuario.ENTREGADOR;
            default:  return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NivelUsuario getNivel() {
        return nivel;
    }

    public void setNivel(NivelUsuario nivel) {
        this.nivel = nivel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }
}
