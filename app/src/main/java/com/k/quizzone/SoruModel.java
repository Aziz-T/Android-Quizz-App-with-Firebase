package com.k.quizzone;

public class SoruModel {
    private String soru, seca,secb,secc,secd,cevap;
    private int setNo;
    public SoruModel(){}

    public SoruModel(String soru, String seca, String secb, String secc, String secd, String cevap,int setNo) {
        this.soru = soru;
        this.seca = seca;
        this.secb = secb;
        this.secc = secc;
        this.secd = secd;
        this.cevap = cevap;
        this.setNo=setNo;
    }

    public int getSetNo() {
        return setNo;
    }

    public void setSetNo(int setNo) {
        this.setNo = setNo;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getSeca() {
        return seca;
    }

    public void setSeca(String seca) {
        this.seca = seca;
    }

    public String getSecb() {
        return secb;
    }

    public void setSecb(String secb) {
        this.secb = secb;
    }

    public String getSecc() {
        return secc;
    }

    public void setSecc(String secc) {
        this.secc = secc;
    }

    public String getSecd() {
        return secd;
    }

    public void setSecd(String secd) {
        this.secd = secd;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }
}
