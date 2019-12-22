package com.mygdx.game.Entities.Adv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Financier extends Advisor  {

    public Financier() {
        numChar = 2;
        ability = (int) (Math.random() * numChar);
        abilityName = abilityNames.get(ability);
        if (ability == 0){
            mod[1] = 90;
        }
        if (ability == 1){
            mod[19] = 10;
        }
        if (ability == 2){
            mod[21] = 10;
        }
    }
    private static List<String> abilityNames = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/AdvText/AbilityNames/Financier")))) {
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
