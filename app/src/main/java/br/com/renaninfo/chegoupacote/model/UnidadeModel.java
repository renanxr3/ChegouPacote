package br.com.renaninfo.chegoupacote.model;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.Unidade;

/**
 * Created by Renan on 27/03/2017.
 */
public class UnidadeModel {

    public static void addUnidade(Unidade unidade) {
        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        db.child("unidades").push().setValue(unidade);
    }

}
