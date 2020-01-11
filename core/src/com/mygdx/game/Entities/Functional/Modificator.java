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
        modifiers = new int[2][numMod.length];
        modifiers[0] = numMod;
        modifiers[1] = levMod;
    }
    public String name;
    public int number;
    private int[] modificator;
    private boolean is = false;
    private int time;
    private int maxTime;
    private int[][] modifiers;

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

    //остается для работы модификаторов в регионе и городе. В некоторых случаях это удобнее
    public int[] getModificator() {
        return modificator;
    }

    public String getName() {
        return name;
    }
    public int[] getNumMod(){
        return modifiers[0];
    }
    public int[] getLevMod(){
        return modifiers[1];
    }
}
