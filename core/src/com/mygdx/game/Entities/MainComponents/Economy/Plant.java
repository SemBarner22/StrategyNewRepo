package com.mygdx.game.Entities.MainComponents.Economy;


import com.mygdx.game.Entities.MainComponents.World;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Plant {
    private int resourceOfPlant;
    private int fR;
    private int sR;
    private String fClass; //RR CR Mineral
    private String sClass;
    private int levelOfPlant = 1;
    private int profit;
    private int upgradeTime;
    private String name;
    private int mod;

    // сделан для вывода дохода от одного предприятия
    public void UpdateProfit(){
        int costs = 0;
        if (upgradeTime == 0) {
            if (fClass.equals("RR")) {
                costs += World.valueRR[fR];
            }
            if (fClass.equals("CR")) {
                costs += World.valueCR[fR];
            }
            if (fClass.equals("Mineral")) {
                costs += World.valueMineral[fR];
            }
            if (sClass.equals("Mineral")) {
                costs += World.valueMineral[sR];
            }
            if (sClass.equals("RR")) {
                costs += World.valueRR[sR];
            }
            if (sClass.equals("CR")) {
                costs += World.valueCR[sR];
            }
            profit = (int) (levelOfPlant * (World.valueCR[resourceOfPlant] * (100 +mod) - costs));
        }
    }

    public int getDemand(String res, int number){
        if (upgradeTime == 0) {
            if (res.equals(fClass)) {
                if (number == fR) {
                    return levelOfPlant;
                }
            }
            if (res.equals(sClass)) {
                if (number == sR) {
                    return levelOfPlant;
                }
            }
        }
        if (upgradeTime != 0){
            upgradeTime--;
        }
        return 0;
    }
    public static List<String> lines;
    static {
        try {
            lines = Files.readAllLines(Paths.get("src\\Texts\\ResourceChain"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Plant(int resourceOfPlant) {
        this.resourceOfPlant = resourceOfPlant;
        upgradeTime = 2;
        String str[] = lines.get(resourceOfPlant*2).split(" ");
        name = lines.get(resourceOfPlant * 2 + 1);
        fR = Integer.parseInt(str[0]);
        fClass = str[1];
        sR = Integer.parseInt(str[2]);
        sClass = str[3];
        System.out.println(fR + fClass + sR + sClass);
    }

    public int getResourceOfPlant() {
        return resourceOfPlant;
    }

    public int getfR() {
        return fR;
    }

    public int getsR() {
        return sR;
    }

    public String getfClass() {
        return fClass;
    }

    public String getsClass() {
        return sClass;
    }

    public int getLevelOfPlant() {
        return levelOfPlant;
    }

    public void Upgrade(){
        levelOfPlant++;
        upgradeTime = levelOfPlant + 1;
    }

    public int getProfit() {
        return profit;
    }

    public int getUpgradeTime() {
        return upgradeTime;
    }

    public String getName() {
        return name;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }
}
