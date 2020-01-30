package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.World;
import com.mygdx.game.Strategy;
import java.util.ArrayList;
/* текщая позиция по экономике такова - есть класс экономика и он принадлежит для каждого города. К региону эта штука не
применима, там используется такая же, только с фиксированным капиталом. Соответственно пока чтов се взаимодействие с
городом и регионом это установка налоговой ставки. Потом надо будет добавить различные эдикты
*/

public class City {
    public City(Position position, int population, int owner, int[] res) {
        this.position = position;
        this.population = population;
        this.owner = owner;
        this.res = res;
        economy = new Economy(population, 100);
        World.totalPopulation += population;
        updateEconomy(0, 0, 0);
        partArmy = new int[8];
        partArmy[0] = 1;
        laborStructure[0] = 0.33;
        laborStructure[1] = 0.33;
        laborStructure[2] = 0.34;
    }

    private boolean occupation = false;
    private int occupator;
    private int unicNumber;
    private Position position;
    private Building[] building = new Building[BS.numberOfBuildings];
    private ArrayList<Plant> plant = new ArrayList<>();
    private int population;
    private int infrastructure = 1;
    private int owner;
    private int prosperity = 1;
    private double autonomy=0;
    private int rebelLevel=0;
    private int[] partArmy;
    private boolean mobilisation = false;
    private Economy economy;
    private int profit;

    //новое производство
    private int production = 1;
    private int[] res;
    private double[] laborStructure = new double[3];

    private int numberOfModificators = 0;
    private Modificator[] modificator = new Modificator[numberOfModificators];

    private Position posArmy;


    //АРМИЯ
    public boolean CheckPosition(){
        for (int i = -1; i < 3; i++) {
            for (int j = -1; j < 3; j++) {
                if (i == -1 || i == 2 || j == -1 || j == 2) {
                    if (World.mof.CheckPosition(new Position(position.GetX() + i, position.GetY() + j)) == -1 && (i != 0 || j != 0)
                            && position.GetX() + i >= 0 && position.GetX() + i < Strategy.F_HEIGHT
                            && position.GetY() + j >= 0 && position.GetY() + j < Strategy.F_WIDTH) {
                        posArmy = new Position(position.GetX() + i, position.GetY() + j);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Position getPosArmy() {
        return posArmy;
    }
    public int getUnicNumber() {
        return unicNumber;
    }
    public int GetEquipment(){
        int equipment = 0;
        for (int i = 0; i<8; i++){
            equipment += BS.equipmentOfSquade[i] * population * BS.baseMobilisation * partArmy[i] / 100;
        }
        return equipment;
    }
    public int[] Mobilisation(){
        int[] armyMan = new int[8];
        for (int i = 0; i<8; i++){
            armyMan[i] = population * BS.baseMobilisation * partArmy[i] / 100;
        }
        return armyMan;
    }


    //Домики остаются. Пока я не знаю в каком виде, но
    public void UpdateModBuild(){
    }

    /*переходим к нормальной экономике. Автономия, просперити, здания, религия остаются. Фактически у нас есть общий выпуск, который ра
    спределяется между товарами в данном регионе. Однако никаких заводов у нас не будет. Вообще план такой, создаем экономику,
    радуемся жизни, проверяем
    Итак, есть содель ромера-Солоу, она нормально работает, учитывая, что один ход это четверть года. Теперь правда надо
    улучшить формуллу для выпуска, добавив туда инфраструтктуру (как чать микроменеджмента)
    Итак, ЭКОНОМИКА
    */
    public int updateEconomy(int taxes, int education, int popGrRate){
        production = economy.ReCount(taxes, prosperity, infrastructure, rebelLevel, education, population);
        Prosperity();
        updatePopulation(popGrRate);
        profit = (int) (economy.getGdp() * (Resources.getValueCR(res[0])*laborStructure[0]
                + Resources.getValueCR(res[1])*laborStructure[1] + laborStructure[2]*Resources.getValueCR(res[2])));
        return (int) (taxes * profit*autonomy/10000);
    }
    private void changeStructure(){
        for (int i = 0; i<3;i++){
            for (int j = 0; j<3;j++){
                if (Resources.getValueCR(res[i])>Resources.getValueCR(res[j])*1.03){
                    double change = 0.1 *(1- Resources.getValueCR(res[j])/Resources.getValueCR(res[i]));
                    laborStructure[i]+=change;
                    laborStructure[j]-=change;
                }
            }
        }
        for (int i = 0; i<3; i++){
            if (laborStructure[i] < 0){
                laborStructure[(i+1)%3] += laborStructure[i]/2;
                laborStructure[(i+2)%3] +=laborStructure[i]/2;
                laborStructure[i] = 0;
            }
        }
        laborStructure[2]=1-laborStructure[1]-laborStructure[0];
        //System.out.println("New structure "+ laborStructure[0]+" "+laborStructure[1]+" "+laborStructure[2]);
    }
    private void Prosperity(){
        int i = (int) (Math.random() * 100);
        if (i < 10 - prosperity){
            prosperity++;
        }
    }
    public void investStock(int invest){
        economy.increaseStock(invest);
    }
    //сейчас добавляем к общему производству мира
    //напишем чему равен спрос в городе на товары. Для начала C=Y-G-s. Но как будет распределено? По соотношению долей
    //цент. то есть если цена p, а сумма всех цент P, то спрос на этот товар будет C*p/P. P лежит в ресурсах
    private int CA =0;
    private int CA2=0;
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
    private void cityDemand(){
        int C = (int) (production *(10-economy.getSaveRate()*10)/10);
        double[] reversedPrices = maximisationUtility();
        //System.out.println("Consumption "+C);
        CA2+=C;
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
    private void investments(){
        int inv = (int) (production *economy.getSaveRate());
        //System.out.println("Investments "+inv);
        CA2+=inv;
        double totalValue=0;
        double priceChecker=0;
        double[] reversedPrices = new double[6];
        int numprice = 0;
        for (int resource: res){
            int[] info = Resources.investCR(resource);
            for (int i = 1; i<3; i++) {
                if (info[-2+2*i] == 0){
                     reversedPrices[numprice] = 1/Resources.getValueRR(info[2*i-1]);
                     numprice++;
                }
                if (info[-2+2*i] == 1){
                    reversedPrices[numprice] = 1/Resources.getValueMineral(info[2*i-1]);
                    numprice++;
                }
                if (info[-2+2*i] == 2){
                    reversedPrices[numprice] = 1/Resources.getValueCR(info[2*i-1]);
                    numprice++;
                }
                //System.out.println("Price checker "+priceChecker);
            }
        }
        double sumRP = 0;
        for (double i: reversedPrices){
            sumRP += i;
            //System.out.println(i);
        }
        for (int i = 0; i < reversedPrices.length; i++){
            reversedPrices[i] /=sumRP;
            //System.out.println(reversedPrices[i]);
        }
        numprice = 0;
        //System.out.println("TotalValue "+totalValue);
        for (int resource: res) {
            int[] info = Resources.investCR(resource);
            for (int i = 1; i<3; i++) {
                if (info[-2+2*i] == 0){
                    World.totalRRDemand[info[2*i-1]] += (int) (1.0*inv/Resources.getValueRR(info[2*i-1])/sumRP);
                    priceChecker+=1/Resources.getValueRR(info[2*i-1])
                            /sumRP;
                    CA+=(int) (1.0*inv/Resources.getValueRR(info[2*i-1])
                            /sumRP);
                }
                if (info[-2+2*i] == 1){
                    World.totalMineralDemand[info[2*i-1]] += (int) (1.0*inv/Resources.getValueMineral(info[2*i-1])
                            /sumRP);
                    priceChecker+=1/Resources.getValueMineral(info[2*i-1])
                            /sumRP;
                    CA+=(int) (1.0*inv/Resources.getValueMineral(info[2*i-1])
                            /sumRP);
                }
                if (info[-2+2*i] == 2){
                    World.totalCRDemand[info[2*i-1]] += (int) (1.0*inv/Resources.getValueCR(info[2*i-1])
                            /sumRP);
                    priceChecker+=1/Resources.getValueCR(info[2*i-1])
                            /sumRP;
                    CA+=(int) (1.0*inv/Resources.getValueCR(info[2*i-1])
                            /sumRP);
                }
            }
        }
        if (priceChecker < 0.9 | priceChecker >1.1){
            System.out.println("WARNING PRICE CHECKER IS NO 1. PRICECHECKER IS "+priceChecker);
        }
    }
    public void updatePD(){
        CA = 0;
        CA2 = -production;
        for (int i = 0; i < res.length; i++){
            World.totalCityProduction[i] +=(int) (1.0*production*laborStructure[i]);
            CA-=(int) (1.0*production*laborStructure[i]);
        }
        investments();
        cityDemand();
        checkCA();
        changeStructure();
    }
    private void checkCA(){
        if (Math.abs(CA)> production/100){
            System.out.println("WARNING CA IS NOT 0. CA is "+CA);
        }
        if (Math.abs(CA2)> production/100){
            System.out.println("WARNING CA2 IS NOT 0. CA is "+CA2);
        }
    }
    // обновление населения
    public void updatePopulation(int rate){
        World.totalPopulation -= population;
        population = (int) (population*(1000+BS.populationRate+1.0*prosperity/3+rate)/1000);
        World.totalPopulation += population;
        economy.setLabor(population);
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
    public void UpdateRebelLevel(int level){
        rebelLevel = 0;
        for (Modificator value : modificator) {
            rebelLevel += value.getModificator()[3  ];
        }
        rebelLevel -= prosperity - level;
    }
    // возвращает стоимость постройки здания
    public int CostOfBuilding(int number){
        return (int) (Math.pow(1.2, building[number].getLevel()) * BS.baseCostBuild[building[number].getClassOf()] *
                (100 - prosperity - 2*infrastructure) / 100);
    }
    //строит здание
    public void Build(int number){
        building[number].StartUpgrading();
        Prosperity();
    }
    public void BuildingTurn(){
        for (Building value : building) {
            if (value.getTime() == 1){
                value.Upgrage();
                UpdateModBuild();
            }
            value.Turn();
        }
    }
    public void occupy(int country){
        occupator = country;
        occupation = owner != country;
    }
    // возвращает стоимость постройки завода
    public int CostOfPlant(int number){
        if (plant.size() > number || plant.get(number) == null){
            return BS.baseCostPlant * (100 - 2*prosperity) / 100;
        } else{
            return (int) ((BS.baseCostPlant) * Math.pow(1.3, plant.get(number).getLevelOfPlant()) * (100 - prosperity - 2*infrastructure) / 100);
        }
    }
    public void newPlant(int resource){
        plant.add(new Plant(resource));
        Prosperity();
    }

    public int CostOfInfrastructure(){
        return (int) (Math.pow(1.4, infrastructure) * BS.baseCostInfrasructure * (100 - 5* prosperity) / 100);
    }
    public void UpgradeInfrastructure(){
        infrastructure++;
        Prosperity();
    }

/*  Старая экономика
    // методы для вывода общего дохода от производства
    public void UpdateProfitFromProduction(){
        profit = 0;
        for (int i =0; i < plant.size(); i++){
            plant.get(i).UpdateProfit();
            profit += plant.get(i).getProfit();
        }
        profit *= BS.baseProfitFromProduction;
    }
    // метод для вывода дохода от налогов
    public void UpdateTax(int mod){
        tax = population * prosperity * (600 + infrastructure * (100+ mod)) / 700 * (100 - autonomy) * BS.baseProfitFromCity / 300000;
    }
*/



    public Position getPosition() {
        return position;
    }
    public int getOwner() {
        return owner;
    }
    public ArrayList<Plant> getPlant() {
        return plant;
    }
    public void setAutonomy(double autonomy) {
        this.autonomy = autonomy;
    }
    public void ActivateModificator(int i){
        modificator[i].Activate();
    }
    public void setOwner(int owner) {
        this.owner = owner;
    }
    public boolean isMobilisation() {
        return mobilisation;
    }
    public void setMobilisation(boolean mobilisation) {
        this.mobilisation = mobilisation;
    }
    public int getPopulation() {
        return population;
    }
    public int[] getCityScreen(){
        int[] res = new int[20];
        res[0] = economy.getGdp();
        res[1] = economy.getStock();
        res[2] = population;
        res[3] = (int) (economy.getScientists()*100);
        res[4] = (int) economy.getTfp()*100;
        res[5] = (int) (economy.getGrowth()*100-100);
        res[6] = economy.getTaxes();
        res[7] = this.res[0];
        res[8] = this.res[1];
        res[9] = this.res[2];
        res[10] = profit;
        return res;
    }
    public int getStock(){
        return economy.getStock();
    }
    public int getPotentialStock(){
        return economy.getPotentialStock();
    }

}