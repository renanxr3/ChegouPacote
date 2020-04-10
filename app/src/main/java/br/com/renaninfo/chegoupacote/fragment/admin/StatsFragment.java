package br.com.renaninfo.chegoupacote.fragment.admin;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.renaninfo.chegoupacote.R;
import br.com.renaninfo.chegoupacote.UsuarioSearchActivity;
import br.com.renaninfo.chegoupacote.model.StatsModel;
import br.com.renaninfo.chegoupacote.vo.AdminStats;
import br.com.renaninfo.chegoupacote.vo.Entregador;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    public StatsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;

        rootView = inflater.inflate(R.layout.fragment_admin_stats, container, false);
        final HashMap<String, String> mapStats = StatsModel.getStatsDoCondominio();
        TextView lblUnidadesSum = (TextView) rootView.findViewById(R.id.lblUnidadesSum);
        TextView lblUsuariosSum = (TextView) rootView.findViewById(R.id.lblUsuariosSum);
        TextView lblEntregadoresSum = (TextView) rootView.findViewById(R.id.lblEntregadoresSum);

        lblUnidadesSum.setText(mapStats.get(AdminStats.STAT_UNIDADES_SUM));
        lblUsuariosSum.setText(mapStats.get(AdminStats.STAT_USUARIOS_SUM));
        lblEntregadoresSum.setText(mapStats.get(AdminStats.STAT_ENTREGADORES_SUM));

        return rootView;
    }

}
