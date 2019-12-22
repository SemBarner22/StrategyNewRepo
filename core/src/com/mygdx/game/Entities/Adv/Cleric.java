package com.mygdx.game.Entities.Adv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cleric extends Advisor {

    private static List<String> abilityNames = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/AdvText/AbilityNames/Cleric")))) {
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
    public Cleric() {
        numChar = 1;
        ability = (int) (Math.random() * numChar);
        abilityName = abilityNames.get(ability);
        if (ability == 0){
            mod[3] = -level / 2;
            abilityName += -(level / 2);
        }
        System.out.println("keek");
    }
}
/* что должно быть в каждом советнике

    private static List<String> abilityNames = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("src\\Adv\\Text\\AbilityNames\\Cleric")))) {
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



 */