package com.mygdx.game.Entities.MainComponents.GovParts;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.GovParts.City;
import com.mygdx.game.Entities.MainComponents.Economy.Resources;
import com.mygdx.game.Entities.MainComponents.World;

public class Region {

    public Region(City[] city, int population, int resource, int capRes, int mineral, int capMin,
                  int religion, int culture, int owner, int numberOfRegion) {
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
        World.totalPopulation +=population;
        this.numberOfRegion = numberOfRegion;
        occupator = owner;
    }
    private int numberOfRegion;
    private int deIureControl;
    private int deFactoControl;

    private Position position;
    private City[] city;
    private int population;
    private int prosperity = 0;
    private int infrastructure = 1;
    private double autonomy;
    private double potentialAutonomy;

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
    private int taxes;

    private int religion;
    private int rebelLevel;
    private int culture;
    private boolean occupation;
    private int occupator;

    private int numberOfModificators = BS.regionModsNum;
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
    public boolean occupy(int cityNum, int country){
        city[cityNum].occupy(country);
        occupation = (cityNum == 0) && (country !=owner);
        return occupation;
    }

    // обновление населения
    private void updatePopulation(int rate){
        World.totalPopulation -= population;
        population = (int) (population*(1000+BS.populationRate+1.0*prosperity/3+rate)/1000);
        World.totalPopulation += population;
    }

    // ЭКОНОМИКА. Обычная экономика с убывающей отдачей
    public int regionProfit(int tax, int popRate){
        taxes=tax;
        updatePopulation(popRate);
        updateProfitMineral();
        updateProfitRR();
        int ret = (int) ((1.0*profitMineral+1.0*profitRR*autonomy/100)*tax/100);
        if (occupation){
            return ret/2;
        } else {
            return ret;
        }
    }
    private void updateProfitRR() {
        productionRR = (int) (1.0*RRTPF/100*capRes* Math.pow(1.0*population*prodResRate/10, 0.3));
        //System.out.println(productionRR);
        profitRR = (int) (productionRR * Resources.getValueRR(resource));
    }
    private void updateProfitMineral() {
        productionMin = (int) (1.0*MinTPF/100* capMin*Math.pow(1.0*population*prodMinRate/10, 0.2));
        //System.out.println(productionMin);
        profitMineral = (int) (productionMin*Resources.getValueMineral(mineral));
    }
    //напишем чему равен спрос в городе на товары. Для начала C=Y-G-s. Но как будет распределено? По соотношению долей
    //цент. то есть если цена p, а сумма всех цент P, то спрос на этот товар будет C*p/P. P лежит в ресурсах
    private int CA = 0;
    private void regionDemand2(){
        int C = productionMin+productionRR;
        double[] reversedPrices = maximisationUtility();
        //System.out.println("Consumption "+C);
        for (int i = 0; i <BS.numberOfRR;i++){
            World.totalRRDemand[i] += (int) (1.0*C*reversedPrices[i+BS.numberOfMineral]);
            CA+=(int) (1.0*C*reversedPrices[i+BS.numberOfMineral]);
        }
        for (int i = 0; i <BS.numberOfCR;i++){
            World.totalCRDemand[i] += (int) (1.0*C*reversedPrices[i+BS.numberOfMineral+BS.numberOfRR]);
            CA +=(int) (1.0*C*reversedPrices[i+BS.numberOfMineral+BS.numberOfRR]);
        }
        for (int i = 0; i <BS.numberOfMineral;i++){
            World.totalMineralDemand[i] += (int) (1.0*C*reversedPrices[i]);
            CA+=(int) (1.0*C*reversedPrices[i]);
        }
    }
    private void regionDemand(){
        int C = productionMin+productionRR;
        double x = 0;
        for (int i = 0; i <BS.numberOfRR;i++){
            World.totalRRDemand[i] += (int) (1.0*C*Resources.getValueRR(i)/Resources.getTotalValue());
            x+=Resources.getValueRR(i)/Resources.getTotalValue();
            CA -=(int) (1.0*C*Resources.getValueRR(i)/Resources.getTotalValue());
        }
        for (int i = 0; i <BS.numberOfCR;i++){
            World.totalCRDemand[i] += (int) (1.0*C*Resources.getValueCR(i)/Resources.getTotalValue());
            x+=Resources.getValueCR(i)/Resources.getTotalValue();
            CA-=(int) (1.0*C*Resources.getValueCR(i)/Resources.getTotalValue());
        }
        for (int i = 0; i <BS.numberOfMineral;i++){
            World.totalMineralDemand[i] += (int) (1.0*C*Resources.getValueMineral(i)/Resources.getTotalValue());
            x+=Resources.getValueMineral(i)/Resources.getTotalValue();
            CA-= (int) (1.0*C*Resources.getValueMineral(i)/Resources.getTotalValue());;
        }
        //System.out.println("Pt"+x);

    }
    private double[] maximisationUtility(){
        double[] reversedPrices = new double[BS.numberOfMineral+BS.numberOfRR+BS.numberOfCR];
        for (int i = 0; i< BS.numberOfMineral; i++){
            reversedPrices[i] = 1/Resources.getValueMineral(i);
        }
        for (int i = BS.numberOfMineral; i< BS.numberOfRR+BS.numberOfMineral; i++){
            reversedPrices[i] = 1/Resources.getValueRR(i-BS.numberOfMineral);
        }
        for (int i = BS.numberOfRR+BS.numberOfMineral; i< BS.numberOfCR+BS.numberOfRR+BS.numberOfMineral; i++){
            reversedPrices[i] = 1/Resources.getValueCR(i-BS.numberOfRR-BS.numberOfMineral);
        }
        double sumRP = 0;
        for (double i: reversedPrices){
            sumRP += i;
        }
        for (int i = 0; i < reversedPrices.length; i++){
            reversedPrices[i] /=sumRP;
        }
        return reversedPrices;
    }
    public void updatePD(){
        World.totalRegionProduction[resource] += productionRR;
        World.totalMineralProduction[mineral] += productionMin;
        CA = productionRR+productionMin;
        regionDemand2();
        //System.out.println("Region CA"+CA);
    }


    //Действия с экономикой. Снижение автономии, донат денег в капитал
    private boolean posDecAut(){
        boolean result = true;
        //TODO сделать проверку на модификатор
        if (autonomy < 20 && result){
            result = false;
        }
        return result;
    }
    public void decreaseAutonomy(){
        if (posDecAut()) {
            autonomy = Math.max(autonomy - 20, 0);
            System.out.println("posDecAut Succeeded");
            //TODO create mod that increase rebel level
        } else {
            System.out.println("posDecAut Failed");
        }
    }
    public int costOfCapitalDonate(City city){
        return Math.max((city.getPotentialStock() - city.getStock())/10, 1000);
    }
    public void capitalDonate(City city){
        city.investStock((int) (costOfCapitalDonate(city)*(100-autonomy)/100));
    }




    public void setAutonomy(double autonomy) {
        potentialAutonomy = autonomy;
        if (Math.abs(this.autonomy - potentialAutonomy) < 0.1){
            this.autonomy = potentialAutonomy;
        } else {
            this.autonomy += (autonomy - potentialAutonomy) / 10;
        }
        for (City city: city) {
            city.setAutonomy(this.autonomy);
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
    //для вывода на экран регионов.
    public String[] getRegionScreen(){
        String[] res = new String[21];
        res[0] = "" + city.length;
        res[1] = "" + productionRR;
        res[2] = "" + profitRR;
        res[3] = "" + productionMin;
        res[4] = "" + profitMineral;
        res[5] = "" + BS.cultureNames[culture];
        res[6] = "" + BS.religionNames[religion];
        res[7] = "" + population;
        int totalPop=0;
        for (City value: city){
            totalPop+=value.getPopulation();
        }
        res[8] = "" +  (totalPop + population);
        res[9]  ="" + infrastructure;
        res[10] = "" + prosperity;
        res[11] = "" + ((int) autonomy);
        return res;
    }
    public int getTotPop(){
        int totalPop=0;
        for (City value: city){
            totalPop+=value.getPopulation();
        }
        return  totalPop + population;
    }
    public double getPotentialAutonomy() {
        return potentialAutonomy;
    }

    public int getNumberOfRegion() {
        return numberOfRegion;
    }
}

