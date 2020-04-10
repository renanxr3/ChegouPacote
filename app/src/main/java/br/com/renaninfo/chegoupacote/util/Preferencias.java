package br.com.renaninfo.chegoupacote.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

public class Preferencias {

    public static final String PREFS_NAME = "RI_CHEGOUPACOTE";

    private Context contexto;
    private SharedPreferences preferences;

    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_USUARIO_ID = "usuario.id";
    private final String CHAVE_USUARIO_EMAIL = "usuario.email";
    private final String CHAVE_USUARIO_EMAIL_BASE64 = "usuario.emailb64";
    private final String CHAVE_USUARIO_NOME = "usuario.nome";
    private final String CHAVE_USUARIO_NIVEL = "usuario.nivel";
    private final String CHAVE_USUARIO_UNIDADES = "usuario.unidades";

    public Preferencias( Context contextoParametro){
        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(PREFS_NAME, MODE );
        editor = preferences.edit();
    }

    public void salvarDados(String idUsuario, String emailUsuario, String emailUsuarioB64, String nomeUsuario, Usuario.NivelUsuario nivelUsuario, List<Unidade> unidades) {
        editor.putString(CHAVE_USUARIO_ID, idUsuario);
        editor.putString(CHAVE_USUARIO_EMAIL, emailUsuario);
        editor.putString(CHAVE_USUARIO_EMAIL_BASE64, emailUsuarioB64);
        editor.putString(CHAVE_USUARIO_NOME, nomeUsuario);
        editor.putString(CHAVE_USUARIO_NIVEL, nivelUsuario.toString());

        Set<String> setUnidades = new HashSet<String>();
        for (Unidade item: unidades) {
            setUnidades.add(item.getId());
        }
        editor.putStringSet(CHAVE_USUARIO_UNIDADES, setUnidades);
        editor.commit();
    }

    public String getUsuarioId(){
        return preferences.getString(CHAVE_USUARIO_ID, null);
    }
    public String getUsuarioEmail(){
        return preferences.getString(CHAVE_USUARIO_EMAIL, null);
    }
    public String getUsuarioEmailBase64(){
        return preferences.getString(CHAVE_USUARIO_EMAIL_BASE64, null);
    }
    public String getUsuarioNome(){
        return preferences.getString(CHAVE_USUARIO_NOME, null);
    }

    public String getUsuarioNivel(){
        return preferences.getString(CHAVE_USUARIO_NIVEL, null);
    }
    public List<Unidade> getUsuarioUnidades(){
        ArrayList<Unidade> retorno = new ArrayList<>();
        Set<String> setUnidades = preferences.getStringSet(CHAVE_USUARIO_UNIDADES, null);
        for (String itemUnidade: setUnidades) {
            retorno.add(new Unidade(itemUnidade, new ArrayList<String>()));
        }
        return retorno;
    }

    public void loadUsuarioLogado() {
        Usuario usuarioLogado = UsuarioLogado.getInstance();
        usuarioLogado.setId(getUsuarioId());
        usuarioLogado.setNome(getUsuarioNome());
        usuarioLogado.setNivel(Usuario.parseNivelUsuario(getUsuarioNivel()));
        usuarioLogado.setEmail(getUsuarioEmail());
        usuarioLogado.setUnidades(getUsuarioUnidades());
    }
}
