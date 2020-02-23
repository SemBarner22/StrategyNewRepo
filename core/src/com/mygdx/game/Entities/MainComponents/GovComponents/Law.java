package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;

public class Law {
    //TODO соответствино считывается из файла в этом порядке. Для каждой страны законы должны быть уникальными,
    //поэтому передавать надо копию, которую 1 раз прочитали в ворлде. Файл создан
    public Law(boolean activation, int[] modifNum, int[] modif, String whoNeed, String whoDontNeed, String name) {
        this.activation = activation;  //по умолчанию фолс, но может быть и тру
        this.modifNum = modifNum;
        this.modif = modif;
        this.whoNeed = whoNeed;
        this.whoDontNeed = whoDontNeed;
        this.name = name;
    }

    private boolean activation = false;
    private int[] modifNum;
    private int[] modif;
    private String whoNeed;
    private String whoDontNeed;
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

    public String getWhoDontNeed() {
        return whoDontNeed;
    }
}
