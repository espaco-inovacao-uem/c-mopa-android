package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by MauroBanze on 7/17/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipa {

    private int id;
    private String codigo;
    private Fiscal fiscal1;
    private Fiscal fiscal2;

    @JsonProperty("data_de_criacao")
    private String dataCriacao;
    @JsonProperty("data_fim")
    private String dataFim;
    private String descricao;

    public Equipa() {

    }

    public Equipa(int id, String codigo, Fiscal fiscal1, Fiscal fiscal2, String dataCriacao,
                  String dataFim, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.fiscal1 = fiscal1;
        this.fiscal2 = fiscal2;
        this.dataCriacao = dataCriacao;
        this.dataFim = dataFim;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Fiscal getFiscal1() {
        return fiscal1;
    }

    public void setFiscal1(Fiscal fiscal1) {
        this.fiscal1 = fiscal1;
    }

    public Fiscal getFiscal2() {
        return fiscal2;
    }

    public void setFiscal2(Fiscal fiscal2) {
        this.fiscal2 = fiscal2;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {

        return id + "-" + codigo + "-" + fiscal1 + "-" + fiscal2 + "-" + dataCriacao + "-" + descricao;
    }
}
