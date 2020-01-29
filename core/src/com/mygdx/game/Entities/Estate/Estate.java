package com.mygdx.game.Entities.Estate;

import com.mygdx.game.Entities.BaseSettings.BS;

import java.util.ArrayList;
import java.util.List;

public abstract class Estate {
    protected String name;
    protected boolean isInLobby = false; // показывает является ли в частью лобби 1/0
    protected int power = 100;
    protected int powerIncrease = 0;
    protected int loyality = 500;
    protected int loyalityIncrease = 1;
    protected int maxLoyality = 500;
    protected Ability[] ability;
    protected int partOfPover;
    protected int profit = 10;
    private int basePoverIncrease = -20;
    // дальше идут бафы
    protected int[] mod = new int[BS.numMod];
    // генералы
    protected boolean general = false;

    // промышленники
    protected int plusMoney = 0;
    protected boolean manufatory = false;
    protected boolean financier = false;


    public void setPartOfPover(int partOfPover) {
        this.partOfPover = partOfPover;
    }

    public int getProfit() {
        return profit;
    }
    public void UpdateBonus(){
    }

    public void UpdateLP(){
        for (int i = 0; i < ability.length; i++){
            ability[i].Time();
        }
        if (loyality > maxLoyality){
            loyality -= loyalityIncrease;
        }
        if (loyality < maxLoyality){
            loyality += loyalityIncrease;
        }
        power += powerIncrease + BS.basePoverIncrease;
        if (power < 100){
            power = 100;
        }
    }

    public void ActivateAbility(int number){}

    public String getBonus(){
        return "Null";
    }
    public boolean getIsInLobby() {
        return isInLobby;
    }

    public int getPower() {
        return power;
    }

    public int getPowerIncrease() {
        return powerIncrease;
    }

    public int getLoyality() {
        return loyality;
    }

    public int getLoyalityIncrease() {
        return loyalityIncrease;
    }

    public int getMaxLoyality() {
        return maxLoyality;
    }

    public Ability[] getAbility() {
        return ability;
    }

    public int getPartOfPover() {
        return partOfPover;
    }

    public void setPowerIncrease(int poverIncrease) {
        this.powerIncrease = poverIncrease;
    }

    public void setLoyalityIncrease(int loyalityIncrease) {
        this.loyalityIncrease = loyalityIncrease;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPlusMoney() {
        return plusMoney;
    }

    public void setPlusMoney(int plusMoney) {
        this.plusMoney = plusMoney;
    }

    public boolean isManufatory() {
        return manufatory;
    }

    public void setManufatory(boolean manufatory) {
        this.manufatory = manufatory;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
    }

    public boolean isFinancier() {
        return financier;
    }

    public void setFinancier(boolean financier) {
        this.financier = financier;
    }

    public abstract List<String> getAbilityName();

    public int[] getMod() {
        return mod;
    }

    public String getName() {
        return name;
    }
}
