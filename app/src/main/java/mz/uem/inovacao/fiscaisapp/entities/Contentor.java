package mz.uem.inovacao.fiscaisapp.entities;

/**
 *
 */

public class Contentor {

    private String locationId;
    private String nome;
    private double latitude;
    private double longitude;

    public Contentor() {

    }

    public Contentor(String locationId, String nome, double latitude, double longitude) {
        this.locationId = locationId;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public String toString() {

        return nome;
    }
}
