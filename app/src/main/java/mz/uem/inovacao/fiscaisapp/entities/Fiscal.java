package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fiscal {

    private int id;
    private String nome;
    private String apelido;
    private String endereco;
    @JsonProperty("numero_telefone")
    private String numeroTelefone;

    @JsonProperty("equipa_by_fiscal1_id")
    private List <?> serverEquipas;

    @JsonProperty("equipa_by_fiscal2_id")
    private List<?> serverEquipas2;

    public Fiscal() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContacto() {
        return numeroTelefone;
    }

    public void setContacto(String contacto) {
        this.numeroTelefone = contacto;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public List<?> getServerEquipas() {
        return serverEquipas;
    }

    public void setServerEquipas(List<?> serverEquipas) {
        this.serverEquipas = serverEquipas;
    }

    public List<?> getServerEquipas2() {
        return serverEquipas2;
    }

    public void setServerEquipas2(List<?> serverEquipas2) {
        this.serverEquipas2 = serverEquipas2;
    }
}
