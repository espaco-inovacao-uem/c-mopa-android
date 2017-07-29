package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 */

public class Categoria {

    private String mopaId;
    private String nome;
    private boolean requiresContentor;

    public Categoria() {

    }

    public Categoria(String mopaId, String nome) {
        this.mopaId = mopaId;
        this.nome = nome;
    }

    public Categoria(String mopaId, String nome, boolean requiresContentor) {
        this.mopaId = mopaId;
        this.nome = nome;
        this.requiresContentor = requiresContentor;
    }

    public String getMopaId() {
        return mopaId;
    }

    public void setMopaId(String mopaId) {
        this.mopaId = mopaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean requiresContentor() {
        return requiresContentor;
    }

    public void setRequiresContentor(boolean requiresContentor) {
        this.requiresContentor = requiresContentor;
    }

    @Override
    public String toString() {

        return nome;
    }
}
