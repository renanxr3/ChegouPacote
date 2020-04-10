package br.com.renaninfo.chegoupacote.model;

import java.util.HashMap;

import br.com.renaninfo.chegoupacote.vo.AdminStats;

/**
 * Created by Renan on 27/03/2017.
 */
public class StatsModel {

    public static HashMap<String, String> getStatsDoCondominio() {
        HashMap<String, String> retorno = new HashMap<>();
        retorno.put(AdminStats.STAT_UNIDADES_SUM,       "123");
        retorno.put(AdminStats.STAT_USUARIOS_SUM,       "456");
        retorno.put(AdminStats.STAT_ENTREGADORES_SUM,   "789");

        retorno.put(AdminStats.STAT_PACOTES_PENDENTES1D_SUM,   	"1");
        retorno.put(AdminStats.STAT_PACOTES_ENTREGUES1D_SUM,   	"11");
        retorno.put(AdminStats.STAT_PACOTES_PENDENTES7D_SUM,   	"7");
        retorno.put(AdminStats.STAT_PACOTES_ENTREGUES7D_SUM,   	"77");
        retorno.put(AdminStats.STAT_PACOTES_PENDENTES1M_SUM,   	"30");
        retorno.put(AdminStats.STAT_PACOTES_ENTREGUES1M_SUM,   	"330");
        retorno.put(AdminStats.STAT_PACOTES_PENDENTES3M_SUM,   	"90");
        retorno.put(AdminStats.STAT_PACOTES_ENTREGUES3M_SUM,   	"990");
        retorno.put(AdminStats.STAT_PACOTES_PENDENTES6M_SUM,   	"180");
        retorno.put(AdminStats.STAT_PACOTES_ENTREGUES6M_SUM,   	"1180");
        retorno.put(AdminStats.STAT_PACOTES_PENDENTES12M_SUM,   "365");
        retorno.put(AdminStats.STAT_PACOTES_ENTREGUES12M_SUM,   "3365");

        return retorno;
    }

}
