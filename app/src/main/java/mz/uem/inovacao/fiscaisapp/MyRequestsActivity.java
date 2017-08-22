package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.uem.inovacao.fiscaisapp.adapters.PedidosValidacaoListAdapter;
import mz.uem.inovacao.fiscaisapp.cloud.Cloud;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil;
import mz.uem.inovacao.fiscaisapp.entities.Alocacao;
import mz.uem.inovacao.fiscaisapp.entities.Distrito;
import mz.uem.inovacao.fiscaisapp.entities.Equipa;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.listeners.CloudResponseListener;
import mz.uem.inovacao.fiscaisapp.listeners.GetObjectsListener;
import mz.uem.inovacao.fiscaisapp.utils.AppConstants;

import static mz.uem.inovacao.fiscaisapp.dreamfactory.client.JsonUtil.mapper;

public class MyRequestsActivity extends AppCompatActivity implements View.OnClickListener {

    private SuperRecyclerView recyclerViewResults;
    private Alocacao alocacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        recyclerViewResults = (SuperRecyclerView) findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(MyRequestsActivity.this,
                LinearLayoutManager.VERTICAL, false));

        fetchMeusPedidos();
        //fetchAlocacao();
        //fetchMyRequests();
    }

    @Override
    public void onClick(View view) {


    }

    private void fetchMeusPedidos() {

        //1. Buscar todas alocacoes
        Cloud.getAlocacoesHoje(Cache.equipa, new CloudResponseListener<Alocacao>() {

            @Override
            public void success(ArrayList<Alocacao> lista) {

                fetchPedidos(lista);
            }

            @Override
            public void error(String error) {

            }
        });
    }

    private void fetchPedidos(List<Alocacao> alocacoes) {

        ArrayList<Distrito> distritos = new ArrayList<Distrito>();
        for (Alocacao alocacao : alocacoes) {

            distritos.add(alocacao.getDistrito());
        }

        Cloud.getPedidosValidacao(distritos, new CloudResponseListener<Pedido>() {

            @Override
            public void success(ArrayList<Pedido> pedidos) {

                recyclerViewResults.setAdapter(new PedidosValidacaoListAdapter(pedidos, MyRequestsActivity.this));
            }

            @Override
            public void error(String error) {
                Toast.makeText(MyRequestsActivity.this, "Erro pedidos", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchAlocacao() {

        Cloud.getAlocacao(Cache.equipa, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                if (lista.size() > 0) {
                    alocacao = (Alocacao) lista.get(0);

                    Log.d("Alocacao", alocacao.toString());

                    Object serverDistrito = alocacao.getServerDistrito();
                    Distrito distrito = mapper.convertValue(serverDistrito, new TypeReference<Distrito>() {
                    });

                    Log.d("Alocacao", distrito.getNome());

                    alocacao.setDistrito(distrito);
                    Cache.alocacao = alocacao;
                    fetchPedidos(distrito);


                } else {

                    showSemAlocacaoDialog();
                    Log.d("Alocacao", "sem alocacoes");
                }

            }

            @Override
            public void error(String error) {

                Log.d("Alocacao", "erro ao buscar alocacoes");

            }
        });
    }

    private void fetchPedidos(Distrito distrito) {

        Cloud.getPedidosValidacao(distrito, new GetObjectsListener() {

            @Override
            public void success(List<?> lista) {

                ArrayList<Pedido> pedidos = new ArrayList<>();

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
                Toast.makeText(MyRequestsActivity.this, "Erro pedidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSemAlocacaoDialog() {
        new MaterialDialog.Builder(this)
                .title("Impossível continuar")
                .content("Sem Alocações. Contacte o Admin do sistema")
                .positiveText("Ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        finish();
                    }
                })
                .cancelable(false)
                .build().show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            Toast.makeText(this, "Actualizando lista", Toast.LENGTH_SHORT).show();
            fetchPedidos(Cache.alocacao.getDistrito());
        }
    }
}
