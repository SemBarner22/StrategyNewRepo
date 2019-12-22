package com.mygdx.game.Entities.Technology;

import com.mygdx.game.Entities.BaseSettings.BS;

public class SubTech {
    public SubTech(int num) {
        this.num = num;
        progress = 0;
    }

    private int num;
    private int level = 0;
    private int[] numBon = BS.numBonSubTech[this.num];
    private int[] bonus = new int[BS.numBonSubTech[num].length];
    private int progress;
    private void TechUp(){
        level++;
        bonus[level] = BS.bonSubTech[num][level];
    }

    public boolean Progress(){
        progress++;
        if (progress == 10){
            progress = 0;
            TechUp();
            return true;
        } else {
            return false;
        }
    }

    public int getProgress() {
        return progress;
    }

    public int getNum() {
        return num;
    }

    public int getLevel() {
        return level;
    }

    public int[] getNumBon() {
        return numBon;
    }

    public int[] getBonus() {
        return bonus;
    }
}

