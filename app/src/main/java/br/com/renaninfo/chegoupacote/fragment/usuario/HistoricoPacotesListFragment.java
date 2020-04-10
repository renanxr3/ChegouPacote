package br.com.renaninfo.chegoupacote.fragment.usuario;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.UnidadeEditActivity;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

public class HistoricoPacotesListFragment extends Fragment {

    private ValueEventListener pacotesPententesValueEventListener;
    private DatabaseReference db;
    private ArrayList<Unidade> listaUnidades;
    private ArrayAdapter<Unidade> arrayAdapterUnidades;
    private ArrayList<PacoteHistorico> listaPacotesHistorico;
    private ArrayAdapter<PacoteHistorico> arrayAdapterHistorico;

    public HistoricoPacotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
//        db.addChildEventListener(unidadesDoCondominioChildEventListener);
        db.addValueEventListener(pacotesPententesValueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
//        db.removeEventListener(unidadesDoCondominioChildEventListener);
        db.removeEventListener(pacotesPententesValueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usuario_pacotes, container, false);

//        listaPacotesHistorico = PacoteModel.getPacotesHistoricoUsuario(UsuarioLogado.getInstance().getId());
        listaPacotesHistorico = new ArrayList<>();

        ListView lvUsuarioPacotesHistorico = (ListView) rootView.findViewById(R.id.lv_usuario_pacotes);
        arrayAdapterHistorico = new ArrayAdapter<PacoteHistorico>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaPacotesHistorico) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("["+listaPacotesHistorico.get(position).getUnidade()+"] Pacote: " + listaPacotesHistorico.get(position).getId() + " (" + listaPacotesHistorico.get(position).getStatusAsString() + ")");
                text2.setText("R: Entregador em 01/01/1980 00:00 / E: Usuario em 01/01/1980 00:00");
                return view;
            }
        };
        lvUsuarioPacotesHistorico.setAdapter(arrayAdapterHistorico);

        db = ConfiguracaoFirebase.getFirebase();
        db = db.child("old_usuario").child(UsuarioLogado.getInstance().getId()).child("pacotes");

        pacotesPententesValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPacotesHistorico.clear();
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    listaPacotesHistorico.add( dados.getValue(PacoteHistorico.class) );
                }
                arrayAdapterHistorico.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        lvUsuarioPacotesHistorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PacoteHistorico pacoteSelecionado = listaPacotesHistorico.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.tem_certeza_atualizar_status_pacote)
                        .setCancelable(false)
                        .setPositiveButton("MARCAR COMO PENDENTE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Marcou como Pendente: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Cancelou: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
                            }
                        })
                ;
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return rootView;
    }

}
