package br.com.renaninfo.chegoupacote.vo;

/**
 * Created by Renan on 27/03/2017.
 */

public class UsuarioLogado {

    private static Usuario usuarioLogado;

    public static Usuario getInstance(){
        if( usuarioLogado == null ){
            usuarioLogado = new Usuario();
        }
        return usuarioLogado;
    }

}
