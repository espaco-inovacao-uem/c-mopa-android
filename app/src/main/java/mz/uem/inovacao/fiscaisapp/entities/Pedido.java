package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {

    private int id;
    private String descricao;
    private String estado;



    @JsonProperty("data_de_registo")
    private String dataRegisto;

    @JsonProperty("ocorrencia_by_ocorrencia_id")
    private Object serverOcorrencia;

    private Object serverEquipa;

    @JsonIgnore
    private Ocorrencia ocorrencia;

    private boolean validado;

    public Pedido() {

    }

    public Pedido(int id, String descricao, String estado, String dataRegisto, Object serverOcorrencia, Object serverEquipa) {
        this.id = id;
        this.descricao = descricao;
        this.estado = estado;
        this.dataRegisto = dataRegisto;
        this.serverOcorrencia = serverOcorrencia;
        this.serverEquipa = serverEquipa;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public Object getServerOcorrencia() {
        return serverOcorrencia;
    }

    public void setServerOcorrencia(Object serverOcorrencia) {
        this.serverOcorrencia = serverOcorrencia;
    }

    public Object getServerEquipa() {
        return serverEquipa;
    }

    public void setServerEquipa(Object serverEquipa) {
        this.serverEquipa = serverEquipa;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public static final String EXTRA_ID = "PEDIDO_ID";
    public static final String EXTRA_DESCRICAO = "PEDIDO_DESCRICAO";
    public static final String EXTRA_ESTADO = "PEDIDO_ESTADO";
    public static final String EXTRA_DATA_REGISTO = "PEDIDO_DATA_REGISTO";

}
