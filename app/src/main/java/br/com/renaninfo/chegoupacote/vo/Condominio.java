package br.com.renaninfo.chegoupacote.vo;

/**
 * Created by Renan on 27/03/2017.
 */
public class Condominio {

    private String id;
    private String nome;
    private StatusCondominio status;

    public enum StatusCondominio {
        ATIVA, INATIVA;
    }

    public static StatusCondominio parseStatusCondominio(String sStatus) {
        switch (sStatus) {
            case "A": return StatusCondominio.ATIVA;
            case "I": return StatusCondominio.INATIVA;
            default:  return null;
        }
    }

    public Condominio() {

    }

    public Condominio(String id, String nome, StatusCondominio status) {
        this.id = id;
        this.nome = nome;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusCondominio getStatus() {
        return status;
    }

    public void setStatus(StatusCondominio status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
