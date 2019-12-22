package com.mygdx.game.Entities.Adv;

public class Scientist extends Advisor  {
    private int baseNumberOfAdvisorChar = 1;

    public Scientist() {
        name = "Ildar Zagretdinov";
        ability = (int) (Math.random() * baseNumberOfAdvisorChar);

    }
}
