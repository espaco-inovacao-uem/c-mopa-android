package mz.uem.inovacao.fiscaisapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;

/**
 * Created by MauroBanze on 6/21/17.
 */

public class OcorrenciasListAdapter extends RecyclerView.Adapter<OcorrenciasListAdapter.ViewHolder> {

    private ArrayList<Ocorrencia> ocorrencias;

    public OcorrenciasListAdapter(ArrayList<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
