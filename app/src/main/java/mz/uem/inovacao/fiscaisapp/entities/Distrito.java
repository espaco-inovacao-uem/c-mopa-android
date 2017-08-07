package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Distrito {

    private int id;
    private String nome;

    @JsonIgnore
    private double latitude;
    @JsonIgnore
    private double longitude;
    @JsonIgnore
    private ArrayList<String> bairros;

    public Distrito() {

    }

    public Distrito(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Distrito(int id, String nome, ArrayList<String> bairros) {
        this.id = id;
        this.nome = nome;
        this.bairros = bairros;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getBairros() {
        return bairros;
    }

    public void setBairros(ArrayList<String> bairros) {
        this.bairros = bairros;
    }

    @Override
    public String toString() {

        return nome;
    }
}
