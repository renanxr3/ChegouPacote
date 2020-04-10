package br.com.renaninfo.chegoupacote.fragment.admin;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.EntregadorActivity;
import br.com.renaninfo.chegoupacote.MainActivity;
import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.UsuarioActivity;
import br.com.renaninfo.chegoupacote.UsuarioSearchActivity;
import br.com.renaninfo.chegoupacote.model.EntregadorModel;
import br.com.renaninfo.chegoupacote.model.UsuarioModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.util.Constants;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntregadorListFragment extends Fragment {

    private ValueEventListener entregadoresDoCondominioValueEventListener;
    private DatabaseReference db;
    private ArrayList<Entregador> listaEntregadores;
    private ArrayAdapter<Entregador> arrayAdapterEntregadores;

    public EntregadorListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        db.addValueEventListener(entregadoresDoCondominioValueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        db.removeEventListener(entregadoresDoCondominioValueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;

        rootView = inflater.inflate(R.layout.fragment_admin_entregadores, container, false);

//                    ArrayList<Entregador> listaEntregadores = EntregadorModel.getEntregadoresDoCondominio();
        listaEntregadores = new ArrayList<>();

        ListView lvEntregadores = (ListView) rootView.findViewById(R.id.lv_entregadores);
        arrayAdapterEntregadores = new ArrayAdapter<Entregador>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, listaEntregadores) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("Entregador: " + (listaEntregadores.get(position).getIdUsuario()!=null?
                        (listaEntregadores.get(position).getUsuario()!=null?listaEntregadores.get(position).getUsuario().getNome():"<usuario null>"):
                        listaEntregadores.get(position).getIdUsuario().substring(0,10)));
//                            text2.setText("Status: " + listaUnidades.get(position).getStatus());
                return view;
            }
        };
        lvEntregadores.setAdapter(arrayAdapterEntregadores);




//        if (Constants.TESTING) {
//            Usuario usuarioEntregador1 = UsuarioModel.entregadorTeste;
//            Entregador entregador = new Entregador(usuarioEntregador1);
//            listaEntregadores.add(entregador);
//        } else {
            db = ConfiguracaoFirebase.getFirebase();
            db = db.child("entregadores");

            entregadoresDoCondominioValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listaEntregadores.clear();
                    for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                        final Entregador entregador = dados.getValue(Entregador.class);
                        listaEntregadores.add( entregador );

                        DatabaseReference db2 = ConfiguracaoFirebase.getFirebase();
                        db2 = db2.child("usuarios").child(entregador.getIdUsuario());
                        db2.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    entregador.setUsuario(dataSnapshot.getValue(Usuario.class));
                                    arrayAdapterEntregadores.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                        );
                    }
                    arrayAdapterEntregadores.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
//        }




        lvEntregadores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Entregador itemSelecionado = listaEntregadores.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.tem_certeza_excluir_entregador)
                        .setCancelable(false)
                        .setPositiveButton("SIM, EXCLUIR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Excluiu: " + itemSelecionado.getIdUsuario(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Cancelou: " + itemSelecionado.getIdUsuario(), Toast.LENGTH_LONG).show();
                            }
                        })
                ;
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

        lvEntregadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entregador itemSelecionado = listaEntregadores.get(position);

                Intent intent = new Intent(getActivity(), UsuarioSearchActivity.class);
//                            intent.putExtra(UsuarioSearchActivity.ACTION, UsuarioSearchActivity.ACTION_EDIT);
                startActivity(intent);

                Snackbar.make(view, "Editar Entregador: " + itemSelecionado.getIdUsuario(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_admin_entregadores_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_adicionar){
            Snackbar.make(getView(), "Associar Entregador", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            Intent intentUnidade = new Intent(getActivity(), UsuarioSearchActivity.class);
            intentUnidade.putExtra("result", "Success");
            this.startActivityForResult(intentUnidade, MainActivity.REQUEST_CODE_USUARIO_SEARCH);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.REQUEST_CODE_USUARIO_SEARCH) {
            if(resultCode == MainActivity.REQUEST_CODE_USUARIO_SEARCH_SUCCESS){
                String idUsuarioSelecionado = data.getStringExtra("idUsuarioSelecionado");
                Log.d(MainActivity.TAG, "REQUEST_CODE_USUARIO_SEARCH_SUCCESS:");

                Toast.makeText(getContext(),"Selecionou o usuario",Toast.LENGTH_SHORT).show();

                Entregador entregador = new Entregador(idUsuarioSelecionado);
                EntregadorModel.salvarEntregador(entregador);
            }
            if (resultCode == MainActivity.REQUEST_CODE_USUARIO_SEARCH_ERROR) {
                //Write your code if there's no result
                Log.d(MainActivity.TAG, "REQUEST_CODE_USUARIO_SEARCH_SUCCESS:");
                Toast.makeText(getContext(),"NAO Selecionou o usuario",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
