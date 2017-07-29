package mz.uem.inovacao.fiscaisapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import mz.uem.inovacao.fiscaisapp.adapters.PedidosValidacaoListAdapter;
import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.mopa.ApiMOPA;

public class MyRequestsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewResults;
    //private ViewGroup formBasicInfo;
    //private Button buttonSearch;

    private ApiMOPA mopa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        //formBasicInfo = (ViewGroup) findViewById(R.id.formBasicInfo);

        /*buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);*/

        recyclerViewResults = (RecyclerView) findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(MyRequestsActivity.this,
                LinearLayoutManager.VERTICAL, false));

        /*mopa = new ApiMOPA(this);
        mopa.fetchOcorrencias(occorrenciasResponseListener);*/

        fetchMyRequests();
    }

    /*private void switchViews() {

        if (formBasicInfo.getVisibility() == View.VISIBLE) {

            formBasicInfo.setVisibility(View.GONE);
            recyclerViewResults.setVisibility(View.VISIBLE);

        } else {
            formBasicInfo.setVisibility(View.VISIBLE);
            recyclerViewResults.setVisibility(View.GONE);
        }
    }*/

    @Override
    public void onClick(View view) {


    }

    /*ApiMOPA.OccorrenciasResponseListener occorrenciasResponseListener = new ApiMOPA.OccorrenciasResponseListener() {

        @Override
        public void onSuccess(ArrayList<Ocorrencia> ocorrencias) {

            recyclerViewResults.setAdapter(new PedidosValidacaoListAdapter(ocorrencias, MyRequestsActivity.this));

            //switchViews();

        }

        @Override
        public void onError() {

            Toast.makeText(MyRequestsActivity.this, "Erro ao buscar ocorrencias do MOPA",
                    Toast.LENGTH_SHORT).show();
        }
    };*/

    private void fetchMyRequests() {

        Cloud.getPedidosValidacao(Cache.equipa, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

                for (int i = 0; i < lista.size(); i++) {

                    Pedido pedido = (Pedido) lista.get(i);

                    Ocorrencia ocorrencia = JsonUtil.mapper.convertValue(pedido.getServerOcorrencia(),
                            new TypeReference<Ocorrencia>() {
                            });

                    pedido.setOcorrencia(ocorrencia);
                    pedidos.add(pedido);
                }

                recyclerViewResults.setAdapter(new PedidosValidacaoListAdapter(pedidos, MyRequestsActivity.this));

                Log.d("Pedidos", "recebi nr pedidos= " + pedidos.size());

            }

            @Override
            public void error(String error) {


            }
        });
    }
}
