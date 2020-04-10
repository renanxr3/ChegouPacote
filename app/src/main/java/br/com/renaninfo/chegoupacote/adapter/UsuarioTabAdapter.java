package br.com.renaninfo.chegoupacote.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.renaninfo.chegoupacote.fragment.admin.EntregadorListFragment;
import br.com.renaninfo.chegoupacote.fragment.admin.StatsFragment;
import br.com.renaninfo.chegoupacote.fragment.admin.UnidadeListFragment;
import br.com.renaninfo.chegoupacote.fragment.usuario.HistoricoPacotesListFragment;
import br.com.renaninfo.chegoupacote.fragment.usuario.MeusPacotesListFragment;

public class UsuarioTabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"Meus Pacotes", "Histórico"};

    public UsuarioTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MeusPacotesListFragment();
                break;
            case 1:
                fragment = new HistoricoPacotesListFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }

}