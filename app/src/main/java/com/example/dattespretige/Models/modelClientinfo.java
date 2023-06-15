package com.example.dattespretige.Models;

public class modelClientinfo {
    private String Articles;
    private String name, address, phone,prix_encours, id_client,status_client,time_proche;
    private String cours ,attente,terminer, annule;

    public modelClientinfo() {
    }

    public modelClientinfo(String time_proche,String articles, String name, String address, String phone, String prix_encours, String id_client, String status_client, String cours, String attente, String terminer, String annule) {
        Articles = articles;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.prix_encours = prix_encours;
        this.id_client = id_client;
        this.status_client = status_client;
        this.cours = cours;
        this.attente = attente;
        this.terminer = terminer;
        this.annule = annule;
        this.time_proche=time_proche;
    }

    public String getTime_proche() {
        return time_proche;
    }

    public void setTime_proche(String time_proche) {
        this.time_proche = time_proche;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public String getAttente() {
        return attente;
    }

    public void setAttente(String attente) {
        this.attente = attente;
    }

    public String getTerminer() {
        return terminer;
    }

    public void setTerminer(String terminer) {
        this.terminer = terminer;
    }

    public String getAnnule() {
        return annule;
    }

    public void setAnnule(String annule) {
        this.annule = annule;
    }

    public String getStatus_client() {
        return status_client;
    }

    public void setStatus_client(String status_client) {
        this.status_client = status_client;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getArticles() {
        return Articles;
    }

    public void setArticles(String Articles) {
        this.Articles = Articles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrix_encours() {
        return prix_encours;
    }

    public void setPrix_encours(String prix_encours) {
        this.prix_encours = prix_encours;
    }
}

