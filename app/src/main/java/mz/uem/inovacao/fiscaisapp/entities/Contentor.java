package mz.uem.inovacao.fiscaisapp.entities;

/**
 *
 */

public class Contentor {

    private String locationId;
    private String nome;

    public Contentor() {

    }

    public Contentor(String locationId, String nome) {
        this.locationId = locationId;
        this.nome = nome;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {

        return nome;
    }
}
