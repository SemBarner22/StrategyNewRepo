package com.mygdx.game.Entities.Adv;

public class Spy extends Advisor {
    private int baseNumberOfAdvisorChar = 1;

    public Spy() {
        name = "Ildar Zagretdinov";
        ability = (int) (Math.random() * baseNumberOfAdvisorChar);

    }
}
