package com.k.quizzone;

public class KategoriModel {
    private String baslik;
    private int sets;
    private String url;
    public KategoriModel(){}
    public KategoriModel(String baslik, int sets, String url) {
        this.baslik = baslik;
        this.sets = sets;
        this.url = url;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
