package com.mygdx.game.Entities.Estate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Manufactor extends Estate {
    private int modProfit = 0;
    private int modProfitFromProduction = 0;
    private int numAbility = 5;


    public Manufactor() {
        ability = new Ability[numAbility];
        for (int i = 0; i < numAbility; i++){
            ability[i] = new Ability();
        }
        profit = 10;
    }
    private static List<String> abilityName = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/EstatesText/AbilityNames/Manufactor")))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                abilityName.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void ActivateAbility(int num){
        if (num == 0 && ability[0].getTime() == 0){
            ability[num].Activate(10);
            modProfit = 3;
            power += 100;
        }
        if (num == 1 && ability[1].getTime() == 0){
            ability[num].Activate(15);
            modProfitFromProduction = 10;
            power += 150;
            loyality -= 50;
        }
        if (num == 2 && ability[2].getTime() == 0){
            ability[num].Activate(10);
            loyality -=150;
            plusMoney = partOfPover;
        }
        if (num == 4 && ability[4].getTime() == 0){
            ability[num].Activate(15);
            loyality -=50;
            power +=100;
            financier = true;
        }
    }
    @Override
    public void UpdateBonus(){
        if (ability[0].getTime() == 7){
            modProfit = 0;
        }
        if (ability[2].getTime() == 5){
            modProfit = 0;
        }

        if (partOfPover >= 500 && loyality >= 300){
            profit = 7;
            mod[19] = modProfitFromProduction + 50;
        } else if (partOfPover >= 400 && loyality >= 300){
            profit = 8;
            mod[19] = modProfitFromProduction + 40;
        } else if (partOfPover >= 300 && loyality >= 300){
            profit = 9;
            mod[19] = modProfitFromProduction + 25;
        } else if (partOfPover >= 100 && loyality >= 300){
            profit = 10;
            mod[19] = modProfitFromProduction +15;
        } else if (partOfPover < 100 && loyality >= 300){
            profit = 10;
            mod[19] = modProfitFromProduction + 10;
        } else if (loyality < 100){
            mod[19] = -20;
            profit = 7;
        } else if (loyality < 200){
            mod[19] = -10;
            profit = 9;
        } else {
            profit = 10;
            mod[19] = 0;
        }
    }

    public List<String> getAbilityName() {
        return abilityName;
    }

}
/*

        Из того, что надо сделать при создании сословия\
private static List<String> abilityName = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("src\\Estate\\Texts\\AbilityNames\\Manufactor")))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                abilityName.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ActivateAbility(int num){
        if (num == 0 && ability[0].getTime() == 0){
            ability[num].Activate();

        }
        if (num == 1 && ability[1].getTime() == 0){
            ability[num].Activate();

        }
        if (num == 2 && ability[2].getTime() == 0){
            ability[num].Activate();

        }
        if (num == 3 && ability[3].getTime() == 0){
            ability[num].Activate();

        }
        if (num == 4 && ability[4].getTime() == 0){
            ability[num].Activate();
            l
        }
    }
    public void UpdateBonus(){
    if (partOfPover >= 500 && loyality >= 300){
        } else if (partOfPover >= 400 && loyality >= 300){
        } else if (partOfPover >= 300 && loyality >= 300){
        } else if (partOfPover >= 100 && loyality >= 300){
        } else if (partOfPover < 100 && loyality >= 300){
        } else if (loyality < 100){
        } else if (loyality < 200){
        } else {
        }
        }
    public List<String> getAbilityName() {
        return abilityName;
    }

        */