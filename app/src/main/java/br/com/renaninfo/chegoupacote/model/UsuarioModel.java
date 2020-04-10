package br.com.renaninfo.chegoupacote.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;

/**
 * Created by Renan on 27/03/2017.
 */

public class UsuarioModel {

//    public static Usuario usuarioTeste = new Usuario("usu1234", Usuario.NivelUsuario.USUARIO, "usuario1@gmail.com", "Usuario 1", new ArrayList<Unidade>());
//    public static Usuario adminTeste = new Usuario("admin1234", Usuario.NivelUsuario.ADMIN, "admin1@gmail.com", "Admin 1", new ArrayList<Unidade>());
//    public static Usuario entregadorTeste = new Usuario("entregador1234", Usuario.NivelUsuario.ENTREGADOR, "entregador1@gmail.com", "Entregador 1", new ArrayList<Unidade>());

//    public static Usuario getUsuarioLogado() {
//        return adminTeste;
//    }

    public static ArrayList<Usuario> getListaUsuarios() {
        final ArrayList<Usuario> retorno = new ArrayList<>();

        DatabaseReference db = ConfiguracaoFirebase.getFirebase().child("usuarios");
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot usuarioChild: dataSnapshot.getChildren()) {
                            Usuario usuario = usuarioChild.getValue(Usuario.class);
                            usuario.setId(usuarioChild.getKey());
                            retorno.add(usuario);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        return retorno;
    }

    public static ArrayList<Usuario> getListaUsuariosByNome(String sNome) {
        ArrayList<Usuario> retorno = new ArrayList<>();
        ArrayList<Usuario> allUsers = getListaUsuarios();

        if ((sNome == null) || ("".equals(sNome))) {
            retorno = allUsers;
        } else {
            for (Usuario item: allUsers) {
                if (item.getNome().contains(sNome)) {
                    retorno.add(item);
                }
            }
        }
        return retorno;
    }


    public static Usuario getUsuarioById(String idUsuario) {
        final Usuario[] usuario = new Usuario[1];
        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        db = db.child("usuarios").child(idUsuario);
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        usuario[0] = dataSnapshot.getValue(Usuario.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        if (usuario.length > 0) {
            return usuario[0];
        } else {
            return null;
        }
    }
}
