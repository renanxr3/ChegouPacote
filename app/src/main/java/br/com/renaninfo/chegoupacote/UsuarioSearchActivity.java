package br.com.renaninfo.chegoupacote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.renaninfo.chegoupacote.model.UsuarioModel;
import br.com.renaninfo.chegoupacote.vo.Unidade;
import br.com.renaninfo.chegoupacote.vo.Usuario;

public class UsuarioSearchActivity extends AppCompatActivity {

    protected ArrayAdapter<Usuario> arrayAdapterUnidades;
    protected EditText txtNome;
    protected Button btnPesquisar;
    protected ListView lvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_search);

        txtNome = (EditText) findViewById(R.id.txtNome);
        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        lvUsuarios = (ListView) findViewById(R.id.lvUsuarios);

        btnPesquisar.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<Usuario> listaUsuarios = UsuarioModel.getListaUsuariosByNome(txtNome.getText().toString());
                    arrayAdapterUnidades.notifyDataSetChanged();
                }
            }
        );

        final List<Usuario> listaUsuarios = UsuarioModel.getListaUsuarios();

        ArrayAdapter<Usuario> arrayAdapterUnidades = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listaUsuarios) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText("Nome: " + listaUsuarios.get(position).getNome() + " (" + listaUsuarios.get(position).getNivel() + ")");
                text2.setText("Email: " + listaUsuarios.get(position).getEmail());
                return view;
            }
        };
        lvUsuarios.setAdapter(arrayAdapterUnidades);

        lvUsuarios.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Usuario usuarioSelecionado = listaUsuarios.get(position);

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("idUsuarioSelecionado", usuarioSelecionado.getId());
                        returnIntent.putExtra("nomeUsuarioSelecionado", usuarioSelecionado.getNome());
                        returnIntent.putExtra("emailUsuarioSelecionado", usuarioSelecionado.getEmail());
                        setResult(MainActivity.REQUEST_CODE_USUARIO_SEARCH_SUCCESS, returnIntent);
                        finish();
                    }
                }
        );
    }
}
