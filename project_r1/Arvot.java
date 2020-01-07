package com.example.project_r1;

public class Arvot {

    private String nimi;
    private int arvo;

    /**
     * Luo Arvo-olion
     * @param nimi annetaan Arvolle nimi
     * @param arvo annetaan Arvolle arvo
     */
    public Arvot(String nimi, int arvo) {
        this.arvo=arvo;
        this.nimi=nimi;
    }

    /**
     *
     * @return palauttaa Arvo-olion arvon
     */
    public int getArvo() {
        return arvo;
    }

    /**
     *
     * @return palauttaa Arvo-olion nimen
     */
    public String getNimi() {
        return nimi;
    }

    /**
     *
     * @param arvo muuttaa Arvo-olion arvon
     */
    public void setArvo(int arvo) {
        this.arvo = arvo;
    }

    /**
     * Lisää arvoon yhden
     */
    public void plus() {
        this.arvo++;
    }

    /**
     *
     * @return palauttaa Arvo-olion nimen kun sitä kutsutaan
     */
    public  String toString() {
        return getNimi();
    }
}

