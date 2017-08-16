package mz.uem.inovacao.fiscaisapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.R;
import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.entities.Validacao;

/**
 *
 */

public class ValidacaoHistoryListAdapter extends RecyclerView.Adapter<ValidacaoHistoryListAdapter.ViewHolder> {

    private ArrayList<Validacao> validacoes;
    private Context context;

    public ValidacaoHistoryListAdapter(ArrayList<Validacao> validacoes, Context context) {
        this.validacoes = validacoes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item_history_validacao, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Validacao validacao = validacoes.get(position);

        viewHolder.validacaoIndex = position;
        viewHolder.textViewId.setText("#" + validacao.getId());
        viewHolder.textViewEstado.setText(validacao.getEstado());
        viewHolder.textViewData.setText(validacao.getDataRegisto());
        viewHolder.textViewBairro.setText(validacao.getBairro());
        //viewHolder.textViewEquipa.setText(Cache.equipa.toString());

    }

    @Override
    public int getItemCount() {

        return validacoes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int validacaoIndex;
        TextView textViewId;
        TextView textViewEstado;
        TextView textViewData;
        TextView textViewBairro;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.textViewValidacaoId);
            textViewEstado = (TextView) itemView.findViewById(R.id.textViewValidacaoEstado);
            textViewData = (TextView) itemView.findViewById(R.id.textViewValidacaoDataCriacao);
            textViewBairro = (TextView) itemView.findViewById(R.id.textViewValidacaoBairro);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            openActivity();
        }

        private void openActivity() {

           /* Intent intent = new Intent(context, PedidoValidacaoDetailsActivity.class);

            Ocorrencia ocorrencia = validacoes.get(ocorrenciaIndex);
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
