package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;

public class Law {
    public Law(boolean activation, int[] modifNum, int[] modif, String whoNeed) {
        this.activation = activation;
        this.modifNum = modifNum;
        this.modif = modif;
        this.whoNeed = whoNeed;
    }

    private boolean activation = false;
    private int[] modifNum;
    private int[] modif;
    private String whoNeed;
    private String name;

    public boolean isActivation() {
        return activation;
    }

    public String getWhoNeed() {
        return whoNeed;
    }

    public void setActivation(boolean activation) {
        this.activation = activation;
    }

    public int[] getModif(){
        int[] result = new int[BS.numMod];
        for (int i = 0; i < modif.length; i++){
            result[modifNum[i]] = modif[i];
        }
        return result;
    }

    public String getName() {
        return name;
    }
}
