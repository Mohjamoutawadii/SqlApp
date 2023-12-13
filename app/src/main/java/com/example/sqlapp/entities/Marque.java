package com.example.sqlapp.entities;

public class Marque {

    private int id;
    private String code;
    private String Libelle;

    public Marque() {
    }

    public Marque(String code, String libelle) {
        this.code = code;
        Libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }
}
