package com.mygdx.game.Entities.MainComponents.Economy;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Debt;
import com.mygdx.game.Entities.MainComponents.Gov;
import com.mygdx.game.Entities.MainComponents.GovComponents.Army;
import com.mygdx.game.Entities.MainComponents.GovParts.Region;
import com.mygdx.game.Entities.MainComponents.World;

import java.util.ArrayList;

public class GovEconomy {
    public GovEconomy(Gov country) {
        this.country = country;
    }
    private Gov country;
    private int updateCultureReg(Region region){
        int mod=0;
        if (country.getCulture() != region.getCulture()) {
            mod += 2;
        }
        if (region.getReligion() != country.getReligion()) {
            mod += 4;
        }
        region.UpdateRebelLevel(country.getMods()[3] + mod);
        if (region.ExchangeReligion(BS.baseChanceOfChangingReligion + country.getMods()[17]) &&
                region.getReligion() != country.getReligion()) {
            region.setReligion(country.getReligion());
        }
        if (region.ExchangeCulture(BS.baseChanceOfChangingCulture + country.getMods()[18]) &&
                region.getCulture() != country.getCulture()) {
            region.setCulture(country.getCulture());
        }
        return mod;
    }
    public void UpdatePD() {
        //System.out.println("Cash"+money+" "+profit);
        for (Region value : country.getRegionControl()) {
            value.updatePD();
            int mod = updateCultureReg(value);
            //переходим к городам
            for (int j = 0; j < value.getCity().length; j++) {
                //обновляем ребелов
                value.getCity()[j].UpdateRebelLevel(country.getMods()[3] + mod);
                //обновляем строительство зданий. Чтобы работало надо сначала создать здания
                //value.getCity()[j].BuildingTurn();
                //добавляем к полному производству
                value.getCity()[j].updatePD();
            }
        }
    }
    // обновляем базовый доход всего государства и максимальный заем. Обновляем автономию. Обновляем суммарную экипированность
    private int profitMod(Region region){
        int mod = 0; // модификатор автономии из-за культуры и религии
        if (country.getCulture() != region.getCulture()){
            mod +=10;
        }
        if (region.getReligion() != country.getReligion()){
            mod +=20;
        }
        return mod;
    }
    private int newAut(Region value, int mod){
        int aut;
        aut = (int) (Math.tan((Math.pow(country.getCapital().GetX() - value.getPosition().GetX(), 2) +
                Math.pow(value.getPosition().GetY() - country.getCapital().GetY(), 2)))
                / Math.sqrt(Math.pow(World.heigthOfMap, 2) + Math.pow(World.wideOfMap, 2)) * BS.baseAutonomy) +
                mod + country.getMods()[8];
        if (aut > 100) {
            aut = 100;
        }
        if (aut < 0) {
            aut = 0;
        }
        return aut;
    }
    private int profitFromRegion = 0;
    private int profitFromCity = 0;
    private int maxEquipment = 0;
    private int profit;
    private int profitFromEstates = 1;
    private int maxDebt = 0;
    private int costArmy = 0;
    private int costAdm = 0;
    private int costDebt = 0;
    private ArrayList<Debt> debt = new ArrayList<>();
    private int cost = 0;

    private void UpdateProfit () {
        profitFromRegion = 0;
        profitFromCity = 0;
        maxEquipment = 0;
        for (Region value : country.getRegionControl()) {
            // обновляем доход от регионов
            int mod = profitMod(value);
            value.setAutonomy(newAut(value, mod));
            profitFromRegion += value.regionProfit(country.getTaxRate(), country.getModPopGrRate());
            for (int j = 0; j < value.getCity().length; j++) {
                //Это надо для создания призывной армии
                maxEquipment += value.getCity()[j].GetEquipment();
                profitFromCity += value.getCity()[j].updateEconomy(
                        country.getTaxRate(), country.getSciSub(), country.getModPopGrRate()
                );
            }
        }
        profit = profitFromCity+profitFromRegion;
        profit *= profitFromEstates;
        profit /= Math.pow(10, estInLobby);
        maxDebt = profit*10;
    }

    //расходы - армия, бюрократия, долги
    //обновляем расходы на армию
    private void UpdateCostArmy () {
        costArmy = 0;
        for (Army value : country.getArmy()) {
            costArmy += value.GetCost();
        }
        costArmy *= BS.baseCostArmy;
    }
    // обновляем расход на бюрократию
    private void UpdateCostAdm () {
        int n = country.getRegionControl().size();
        for (Region value : country.getRegionControl()) {
            n += value.getCity().length;
        }
        costAdm = n * BS.baseCostAdm;
    }
    // обновляем расход на долги. Можно использовать только раз в ход, потому что выплачивается долг дополнительно
    private void UpdateCostDebt () {
        costDebt = 0;
        for (int i = 0; i <debt.size(); i++){
            while (debt.get(i).getTime() == 0) {
                country.PlusMoney(-debt.get(i).getSum());
                debt.remove(i);
            }
            if (i < debt.size()) {
                costDebt += debt.get(i).getSum() * debt.get(i).getInterest();
                debt.get(i).PayDay();
            }
        }
    }
    //обновляем все расходы
    private void UpdateCost() {
        UpdateCostArmy();
        UpdateCostAdm();
        UpdateCostDebt();

    }
    // этот класс сделан специально, чтобы пересчитывать при найме армии и других изменниях костов
    private void ReCountCost(){
        cost = costArmy + costAdm + costDebt;
    }
    // обновляем профит от
    private int estInLobby;
    private void UpdateProfitFromEstates(){
        country.UpdateEstate();
        estInLobby = 0;
        profitFromEstates = 1;
        for (int i = 0; i < country.getEstate().length; i++){
            if (country.getEstate()[i].getIsInLobby()) {
                estInLobby +=1;
                profitFromEstates *= country.getEstate()[i].getProfit();
            }
        }
    }
    //изменение казны
    public void MakeMoney() {
        UpdateProfitFromEstates();
        UpdateProfit();
        UpdateCost();
        ReCountCost();
        country.PlusMoney(profit - cost);
        while (!country.CheckMoney(0)){
            TakeDebt();
        }
        //надо сделать проверку на количество долгов и сделать банкротство вообще надо придуать, что надоделать
    }
    //берем в долг
    public void TakeDebt(){
        debt.add(new Debt(maxDebt, BS.baseInterest + country.getMods()[6], 10));
        country.PlusMoney(maxDebt);
    }

    public int getProfitFromRegion() {
        return profitFromRegion;
    }

    public int getProfitFromCity() {
        return profitFromCity;
    }

    public int getMaxEquipment() {
        return maxEquipment;
    }

    public int getProfit() {
        return profit;
    }

    public int getProfitFromEstates() {
        return profitFromEstates;
    }

    public int getMaxDebt() {
        return maxDebt;
    }

    public int getCostArmy() {
        return costArmy;
    }

    public int getCostAdm() {
        return costAdm;
    }

    public int getCostDebt() {
        return costDebt;
    }

    public ArrayList<Debt> getDebt() {
        return debt;
    }

    public int getCost() {
        return cost;
    }

    public String[] getGovInfo(){
        String[] res = new String[15];
        res[0] = "Main culture " + BS.cultureNames[country.getCulture()];
        res[1] = "Main religion " + BS.religionNames[country.getReligion()];
        return res;
    }
    public String[] getEconomy(){
        String[] res = new String[15];
        res[0] = "Cash " + country.getMoney();
        res[1] = "Profit " + profit;
        res[2] = "Costs " + cost;
        res[3] = "Cost adm " + costAdm;
        res[4] = "Cost army " + costArmy;
        res[5] = "Region profit " + profitFromRegion;
        res[6] = "City profit " + profitFromCity;
        res[7] = "Max debt " + maxDebt;
        int totDebt = 0;
        for (Debt i: debt){
            totDebt+=i.getSum();
        }
        res[8] = "Total debt " + totDebt;
        res[9] = "Total population " + country.getTotPop();
        return res;
    }
}
