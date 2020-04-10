package br.com.renaninfo.chegoupacote;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.adapter.UsuarioTabAdapter;
import br.com.renaninfo.chegoupacote.component.SlidingTabLayout;
import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.model.UsuarioModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.Pacote;
import br.com.renaninfo.chegoupacote.vo.PacoteHistorico;
import br.com.renaninfo.chegoupacote.vo.Usuario;
import br.com.renaninfo.chegoupacote.vo.UsuarioLogado;

public class UsuarioActivity extends AppCompatActivity {

    private UsuarioTabAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);

        slidingTabLayout.setDistributeEvenly(true);

        mSectionsPagerAdapter = new UsuarioTabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        slidingTabLayout.setViewPager(mViewPager);

//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Associar Entregador", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });
//        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.action_modo_usuario);

        Usuario usuarioLogado = UsuarioLogado.getInstance();
        if (!usuarioLogado.isNivelAdmin()) {
            menu.removeItem(R.id.action_modo_admin);
        }
        if (!((usuarioLogado.isNivelEntregador()) || (usuarioLogado.isNivelAdmin()))) {
            menu.removeItem(R.id.action_modo_entregador);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Snackbar.make(this.getCurrentFocus(), "TODO: Settings", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        } else if (id == R.id.action_modo_admin) {
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra("result", "Success");
            finish();
            startActivity(intent);
        } else if (id == R.id.action_modo_entregador) {
            Intent intent = new Intent(this, EntregadorActivity.class);
            intent.putExtra("result", "Success");
            finish();
            startActivity(intent);
        } else if (id == R.id.action_logout) {
            ConfiguracaoFirebase.getFirebaseAutenticacao().signOut();
            Intent intent = new Intent(this, LoginDecisaoActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
