package com.mygdx.game.Entities.Estate;

public class Ability {
    private int time;
    private boolean isActivate = false;

    public Ability() {}

    public int getTime() {
        return time;
    }

    public boolean Activate() {
        return isActivate;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void Time(){
        if (time != 0){
            time--;
        } else {
            isActivate = false;
        }
    }
    public void Activate(int time){
        this.time = time;
        isActivate = true;
    }
}
