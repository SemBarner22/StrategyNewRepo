package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BaseFeatures;
import com.mygdx.game.Entities.Functional.RulerFeature;

import java.util.ArrayList;

public class Ruler {
    private String name;
    private int start;
    private int age;
    private ArrayList<RulerFeature> features = new ArrayList<>();

    public Ruler(String name, int age) {
        this.name = name;
        this.start = age;
        this.age = age;
    }
    private void addFeature(){
        int i = (int)(Math.random()*BaseFeatures.rulerFeatures.size());
        boolean repeat = true;
        int safe = 0;
        while (repeat){
            i = (int)(Math.random()*BaseFeatures.rulerFeatures.size());
            repeat = false;
            for (RulerFeature value: features){
                if (i == value.getNum()){
                    repeat = true;
                }
            }
            safe += 1;
            if (safe == 50){
                break;
            }
        }
        if (safe < 50) {
            features.add(BaseFeatures.rulerFeatures.get(i));
        }
    }
    public boolean upAge(){
        age += 1;
        if ((age-start) % 10 == 0){
            addFeature();
        }
        return death();
    }
    private boolean death(){
        if (Math.random()*100 < age){
            return true;
        }
        else{
            return false;
        }
    }
}
