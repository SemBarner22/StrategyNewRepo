package com.mygdx.game.Entities.MainComponents.GovComponents;

public class Resource {
    //для создания региональных и минералов
    public Resource() {
    }
    //для создания городских


    public Resource(int fResClass, int fRes, int sResClass, int sRes) {
        this.fRes = fRes;
        this.sRes = sRes;
        this.fResClass = fResClass;
        this.sResClass = sResClass;
    }

    private double value = 10;
    private int demand;
    private int supply;
    //1 - mineral, 0 - RR, 2 -CR
    private int fRes;
    private int sRes;
    private int fResClass;
    private int sResClass;

    public void countValue(){
        if (demand>supply){
            value = Math.max(value+0.1 *demand/supply, value+0.5);
        } else{
            value=Math.max(value-0.1*supply/demand, value-0.5);
        }
        if (value<0){
            value = 0.1;
        }
    }

    public double getValue() {
        return value;
    }

    public int getDemand() {
        return demand;
    }

    public int getSupply() {
        return supply;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    public int getfRes() {
        return fRes;
    }

    public int getsRes() {
        return sRes;
    }

    public int getfResClass() {
        return fResClass;
    }

    public int getsResClass() {
        return sResClass;
    }
    public int[] investCR(){
        int[] ret = {fResClass, fRes, sResClass, sRes};
        return ret;
    }
}
