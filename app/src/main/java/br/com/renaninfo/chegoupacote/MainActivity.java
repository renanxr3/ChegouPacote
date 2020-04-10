package br.com.renaninfo.chegoupacote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.renaninfo.chegoupacote.model.PacoteModel;
import br.com.renaninfo.chegoupacote.vo.Pacote;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "RI_CHEGOUPACOTE";

    public static final int REQUEST_CODE_LOGIN = 1;
    public static final int REQUEST_CODE_LOGIN_SUCCESS = 2;
    public static final int REQUEST_CODE_LOGIN_ERROR = 3;

    public static final int REQUEST_CODE_UNIDADE_ADD = 1;
    public static final int REQUEST_CODE_UNIDADE_ADD_SUCCESS = 2;
    public static final int REQUEST_CODE_UNIDADE_ADD_ERROR = 3;

    public static final int REQUEST_CODE_USUARIO_SEARCH = 1;
    public static final int REQUEST_CODE_USUARIO_SEARCH_SUCCESS = 2;
    public static final int REQUEST_CODE_USUARIO_SEARCH_ERROR = 3;




    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_LOGIN) {
            if(resultCode == MainActivity.REQUEST_CODE_LOGIN_SUCCESS){
                String result=data.getStringExtra("result");
                Log.d(MainActivity.TAG, "REQUEST_CODE_LOGIN_SUCCESS:");
            }
            if (resultCode == MainActivity.REQUEST_CODE_LOGIN_ERROR) {
                //Write your code if there's no result
                Log.d(MainActivity.TAG, "REQUEST_CODE_LOGIN_ERROR:");
            }
        }
    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_usuario_pacotes, container, false);

                    final ArrayList<Pacote> listaPacotes = PacoteModel.getPacotesPendentesUsuario();

                    ListView lvUsuarioPacotes = (ListView) rootView.findViewById(R.id.lv_usuario_pacotes);
                    ArrayAdapter<Pacote> arrayAdapter = new ArrayAdapter<Pacote>(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaPacotes) {
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                            text1.setText("Pacote: " + listaPacotes.get(position).getId());
                            text2.setText("Unidade: " + listaPacotes.get(position).getNomeUnidade());
                            return view;
                        }
                    };
                    lvUsuarioPacotes.setAdapter(arrayAdapter);

                    return rootView;

                case 3:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    return rootView;

                case 4:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    return rootView;

                case 5:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    TextView textView2 = (TextView) rootView.findViewById(R.id.section_label);
                    textView2.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
