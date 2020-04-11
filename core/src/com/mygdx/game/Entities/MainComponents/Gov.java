package com.mygdx.game.Entities.MainComponents;

import com.mygdx.game.Entities.Adv.Advisor;
import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Estate.Estate;
import com.mygdx.game.Entities.Estate.Generals;
import com.mygdx.game.Entities.Estate.Manufactor;
import com.mygdx.game.Entities.Functional.Debt;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.MainComponents.Economy.Construction;
import com.mygdx.game.Entities.MainComponents.Economy.GovEconomy;
import com.mygdx.game.Entities.MainComponents.GovComponents.Army;
import com.mygdx.game.Entities.MainComponents.GovComponents.GovArmy;
import com.mygdx.game.Entities.MainComponents.GovParts.City;
import com.mygdx.game.Entities.MainComponents.GovComponents.Laws;
import com.mygdx.game.Entities.MainComponents.GovParts.Region;

import java.util.ArrayList;
import java.util.List;

public class Gov {
    private void constructMod(){
        int i = 1;
        int k;
        int j = 0;
        List<String> string = World.lines;
        /*
        Перое число это номер этого модификатора
        Второе это имя
        Третье это максимальное время
        Четвертое это
         */
        while (!string.get(i).equals("end")) {
            k = i;
            i += 4;
            int m = 0;
            int[][] mods = new int[2][Integer.parseInt(string.get(k+3))];
            while(!string.get(i).equals("stop")){
                mods[0][m] = Integer.parseInt(string.get(i));
                i++;
                mods[1][m] = Integer.parseInt(string.get(i));
                i++;
                m++;
            }
            modificator[j] = new Modificator(Integer.parseInt(string.get(k)), string.get(k+1),
                    Integer.parseInt(string.get(k+2)), BS.numMod, mods[0], mods[1]);
            j++;
            i++;
        }
    }
    private void constructAdv() {
        for (int t = 0; t < 5; t++) {
            govAdvisors.createAdvisor("Cleric", true);
            govAdvisors.assignAdvisor(t, t);
        }
        for (int t = 0; t < 10; t++) {
            govAdvisors.createAdvisor("Diplomat", true);
        }
    }
    private void constructEstate(){
        //Создаем сословия
        estate = new Estate[2];
        estate[0] = new Generals();
        estate[1] = new Manufactor();
    }
    public Gov(ArrayList<Region> region){
        this.region = region;
        regionControl = region;
        //constructMod();
        constructAdv();
        constructEstate();
        capital = region.get(0).getCity()[0].getPosition();
        adm = 100;
        modificator = World.modificators.toArray(new Modificator[World.modificators.size()]).clone();//TODO хз что сделал чекай
        System.out.println("Number Of mods " + modificator.length);
    }
    private boolean isPlayer = true;


    // мдификаторы
    private int[] mods;
    private int modInterest = 0;
    private int modPrestige = 0; // из 10000
    private int modLegecimacy = 10000; // из 10000
    private int modAdm = 0;
    private int[] powerIncrease = new int[BS.baseNumberOfEstates];
    private int[] loyalityIncrease = new int[BS.baseNumberOfEstates];
    private int modShock; //в процентах
    private int modFire;
    private int modTactic;
    private int modMorale;
    private int modOrganisation;
    private int modPopGrRate = 0;

    private Modificator[] modificator;
    //инициализируем модификаторы

    // обновляем все моды; сначала обнуляем затем добавляем во всех структурах, которые влияют на них
    private void AddToMod(int[] array) {
        mods = array;
        modInterest += array[4];
        modPrestige += array[5];
        modLegecimacy += array[6];
        modAdm += array[7];
        modShock += array[12];
        modFire += array[13];
        modTactic += array[14];
        modMorale += array[15];
        modOrganisation += array[16];
    }
    private void NullMod(){
        modInterest =0;
        modPrestige =0;
        modLegecimacy =0;
        modAdm =0;
        modShock =0;
        modFire =0;
        modTactic =0;
        modMorale =0;
        modOrganisation =0;
    }

    public void updateMod(){
        NullMod();
        int[] totSum = new int[BS.numMod];
        for (int i = 0; i < modificator.length; i++){
            if (modificator[i].getIs()){
                for (int j = 0; i < modificator[i].getNumMod().length; i++){
                    totSum[modificator[i].getNumMod()[j]] += modificator[i].getLevMod()[j];
                }
            }
        }
        AddToMod(totSum);
        //TODO добовляем моды законов. убрать коммент после инциализации
        //AddToMod(laws.getModif());
        for (Advisor advisor: govAdvisors.getAdvList()){
            if (advisor.getHaveJob() >= 0) {
                AddToMod(advisor.getMod());
            }
        }
        for (int i = 0; i < estate.length; i++){
            if (estate[i].getIsInLobby()) {
                AddToMod(estate[i].getMod());
                PlusMoney(estate[i].getPlusMoney() * govEconomy.getProfit() / 10);
                estate[i].setPlusMoney(0);
                if (estate[i].isManufatory()){
                    int reg = (int) (Math.random() * regionControl.size());
                    int cit = (int) (Math.random() * regionControl.get(reg).getCity().length);
                    if (regionControl.get(reg).getCity()[cit].getPlant().size() != 0){
                        regionControl.get(reg).getCity()[cit].getPlant().get((int) (Math.random() * regionControl.get(reg).getCity()[cit].getPlant().size())).Upgrade();
                    } else {
                        regionControl.get(reg).getCity()[cit].newPlant((int) (Math.random() * BS.numberOfCR));
                    }
                    estate[i].setManufatory(false);
                }
                if (estate[i].isFinancier()){
                    govAdvisors.createAdvisor("Financier", true);
                    estate[i].setFinancier(false);
                }
                if (estate[i].isGeneral()){
                    govAdvisors.createAdvisor("General", true);
                    estate[i].setFinancier(false);
                }
            }
        }

    }

    //доходы
    private int money;
    private int taxRate = 1;
    // расходы
    private int sciSub = 0;
    // другие ресурсы
    private int adm;
    private int prestige; //от -10000 до 10000. Показываются только первые 3 цифры
    private int legicimacy; //по-разному. Пока что делаю для королевства также, как престиж
    // государственное устройство
    private int counryNum;
    private int totPop;
    private int maxArmy;
    private ArrayList<Region> regionControl;
    private ArrayList<Region> region;
    private Position capital;
    private int religion = 0;
    private int culture = 0;
    public int getReligion() {
        return religion;
    }
    public int getCulture() {
        return culture;
    }
    private Laws laws;
    //Метод дает номер региона в массиве region, если ему дать регистрационный номер региона
    public int getNumRegion(int numberOfRegion){
        for (int i = 0; i <region.size();i++){
            if (region.get(i).getNumberOfRegion() ==numberOfRegion){
                return i;
            }
        }
        return -1;
    }
    public int getNumRegionControl(int numberOfRegion){
        for (int i = 0; i <regionControl.size();i++){
            if (regionControl.get(i).getNumberOfRegion() ==numberOfRegion){
                return i;
            }
        }
        return -1;
    }
    public int[] getNumCity(int numberOfCity){
        for (int i = 0; i <regionControl.size();i++){
            for (int j = 0; j < regionControl.get(i).getCity().length; j++){
                if (regionControl.get(i).getCity()[j].getUnicNumber() == numberOfCity){
                    int[] result = new int[2];
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        System.out.println("ERROR IN getNumCity");
        return null;
    }

    // армия
    public ArrayList<Army> army = new ArrayList<>();
    public ArrayList<Army> mobilisateArmy = new ArrayList<>();
    private int totalArmy = 0;
    //чисто для призывной армии. Для всех остальных считается по другому в самой армии. Надо только будет сделать
    //кнопку для пополнения всех.
    //events
    private boolean nextEvent = false;
    private int numNextEvent = -1;

    public boolean isNextEvent() {
        return nextEvent;
    }
    public int getNumNextEvent() {
        return numNextEvent;
    }
    public void setNextEvent(boolean nextEvent, int numNextEvent) {
        this.nextEvent = nextEvent;
        this.numNextEvent = numNextEvent;
    }

    // советники
    private GovAdvisors govAdvisors = new GovAdvisors();
    public GovAdvisors getGovAdvisors() {
        return govAdvisors;
    }


    // Сословия
    private Estate[] estate;
    public void UpdateEstate(){
        int commonPower = 0;
        for (int i =0; i < estate.length; i++){
            loyalityIncrease[i] = BS.baseLoyalityIncrease;
            estate[i].setLoyalityIncrease(loyalityIncrease[i]);
            if (estate[i].getIsInLobby()) {
                powerIncrease[i] = 1;
            } else {
                powerIncrease[i] = 0;
            }
            estate[i].setPowerIncrease(powerIncrease[i]);
            estate[i].UpdateLP();
            commonPower += estate[i].getPower();
        }
        for (int i = 0; i<estate.length; i++){
            estate[i].setPartOfPover(estate[i].getPower() / (commonPower / 10));
            estate[i].UpdateBonus();
        }
    }
    public void ExchangeEstate(int newOne, int oldOne){
        estate[newOne].setPower(estate[oldOne].getPower() / 2 + estate[newOne].getPower());
        estate[oldOne].setPower(estate[oldOne].getPower() / 2);
    }

    private GovEconomy govEconomy = new GovEconomy(this);
    public GovEconomy getGovEconomy() {
        return govEconomy;
    }

    public void MakeMoney() {
        govAdvisors.upAge();
        updateMod();

        updateMods();

        govArmy.UpdateArmy();
        updateTotPop();
        govEconomy.MakeMoney();
        //laws.turn();
        //надо сделать проверку на количество долгов и сделать банкротство вообще надо придуать, что надо делать
    }
    // проверка на наличие суммы денег
    public boolean CheckMoney( int number){
        return number < money;
    }
    // check amount of adm points
    public boolean checkAdm(int number){
        return number > adm;
    }
    // получаем деньги
    public void PlusMoney(int m){
        money += m;
    }
    //invest in stock тут как раз обращаться уже надо по номеру региона
    public boolean posibInvest(int reg, int city){
        City city1 = region.get(getNumRegion(reg)).getCity()[city];
        if (getNumRegion(reg) != -1){
            return CheckMoney(region.get(getNumRegion(reg)).costOfCapitalDonate(city1));
        } else {
            System.out.println("Region is not by our control");
            return false;
        }
    }
    public void invest(int reg, int city){
        City city1 = region.get(getNumRegion(reg)).getCity()[city];
        if (posibInvest(reg, city)){
            region.get(getNumRegion(reg)).getCity()[city]
                    .investStock(region.get(getNumRegion(reg)).costOfCapitalDonate(city1));
            PlusMoney(-region.get(getNumRegion(reg)).costOfCapitalDonate(city1));
            System.out.println("investment succeeded " + money + " " + region.get(getNumRegion(reg)).getCity()[city].
                    getStock());
        } else {
            System.out.println("investment failed");
        }
    }

    //обновляем другие ресурсы
    public void UpdateAPL() {
        PlusAdm(BS.baseAdm + modAdm);
        PlusPrestige(-(prestige * (100 - BS.basePrestige) / 100 - modPrestige)+1000);
        PlusLegicimacy(BS.baseLegicimacy + modLegecimacy);
    }
    public void PlusPrestige(int p){
        prestige += p;
        if (prestige > 10000){
            prestige = 10000;
        }
        if (prestige<0){
            prestige = 0;
        }
    }
    public void PlusAdm(int p){
        adm += p;
        if (adm > 10000){
            adm = 10000;
        }
    }
    public void PlusLegicimacy(int p){
        legicimacy += p;
        if (legicimacy > 10000){
            legicimacy = 10000;
        }
        if (legicimacy < 0){
            legicimacy = 0;
        }
    }
    private void updateTotPop(){
        totPop = 0;
        for (Region value : region){
            totPop += value.getTotPop();
        }
        maxArmy = BS.baseArmyAmount * totPop;
    }

    // СТРОИТЕЛЬСТВО. Внутри реализованны все методы
    public Construction construction = new Construction(this);

    //  ДА ЗДРАСТВУЕТ ВЕЛИКАЯ ФРАНЦУЗКАЯ АРМИЯ. В этом классе собраны все методы для армии
    public GovArmy govArmy = new GovArmy(this);

    private void updateMods(){
        for (Modificator modif: modificator){
            modif.turn();
        }
    }
    public void activateModificator(int i){
        modificator[i].Activate();
    }
    public String[] getModifiers(){
        ArrayList<String> res = new ArrayList<>();
        for (Modificator mod: modificator){
            if (mod.getIs()){
                res.add(mod.getName() + ". Time " + mod.getTime());
            }
        }
        return res.toArray(new String[0]);
    }
    public String[] armyMod(){
        String[] st = new String[3];
        st[0] = "Morale " + modMorale + "%";
        st[1] = "Tactic " + modTactic;
        st[2] = "Organisation " + modOrganisation + "%";
        return st;
    }
    public int getEventWeight(int num){
        return World.events[num].getProbability();
    }
    // дальше идут только геттеры
    //в этом методе мы передаем номер игрока (сам его возьмешь), количество денег, доход
    public int[] mainScreen10Getters(){
        int[] res = new int[6];
        res[0] = money;
        res[1] = govEconomy.getProfit();
        res[2] = adm;
        res[3] = legicimacy;
        res[4] = prestige/10;
        return res;
    }
    //для скрина экономика
    public String[] getEconomy(){
        String[] res = new String[15];
        res[0] = "Cash " + money;
        res[1] = "Profit " + govEconomy.getProfit();
        res[2] = "Costs " + govEconomy.getCost();
        res[3] = "Cost adm " + govEconomy.getCostAdm();
        res[4] = "Cost army " + govEconomy.getCostArmy();
        res[5] = "Region profit " + govEconomy.getProfitFromRegion();
        res[6] = "City profit " + govEconomy.getProfitFromCity();
        res[7] = "Max debt " + govEconomy.getMaxDebt();
        int totDebt = 0;
        for (Debt i: govEconomy.getDebt()){
            totDebt+=i.getSum();
        }
        res[8] = "Total debt " + totDebt;
        res[9] = "Total population " + totPop;
        return res;
    }
    //для скрина с армиями
    public String[] getArmyInfo(){
        String[] res = new String[16];
        res[0] = "Max army " + maxArmy;
        res[1] = "Current army " + totalArmy;
        res[2] = "Mod morale" + modMorale + "%";
        res[3] = "Tactic" + modTactic;
        return res;
    }
    //для скрина с государством
    public String[] getGovInfo(){
        String[] res = new String[15];
        res[0] = "Main culture " + BS.cultureNames[culture];
        res[1] = "Main religion " + BS.religionNames[religion];
        return res;
    }
    public int getModShock() {
        return modShock;
    }
    public int getModFire() {
        return modFire;
    }
    public ArrayList<Region> getRegionControl() {
        return regionControl;
    }
    public ArrayList<Region> getRegion() {
        return region;
    }
    public int getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public void removeRegion(Region reg){
        regionControl.remove(reg);
    }
    public void addRegion(Region reg){
        regionControl.add(reg);
    }
    public void setCounryNum(int counryNum) {
        this.counryNum = counryNum;
    }
    public int[] getMods() {
        return mods;
    }
    public Position getCapital() {
        return capital;
    }
    public int getModPopGrRate() {
        return modPopGrRate;
    }
    public int getSciSub() {
        return sciSub;
    }
    public ArrayList<Army> getArmy(){
        return army;
    }
    public Estate[] getEstate() {
        return estate;
    }
    public int getModInterest() {
        return modInterest;
    }
    public int getAdm() {
        return adm;
    }
    public int getPrestige() {
        return prestige;
    }
    public int getLegicimacy() {
        return legicimacy;
    }
    public int getCounryNum() {
        return counryNum;
    }
    public int getMaxArmy() {
        return maxArmy;
    }
}