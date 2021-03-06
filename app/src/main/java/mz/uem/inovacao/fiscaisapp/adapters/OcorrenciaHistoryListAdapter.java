package mz.uem.inovacao.fiscaisapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.R;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;

/**
 *
 */

public class OcorrenciaHistoryListAdapter extends RecyclerView.Adapter<OcorrenciaHistoryListAdapter.ViewHolder> {

    private ArrayList<Ocorrencia> ocorrencias;
    private Context context;

    public OcorrenciaHistoryListAdapter(ArrayList<Ocorrencia> ocorrencias, Context context) {
        this.ocorrencias = ocorrencias;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item_history_ocorrencia, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Ocorrencia ocorrencia = ocorrencias.get(position);

        viewHolder.ocorrenciaIndex = position;
        viewHolder.textViewId.setText("#" + ocorrencia.getId());
        viewHolder.textViewCategoria.setText(ocorrencia.getCategoria());
        viewHolder.textViewData.setText(ocorrencia.getData());
        viewHolder.textViewBairro.setText(ocorrencia.getBairro());

    }

    @Override
    public int getItemCount() {

        return ocorrencias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int ocorrenciaIndex;
        TextView textViewId;
        TextView textViewCategoria;
        TextView textViewData;
        TextView textViewBairro;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.textViewOcorrenciaId);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewOcorrenciaCategoria);
            textViewData = (TextView) itemView.findViewById(R.id.textViewOcorrenciaDataCriacao);
            textViewBairro = (TextView) itemView.findViewById(R.id.textViewOcorrenciaBairro);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            openActivity();
        }

        private void openActivity() {

           /* Intent intent = new Intent(context, PedidoValidacaoDetailsActivity.class);

            Ocorrencia ocorrencia = ocorrencias.get(ocorrenciaIndex);
            Ocorrencia ocorrencia = ocorrencia.getOcorrencia();

            intent.putExtra(ApiMOPA.MOPA_ID, ocorrencia.getCodigoMOPA());
            intent.putExtra(ApiMOPA.MOPA_ESTADO, ocorrencia.getEstado());
            intent.putExtra(ApiMOPA.MOPA_CATEGORIA, ocorrencia.getCategoria());
            intent.putExtra(ApiMOPA.MOPA_DESCRICAO, ocorrencia.getDescricao());
            intent.putExtra(ApiMOPA.MOPA_BAIRRO, ocorrencia.getBairro());
            intent.putExtra(ApiMOPA.MOPA_DATA_CRIACAO, ocorrencia.getData());

            intent.putExtra(Pedido.EXTRA_ID, ocorrencia.getMopaId());
            intent.putExtra(Pedido.EXTRA_DESCRICAO, ocorrencia.getDescricao());
            intent.putExtra(Pedido.EXTRA_ESTADO, ocorrencia.getEstado());
            intent.putExtra(Pedido.EXTRA_DATA_REGISTO, ocorrencia.getDataRegisto());

            context.startActivity(intent);*/
        }
    }
}
