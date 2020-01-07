package com.mygdx.game.Entities.Adv;

import com.mygdx.game.Entities.BaseSettings.BS;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Advisor {
    protected String name = names.get((int) (Math.random() * names.size()));
    protected int level = ((int) (Math.random() * 15)) % 10;
    protected int ability = 0;
    protected int age = (int) (Math.random() * 40) + 100;
    protected int haveJob = -1;
    protected int numChar;
    protected String abilityName;
    // выводить возраст надо деля на 4

    //список возможных бафов
    protected int[] mod = new int[BS.numMod];

    public boolean Death(){
        return (Math.random() * 1000000 - (age*age*age)/64 > 0);
    }
    public void AgeUp(){
        age++;
    }
    private static List<String> names = new ArrayList<>();
    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/AdvText/Names")))) {

            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                names.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getAge() {
        return age/4;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getAbility() {
        return ability;
    }

    public void setHaveJob(int haveJob) {
        this.haveJob = haveJob;
    }

    public int getHaveJob() {
        return haveJob;
    }

    public String getAbilityName(){
        return abilityName == null ? "No advisor" : abilityName;
    }

    public int[] getMod() {
        return mod;
    }

    public int getNumChar() {
        return numChar;
    }
}
