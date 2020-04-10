package br.com.renaninfo.chegoupacote.fragment.entregador;


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
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.Unidade;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdicionarPacoteFragment extends Fragment {

    private DatabaseReference db;
    private ArrayList<Unidade> listaUnidades;
    private List<String> listaNomeUnidades;

    private Spinner cmbUnidade;
    private EditText txtUnidade;
    private EditText txtObservacao;

    private ArrayAdapter<String> adapterCmb;

    private Unidade unidadeSelecionada;

    public AdicionarPacoteFragment() {
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
//        db.addValueEventListener(unidadesDoCondominioValueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
//        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
//        db.removeEventListener(unidadesDoCondominioValueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entregador_addpacote, container, false);

        cmbUnidade = (Spinner) rootView.findViewById(R.id.cmbUnidade);
        txtUnidade = (EditText) rootView.findViewById(R.id.txtUnidade);
        txtObservacao = (EditText) rootView.findViewById(R.id.txtObservacao);

        listaUnidades = new ArrayList<>();
        listaNomeUnidades = new ArrayList<>();

        adapterCmb = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listaNomeUnidades);
        cmbUnidade.setAdapter(adapterCmb);

        db = ConfiguracaoFirebase.getFirebase().child("unidades");
        db.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listaUnidades.clear();
                        listaNomeUnidades.clear();
                        for (DataSnapshot dados: dataSnapshot.getChildren() ){
                            Unidade unidade = dados.getValue(Unidade.class);
                            unidade.setId(dados.getKey());
                            listaUnidades.add(unidade);
                            listaNomeUnidades.add(unidade.getId());
                        }
                        if (!listaUnidades.isEmpty()) {
                            listaNomeUnidades.add(0, "Selecione"); // TODO 0,
                        }
                        adapterCmb.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        cmbUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) {
                if (position > 0) {
                    int positionReal = position - 1;
                    unidadeSelecionada = listaUnidades.get(positionReal);
                    Snackbar.make(getView(), "Selecionou Unidade: " + listaNomeUnidades.get(positionReal), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    unidadeSelecionada = null;
                    Snackbar.make(getView(), "Nenhum Selecionado (Selecione)", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                unidadeSelecionada = null;
                Snackbar.make(getView(), "Nenhum Selecionado", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_entregador_addpacote, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_salvar){
            salvarEdicao();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvarEdicao() {
        String udUnidadeInformada = getIdUnidadeFromNome();

        if ((unidadeSelecionada == null) && ("".equals(txtUnidade.getText().toString()))) {
            Snackbar.make(getView(), "Por favor informe ou selecione uma unidade", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        } else if ((unidadeSelecionada == null) && (!"".equals(txtUnidade.getText().toString())) && (udUnidadeInformada == null)) {
            Snackbar.make(getView(), "A unidade informada não existe", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        Pacote pacoteParaAdicionar = new Pacote();
        if (unidadeSelecionada != null) {
            pacoteParaAdicionar.setNomeUnidade(unidadeSelecionada.getNome());
            pacoteParaAdicionar.setIdUnidade(unidadeSelecionada.getId());
        } else if ((!"".equals(txtUnidade.getText().toString())) && (udUnidadeInformada != null)) {
            pacoteParaAdicionar.setNomeUnidade(txtUnidade.getText().toString());
            pacoteParaAdicionar.setIdUnidade(udUnidadeInformada);
        }
        pacoteParaAdicionar.setObs(txtObservacao.getText().toString());

        PacoteModel.addPacote(pacoteParaAdicionar);
        Snackbar.make(getView(), "Pacote adicionado com sucesso", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        limpaView();
        // carregaView();
    }

    private String getIdUnidadeFromNome() {
        String resultado = null;
        for (Unidade unidade: listaUnidades) {
            if (unidade.getNome().equals(txtUnidade.getText().toString())) {
                resultado = unidade.getId();
            }
        }
        return resultado;
    }

    private void limpaView() {
        cmbUnidade.setSelection(-1);
        txtUnidade.setText("");
        txtObservacao.setText("");
    }

}
