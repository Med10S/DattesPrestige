package com.example.dattespretige.Models;


public class commande {
    private String duplication,id_client,name,type,total;
    private String  time, product, details, status, price, id, timstamp;
    private String café,Truffe, Pistache, Noix_de_coco, Speculos, Caramel_beurre_salé, praliné,
            Amande_gout_rose, Amande_gout_orange, Citron,
            Gingember_citron_Vert, framboise, caramel_chocolate,
            amande_Rose, Amande_Orange, Amande_gingembre, Amande_kaab_ghzal, Pistache_beldi;
    public commande() {

    }


    public commande(String total, String type,String name,String id_client,String duplication, String time, String product, String details, String praliné, String status, String price, String id, String timestamp, String café, String truffe, String pistache, String noix_de_coco, String speculos, String caramel_beurre_salé, String amande_gout_rose, String Amande_gout_orange, String citron, String gingember_citron_Vert, String framboise, String caramel_chocolate, String amande_Rose, String amande_Orange, String amande_gingembre, String amande_kaab_ghzal, String pistache_beldi) {

        this.time = time;
        this.product = product;
        this.details = details;
        this.praliné = praliné;
        this.status = status;
        this.price = price;
        this.id = id;
        this.timstamp = timestamp;
        this.café = café;
        this.Truffe = truffe;
        this.Pistache = pistache;
        this.Noix_de_coco = noix_de_coco;
        this.Speculos = speculos;
        this.Caramel_beurre_salé = caramel_beurre_salé;
        this.Amande_gout_rose = amande_gout_rose;
        this.Amande_gout_orange = Amande_gout_orange;
        this.Citron = citron;
        this.Gingember_citron_Vert = gingember_citron_Vert;
        this.framboise = framboise;
        this.caramel_chocolate = caramel_chocolate;
        this.amande_Rose = amande_Rose;
        this.Amande_Orange = amande_Orange;
        this.Amande_gingembre = amande_gingembre;
        this.Amande_kaab_ghzal = amande_kaab_ghzal;
        this.Pistache_beldi = pistache_beldi;
        this.duplication=duplication;
        this.id_client=id_client;
        this.name=name;
        this.type=type;
        this.total=total;
    }

    public String getTimstamp() {
        return timstamp;
    }

    public void setTimstamp(String timstamp) {
        this.timstamp = timstamp;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getDuplication() {
        return duplication;
    }

    public void setDuplication(String duplication) {
        this.duplication = duplication;
    }

    public String getTruffe() {
        return Truffe;
    }

    public void setTruffe(String truffe) {
        Truffe = truffe;
    }

    public String getPistache() {
        return Pistache;
    }

    public void setPistache(String pistache) {
        Pistache = pistache;
    }

    public String getNoix_de_coco() {
        return Noix_de_coco;
    }

    public void setNoix_de_coco(String noix_de_coco) {
        Noix_de_coco = noix_de_coco;
    }

    public String getSpeculos() {
        return Speculos;
    }

    public void setSpeculos(String speculos) {
        Speculos = speculos;
    }

    public String getAmande_gout_rose() {
        return Amande_gout_rose;
    }

    public void setAmande_gout_rose(String amande_gout_rose) {
        Amande_gout_rose = amande_gout_rose;
    }

    public String getAmande_gout_orange() {
        return Amande_gout_orange;
    }

    public void setAmande_gout_orange(String amande_gout_orange) {
        Amande_gout_orange = amande_gout_orange;
    }

    public String getCitron() {
        return Citron;
    }

    public void setCitron(String citron) {
        Citron = citron;
    }

    public String getGingember_citron_Vert() {
        return Gingember_citron_Vert;
    }

    public void setGingember_citron_Vert(String gingember_citron_Vert) {
        Gingember_citron_Vert = gingember_citron_Vert;
    }

    public String getFramboise() {
        return framboise;
    }

    public void setFramboise(String framboise) {
        this.framboise = framboise;
    }

    public String getCaramel_chocolate() {
        return caramel_chocolate;
    }

    public void setCaramel_chocolate(String caramel_chocolate) {
        this.caramel_chocolate = caramel_chocolate;
    }

    public String getAmande_Rose() {
        return amande_Rose;
    }

    public void setAmande_Rose(String amande_Rose) {
        this.amande_Rose = amande_Rose;
    }

    public String getAmande_Orange() {
        return Amande_Orange;
    }

    public void setAmande_Orange(String amande_Orange) {
        Amande_Orange = amande_Orange;
    }

    public String getAmande_gingembre() {
        return Amande_gingembre;
    }

    public void setAmande_gingembre(String amande_gingembre) {
        Amande_gingembre = amande_gingembre;
    }

    public String getAmande_kaab_ghzal() {
        return Amande_kaab_ghzal;
    }

    public void setAmande_kaab_ghzal(String amande_kaab_ghzal) {
        Amande_kaab_ghzal = amande_kaab_ghzal;
    }

    public String getPistache_beldi() {
        return Pistache_beldi;
    }

    public void setPistache_beldi(String pistache_beldi) {
        Pistache_beldi = pistache_beldi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaramel_beurre_salé() {
        return Caramel_beurre_salé;
    }

    public void setCaramel_beurre_salé(String caramel_beurre_salé) {
        this.Caramel_beurre_salé = caramel_beurre_salé;
    }

    public String getPraliné() {
        return praliné;
    }

    public void setPraliné(String praliné) {
        this.praliné = praliné;
    }

    public String getCafé() {
        return café;
    }

    public void setCafé(String café) {
        this.café = café;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
