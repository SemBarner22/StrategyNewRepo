package com.mygdx.game.Entities.MainComponents.Diplonacy;

import java.util.ArrayList;

public class Subdip {
    public Subdip() {
        ally = false;
        war = false;
        opinion = 0;
    }

    private boolean ally;
    private boolean askForAlly = false;
    private boolean declareWar = false;
    private boolean askForPeace = false;
    private boolean war;
    private int opinion;
    private ArrayList<ModDip> modDips = new ArrayList<>();

    //turn происходит до!!! хода
    public void preTurn(){
        for (ModDip value: modDips){
            value.turn();
        }
        getOpinion();
        if (opinion < 0){
            //TODO Even breaking ally
            ally = false;
        }
        if (declareWar){
            war = true;
            declareWar = false;
        }
        if (askForPeace){
            //TODO create event for offer of peace
            war = false;
            askForPeace = false;
        }
        if (askForAlly){
            //TODO create event for offer of ally
            ally = true;
            askForAlly = false;
        }
    }

    public int getOpinion(){
        opinion = 0;
        for (ModDip value: modDips){
            opinion += value.getMod();
        }
        return opinion;
    }
    public boolean posAlly(){
        return (opinion >= 0) && (!war);
    }

    public void setMod(int mod, int len, String name){
        ModDip md = new ModDip(len, name, mod);
        for (ModDip value: modDips){
            if (md.getName().equals(value.getName())){
                modDips.remove(value);
            }
        }
        modDips.add(md);
    }

    public void setAskForAlly(boolean askForAlly) {
        this.askForAlly = askForAlly;
    }

    public void setDeclareWar(boolean declareWar) {
        this.declareWar = declareWar;
    }

    public void setAskForPeace(boolean askForPeace) {
        this.askForPeace = askForPeace;
    }

    public void breakAlly(){
        ally = false;
    }

    public ArrayList<String> getMods(){
        ArrayList<String> res = new ArrayList<>();
        for (ModDip value: modDips){
            res.add(value.getAll());
        }
        return res;
    }

    public boolean isAlly() {
        return ally;
    }

    public boolean isWar() {
        return war;
    }
}
