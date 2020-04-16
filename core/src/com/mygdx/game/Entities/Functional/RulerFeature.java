package com.mygdx.game.Entities.Functional;

public class RulerFeature {
    public RulerFeature(int num, int bonus, int value, String name) {
        this.bonus = bonus;
        this.num = num;
        this.name = name;
        this.value = value;
    }
    private int num;
    private int bonus;
    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public int getBonus() {
        return bonus;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
}
