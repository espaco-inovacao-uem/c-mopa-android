package mz.uem.inovacao.fiscaisapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by LIMS06 on 6/4/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    //@JsonProperty("id")
    private long id;
    private String username;
    private String password;

    @JsonProperty("password_expired")
    private boolean passwordExpired;

    public User() {

    }

    public User(String nome, String password) {
        this.username = nome;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }
}
