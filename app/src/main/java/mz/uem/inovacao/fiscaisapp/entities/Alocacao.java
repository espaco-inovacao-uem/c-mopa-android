package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alocacao {

    private String id;
    private Distrito distrito;
    private Equipa equipa;

    @JsonProperty("data_inicio")
    private String dataInicio;

    @JsonProperty("data_fim")
    private String dataFim;
    private String descricao;

    @JsonProperty("distrito_by_distrito_id")
    private Object serverDistrito;

    @JsonProperty("equipa_by_equipa_id")
    private Object serverEquipa;


    public Alocacao() {

    }

    public Alocacao(String id, Distrito distrito, Equipa equipa, String dataInicio, String descricao,
                    Object serverDistrito, Object serverEquipa) {
        this.id = id;
        this.distrito = distrito;
        this.equipa = equipa;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
        this.serverDistrito = serverDistrito;
        this.serverEquipa = serverEquipa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
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

    public Object getServerDistrito() {
        return serverDistrito;
    }

    public void setServerDistrito(Object serverDistrito) {
        this.serverDistrito = serverDistrito;
    }

    public Object getServerEquipa() {
        return serverEquipa;
    }

    public void setServerEquipa(Object serverEquipa) {
        this.serverEquipa = serverEquipa;
    }

    @Override
    public String toString() {
        return "Alocacao{" +
                "id='" + id + '\'' +
                '}';
    }
}
