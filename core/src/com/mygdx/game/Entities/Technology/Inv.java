package com.mygdx.game.Entities.Technology;

import com.mygdx.game.Entities.BaseSettings.BS;

public class Inv {
    public Inv(int cost, int numBon, int bon, String name) {
        this.cost = cost;
        this.numBon = numBon;
        this.bon = bon;
        this.name = name;
    }

    private int cost;
    private String name;
    private boolean made = false;
    private int numBon;
    private int bon;
    public void Made(){
        made = true;
    }

    public boolean isMade() {
        return made;
    }

    public int getCost() {
        return cost;
    }

    public int getNumBon() {
        return numBon;
    }

    public int getBon() {
        return bon;
    }
    public String getBonName(){
        return BS.nameMod[numBon] + bon;
    }

    public String getName() {
        return name;
    }
}
