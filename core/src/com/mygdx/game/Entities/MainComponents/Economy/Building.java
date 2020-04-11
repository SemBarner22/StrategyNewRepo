package com.mygdx.game.Entities.MainComponents.Economy;

public class Building {
    // тут идут вообще все возможные модификаторы, которые могут получить здания. Тогда здание это набор цифр, обозначающий характеристимки в заданной последовательности


    // все остальное
    private int level = 0;
    private int classOf;
    private int time = 0;

    public void Upgrage(){
        level++;
    }
    public void StartUpgrading(){
        time = level;
    }
    public void Turn(){
        if (time !=0){
            time--;
        }
    }

    public Building(int classOf) {
        this.classOf = classOf;
    }

    public int getLevel() {
        return level;
    }

    public int getClassOf() {
        return classOf;
    }

    public int getTime() {
        return time;
    }
}
