package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *
 */

public class Ocorrencia {

    private int id;
    private int version;
    private String bairro;
    private String contentor;
    private String quarteirao;
    private String distrito;
    private Date data;
    private String descricao;

    @JsonProperty("estado_id")
    private int estado;
    private Fiscal fiscal;
    private int tipo;

    @JsonProperty("codigo")
    private int codigoMOPA;

    public Ocorrencia() {

    }

    public Ocorrencia(int id, int version, String bairro, String contentor, String quarteirao, String distrito, Date data, String descricao, int estado, Fiscal fiscal, int tipo, int codigoMOPA) {
        this.id = id;
        this.version = version;
        this.bairro = bairro;
        this.contentor = contentor;
        this.quarteirao = quarteirao;
        this.distrito = distrito;
        this.data = data;
        this.descricao = descricao;
        this.estado = estado;
        this.fiscal = fiscal;
        this.tipo = tipo;
        this.codigoMOPA = codigoMOPA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getContentor() {
        return contentor;
    }

    public void setContentor(String contentor) {
        this.contentor = contentor;
    }

    public String getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Fiscal getFiscal() {
        return fiscal;
    }

    public void setFiscal(Fiscal fiscal) {
        this.fiscal = fiscal;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCodigoMOPA() {
        return codigoMOPA;
    }

    public void setCodigoMOPA(int codigoMOPA) {
        this.codigoMOPA = codigoMOPA;
    }
}
