package com.mygdx.game.Entities.MainComponents.Diplonacy;

public class ModDip {
    public ModDip(int length, String name, int mod) {
        this.length = length;
        this.name = name;
        this.mod = mod;
    }

    private int length;
    private String name;
    private int mod;

    public void turn(){
        if (mod>0 && length > 0){
            mod = (int) (1.0*mod*(length-1)/length);
        } else {
            mod = 0;
            length = 0;
        }
    }
    public boolean shouldRemove(){
        if (mod == 0 || length == 0){
            return true;
        } else {
            return false;
        }
    }

    public String getAll(){
        return ""+mod + " - " + name + ". Duration "+length + ". " ;
    }

    public int getMod() {
        return mod;
    }

    public String getName() {
        return name;
    }
}
