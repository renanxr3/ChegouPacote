package br.com.renaninfo.chegoupacote.fragment.usuario;


import android.content.DialogInterface;
import android.os.Bundle;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeusPacotesListFragment extends Fragment {

//    private DatabaseReference db;
    private Query query;
    private ValueEventListener pacotesPendentesValueEventListener;
    private ArrayList<Pacote> listaPacotes;
    private ArrayAdapter<Pacote> arrayAdapterPacotes;

    public MeusPacotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        query.addValueEventListener(pacotesPendentesValueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
//        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
        query.removeEventListener(pacotesPendentesValueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usuario_pacotes, container, false);

//        listaPacotes = PacoteModel.getPacotesPendentesUsuario();
        listaPacotes = new ArrayList<>();

        ListView lvUsuarioPacotes = (ListView) rootView.findViewById(R.id.lv_usuario_pacotes);
        arrayAdapterPacotes = new ArrayAdapter<Pacote>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaPacotes) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("["+listaPacotes.get(position).getNomeUnidade()+"] Pacote: " + listaPacotes.get(position).getId() + " (" + PacoteHistorico.getStatusAsString(PacoteHistorico.StatusPacote.PENDENTE) + ")");
                text2.setText("Por Entregador em 01/01/1980 00:00");
                return view;
            }
        };
        lvUsuarioPacotes.setAdapter(arrayAdapterPacotes);

        query = ConfiguracaoFirebase.getFirebase().child("pacotes").orderByChild("status").equalTo("PEN").limitToFirst(10);
        pacotesPendentesValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPacotes.clear();
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    Pacote pacote = dados.getValue(Pacote.class);
                    pacote.setId(dados.getKey());
                    listaPacotes.add(pacote);
                }
                arrayAdapterPacotes.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        lvUsuarioPacotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Pacote pacoteSelecionado = listaPacotes.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.tem_certeza_atualizar_status_pacote)
                        .setCancelable(false)
                        .setPositiveButton("Recebido", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PacoteModel.marcarComoRecebido(pacoteSelecionado);
                                Toast.makeText(getActivity(), "Marcou como Recebido: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Pendente", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PacoteModel.marcarComoPendente(pacoteSelecionado);
                                Toast.makeText(getActivity(), "Marcou como Pendente: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
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
