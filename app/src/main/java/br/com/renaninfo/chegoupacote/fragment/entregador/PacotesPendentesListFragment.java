package br.com.renaninfo.chegoupacote.fragment.entregador;


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
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

/**
 * A simple {@link Fragment} subclass.
 */
public class PacotesPendentesListFragment extends Fragment {

//    private ChildEventListener unidadesDoCondominioChildEventListener;
    private ValueEventListener pacotesPententesValueEventListener;
    private DatabaseReference db;

    private ArrayList<Pacote> listaPacotes;

    private ArrayAdapter<Pacote> arrayAdapterPacotes;


    public PacotesPendentesListFragment() {
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
//        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
//        db.removeEventListener(unidadesDoCondominioChildEventListener);
        db.removeEventListener(pacotesPententesValueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entregador_listapacote, container, false);

//        final ArrayList<Pacote> listaPacotes = PacoteModel.getPacotesPendentesCondominio();
        listaPacotes = new ArrayList<>();

        ListView lvPacotes = (ListView) rootView.findViewById(R.id.lv_pacotes);
        arrayAdapterPacotes = new ArrayAdapter<Pacote>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaPacotes) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("Unidade: " + listaPacotes.get(position).getNomeUnidade());
                text2.setText("Status: " + PacoteHistorico.getStatusAsString(PacoteHistorico.StatusPacote.PENDENTE));
                return view;
            }
        };
        lvPacotes.setAdapter(arrayAdapterPacotes);

        db = ConfiguracaoFirebase.getFirebase();
        db = db.child("pacotes");

        pacotesPententesValueEventListener = new ValueEventListener() {
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

        lvPacotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Pacote pacoteSelecionado = listaPacotes.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.tem_certeza_atualizar_status_pacote)
                        .setCancelable(false)
                        .setPositiveButton("Entregue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PacoteModel.marcarComoEntregue(pacoteSelecionado);
                                Toast.makeText(getActivity(), "Marcou como Recebido: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
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
