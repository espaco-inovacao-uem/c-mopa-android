package mz.uem.inovacao.fiscaisapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.mopa.ApiMOPA;
import mz.uem.inovacao.fiscaisapp.mopa.OccorrenciasResponseListener;

public class ConfirmReportActivity extends AppCompatActivity {

    private RecyclerView recyclerViewResults;
    private ApiMOPA mopa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_report);

        this.recyclerViewResults = (RecyclerView) findViewById(R.id.recyclerViewResults);

        mopa = new ApiMOPA(this);
        mopa.fetchOcorrencias(new OccorrenciasResponseListener() {

            @Override
            public void onSuccess(ArrayList<Ocorrencia> ocorrencias) {

            }

            @Override
            public void onError() {

            }
        });
    }
}
