package com.winiarza.asystent.asystentwiniarza.Firebase.ModelFirebase;

public class Recipes {
    private int ilosc;
    private String jednostka;
    private String nazwa;
    public Recipes(){

    }
    public Recipes(int ilosc, String jednostka, String nazwa) {
        this.ilosc = ilosc;
        this.jednostka = jednostka;
        this.nazwa = nazwa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

}
