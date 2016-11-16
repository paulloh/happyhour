package com.paulo.android.happyhour.model;

/**
 * Created by Paulo Henrique on 19/10/2016.
 */
public class Perfil {

    public Perfil(){

    }
    public Perfil(String name, String dataNasc, String email, String picture) {
        this.name = name;
        this.dataNasc = dataNasc;
        this.email = email;
        this.picture = picture;
    }

    private String id;
    private String name;
    private String dataNasc;
    private String city;
    private Integer uf;
    private String email;
    private String password;
    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getUf() {
        return uf;
    }

    public void setUf(Integer uf) {
        this.uf = uf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
