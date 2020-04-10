package br.com.renaninfo.chegoupacote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.renaninfo.chegoupacote.model.EntregadorModel;
import br.com.renaninfo.chegoupacote.model.UnidadeModel;
import br.com.renaninfo.chegoupacote.model.UsuarioModel;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;

public class UnidadeEditActivity extends AppCompatActivity {

    public static final String ACTION = "action";
    public static final String ACTION_EDIT = "action_edit";
    public static final String ACTION_ADD = "action_add";

    private EditText txtNome;
    private ListView elvUsuarios;
    private List<Usuario> listaUsuarios;
    private List<String> listaIdUsuarios;
    private ArrayAdapter<Usuario> arrayAdapterUnidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNome = (EditText) findViewById(R.id.txtNome);
        elvUsuarios = (ListView) findViewById(R.id.elvUsuarios);

//        listaUsuarios = UsuarioModel.getListaUsuarios();
        listaUsuarios = new ArrayList<>();
        listaIdUsuarios = new ArrayList<>();
//        for (Usuario usuario: listaUsuarios) {
//            listaIdUsuarios.add(usuario.getId());
//        }

        arrayAdapterUnidades = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listaUsuarios) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("Nome: " + listaUsuarios.get(position).getNome() + " (" + listaUsuarios.get(position).getNivel() + ")");
                text2.setText("Email: " + listaUsuarios.get(position).getEmail());
                return view;
            }
        };
        elvUsuarios.setAdapter(arrayAdapterUnidades);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarEdicao(view);
            }
        });
    }

    private void salvarEdicao(View view) {
        Unidade unidade = new Unidade(txtNome.getText().toString(), listaIdUsuarios);
        UnidadeModel.addUnidade(unidade);
        Snackbar.make(view, "Unidade criada com sucesso", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                Intent returnIntent = new Intent();
//                setResult(MainActivity.REQUEST_CODE_UNIDADE_ADD_SUCCESS, returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unidade_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_salvar) {
            salvarEdicao(getCurrentFocus());
            return true;
        } else if(id == R.id.action_procurar_usuario) {
            Intent intent = new Intent(getBaseContext(), UsuarioSearchActivity.class);
            intent.putExtra("result", "Success");
            this.startActivityForResult(intent, MainActivity.REQUEST_CODE_USUARIO_SEARCH);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.REQUEST_CODE_USUARIO_SEARCH) {
            if(resultCode == MainActivity.REQUEST_CODE_USUARIO_SEARCH_SUCCESS){
                Log.d(MainActivity.TAG, "REQUEST_CODE_USUARIO_SEARCH_SUCCESS:");

                String idUsuarioSelecionado = data.getStringExtra("idUsuarioSelecionado");
                String nomeUsuarioSelecionado = data.getStringExtra("nomeUsuarioSelecionado");
                String emailUsuarioSelecionado = data.getStringExtra("emailUsuarioSelecionado");

                Log.d(MainActivity.TAG, "Usuario selecionado: " + idUsuarioSelecionado + " | " + nomeUsuarioSelecionado + " | " + emailUsuarioSelecionado);

                Toast.makeText(getApplicationContext(),"Selecionou o usuario " + idUsuarioSelecionado,Toast.LENGTH_SHORT).show();

                listaUsuarios.add(new Usuario(idUsuarioSelecionado, emailUsuarioSelecionado, nomeUsuarioSelecionado));
                listaIdUsuarios.add(idUsuarioSelecionado);
                arrayAdapterUnidades.notifyDataSetChanged();
            }
            if (resultCode == MainActivity.REQUEST_CODE_USUARIO_SEARCH_ERROR) {
                //Write your code if there's no result
                Log.d(MainActivity.TAG, "REQUEST_CODE_USUARIO_SEARCH_SUCCESS:");
                Toast.makeText(getApplicationContext(),"NAO Selecionou o usuario",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
