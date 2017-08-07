package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ocorrencia {

    private int id;

    @JsonProperty("codigo")
    private String codigoMOPA;
    private Distrito distrito;
    private String bairro;
    private String categoria;

    private String contentor;
    //private String quarteirao;

    private String data;
    private String descricao;

    @JsonProperty("estado")
    private String estado;

    private Equipa equipa;

    @JsonProperty("url_imagem")
    private String urlImagem;

    @JsonProperty("equipa_id")// important for server
    private int equipaForeignKey;

    @JsonProperty("distrito_id")// important for server
    private int distritoForeignKey;


    public Ocorrencia() {

    }

    public Ocorrencia(Distrito distrito, String bairro, String categoria, String contentor,
                      String data, String descricao, String estado, Equipa equipa) {

        this.distrito = distrito;
        this.bairro = bairro;
        this.categoria = categoria;
        this.contentor = contentor;
        //this.quarteirao = quarteirao;
        this.data = data;
        this.descricao = descricao;
        this.estado = estado;
        this.equipa = equipa;
        setUrlImagem("");

    }

    public Ocorrencia(int id, String codigoMOPA, Distrito distrito, String bairro, String categoria,
                      String contentor, String data, String descricao, String estado, Equipa equipa) {
        this.id = id;
        this.codigoMOPA = codigoMOPA;
        this.distrito = distrito;
        this.bairro = bairro;
        this.categoria = categoria;
        this.contentor = contentor;
        this.data = data;
        this.descricao = descricao;
        this.estado = estado;
        this.equipa = equipa;
        setUrlImagem("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoMOPA() {
        return codigoMOPA;
    }

    public void setCodigoMOPA(String codigoMOPA) {
        this.codigoMOPA = codigoMOPA;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    /*public String getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(String quarteirao) {
        this.quarteirao = quarteirao;
    }*/

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public int getEquipaForeignKey() {
        return equipaForeignKey;
    }

    public void setEquipaForeignKey(int equipaForeignKey) {
        this.equipaForeignKey = equipaForeignKey;
    }

    public int getDistritoForeignKey() {
        return distritoForeignKey;
    }

    public void setDistritoForeignKey(int distritoForeignKey) {
        this.distritoForeignKey = distritoForeignKey;
    }
}
