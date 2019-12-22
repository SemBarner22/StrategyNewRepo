package com.mygdx.game.Entities.Adv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class General extends Advisor {
    private int fire;
    private int shock;
    private int tactic;
    private int mobility;
    private int[] bonus = new int[4];
    private int inArmy = -1;
    private String generalAbility;

    // тут идут характеристики генерала

    public General() {
        // создаем ххарактеристики при ведении армии
        tactic = level;
        fire = level + ((int) (Math.random()) * 6 - 3);
        if (fire < 0){
            fire = 0;
        }
        shock = level + ((int) (Math.random()) * 6 - 3);
        if (shock < 0){
            shock = 0;
        }
        mobility = level + ((int) (Math.random()) * 6 - 3);
        if (mobility < 0){
            mobility = 0;
        }
        int bon = (int) (Math.random() * 4);
        bonus[bon] = 1;
        generalAbility = gan.get(bon);
        // создаем характеристики советника
        numChar = 3;
        ability = (int) (Math.random() * numChar);
        abilityName = abilityNames.get(ability);
        if (ability == 0){
            mod[14] = 2;
        }
        if (ability == 1){
            mod[13] = 10;
        }
        if (ability == 2){
            mod[12] = 10;
        }
    }
    private static List<String> abilityNames = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/AdvText/AbilityNames/General")))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                abilityNames.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<String> gan = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/AdvText/GeneralAbilities")))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                gan.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getFire() {
        return fire;
    }

    public int getShock() {
        return shock;
    }

    public int getTactic() {
        return tactic;
    }

    public int getMobility() {
        return mobility;
    }

    public int[] getBonus() {
        return bonus;
    }

    public int getInArmy() {
        return inArmy;
    }

    public void setInArmy(int inArmy) {
        this.inArmy = inArmy;
    }

    public String getGeneralAbility() {
        return generalAbility;
    }
}
