package mz.uem.inovacao.fiscaisapp.entities;

import java.util.ArrayList;

/**
 *
 */

public class Distrito {

    private int id;
    private String nome;

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
