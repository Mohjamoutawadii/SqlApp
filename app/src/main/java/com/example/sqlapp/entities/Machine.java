package com.example.sqlapp.entities;

import java.util.Date;

public class Machine {
    private int id;
    private String reference;
    private int prix;
    private String date;
    private String marqueCode;


    public Machine() {
    }

    public Machine(String reference, int prix, String date, String marqueCode) {
        this.reference = reference;
        this.prix = prix;
        this.date = date;
        this.marqueCode = marqueCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarqueCode() {
        return marqueCode;
    }

    public void setMarqueCode(String marqueCode) {
        this.marqueCode = marqueCode;
    }
}
