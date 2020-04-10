package br.com.renaninfo.chegoupacote.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.renaninfo.chegoupacote.fragment.entregador.AdicionarPacoteFragment;
import br.com.renaninfo.chegoupacote.fragment.entregador.PacotesPendentesListFragment;
import br.com.renaninfo.chegoupacote.fragment.usuario.HistoricoPacotesListFragment;
import br.com.renaninfo.chegoupacote.fragment.usuario.MeusPacotesListFragment;

public class EntregadorTabAdapter extends FragmentStatePagerAdapter {

//    private String[] tituloAbas = {"Adicionar", "Pacotes Pendentes", "Pacotes por Unidade", "Histórico"};
    private String[] tituloAbas = {"Adicionar", "Pacotes Pendentes"};

    public EntregadorTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new AdicionarPacoteFragment();
                break;
            case 1:
                fragment = new PacotesPendentesListFragment();
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
