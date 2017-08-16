package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Validacao {

    private int id;

    @JsonProperty("data_de_registo")
    private String dataRegisto;

    private String descricao;
    private String estado;

    @JsonProperty("foto_url")
    private String fotoUrl;

    private String bairro;

    @JsonIgnore
    private String latitude;
    @JsonIgnore
    private String longitude;

    private Pedido pedidoValidacao;

    @JsonProperty("pedido_id")
    private int serverPedidoValidacao;

    private Equipa equipa;

    @JsonProperty("equipa_id")
    private int serverEquipa;


    public Validacao() {

    }

    public Validacao(String dataRegisto, String descricao, String estado, String fotoUrl,
                     Pedido pedidoValidacao, Equipa equipa) {

        this.dataRegisto = dataRegisto;
        this.descricao = descricao;
        this.estado = estado;
        this.fotoUrl = fotoUrl;
        this.pedidoValidacao = pedidoValidacao;
        this.equipa = equipa;
    }


    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(String dataRegisto) {
        this.dataRegisto = dataRegisto;
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

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Pedido getPedidoValidacao() {
        return pedidoValidacao;
    }

    public void setPedidoValidacao(Pedido pedidoValidacao) {
        this.pedidoValidacao = pedidoValidacao;
    }

    public int getServerPedidoValidacao() {
        return serverPedidoValidacao;
    }

    public void setServerPedidoValidacao(int serverPedidoValidacao) {
        this.serverPedidoValidacao = serverPedidoValidacao;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public int getServerEquipa() {
        return serverEquipa;
    }

    public void setServerEquipa(int serverEquipa) {
        this.serverEquipa = serverEquipa;
    }
}
