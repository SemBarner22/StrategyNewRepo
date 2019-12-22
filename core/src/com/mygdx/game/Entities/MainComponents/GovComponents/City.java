package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.World;

import java.util.ArrayList;


public class City {
    public City(int population, int infrastructure, int prosperity, int religion, Position pos) {
        this.population = population;
        this.infrastructure = infrastructure;
        this.prosperity = prosperity;
        this.religion = religion;
        position = pos;
    }

    private Position position;
    private Building[] building = new Building[BS.numberOfBuildings];
    private ArrayList<Plant> plant = new ArrayList<>();
    private int population;
    private int infrastructure;
    private int owner;
    private int prosperity;
    private int autonomy;
    private int profit;
    private int tax;
    private int religion;
    private int rebelLevel;
    private int[] partArmy;
    private boolean mobilisation;

    private int numberOfModificators = 0;
    private Modificator[] modificator = new Modificator[numberOfModificators];

    private Position posArmy;

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
    // тут надо прописать бонусы от домов
    public void UpdateModBuild(){
    }

    private void Prosperity(){
        int i = (int) (Math.random() * 100);
        if (i < 10 - prosperity){
            prosperity++;
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

    // методы для вывода общего дохода от производства
    public void UpdateProfitFromProduction(){
        profit = 0;
        for (int i =0; i < plant.size(); i++){
            plant.get(i).UpdateProfit();
            profit += plant.get(i).getProfit();
        }
        profit *= BS.baseProfitFromProduction;
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
    // метод для вывода дохода от налогов
    public void UpdateTax(int mod){
        tax = population * prosperity * (600 + infrastructure * (100+ mod)) / 700 * (100 - autonomy) * BS.baseProfitFromCity / 300000;
    }
    // обновление населения
    public void updatePopulation(int rate){
        population *= 1000 + rate + BS.populationRate;
        population /= 1000;
    }
    // обновление автономии

    public Position getPosition() {
        return position;
    }
    public int getPopulation() {
        return population;
    }

    public int getInfrastructure() {
        return infrastructure;
    }

    public int getOwner() {
        return owner;
    }

    public int getProsperity() {
        return prosperity;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public ArrayList<Plant> getPlant() {
        return plant;
    }

    public int getProfit() {
        return profit;
    }

    public int getTax() {
        return tax;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
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