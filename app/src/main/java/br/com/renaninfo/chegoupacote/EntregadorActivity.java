package br.com.renaninfo.chegoupacote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.renaninfo.chegoupacote.adapter.EntregadorTabAdapter;
import br.com.renaninfo.chegoupacote.component.SlidingTabLayout;
import br.com.renaninfo.chegoupacote.model.EntregadorModel;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.model.StatsModel;
import br.com.renaninfo.chegoupacote.model.UnidadeModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.AdminStats;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;
import br.com.renaninfo.chegoupacote.vo.Unidade;

public class EntregadorActivity extends AppCompatActivity {

    private EntregadorTabAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);

        slidingTabLayout.setDistributeEvenly(true);

        mSectionsPagerAdapter = new EntregadorTabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        slidingTabLayout.setViewPager(mViewPager);

        mViewPager.setCurrentItem(1);

//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View fragmentoAtual = view.getRootView();
//                final Context contexto = view.getContext();
//
//                switch (mViewPager.getCurrentItem()) {
//                    case 0:
//                        Snackbar.make(view, "Salvar ou Remover este botão", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//                        // Add pacote em Unidades com Pacotes Pendentes
//                        DatabaseReference db = ConfiguracaoFirebase.getFirebase();
//                        db = db.child("upp").child(unidadeSelecionada);
//
//                        break;
//                    case 1:
//                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contexto);
//                        alertDialog.setTitle("Pesquisar");
//                        alertDialog.setMessage("Informe a Unidade");
//
//                        final EditText input = new EditText(contexto);
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.MATCH_PARENT);
//                        input.setLayoutParams(lp);
//                        alertDialog.setView(input);
//                        alertDialog.setIcon(R.drawable.common_full_open_on_phone);
//
//                        alertDialog.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(contexto, "Pesquisar Unidade!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                        alertDialog.setNegativeButton("NO",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        alertDialog.show();
//                        break;
//                    case 2:
//                        // Esconde botão
////                    fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
////                    fab.setVisibility(View.INVISIBLE);
//                        Snackbar.make(view, "Esconder este botão", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                        break;
//                    case 3:
//                        Snackbar.make(view, "Replicar código para pesquisar unidade", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                        break;
//                    default:
//                        Log.d(MainActivity.TAG, "default");
//                        break;
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.action_modo_entregador);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Snackbar.make(this.getCurrentFocus(), "TODO: Settings", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        } else if (id == R.id.action_modo_usuario) {
            Intent intent = new Intent(this, UsuarioActivity.class);
            intent.putExtra("result", "Success");
            finish();
            startActivity(intent);
        } else if (id == R.id.action_modo_entregador) {
            Intent intent = new Intent(this, EntregadorActivity.class);
            intent.putExtra("result", "Success");
            finish();
            startActivity(intent);
        } else if (id == R.id.action_modo_admin) {
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra("result", "Success");
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
