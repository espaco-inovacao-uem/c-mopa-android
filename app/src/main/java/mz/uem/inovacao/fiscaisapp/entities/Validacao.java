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
    private Date dataRegisto;

    private String descricao;
    private String estado;

    @JsonProperty("foto_url")
    private String fotoUrl;

    @JsonIgnore
    private String latitude;
    @JsonIgnore
    private String longitude;

    private Pedido pedidoValidacao;

    @JsonProperty("pedido_id")
    private int serverPedidoValidacao;

    public Validacao() {

    }

    public Validacao(Date dataRegisto, String descricao, String estado, String fotoUrl,
                     Pedido pedidoValidacao) {

        this.dataRegisto = dataRegisto;
        this.descricao = descricao;
        this.estado = estado;
        this.fotoUrl = fotoUrl;
        this.pedidoValidacao = pedidoValidacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
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
}
