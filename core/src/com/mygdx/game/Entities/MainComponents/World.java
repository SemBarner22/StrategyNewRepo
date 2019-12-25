package com.mygdx.game.Entities.MainComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Maps.CityAttack;
import com.mygdx.game.Entities.Functional.Maps.CityCoordinate;
import com.mygdx.game.Entities.Functional.Maps.MapOfArmies;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.GovComponents.Army;
import com.mygdx.game.Entities.MainComponents.GovComponents.City;
import com.mygdx.game.Entities.MainComponents.GovComponents.Region;
import com.mygdx.game.Entities.MainComponents.GovComponents.Resources;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.file.Files.readAllLines;

//* Название ресурсов mineral RR CR
public class World {

    public World(int currentPlayers) throws IOException {
        //создаем карту для аттак городов
        moFConstructor();
        /*TODO Тут надо создать конструктор городов и регионов. В инитах есть все необходимые данные. Я думаю
            что надо создать большой массив всех городов, а потом регионам просто давать передавать соответсвующий номер
            Имхо будет проще так. Но в общем файлы есть, ты говорил, что сможешь создать инициализатор регионов из файла
            оотвественно все регионы надо запихать в переменную allRegions
            Позиция региона задается кстати по первому городу, которй в нем содержится.
            В гов соответсвенно надо запихать те регионы, владельцем которых является эта страна
             */
        int[] resurs = new int[3];
        resurs[1] = 1;
        resurs[2]=2;
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(new Position(2, 2), 5000, 0, resurs));
        cities.add(new City(new Position(10, 10), 5000, 1, resurs));
        cities.add(new City(new Position(10, 2), 5000, 2, resurs));
        City[] arcity = new City[1];
        arcity[0] = cities.get(0);
        allRegions.add(new Region(arcity, 5000, 0, 100, 0, 100, 0, 0, 0));
        arcity[0] = cities.get(1);
        allRegions.add(new Region(arcity, 5000, 0, 100, 0, 100, 0, 0, 1));
        arcity[0] = cities.get(2);
        allRegions.add(new Region(arcity, 5000, 0, 100, 0, 100, 0, 0, 2));
        for (int i = 0; i < currentPlayers; ++i) {
            ArrayList<Region> regions = new ArrayList<>();
            for (Region reg: allRegions
                 ) {
                if (reg.getOwner() == i){
                    regions.add(reg);
                }
            }
            //TODO тут я сделал это слишком долгим, но чет я хз, как сделать это в один пробег. Наверное надо создать
            // массив массивов, тогда будет норм. В общем если будет не лень, то исправь, но вообще это не критично
            // (если конечно ты не будешь считать, что не зря тебя учили оптимизироват целых полтора года в итмо)
            // В итоге я получаю массив регионов, которые относятся к i ому игроку и просто кидаю их в гов. Там только
            // одна вещь для конструктора нужна пока что. Фактически, если написать инициалтизатор для городов и
            // регионов, то все готово
            country.add(new Gov(regions));
        }
    }
    private void moFConstructor(){
        ArrayList<Position> pos = new ArrayList<>();
        ArrayList<CityCoordinate> coord = new ArrayList<>();
        for (int i = 0; i < country.size(); i++){
            for (int j = 0; j < country.get(i).getRegionControl().size(); j++){
                for (int k = 0; k < country.get(i).getRegionControl().get(j).getCity().length; k++){
                    pos.add(country.get(i).getRegionControl().get(j).getCity()[k].getPosition());
                    coord.add(new CityCoordinate(i, j, k));
                }
            }
        }
        Position[] positions = new Position[pos.size()];
        CityCoordinate[] cityCoordinates = new CityCoordinate[coord.size()];
        for (int i = 0; i < pos.size(); i++){
            positions[i] = pos.get(i);
            cityCoordinates[i] = coord.get(i);
        }
        cityAttack = new CityAttack(positions, cityCoordinates);
    }

    // компонены мира
    private boolean endGame = false;
    private Resources resources = new Resources();
    private ArrayList<Gov> country = new ArrayList<>();
    public static int totalPopulation = 0;
    private CityAttack cityAttack;
    private ArrayList<Region> allRegions = new ArrayList<>();

    public static int heigthOfMap = 5;
    public static int wideOfMap = 5;

    public static MapOfArmies mof = new MapOfArmies(wideOfMap, heigthOfMap);

    public static List<String> lines;

    static {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                //new FileInputStream("src\\Texts\\GovModificator")))) {
                //TODO
                new FileInputStream(  "res/CommonText/GovModificator")))) {
            String nextLine;
            lines = new ArrayList<>();
            while ((nextLine = bufferedReader.readLine()) != null) {
                Logger.getLogger("Pog");
                System.out.println("Pog");
                lines.add(nextLine);
            }
        /*try {
            lines = readAllLines(Paths.get("src\\Texts\\GovModificator"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Gov getPlayerGov(int index) {
        return country.get(index);
    }
    // цены на ресурсы

    public static double[] valueRR = new double[BS.numberOfRR];
    public static double[] valueMineral = new double[BS.numberOfMineral];
    public static double[] valueCR = new double[BS.numberOfCR];

    private static int[] basePopulationRRDemand = new int[BS.numberOfRR];
    private static int[] basePopulationMineralDemand = new int[BS.numberOfMineral];
    private static int[] basePopulationCRDemand = new int[BS.numberOfCR];

    private static int[] baseValueCR =  new int[BS.numberOfCR];
    private static int[] baseValueRR = new int[BS.numberOfRR];
    private static int[] baseValueMineral = new int[BS.numberOfMineral];
    //задаем базовые цены и спрос на ресурсы
    static {
        for (int i =0; i < BS.numberOfRR; i++){
            baseValueRR[i] = 1;
            basePopulationRRDemand[i] = 1;
        }
        for (int i =0; i < BS.numberOfMineral; i++){
            baseValueMineral[i] = 1;
            basePopulationMineralDemand[i] = 1;
        }
        for (int i =0; i < BS.numberOfCR; i++){
            baseValueCR[i] = 1;
            basePopulationCRDemand[i] = 1;
        }
    }

    // производство ресурсов
    public static int[] totalCityProduction = new int[BS.numberOfCR];
    public static int[] totalRegionProduction = new int[BS.numberOfRR];
    public static int[] totalMineralProduction = new int[BS.numberOfMineral];

    // спрос на ресурсы
    public static int[] totalPlantCRDemand = new int[BS.numberOfCR];
    public static int[] totalPlantRRDemand = new int[BS.numberOfRR];
    public static int[] totalPlantMineralDemand = new int[BS.numberOfMineral];

    public static int[] totalCRDemand = new int[BS.numberOfCR];
    public static int[] totalRRDemand = new int[BS.numberOfRR];
    public static int[] totalMineralDemand = new int[BS.numberOfMineral];

    /*не знаю где это оставить, поэтому пусть будут тут
     Находит 2 армии по координатам и сталкивает их. Можно сократить конечно количество опреций, тк мы знаем первую страну, но есть варик делать
    и все перемещения через карту армий.
     Мой тебе совет НЕ ЛЕЗЬ СУКА, ТАМ 150 СТРОК ИХ ДАЖЕ Я НЕ МОГУ ПОНЯТЬ
     Но если я не ошибаюсь, то она обрабатывает вообще все перемещения включая битвы, отступления и прочую ересь
    */
    public void MoveArmy(Army army, Position second){
        if ((!cityAttack.CheckPosition(second)) && army.CheckMove(second)){
            if (mof.CheckPosition(second) == -1){
                army.Move(second);
                //проверяем алекватная ли клетка
                if (!(cityAttack.CheckPositionCityAttack(second) == null |
                        cityAttack.CheckPositionCityAttack(second).getCountry() == army.getCountry())) {
                    // если нет,то идет захват города
                    int co = cityAttack.CheckPositionCityAttack(second).getCountry();
                    int re = cityAttack.CheckPositionCityAttack(second).getRegion();
                    int ci = cityAttack.CheckPositionCityAttack(second).getCity();
                    Position cit = country.get(co).getRegionControl().get(re).getCity()[ci].getPosition();
                    boolean defend = false;
                    for (int i = -1; i <2; i++){
                        for (int j = -1; j<2; j++){
                            Position posi = new Position(cit.GetX() + i, cit.GetY() +j);
                            if (mof.CheckPosition(posi) == co){
                                defend = true;
                            }
                        }
                    }
                    boolean regionAttack = true;
                    if (!defend){
                        country.get(co).getRegionControl().get(re).getCity()[ci].setOwner(army.getCountry());
                        for (int k = 0; k < country.get(co).getRegionControl().get(re).getCity().length; k++){
                            if (country.get(co).getRegionControl().get(re).getCity()[k].getOwner() != army.getCountry()){
                                regionAttack = false;
                            }
                        }
                        if (regionAttack){
                            if (country.get(army.getCountry()).getRegion().contains(country.get(co).getRegionControl().get(re))){
                                country.get(co).getRegionControl().get(re).setOccupation(false);
                            } else {
                                country.get(co).getRegionControl().get(re).setOccupation(true);
                            }
                            country.get(army.getCountry()).getRegionControl().add(country.get(co).getRegionControl().get(re));
                            country.get(co).getRegionControl().remove(country.get(co).getRegionControl().get(re));
                        }
                    }
                }
                //тут надо прописать дипломатическую атаку. Пока что дипломатии нет и просто атака всех армий,
            } else if (mof.CheckPosition(second) != army.getCountry()) {
                Battle(army.getPosition(), second);
            }
        }
    }
    // позволяет Сражаться армиям, которые находятся на двух позициях
    private void Battle(Position position, Position battle){
        for (int j = 0; j < country.get(mof.CheckPosition(position)).army.size() ; j++){
            if (country.get(mof.CheckPosition(position)).army.get(j).getPosition() == position){
                for (int k = 0; k < country.get(mof.CheckPosition(battle)).army.size() ; k++){
                    if (country.get(mof.CheckPosition(battle)).army.get(j).getPosition() == battle){
                        boolean win = Fight(country.get(mof.CheckPosition(position)).army.get(j), country.get(mof.CheckPosition(battle)).army.get(j));
                        if (win){
                            int regi = (int) (Math.random() * country.get(mof.CheckPosition(battle)).getRegionControl().size());
                            if (country.get(mof.CheckPosition(battle)).getRegionControl().get(regi).getCity()[0].CheckPosition()){
                                country.get(mof.CheckPosition(battle)).army.get(j).
                                        Move(country.get(mof.CheckPosition(battle)).getRegionControl().get(regi).getCity()[0].getPosArmy());
                            } else {
                                country.get(mof.CheckPosition(battle)).army.remove(j);
                            }

                        } else {
                            int regi = (int) (Math.random() * country.get(mof.CheckPosition(position)).getRegionControl().size());
                            if (country.get(mof.CheckPosition(position)).getRegionControl().get(regi).getCity()[0].CheckPosition()){
                                country.get(mof.CheckPosition(position)).army.get(j).
                                        Move(country.get(mof.CheckPosition(position)).getRegionControl().get(regi).getCity()[0].getPosArmy());
                            } else {
                                country.get(mof.CheckPosition(position)).army.remove(j);
                            }
                        }
                    }
                }
            }
        }

    }
    //функция, которая позволяет сражаться двум армиям
    private boolean Fight(Army army1, Army army2) {
        int country1 = army1.getCountry();
        int country2 = army2.getCountry();
        int baseAmmount1 = army1.getAmount();
        int baseAmmount2 = army2.getAmount();
        int baseOrganisation1 = army1.getOrganization();
        int baseOrganisation2 = army2.getOrganization();
        army1.UpdateSF();
        army2.UpdateSF();
        while ((army1.getMorale() > 100) && (army2.getMorale() > 100)){
            army1.Lose(100 - (army2.getFire() *(100 + country.get(country2).getModFire()) * army2.getEquipment()/ army1.getEquipment()/ army1.getAmount()) / 2 - BS.baseDamage);
            army2.Lose(100 - (army1.getFire() *(100 + country.get(country1).getModFire()) * army1.getEquipment()/ army2.getEquipment()/ army2.getAmount()) / 2 - BS.baseDamage);
            army1.UpdateSF();
            army2.UpdateSF();
            if ((army1.getMorale() > 100) && (army2.getMorale() > 100)) {
                army1.Lose(100 - (army2.getShock() * (100 + country.get(country2).getModShock()) / army1.getAmount()) / 2 - BS.baseDamage);
                army2.Lose(100 - (army1.getShock() * (100 + country.get(country1).getModShock()) / army2.getAmount()) / 2 - BS.baseDamage);
                army1.UpdateSF();
                army2.UpdateSF();
            }
        }
        // теперь проводим обновление обмундирования
        if (army1.getMorale()>army2.getMorale()){
            int j = (400 * army1.getAmount() / baseAmmount1 / 3 + army2.getAmount()/baseAmmount2 / 3);
            army1.setOrganization(baseOrganisation1);
            if (j > 100){
                j = 100;
            }
            army1.LoseEquipment(j);
            army1.WinBattle();
            int lose = army2.LoseBattle();
            if (lose == 1){
                army2.LoseEquipment(300 * army2.getAmount() / baseAmmount2 / 3);
            } else if (lose == 2){
                army2.LoseEquipment(100 * army2.getAmount() / baseAmmount2 / 3);
            } else if (lose == 3){
                army2.LoseEquipment(50 * army2.getAmount() / baseAmmount2 / 3);
            } else if (lose == 4){
                country.get(country2).army.remove(army2);
            }
            return true;
        } else{
            int j = (400 * army2.getAmount() / baseAmmount2 / 3 + army1.getAmount()/baseAmmount1 / 3);
            army2.setOrganization(baseOrganisation2);
            if (j > 100){
                j = 100;
            }
            army2.LoseEquipment(j);
            army2.WinBattle();
            int lose = army1.LoseBattle();
            if (lose == 1){
                army1.LoseEquipment(300 * army1.getAmount() / baseAmmount1 / 3);
            } else if (lose == 2){
                army2.LoseEquipment(100 * army1.getAmount() / baseAmmount1 / 3);
            } else if (lose == 3){
                army2.LoseEquipment(50 * army1.getAmount() / baseAmmount1 / 3);
            } else if (lose == 4){
                country.get(country1).army.remove(army1);
            }
            return false;
        }

    }

    // обнуление массива
    private void NullArray(int[] array){
        for (int j = 0; j < array.length; j++){
            array[j] = 0;
        }
    }
    // считаем цены на ресурсы
    private void Market(){
        //считаем цены на региональные ресурсы
        for (int j = 0; j < BS.numberOfRR; j++) {
            if (totalRegionProduction[j] != 0) {
                valueRR[j] = (1.0*totalPlantRRDemand[j] + totalPopulation * basePopulationRRDemand[j]) * baseValueRR[j] / totalRegionProduction[j];
            } else {
                valueRR[j] = 0;
            }
            //System.out.println(totalPopulation);
            //System.out.println(totalCityProduction[0]);
        }
        //считаем цены на ископаемые ресурсы
        for (int j = 0; j < BS.numberOfMineral; j++) {
            if (totalMineralProduction[j] != 0) {
                valueMineral[j] = (1.0*totalPlantMineralDemand[j] + totalPopulation * basePopulationMineralDemand[j]) * baseValueMineral[j] / totalMineralProduction[j];
            } else {
                valueMineral[j] = 0;
            }
        }
        // считаем цены на городские товары
        for (int j = 0; j < BS.numberOfCR; j++) {
            if (totalCityProduction[j] != 0) {
                valueCR[j] = (1.0*totalPlantCRDemand[j] + totalPopulation * basePopulationCRDemand[j]) * baseValueCR[j] / totalCityProduction[j];
            } else {
                valueCR[j] = 0;
            }
        }
        System.out.println(valueCR[0]);
        //Обнуляем разные важные массивы
        NullArray(totalCityProduction);
        NullArray(totalMineralProduction);
        NullArray(totalRegionProduction);
        NullArray(totalPlantCRDemand);
        NullArray(totalPlantRRDemand);
        NullArray(totalPlantMineralDemand);
    }
    //пацанский маркет
    private void trueMarket(){
        resources.setCR(totalCRDemand, totalCityProduction);
        resources.setMineral(totalMineralDemand, totalMineralProduction);
        resources.setRR(totalRRDemand,totalRegionProduction);
        resources.updateTotalValue();

        //checkTotalPD();
        resources.showPrices();
        NullArray(totalCityProduction);
        NullArray(totalMineralProduction);
        NullArray(totalRegionProduction);
        NullArray(totalCRDemand);
        NullArray(totalMineralDemand);
        NullArray(totalRRDemand);
    }
    // все что делается до хода
    public void preTurn(int i){
        country.get(i).MakeMoney();
    }
    // неожиданно после хода
    public void afterTurn(int i){
        country.get(i).UpdatePD();
    }
    //после хода всех игроков. Сюдаже пихается дата и прочее
    public void AfterGlobalTurn(){
        trueMarket();
    }
    //Нужен для проверки текущего балланса. Чисто служебный метод
    private void checkTotalPD(){
        int res = 0;
        System.out.println(totalCityProduction[0]+" " +totalCityProduction[1]+" "+totalCityProduction[2]+" "
                +totalRegionProduction[0]+" "+ totalMineralProduction[0]+" "+totalMineralDemand[0]+" "+totalRRDemand[0]
                +" "+totalCRDemand[0]+" "+ totalCRDemand[1]+" "+totalCRDemand[2]);
        for (int i: totalRegionProduction) {
            res+=i;
        }
        for (int i: totalMineralProduction) {
            res+=i;
        }
        for (int i: totalCityProduction) {
            res+=i;
        }
        for (int i: totalMineralDemand) {
            res-=i;
        }
        for (int i: totalRRDemand) {
            res-=i;
        }
        for (int i: totalCRDemand) {
            res-=i;
        }
        System.out.println("Current account "+res);
    }
//    public void Main() {
//        int i = -1;
//        while (!endGame){
//            i++;
//            if (i == country.size()){
//                AfterGlobalTurn();
//            }
//            preTurn(i);
//            // тут как раз начинается ход игрока
//            //тут он кончается
//            afterTurn(i);
//        }
//    }
}