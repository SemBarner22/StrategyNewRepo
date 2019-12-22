package com.mygdx.game.Entities.Functional;

public class Modificator {
    public Modificator(int number, String name, int maxTime, int numberOfMod, int[] numMod, int[] levMod) {
        modificator = new int[numberOfMod];
        for (int i = 0; i < numMod.length; i++){
            modificator[numMod[i]] = levMod[i];
        }
        this.maxTime = maxTime;
        this.name = name;
        this.number = number;
    }
    public String name;
    public int number;
    private int[] modificator;
    private boolean is = false;
    private int time;
    private int maxTime;

    public void Activate(){
        time = maxTime;
        is = true;
    }
    public void Deactivate(){
        time = 0;
        is = false;
    }

    public void Turn(){
        if (time == 0) {
            time--;
        } else {
            Deactivate();
        }
    }

    public boolean getIs() {
        return is;
    }

    public int getTime() {
        return time;
    }

    public int[] getModificator() {
        return modificator;
    }
}
