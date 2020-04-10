package br.com.renaninfo.chegoupacote.fragment.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.MainActivity;
import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.UnidadeEditActivity;
import br.com.renaninfo.chegoupacote.UsuarioSearchActivity;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Unidade;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnidadeListFragment extends Fragment {

//    private ChildEventListener unidadesDoCondominioChildEventListener;
    private ValueEventListener unidadesDoCondominioValueEventListener;
    private DatabaseReference db;

    private ArrayList<Unidade> listaUnidades;

    private ArrayAdapter<Unidade> arrayAdapterUnidades;


    public UnidadeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        rootView = inflater.inflate(R.layout.fragment_admin_unidades, container, false);

        // final ArrayList<Unidade> listaUnidades = UnidadeModel.getUnidadesDoCondominio();
        listaUnidades = new ArrayList<>();

        ListView lvUnidades = (ListView) rootView.findViewById(R.id.lv_unidades);
        arrayAdapterUnidades = new ArrayAdapter<Unidade>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, listaUnidades) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("Unidade: " + listaUnidades.get(position).getNome());
//                            text2.setText("Status: " + listaUnidades.get(position).getStatus());
                return view;
            }
        };
        lvUnidades.setAdapter(arrayAdapterUnidades);

        db = ConfiguracaoFirebase.getFirebase();
        db = db.child("unidades");
        unidadesDoCondominioValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaUnidades.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren() ){
                    Unidade unidade = dados.getValue(Unidade.class);
                    unidade.setId(dados.getKey());
                    listaUnidades.add(unidade);
                }
                arrayAdapterUnidades.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

//        unidadesDoCondominioChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                listaUnidades.add(dataSnapshot.getValue(Unidade.class));
//                arrayAdapterUnidades.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////                listaUnidades.set(listaUnidades.indexOf(dataSnapshot.get) ,dataSnapshot.getValue(Unidade.class));
//                arrayAdapterUnidades.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                listaUnidades.remove(dataSnapshot.getValue(Unidade.class));
//                arrayAdapterUnidades.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };

        lvUnidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Unidade itemSelecionado = listaUnidades.get(position);

                Intent intent = new Intent(getActivity(), UnidadeEditActivity.class);
                intent.putExtra(UnidadeEditActivity.ACTION, UnidadeEditActivity.ACTION_EDIT);
                startActivity(intent);

                Snackbar.make(view, "Editar Unidade: " + itemSelecionado.getId(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_admin_unidades_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_adicionar){
            Intent intentUnidade = new Intent(getActivity(), UnidadeEditActivity.class);
//            intentUnidade.putExtra("result", "Success");
            startActivity(intentUnidade);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
