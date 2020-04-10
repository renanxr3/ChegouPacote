package br.com.renaninfo.chegoupacote.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.renaninfo.chegoupacote.AdminActivity;
import br.com.renaninfo.chegoupacote.fragment.admin.EntregadorListFragment;
import br.com.renaninfo.chegoupacote.fragment.admin.StatsFragment;
import br.com.renaninfo.chegoupacote.fragment.admin.UnidadeListFragment;

public class AdminTabAdapter extends FragmentStatePagerAdapter {

//    private String[] tituloAbas = {"Unidades", "Entregadores", "Estatísticas"};
    private String[] tituloAbas = {"Unidades", "Entregadores" };

    public AdminTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new UnidadeListFragment();
                break;
            case 1:
                fragment = new EntregadorListFragment();
                break;
            case 2:
                fragment = new StatsFragment();
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
