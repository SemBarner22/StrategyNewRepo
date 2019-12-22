package com.mygdx.game.Entities;

import com.mygdx.game.Entities.Adv.General;
import com.mygdx.game.Entities.Estate.Estate;
import com.mygdx.game.Entities.Estate.Manufactor;
import com.mygdx.game.Entities.Estate.Generals;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        Estate[] estates = new Estate[2];
        estates[0] = new Manufactor();
        estates[1] = new Generals();
        System.out.println(estates[1].getAbilityName());
    }

}
