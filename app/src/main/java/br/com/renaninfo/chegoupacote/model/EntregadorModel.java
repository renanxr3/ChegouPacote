package br.com.renaninfo.chegoupacote.model;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.LoginActivity;
import br.com.renaninfo.chegoupacote.MainActivity;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.util.Constants;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;

/**
 * Created by Renan on 27/03/2017.
 */
public class EntregadorModel {

    public static void salvarEntregador(Entregador entregador) {
        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        db.child("entregadores").push().setValue(entregador);
    }

}
