package com.mygdx.game.Entities.Estate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Proles extends Estate{
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
        if (ability[num].getTime() == 0){
            mod[3] = - 10;
        }
    }
    public void UpdateBonus(){
        if (partOfPover >= 500 && loyality >= 300){
        } else if (partOfPover >= 400 && loyality >= 300){
            mod[3] = -1;
        } else if (partOfPover >= 300 && loyality >= 300){
            mod[3] = -2;
        } else if (partOfPover >= 100 && loyality >= 300){
            mod[3] = -3;
        } else if (partOfPover < 100 && loyality >= 300){
            mod[3] = -5;
        } else if (loyality < 100){
            mod[3] = 5;
        } else if (loyality < 200){
            mod[3] = 3;
        } else {
            mod[3] = 1;
        }
    }
    public List<String> getAbilityName() {
        return abilityName;
    }

}
