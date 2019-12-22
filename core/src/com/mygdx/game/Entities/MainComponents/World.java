package com.mygdx.game.Entities.MainComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Maps.CityAttack;
import com.mygdx.game.Entities.Functional.Maps.CityCoordinate;
import com.mygdx.game.Entities.Functional.Maps.MapOfArmies;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.GovComponents.Army;
import com.mygdx.game.Entities.MainComponents.GovComponents.Region;

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
    private ArrayList<Gov> country = new ArrayList<>();
    private int totalPopulation;
    private CityAttack cityAttack;
    private ArrayList<Region> allRegions;

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

    public static int[] valueRR = new int[BS.numberOfRR];
    public static int[] valueMineral = new int[BS.numberOfMineral];
    public static int[] valueCR = new int[BS.numberOfCR];

    private int[] baseValueCR =  new int[BS.numberOfCR];
    private int[] baseValueRR = new int[BS.numberOfRR];
    private int[] baseValueMineral = new int[BS.numberOfMineral];

    // производство ресурсов
    public static int[] totalCityProduction = new int[BS.numberOfCR];
    public static int[] totalRegionProduction = new int[BS.numberOfRR];
    public static int[] totalMineralProduction = new int[BS.numberOfMineral];

    // спрос на ресурсы
    public static int[] totalPlantCRDemand = new int[BS.numberOfCR];
    public static int[] totalPlantRRDemand = new int[BS.numberOfRR];
    public static int[] totalPlantMineralDemand = new int[BS.numberOfMineral];

    private int[] basePopulationRRDemand = new int[BS.numberOfRR];
    private int[] basePopulationMineralDemand = new int[BS.numberOfMineral];
    private int[] basePopulationCRDemand = new int[BS.numberOfCR];

    // Служебное
    public static boolean otladka = true;
    // штука, которая дает массив заводов



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
                valueRR[j] = (totalPlantRRDemand[j] + totalPopulation * basePopulationRRDemand[j]) * baseValueRR[j] / totalRegionProduction[j];
            } else {
                valueRR[j] = 0;
            }
        }
        //считаем цены на ископаемые ресурсы
        for (int j = 0; j < BS.numberOfCR; j++) {
            if (totalMineralProduction[j] != 0) {
                valueMineral[j] = (totalPlantMineralDemand[j] + totalPopulation * basePopulationMineralDemand[j]) * baseValueMineral[j] / totalMineralProduction[j];
            } else {
                valueMineral[j] = 0;
            }
        }
        // считаем цены на городские товары
        for (int j = 0; j < BS.numberOfCR; j++) {
            if (totalCityProduction[j] != 0) {
                valueCR[j] = (totalPlantCRDemand[j] + totalPopulation * basePopulationCRDemand[j]) * baseValueCR[j] / totalCityProduction[j];
            } else {
                valueCR[j] = 0;
            }
        }
        //Обнуляем разные важные массивы
        NullArray(totalCityProduction);
        NullArray(totalMineralProduction);
        NullArray(totalRegionProduction);
        NullArray(totalPlantCRDemand);
        NullArray(totalPlantRRDemand);
        NullArray(totalPlantMineralDemand);
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
        Market();
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