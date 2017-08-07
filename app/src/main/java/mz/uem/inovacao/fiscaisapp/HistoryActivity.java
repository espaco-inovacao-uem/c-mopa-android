package mz.uem.inovacao.fiscaisapp;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.uem.inovacao.fiscaisapp.adapters.OcorrenciaHistoryListAdapter;
import mz.uem.inovacao.fiscaisapp.adapters.ValidacaoHistoryListAdapter;
import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Validacao;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;

public class HistoryActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    public static class OcorrenciasHistoryFragment extends Fragment {

        private SuperRecyclerView recyclerView;

        public OcorrenciasHistoryFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_history_ocorrencias, container, false);

            recyclerView = (SuperRecyclerView) rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));

            fetchOcorrencias();
            return rootView;
        }

        private void fetchOcorrencias() {
            Cloud.getEquipaOcorrencias(Cache.equipa, new GetObjectsListener() {

                @Override
                public void success(List<?> lista) {

                    ArrayList<Ocorrencia> ocorrencias = new ArrayList<>();

                    for (int i = 0; i < lista.size(); i++) {

                        Ocorrencia ocorrencia = (Ocorrencia) lista.get(i);
                        ocorrencias.add(ocorrencia);
                    }

                    recyclerView.setAdapter(new OcorrenciaHistoryListAdapter(ocorrencias, getActivity()));
                }

                @Override
                public void error(String error) {

                    Log.e("History", error);
                }
            });
        }
    }

    public static class ValidacoesHistoryFragment extends Fragment {

        private SuperRecyclerView recyclerView;

        public ValidacoesHistoryFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_history_ocorrencias, container, false);

            recyclerView = (SuperRecyclerView) rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));

            fetchValidacoes();
            return rootView;
        }

        private void fetchValidacoes() {
            Cloud.getEquipaValidacoes(Cache.equipa, new GetObjectsListener() {

                @Override
                public void success(List<?> lista) {

                    ArrayList<Validacao> validacoes = new ArrayList<>();

                    for (int i = 0; i < lista.size(); i++) {

                        Validacao validacao = (Validacao) lista.get(i);
                        validacoes.add(validacao);
                    }

                    recyclerView.setAdapter(new ValidacaoHistoryListAdapter(validacoes, getActivity()));
                }

                @Override
                public void error(String error) {

                    Log.e("History", error);
                }
            });
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new OcorrenciasHistoryFragment();

            } else {
                return new ValidacoesHistoryFragment();
            }
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MEUS REPORTS";
                case 1:
                    return "MINHAS VALIDAÇÕES";
            }
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
