package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.World;

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
        economy = new Economy(population, 1000, 1000);
    }

    private Position position;
    private Building[] building = new Building[BS.numberOfBuildings];
    private ArrayList<Plant> plant = new ArrayList<>();
    private int population;
    private int infrastructure = 1;
    private int owner;
    private int prosperity = 1;
    private int autonomy=0;
    private int rebelLevel=0;
    private int[] partArmy;
    private boolean mobilisation = false;
    private Economy economy;

    //новое производство
    private int production = 1;
    private int[] res;

    private int numberOfModificators = 0;
    private Modificator[] modificator = new Modificator[numberOfModificators];

    private Position posArmy;


    //АРМИЯ
    public boolean CheckPosition(){
        for (int i = -1; i <2; i++) {
            for (int j = -1; j < 2; j++) {
                if (World.mof.CheckPosition(new Position(position.GetX()+i, position.GetY()+j)) == -1 && (i != 0 | j != 0)){
                    posArmy = new Position(position.GetX()+i, position.GetY()+j);
                    return true;
                }
            }
        }
        return false;
    }
    public Position getPosArmy() {
        return posArmy;
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
        int profit = economy.getGdp() * (World.valueCR[res[0]] + World.valueCR[res[1]] + World.valueCR[res[2]]) /3;
        return taxes * profit*autonomy/10000;
    }
    private void Prosperity(){
        int i = (int) (Math.random() * 100);
        if (i < 10 - prosperity){
            prosperity++;
        }
    }
    //сейчас добавляем к общему производству мира
    public void updatePD(){
        for (int i = 0; i < res.length; i++){
            World.totalCityProduction[i] +=production/3;
        }
    }
    // обновление населения
    public void updatePopulation(int rate){
        population = (int) (population*(1000+BS.populationRate+1.0*prosperity/3+rate)/1000);
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

    public void setAutonomy(int autonomy) {
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
}