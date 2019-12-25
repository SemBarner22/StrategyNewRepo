package com.mygdx.game.Entities.MainComponents.GovComponents;

public class Economy {
    private int labor;
    private int stock;
    private int tfp;
    private int amort = 20;
    private int saveRate = 3;
    private int popG = 10;
    private int gdp = 1;
    private double scientists = 10;
    private double growth = 0;

    public Economy(int labor, int tfp) {
        this.labor = labor;
        this.stock = (int) (saveRate*Math.pow(labor, 1.5)*tfp/amort);
        this.tfp = tfp;
        CountGdp(0);
    }
    private void CountGdp(int prosperity){
        int gr = gdp;
        gdp = (int) (1.0*tfp / 100 * Math.pow(stock, 0.34) * Math. pow((100-scientists) *labor/100, 0.66) * (100+prosperity)/1000);
        //System.out.println();
        growth = (1.0*gdp-gr)*100/gr;
    }
    public int ReCount(int govSp, int prosperity, int infrastructure, int rebel, int education, int population){
        scientists += Math.random()/2 - 0.25 +education *1.0/200;
        int gr = tfp;
        tfp += (int) (labor * Math.pow(scientists, 0.25) /10000);
        stock += (int) (((gdp-govSp)*1.0 * (saveRate*10-rebel+prosperity)/10 * (Math.random() +3)/4 - 1.0*(amort-infrastructure)/ 10 *stock* (Math.random() +3)/4)/10);
        if (stock < 1){
            stock = 100;
        }
        labor = population;
        CountGdp(prosperity);
        return gdp;
    }

    public int getSaveRate() {
        return saveRate;
    }

    public int getTfp() {
        return tfp;
    }

    public int getGdp() {
        return gdp;
    }

    public void setLabor(int labor) {
        this.labor = labor;
    }
}
