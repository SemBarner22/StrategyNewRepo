package com.mygdx.game.Entities.Functional;

public class Debt {
    private int sum;
    private int interest;
    private int time;

    public Debt(int sum, int interest, int time){
        this.sum = sum;
        this.interest = interest;
        this.time = time;
    }
    //something unnecessary
    //T4d5hy43

    //unnecessary too

    public void PayDay(){
        time -=1;
    }

    public int getSum() {
        return sum;
    }

    public int getInterest() {
        return interest;
    }

    public int getTime() {
        return time;
    }
}
