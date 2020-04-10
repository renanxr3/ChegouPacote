package br.com.renaninfo.chegoupacote.vo;

/**
 * Created by Renan on 27/03/2017.
 */

public class PacoteHistorico {

    private String id;
    private String unidade;
    private String obs;
    private StatusPacote status;

    public enum StatusPacote {
        PENDENTE, ENTREGUE, RECEBIDO
    }

    public StatusPacote parseStatusPacote(String sStatus) {
        switch (sStatus) {
            case "P":
            case "PENDENTE": return StatusPacote.PENDENTE;
            case "E":
            case "ENTREGUE": return StatusPacote.ENTREGUE;
            case "R":
            case "RECEBIDO": return StatusPacote.RECEBIDO;
            default:  return null;
        }
    }

    public static String getStatusAsString(StatusPacote status) {
        switch (status) {
            case PENDENTE: return "Pendente Retirada";
            case ENTREGUE: return "Marcado como Entregue";
            case RECEBIDO: return "Recebido com Sucesso";
            default:       return null;
        }
    }

    public String getStatusAsString() {
        switch (status) {
            case PENDENTE: return "Pendente Retirada";
            case ENTREGUE: return "Marcado como Entregue";
            case RECEBIDO: return "Recebido com Sucesso";
            default:       return null;
        }
    }

    public PacoteHistorico() {

    }

    public PacoteHistorico(String id, String unidade, String obs, StatusPacote status) {
        this.id = id;
        this.unidade = unidade;
        this.obs = obs;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public StatusPacote getStatus() {
        return status;
    }

    public void setStatus(StatusPacote status) {
        this.status = status;
    }
}
