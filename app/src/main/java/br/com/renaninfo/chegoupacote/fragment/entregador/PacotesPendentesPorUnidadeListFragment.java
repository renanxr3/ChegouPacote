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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.UnidadeEditActivity;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.Unidade;

/**
 * A simple {@link Fragment} subclass.
 */
public class PacotesPendentesPorUnidadeListFragment extends Fragment {

//    private ChildEventListener unidadesDoCondominioChildEventListener;
    private ValueEventListener unidadesDoCondominioValueEventListener;
    private DatabaseReference db;

    private ArrayList<Unidade> listaUnidades;

    private ArrayAdapter<Unidade> arrayAdapterUnidades;


    public PacotesPendentesPorUnidadeListFragment() {
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

        rootView = inflater.inflate(R.layout.fragment_entregador_listaunidade, container, false);

        final ArrayList<Unidade> listaUnidades = PacoteModel.getUnidadesComPacotesPendentes();
        final ArrayList<Pacote>  listaPacotesDaUnidade = PacoteModel.getPacotesPendentesCondominio();

        String NAME = "NAME";
        final List<String> group = new ArrayList<>();
        final List<List<String>> child = new ArrayList<>();
        for (Unidade item: listaUnidades) {
            group.add("Unidade: " + item.getId());

            List<String> childChild = new ArrayList<>();
            for (Pacote item2: listaPacotesDaUnidade) {
                childChild.add(item2.getId());
            }
            child.add(childChild);
        }

        ExpandableListAdapter mAdapter;
        ExpandableListView elv_pacotes_unidades = (ExpandableListView) rootView.findViewById(R.id.lv_pacotes_unidades);

        final List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < group.size(); i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, group.get(i));

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < child.get(i).size(); j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, child.get(i).get(j));
            }
            childData.add(children);
        }

        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(getContext(), groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME }, new int[] { android.R.id.text1 },
                childData, android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME }, new int[] { android.R.id.text1 });
        elv_pacotes_unidades.setAdapter(mAdapter);

//                    lvPacotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            final Pacote pacoteSelecionado = listaPacotes.get(position);
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage(R.string.tem_certeza_atualizar_status_pacote)
//                                    .setCancelable(false)
//                                    .setPositiveButton("Entregue", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            Toast.makeText(getActivity(), "Marcou como Recebido: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
//                                        }
//                                    })
//                                    .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            Toast.makeText(getActivity(), "Cancelou: " + pacoteSelecionado.getId(), Toast.LENGTH_LONG).show();
//                                        }
//                                    })
//                            ;
//                            AlertDialog alert = builder.create();
//                            alert.show();
//                        }
//                    });

        elv_pacotes_unidades.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        groupData.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        elv_pacotes_unidades.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        groupData.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        elv_pacotes_unidades.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext(),
                        groupData.get(groupPosition)
                                + " -> "
//                                            + child.get(groupData.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT
                                + child.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT
                )
                        .show();
                return false;
            }
        });

        return rootView;
    }

}
