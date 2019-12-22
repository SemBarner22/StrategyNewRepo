package com.mygdx.game.Entities.Adv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Diplomat extends Advisor  {

    public Diplomat() {
        numChar = 1;
        ability = (int) (Math.random() * numChar);
        abilityName = abilityNames.get(ability);
        if (ability == 0){
            mod[5] = 50;
            abilityName += 50;
        }
    }
    private static List<String> abilityNames = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/AdvText/AbilityNames/Diplomat")))) {
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
}
