package com.mygdx.game.Entities.MainComponents.Economy;

public class CityEconomy {
    private int labor;
    private int stock;
    private double tfp;
    private double amort = 0.2;
    private double saveRate = 0.3;
    private int popG = 10;
    private int gdp = 1;
    private double scientists = 10;
    private double growth = 0;
    private int taxes = 0;
    private int basePop;

    public CityEconomy(int labor, int tfp) {
        this.labor = labor;
        basePop=labor;
        this.stock = (int) (Math.pow(saveRate*Math.pow(labor, 0.66)*tfp/amort, 1.5)/1000);
        this.tfp = tfp;
        CountGdp(0, 0);
        //Экономика типо работает и без этого, но вообще метод, который физически вычисляет начальное оптимальное значение капитала
        /*for (int i = 0; i <1000; i++){
            amortisation();
            CountGdp(0, 0);
        }*/
        System.out.println("Base change of capital "+(gdp*1.0 * saveRate - 1.0*amort *stock));
    }
    public int getPotentialStock(){
        return (int) (Math.pow(saveRate*Math.pow(labor, 0.66)*tfp/amort, 1.5)/1000);
    }
    private void CountGdp(int prosperity, int tax){
        int gr = gdp;
        gdp = (int) (1.0*tfp/100 *(50-tax)/50 * Math.pow(stock, 0.34) * Math. pow((100-scientists) *labor/100, 0.66) * (100+prosperity)/100);
        growth = (1.0*gdp-gr)*100/gr;
    }
    private void amortisation(){
        stock +=(int) (gdp*1.0 * saveRate - 1.0*amort *stock);
    }
    public int ReCount(int govSp, int prosperity, int infrastructure, int rebel, int education, int population){
        taxes = govSp;
        scientists += Math.random()/2 - 0.25 +education *1.0/200;
        tfp += labor * Math.pow(scientists, 0.2) /basePop;
        //System.out.println("Stock Change"+(int) (gdp*1.0 * saveRate) +" Stock decrease"+ (1.0*amort *stock));
        //stock += (int) (gdp*1.0 *(50-govSp)/50* (saveRate*100-Math.max(rebel, 0)+prosperity)/100 - 1.0*(amort*100-infrastructure)/ 100 *stock);
        stock +=(int) (gdp*1.0 * saveRate - 1.0*amort *stock);
        if (stock < 1){
            stock = 100;
        }
        //System.out.println("GDP "+gdp+" Stock "+stock+" TFP "+tfp+" Scientists "+scientists + " Labour "+ labor + " tax " + govSp + " Rebel "+ rebel);
        labor = population;
        CountGdp(prosperity, govSp);
        return gdp;
    }

    public double getSaveRate() {
        return saveRate;
    }

    public double getTfp() {
        return tfp;
    }

    public int getGdp() {
        return gdp;
    }

    public void setLabor(int labor) {
        this.labor = labor;
    }

    public int getLabor() {
        return labor;
    }

    public int getStock() {
        return stock;
    }

    public double getScientists() {
        return scientists;
    }

    public double getGrowth() {
        return growth;
    }

    public int getTaxes() {
        return taxes;
    }

    public void increaseStock(int stock) {
        this.stock += stock;
    }
}
