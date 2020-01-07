package com.example.project_r1;

import java.util.ArrayList;

public class Lista {
    private static final Lista ourInstance = new Lista();
    private ArrayList<Arvot> lista;

    /**
     * Voidaan kutsua tätä singletonia muualta
     * @return palauttaa tämän olion
     */
    public static Lista getInstance() {
        return ourInstance;
    }

    /**
     * Luodaan Listas
     * Lisätään arvot listaan
     */
    private Lista() {
        lista = new ArrayList<>();
        lista.add(new Arvot("Pelaaminen",0));
        lista.add(new Arvot("Työnteko",0));
        lista.add(new Arvot("Nettisurffaus",0));
        lista.add(new Arvot("Videoiden katselu",0));
        lista.add(new Arvot("Streamien katselu",0));
    }

    /**
     * Luodaan metodi joka tuo singletonista sille tallennetun listan
     * @return palauttaa listan
     */
    public ArrayList<Arvot> getLista() {
        return lista;
    }
}

