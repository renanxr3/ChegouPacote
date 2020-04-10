package br.com.renaninfo.chegoupacote.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

/**
 * Created by Renan on 27/03/2017.
 */
public class PacoteModel {

    public static ArrayList<Pacote> getPacotesPendentesUsuario() {
        final ArrayList<String> listaIdsPacotes = new ArrayList<>();
        final ArrayList<Pacote> retorno = new ArrayList<>();
        for (Unidade unidadeDoUsuario: UsuarioLogado.getInstance().getUnidades()) {
            DatabaseReference db = ConfiguracaoFirebase.getFirebase();
            db = db.child("unidades").child(unidadeDoUsuario.getId()).child("pacotes");
            db.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                                listaIdsPacotes.add(dados.getValue(String.class));
                            }
//                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
        }

        for (String idPacote: listaIdsPacotes) {
            DatabaseReference db = ConfiguracaoFirebase.getFirebase();
            db = db.child("pacotes").child(idPacote);
            db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Pacote pacote = dataSnapshot.getValue(Pacote.class);
                        retorno.add(pacote);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
            );
        }

        return retorno;
    }

    public static ArrayList<PacoteHistorico> getPacotesHistoricoUsuario(String idUsuario) {
        final ArrayList<PacoteHistorico> retorno = new ArrayList<>();
        final ArrayList<String> listaIdsPacotes = new ArrayList<>();

        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        db = db.child("old_usuario").child(idUsuario).child("pacotes");
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                            retorno.add(dados.getValue(PacoteHistorico.class));
                            listaIdsPacotes.add(dados.getKey());
                        }
//                            adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        return retorno;
    }

    public static ArrayList<Unidade> getUnidadesComPacotesPendentes() {
        final ArrayList<Unidade> retorno = new ArrayList<>();

//        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
//        db = db.child("upp");
//        db.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for ( DataSnapshot dados: dataSnapshot.getChildren() ){
//                            retorno.add(new Unidade(dados.getValue(String.class)));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                }
//        );
        return retorno;
    }

    public static ArrayList<Pacote> getPacotesPendentesCondominio() {
        final ArrayList<Pacote> retorno = new ArrayList<>();

        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        db = db.child("pacotes");
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                            Pacote pacote = dataSnapshot.getValue(Pacote.class);
                            retorno.add(pacote);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        return retorno;
    }

    public static ArrayList<PacoteHistorico> getPacotesHistoricoCondominio() {
        final ArrayList<PacoteHistorico> retorno = new ArrayList<>();
        final ArrayList<String> listaIdsPacotes = new ArrayList<>();

        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        db = db.child("old_pacotes");
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                            retorno.add(dados.getValue(PacoteHistorico.class));
                            listaIdsPacotes.add(dados.getKey());
                        }
//                            adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        return retorno;
    }

    public static void addPacote(Pacote pacoteParaAdicionar) {
        pacoteParaAdicionar.setStatus("PEN");

        DatabaseReference db = ConfiguracaoFirebase.getFirebase().child("pacotes").push();
        String pushed = db.getKey();
        db.setValue(pacoteParaAdicionar);

        ConfiguracaoFirebase.getFirebase().child("unidades").child(pacoteParaAdicionar.getIdUnidade()).child("pacotes").child(pushed).setValue(pacoteParaAdicionar);
    }

    public static void marcarComoRecebido(Pacote pacote) {
        DatabaseReference db = ConfiguracaoFirebase.getFirebase().child("pacotes").child(pacote.getId()).child("status");
        db.setValue("REC");
    }

    public static void marcarComoEntregue(Pacote pacote) {
        DatabaseReference db = ConfiguracaoFirebase.getFirebase().child("pacotes").child(pacote.getId()).child("status");
        db.setValue("ENT");
    }

    public static void marcarComoPendente(Pacote pacote) {
        DatabaseReference db = ConfiguracaoFirebase.getFirebase().child("pacotes").child(pacote.getId()).child("status");
        db.setValue("PEN");
    }

}
