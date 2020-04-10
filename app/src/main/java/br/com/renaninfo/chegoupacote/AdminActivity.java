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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.renaninfo.chegoupacote.adapter.AdminTabAdapter;
import br.com.renaninfo.chegoupacote.component.SlidingTabLayout;
import br.com.renaninfo.chegoupacote.model.EntregadorModel;
import br.com.renaninfo.chegoupacote.model.StatsModel;
import br.com.renaninfo.chegoupacote.util.ConfiguracaoFirebase;
import br.com.renaninfo.chegoupacote.vo.AdminStats;
import br.com.renaninfo.chegoupacote.vo.Entregador;
import br.com.renaninfo.chegoupacote.vo.Unidade;

public class AdminActivity extends AppCompatActivity {

    private AdminTabAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);

        slidingTabLayout.setDistributeEvenly(true);

        mSectionsPagerAdapter = new AdminTabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        tabLayout.setupWithViewPager(mViewPager);
        slidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.removeItem(R.id.action_modo_admin);

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
        } else if (id == R.id.action_logout) {
            ConfiguracaoFirebase.getFirebaseAutenticacao().signOut();
            Intent intent = new Intent(this, LoginDecisaoActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_UNIDADE_ADD) {
            if(resultCode == MainActivity.REQUEST_CODE_UNIDADE_ADD_SUCCESS){
                String result = data.getStringExtra("result");
                Log.d(MainActivity.TAG, "REQUEST_CODE_UNIDADE_ADD_SUCCESS:");
                Toast.makeText(getBaseContext(),"Adicionou unidade",Toast.LENGTH_SHORT).show();
            }
            if (resultCode == MainActivity.REQUEST_CODE_UNIDADE_ADD_ERROR) {
                //Write your code if there's no result
                Log.d(MainActivity.TAG, "REQUEST_CODE_UNIDADE_ADD_ERROR:");
                Toast.makeText(getBaseContext(),"NAO Adicionou unidade",Toast.LENGTH_SHORT).show();
            }
        }
    } //onActivityResult

}
