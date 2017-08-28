package mz.uem.inovacao.fiscaisapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import mz.uem.inovacao.fiscaisapp.database.Cache;
import mz.uem.inovacao.fiscaisapp.entities.Ocorrencia;
import mz.uem.inovacao.fiscaisapp.entities.Pedido;
import mz.uem.inovacao.fiscaisapp.mopa.ApiMOPA;

public class PedidoValidacaoDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Pedido pedido;
    private Ocorrencia ocorrencia;

    private TextView textViewOcorrenciaCategoria, textViewOcorrenciaBairro,
            textViewOcorrenciaDescricao, textViewOcorrenciaDataCriacao;

    private TextView textViewPedidoEstado, textViewPedidoData,
            textViewPedidoDescricao;

    private Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_validacao_details);

        textViewOcorrenciaCategoria = (TextView) findViewById(R.id.textViewOcorrenciaCategoria);
        textViewOcorrenciaBairro = (TextView) findViewById(R.id.textViewOcorrenciaBairro);
        textViewOcorrenciaDescricao = (TextView) findViewById(R.id.textViewOcorrenciaDescricao);
        textViewOcorrenciaDataCriacao = (TextView) findViewById(R.id.textViewOcorrenciaDataCriacao);

        textViewPedidoEstado = (TextView) findViewById(R.id.textViewPedidoEstado);
        textViewPedidoData = (TextView) findViewById(R.id.textViewPedidoData);
        textViewPedidoDescricao = (TextView) findViewById(R.id.textViewPedidoDescricao);

        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(this);

        Intent intent = getIntent();

        int pedidoId = intent.getIntExtra(Pedido.EXTRA_ID, -1);
        String pedidoDescricao = intent.getStringExtra(Pedido.EXTRA_DESCRICAO);
        String pedidoEstado = intent.getStringExtra(Pedido.EXTRA_ESTADO);
        String pedidoDataRegisto = intent.getStringExtra(Pedido.EXTRA_DATA_REGISTO);

        pedido = new Pedido(pedidoId, pedidoDescricao, pedidoEstado, pedidoDataRegisto, null, null);
        Cache.pedidoValidacao = pedido;

        String ocorrenciaId = intent.getStringExtra(ApiMOPA.MOPA_ID);
        String ocorrenciaEstado = intent.getStringExtra(ApiMOPA.MOPA_ESTADO);
        String categoria = intent.getStringExtra(ApiMOPA.MOPA_CATEGORIA);
        String ocorrenciaDescricao = intent.getStringExtra(ApiMOPA.MOPA_DESCRICAO);
        String bairro = intent.getStringExtra(ApiMOPA.MOPA_BAIRRO);

        String dateString = intent.getStringExtra(ApiMOPA.MOPA_DATA_CRIACAO);


        ocorrencia = new Ocorrencia(1, ocorrenciaId, null, bairro, categoria, null,
                dateString, ocorrenciaDescricao, ocorrenciaEstado, null);

        Cache.pedidoValidacao.setOcorrencia(ocorrencia);

        populateFields();
    }

    private void populateFields() {

        textViewPedidoEstado.setText("Validar estado:" + pedido.getEstado() + "");
        textViewPedidoData.setText(pedido.getDataRegisto());
        textViewPedidoDescricao.setText(pedido.getDescricao());

        textViewOcorrenciaCategoria.setText(ocorrencia.getCategoria());
        textViewOcorrenciaBairro.setText("Bairro: " + ocorrencia.getBairro() + "");
        textViewOcorrenciaDescricao.setText(ocorrencia.getDescricao());
        textViewOcorrenciaDataCriacao.setText(ocorrencia.getData());
    }

    private SpannableString textWithBoldLabel(String labelToBold, String contentText) {

        SpannableString str = new SpannableString(labelToBold + contentText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, labelToBold.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return str;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(PedidoValidacaoDetailsActivity.this,
                SubmitValidacaoActivity.class);
        startActivityForResult(intent, 0);

    }

    private String getFormatedDate(Date date) {
        return DateFormat.format("yyyy-MM-dd hh:mm:ss a", date).toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {//ocorrencia foi salva

            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
