package com.mygdx.game.Entities.MainComponents.GovComponents;

public class Economy {
    private int labor;
    private int stock;
    private int tfp;
    private int amort = 1;
    private int saveRate = 3;
    private int popG = 10;
    private int gdp = 1;
    private double scientists = 10;
    private int govSp = 1;
    private double growth = 0;

    public Economy(int labor, int stock, int tfp) {
        this.labor = labor;
        this.stock = stock;
        this.tfp = tfp;
        CountGdp();
    }
    private void CountGdp(){
        int gr = gdp;
        gdp = (int) (tfp / 100 * Math.pow(stock, 0.34) * Math. pow((100-scientists) *labor/100, 0.66)/10);
        //System.out.println();
        growth = (1.0*gdp-gr)*100/gr;
    }
    public void ReCount(){
        scientists += Math.random()/2 - 0.25;
        int gr = tfp;
        tfp += (int) (labor * Math.pow(scientists, 0.25) /10000);
        govSp = 0;
        stock += (int) (((gdp-govSp) * saveRate * (Math.random() +3)/4 - amort*stock* (Math.random() +3)/4)/10);
        if (stock < 1){
            stock = 100;
        }
        labor = (int) ((labor*(1000+popG)/1000));
        CountGdp();
        String st = gdp/labor +" ";
        System.out.println(st);
        String str = Integer.toString(tfp)+ " "+ Integer.toString(stock)+" "+ Integer.toString(labor)+" "+ Integer.toString(gdp);
        System.out.println(str);
    }

    public int getLabor() {
        return labor;
    }

    public int getStock() {
        return stock;
    }

    public int getTfp() {
        return tfp;
    }

    public int getAmort() {
        return amort;
    }

    public int getGdp() {
        return gdp;
    }
}
