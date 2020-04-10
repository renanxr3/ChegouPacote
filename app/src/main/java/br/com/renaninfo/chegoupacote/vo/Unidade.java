package br.com.renaninfo.chegoupacote.vo;

import com.google.firebase.database.Exclude;

import java.util.List;

/**
 * Created by Renan on 27/03/2017.
 */

public class Unidade {

    private String id;
    private String nome;
    private List<String> usuarios;
//    private StatusUnidade status;

//    public enum StatusUnidade {
//        ATIVA, INATIVA;
//    }

//    public static StatusUnidade parseStatusUnidade(String sStatus) {
//        switch (sStatus) {
//            case "A": return StatusUnidade.ATIVA;
//            case "I": return StatusUnidade.INATIVA;
//            default:  return null;
//        }
//    }

//    public Unidade(String id, StatusUnidade status) {
//        this.id = id;
//        this.status = status;
//    }

    public Unidade() {

    }

    public Unidade(String id, List<String> usuarios) {
        this.id = id;
        this.usuarios = usuarios;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    //    public StatusUnidade getStatus() {
//        return status;
//    }
//
//    public void setStatus(StatusUnidade status) {
//        this.status = status;
//    }
}
