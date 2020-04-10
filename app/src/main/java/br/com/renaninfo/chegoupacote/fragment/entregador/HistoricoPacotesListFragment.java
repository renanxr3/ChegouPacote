package br.com.renaninfo.chegoupacote.fragment.entregador;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.UnidadeEditActivity;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;
import br.com.renaninfo.chegoupacote.vo.Unidade;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoPacotesListFragment extends Fragment {

//    private ChildEventListener unidadesDoCondominioChildEventListener;
    private ValueEventListener unidadesDoCondominioValueEventListener;
    private DatabaseReference db;

    private ArrayList<Unidade> listaUnidades;

    private ArrayAdapter<Unidade> arrayAdapterUnidades;


    public HistoricoPacotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
//        db.addChildEventListener(unidadesDoCondominioChildEventListener);
        db.addValueEventListener(unidadesDoCondominioValueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
//        db.removeEventListener(unidadesDoCondominioChildEventListener);
        db.removeEventListener(unidadesDoCondominioValueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;

        rootView = inflater.inflate(R.layout.fragment_entregador_historicopacote, container, false);

        final ArrayList<PacoteHistorico> listaHistoricoPacotes = PacoteModel.getPacotesHistoricoCondominio();

        ListView lvHistoricoPacotes = (ListView) rootView.findViewById(R.id.lv_pacotes);
        ArrayAdapter<PacoteHistorico> arrayAdapterHistoricoPacotes = new ArrayAdapter<PacoteHistorico>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaHistoricoPacotes) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("Unidade: " + listaHistoricoPacotes.get(position).getUnidade());
                text2.setText("Status: " + listaHistoricoPacotes.get(position).getStatus());
                return view;
            }
        };
        lvHistoricoPacotes.setAdapter(arrayAdapterHistoricoPacotes);

        return rootView;
    }

}
