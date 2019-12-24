package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.GovComponents.City;
import com.mygdx.game.Entities.MainComponents.World;

public class Region {

    public Region(City[] city, int population, int resource, int capRes, int mineral, int capMin,
                  int religion, int culture, int owner) {
        this.city = city;
        position = city[0].getPosition();
        this.population = population;
        this.resource = resource;
        this.capRes = capRes;
        this.mineral = mineral;
        this.capMin = capMin;
        this.religion = religion;
        this.culture = culture;
        this.owner = owner;
    }

    private Position position;
    private City[] city;
    private int population;
    private int prosperity = 0;
    private int infrastructure = 1;
    private int autonomy;

    private int resource;
    private int capRes;
    private int productionRR;
    private int profitRR;
    private int RRTPF = 1000;
    private int mineral;
    private int capMin;
    private int productionMin;
    private int profitMineral;
    private int MinTPF = 1000;
    private int prodResRate = 5;
    private int prodMinRate = 5;
    private int owner;

    private int religion;
    private int rebelLevel;
    private int culture;
    private boolean occupation;

    private int numberOfModificators = 0;
    private Modificator[] modificator = new Modificator[numberOfModificators];

    public void updateRegion(){
        Prosperity();
    }

    private void Prosperity(){
        int i = (int) (Math.random() * 100);
        if (i < 10 - prosperity){
            prosperity++;
        }
    }
    public int CostOfInfrastructure(){
        return (int) (Math.pow(1.4, infrastructure) * BS.baseCostInfrasructure * (100 - 5* prosperity) / 100);
    }
    public void UpgradeInfrastructure(){
        infrastructure++;
        for (int i = 0; i <10; i++){
            Prosperity();
        }
    }

    public boolean ExchangeReligion(int prob){
        int i = (int) (Math.random() * 100);
        int mod = 0;
        for (int j = 0; j < modificator.length; j++){
            mod += modificator[j].getModificator()[17];
        }
        if (i < prob - rebelLevel + mod){
            return true;
        } else{
            return false;
        }
    }
    public boolean ExchangeCulture(int prob){
        int i = (int) (Math.random() * 1000);
        int mod = 0;
        for (int j = 0; j < modificator.length; j++){
            mod += modificator[j].getModificator()[18];
        }
        if (i < prob - rebelLevel * 2 + mod){
            return true;
        } else{
            return false;
        }
    }
    public void UpdateRebelLevel(int level){
        rebelLevel = 0;
        for (Modificator value : modificator) {
            rebelLevel += value.getModificator()[3];
        }
        rebelLevel -= prosperity - level;
    }

    // обновление населения
    private void updatePopulation(int rate){
        population *= 1000 + rate + BS.populationRate;
        population /= 1000;
    }

    // ЭКОНОМИКА. Обычная экономика с убывающей отдачей
    public int regionProfit(int tax){
        updateProfitMineral(tax);
        updateProfitRR(tax);
        if (occupation){
            return (profitMineral+profitRR)/2;
        } else {
            return profitMineral + profitRR;
        }
    }
    private void updateProfitRR(int tax) {
        productionRR = (int) (RRTPF*capRes* Math.pow(1.0*population*prodResRate/10, 0.6));
        profitRR = productionRR * World.valueRR[mineral] *tax *autonomy/10000;
    }
    private void updateProfitMineral(int tax) {
        productionMin = (int) (MinTPF* capMin*Math.pow(1.0*population*prodMinRate/10, 0.4));
        profitMineral = productionMin*World.valueMineral[mineral]*tax/100;
    }
    public void updatePD(){
        World.totalRegionProduction[resource] += productionRR;
        World.totalMineralProduction[mineral] += productionMin;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
        for (City city: city) {
            city.setAutonomy(autonomy);
        }
    }

    public Position getPosition() {
        return position;
    }
    public int getReligion() {
        return religion;
    }
    public void setReligion(int religion) {
        this.religion = religion;
    }
    public int getCulture() {
        return culture;
    }
    public void setCulture(int culture) {
        this.culture = culture;
    }
    public void ActivateModificator(int i){
        modificator[i].Activate();
    }
    public boolean isOccupation() {
        return occupation;
    }
    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }
    public City[] getCity() {
        return city;
    }
    public int getOwner() {
        return owner;
    }
}

