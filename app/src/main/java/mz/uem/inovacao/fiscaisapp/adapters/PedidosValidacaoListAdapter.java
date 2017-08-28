package mz.uem.inovacao.fiscaisapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mz.uem.inovacao.fiscaisapp.PedidoValidacaoDetailsActivity;
import mz.uem.inovacao.fiscaisapp.R;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.mopa.ApiMOPA;

/**
 *
 */

public class PedidosValidacaoListAdapter extends RecyclerView.Adapter<PedidosValidacaoListAdapter.ViewHolder> {

    private ArrayList<Pedido> pedidos;
    private Activity context;

    public PedidosValidacaoListAdapter(ArrayList<Pedido> pedidos, Activity context) {
        this.pedidos = pedidos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item_ocorrencia, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Ocorrencia ocorrencia = pedidos.get(position).getOcorrencia();

        viewHolder.ocorrenciaIndex = position;
        viewHolder.textViewId.setText("#" + ocorrencia.getId());
        viewHolder.textViewCategoria.setText(ocorrencia.getCategoria());
        viewHolder.textViewEstado.setText(ocorrencia.getEstado());
        viewHolder.textViewBairro.setText(ocorrencia.getBairro());

    }

    @Override
    public int getItemCount() {

        return pedidos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int ocorrenciaIndex;
        TextView textViewId;
        TextView textViewCategoria;
        TextView textViewEstado;
        TextView textViewBairro;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.textViewOcorrenciaId);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewOcorrenciaCategoria);
            textViewEstado = (TextView) itemView.findViewById(R.id.textViewOcorrenciaEstado);
            textViewBairro = (TextView) itemView.findViewById(R.id.textViewOcorrenciaBairro);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            openActivity();
        }

        private void openActivity() {

            Intent intent = new Intent(context, PedidoValidacaoDetailsActivity.class);

            Pedido pedido = pedidos.get(ocorrenciaIndex);
            Ocorrencia ocorrencia = pedido.getOcorrencia();

            intent.putExtra(ApiMOPA.MOPA_ID, ocorrencia.getCodigo());
            intent.putExtra(ApiMOPA.MOPA_ESTADO, ocorrencia.getEstado());
            intent.putExtra(ApiMOPA.MOPA_CATEGORIA, ocorrencia.getCategoria());
            intent.putExtra(ApiMOPA.MOPA_DESCRICAO, ocorrencia.getDescricao());
            intent.putExtra(ApiMOPA.MOPA_BAIRRO, ocorrencia.getBairro());
            intent.putExtra(ApiMOPA.MOPA_DATA_CRIACAO, ocorrencia.getData());

            intent.putExtra(Pedido.EXTRA_ID, pedido.getId());
            intent.putExtra(Pedido.EXTRA_DESCRICAO, pedido.getDescricao());
            intent.putExtra(Pedido.EXTRA_ESTADO, pedido.getEstado());
            intent.putExtra(Pedido.EXTRA_DATA_REGISTO, pedido.getDataRegisto());

            context.startActivityForResult(intent, 0);
        }
    }
}
