package com.mygdx.game.Entities.MainComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Event;
import com.mygdx.game.Entities.Functional.Maps.CityAttack;
import com.mygdx.game.Entities.Functional.Maps.CityCoordinate;
import com.mygdx.game.Entities.Functional.Maps.MapOfArmies;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.Functional.Modificator;
import com.mygdx.game.Entities.MainComponents.GovComponents.Army;
import com.mygdx.game.Entities.MainComponents.GovComponents.City;
import com.mygdx.game.Entities.MainComponents.GovComponents.Region;
import com.mygdx.game.Entities.MainComponents.GovComponents.Resources;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.nio.file.Files.readAllLines;

//* Название ресурсов mineral RR CR
public class World {

    /*TODO крч я чет запутался, напиши инициализатор модификаторов, смотри файл GovModificator и класс Modificator
    И еще, проверь, нормально ли я скопировал это в гове
     */
    public static Modificator[] govMods;
    public static ArrayList<Modificator> modificators = new ArrayList<>();
    public void initModGov() throws IOException{
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/Inits/GovModificator")))) {
            String nextLine;
            bufferedReader.readLine();
            String[] init = new String[4];
            int numberOfMod = 0;
            while ((nextLine = bufferedReader.readLine()) != null) {
                numberOfMod += 1;
                Scanner scanner = new Scanner(nextLine);
                for (int i = 0; i < 4; i++){
                     init[i] = scanner.next();
                }
                int numMod = Integer.parseInt(init[3]);
                int[][] mods = new int[2][numMod];
                for (int i = 0; i < numMod; i++){
                    mods[0][i] = scanner.nextInt();
                    mods[1][i] = scanner.nextInt();
                }
                modificators.add(new Modificator(Integer.parseInt(init[0]), init[1], Integer.parseInt(init[2]),
                        Integer.parseInt(init[3]), mods[0], mods[1]));
            }
            govMods = modificators.toArray(new Modificator[0]);
            BS.numberOfModificators = numberOfMod;
            System.out.println("Ammount of govMods " + govMods.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initCityRegGov(int currentPlayers) throws IOException {
        /*TODO Тут надо создать конструктор городов и регионов. В инитах есть все необходимые данные. Я думаю
            что надо создать большой массив всех городов, а потом регионам просто давать передавать соответсвующий номер
            Имхо будет проще так. Но в общем файлы есть, ты говорил, что сможешь создать инициализатор регионов из файла
            оотвественно все регионы надо запихать в переменную allRegions
            Позиция региона задается кстати по первому городу, которй в нем содержится.
            В гов соответсвенно надо запихать те регионы, владельцем которых является эта страна
             */
        ArrayList<City> cities = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/Inits/CityInit")))) {
            String nextLine;
            bufferedReader.readLine();
            int unicNumber = 0;
            while ((nextLine = bufferedReader.readLine()) != null) {
                Scanner scanner = new Scanner(nextLine);
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int popul = scanner.nextInt();
                int owner = scanner.nextInt();
                int[] res = new int[3];
                res[0] = scanner.nextInt();
                res[1] = scanner.nextInt();
                res[2] = scanner.nextInt();
                mof.addCity(new Position(x, y), unicNumber, owner);
                cities.add(new City(new Position(x, y), popul, owner, res, unicNumber));
                unicNumber++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Сначала идет число городов, потом их номера(такие, что в файле инит), потом инты какие нужны в конструкторе
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("res/Inits/RegionInit")))) {
            String nextLine;
            bufferedReader.readLine();
            // 1 2 5000 0 1000 0 1000 0 0 3
            int unicNumber = 0;
            while ((nextLine = bufferedReader.readLine()) != null) {
                Scanner scanner = new Scanner(nextLine);
                int count = scanner.nextInt();
                City[] citiesArray = new City[count];
                for (int i = 0; i < count; ++i) {
                    citiesArray[i] = cities.get(scanner.nextInt());
                }
                //City[] city, int population, int resource, int capRes, int mineral, int capMin,
                //                  int religion, int culture, int owner
                int popul = scanner.nextInt();
                int res = scanner.nextInt();
                int capRes = scanner.nextInt();
                int min = scanner.nextInt();
                int capMin = scanner.nextInt();
                int relig = scanner.nextInt();
                int cult = scanner.nextInt();
                int owner = scanner.nextInt();
                allRegions.add(new Region(citiesArray, popul, res, capRes, min, capMin, relig, cult, owner, unicNumber));
                unicNumber++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*TODO Так, я не изменяю конструктор, но вообще просто пронумеруй регионы все в любом порядке. в гове будет
        лежать метод, который будет давать регион по номеру. есть две переменные еще, deIureControl и deFractoControl
        Их делаешь одинаковыми и равными номеру игрока, которому они принадлежат. И в общем, тогда ты можешь также
        пронумеровать все регионы на карте, а затем легко получать информацию о государстве
        */
        for (int i = 0; i < currentPlayers; ++i) {
            ArrayList<Region> regions = new ArrayList<>();
            for (Region reg: allRegions) {
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
            country.get(i).setCounryNum(i);
        }
    }

    public World(int currentPlayers) throws IOException {
        //создаем карту для аттак городов
        moFConstructor();
        //запихал все это в отдельный метод, ибо инициализация это пипец как многовсего
        initCityRegGov(currentPlayers);
        initModGov();
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
    public static Event[] events;
    private int turnNumber = 1;
    public static Resources resources = new Resources();
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
                new FileInputStream("desktop/build/resources/main/res/Inits/GovModificator")))) {
            String nextLine;
            lines = new ArrayList<>();
            while ((nextLine = bufferedReader.readLine()) != null) {
                Logger.getLogger("Pog");
                lines.add(nextLine);
            }
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

    Короче хз, как это все работает. Походу сейчас придется передлывать Этот метод, а если быть точнее, то
    заменять его другим. Только теперь я буду писать. что он делает и зачем.
    Фактически у нас есть 4 возможных взаимодействия
    1) Просто походить
    2) Сражаться
    3) Захватить город
    4) Посражаться и захватить город
    Будет проще сделать метод, который говорит какого рода у нас взаимодействие. Сражение происходит, если 2
    враждебные армии находятся в одной клетке
    Захват города происходит, если мы находимся в соседней клетке и больше нет армий другого государства (враждебного
    блока)
    Тогда просто разбиваем весь метод на 2 различных подметода.
    В случае если просто ход, то движемся и все
    Если битва, то у нас вроде есть метод батл. Он вроде работающий.
    И в конце мы чекаем захват города, можем ли мы это сделать.

     Мой тебе совет НЕ ЛЕЗЬ СУКА, ТАМ 150 СТРОК ИХ ДАЖЕ Я НЕ МОГУ ПОНЯТЬ
     Но если я не ошибаюсь, то она обрабатывает вообще все перемещения включая битвы, отступления и прочую ересь
    */
    public void moveArmy(Position first, Position second){
        //if attack army
        Army selArm = country.get(mof.CheckPosition(first)).getArmyPos(first);
        boolean battle = false;
        if (mof.checkArmy(second)){
            battle = true;
            Battle(selArm.getPosition(), second);
        } else {
            selArm.Move(second);
        }

        //if attack City
        if (mof.checkCity(second) && mof.posOccupy(second)){
            int number = mof.getCityCoordinates(second)[1];
            int strana = mof.getCityCoordinates(second)[0];
            int[] coord = country.get(strana).getNumCity(number);
            //как это будет происходить нам надо найти город, сделать его окупированным, перевести из одного
            //ситиконтрол в другой. Также надо проверить, можно ли его окупировать
            boolean occup = country.get(strana).getRegionControl().get(coord[0]).
                    occupy(coord[1], selArm.getCountry());
            Region regi = country.get(strana).getRegionControl().get(coord[0]);
            if (occup){
                country.get(strana).removeRegion(regi);
                country.get(selArm.getCountry()).addRegion(regi);
            }
        }
    }
    public void MoveArmyOld(Army army, Position second){
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
    // позволяет Сражаться армиям, которые находятся на двух позициях. Типо рабочий вариант, но отступление хромает. Также гетпосарми надо исправить
    private void Battle(Position position, Position battle){
        for (int j = 0; j < country.get(mof.CheckPosition(position)).army.size() ; j++){
            if (country.get(mof.CheckPosition(position)).army.get(j).getPosition() == position){
                for (int k = 0; k < country.get(mof.CheckPosition(battle)).army.size() ; k++){
                    if (country.get(mof.CheckPosition(battle)).army.get(j).getPosition() == battle){
                        boolean win = Fight(country.get(mof.CheckPosition(position)).army.get(j), country.get(mof.CheckPosition(battle)).army.get(j));
                        if (win){
                            int regi = (int) (Math.random() * country.get(mof.CheckPosition(battle)).getRegionControl().size());
                            //TODO make it normal
                            if (country.get(mof.CheckPosition(battle)).getRegionControl().get(regi).getCity()[0].checkPosition()){
                                country.get(mof.CheckPosition(battle)).army.get(j).
                                        Move(country.get(mof.CheckPosition(battle)).getRegionControl().get(regi).getCity()[0].getPosArmy());
                            } else {
                                country.get(mof.CheckPosition(battle)).army.remove(j);
                            }

                        } else {
                            int regi = (int) (Math.random() * country.get(mof.CheckPosition(position)).getRegionControl().size());
                            if (country.get(mof.CheckPosition(position)).getRegionControl().get(regi).getCity()[0].checkPosition()){
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
    private double increaseMin = 1;
    private double increaseRR = 1;
    public double increaseCR = 1;
    private void trueMarket(){
        double change = 0.05*Resources.getAvCR()/Resources.getAvRR();
        if (Resources.getAvRR() == Math.max(Math.max(Resources.getAvCR() , Resources.getAvRR()), Resources.getAvMin())){
            increaseRR -=change;
        }
        if (Resources.getAvMin() == Math.max(Math.max(Resources.getAvCR() , Resources.getAvRR()), Resources.getAvMin())){
            increaseMin -=change;
        }
        if (Resources.getAvCR() == Math.max(Math.max(Resources.getAvCR() , Resources.getAvRR()), Resources.getAvMin())){
            increaseCR -= change;
        }

        if (Resources.getAvRR() == Math.min(Math.min(Resources.getAvCR() , Resources.getAvRR()), Resources.getAvMin())){
            increaseRR +=change;
        }
        if (Resources.getAvMin() == Math.min(Math.min(Resources.getAvCR() , Resources.getAvRR()), Resources.getAvMin())){
            increaseMin +=change;
        }
        if (Resources.getAvCR() == Math.min(Math.min(Resources.getAvCR() , Resources.getAvRR()), Resources.getAvMin())){
            increaseCR += change;
        }
        //System.out.println(increaseCR +" " + increaseRR + " " + increaseMin);
        /*
        increaseMin = 1;
        increaseRR = 1;
        increaseCR = 1;
         */
        double incOverTime = 1;
        if (turnNumber > 70){
            incOverTime += Math.min((int) ((turnNumber - 60) / 10) *0.05, 1);
        }
        if (turnNumber > 110){
            incOverTime += Math.min((int) ((turnNumber - 110) / 10) *0.05, 1);
        }
        if (turnNumber > 150){
            incOverTime += Math.min((int) ((turnNumber - 150) / 10) *0.02, 1);
        }
        if (turnNumber > 180){
            incOverTime += Math.min((int) ((turnNumber - 180) / 10) *0.03, 1);
        }
        resources.setCR(totalCRDemand, totalCityProduction, increaseCR/4*incOverTime);
        resources.setMineral(totalMineralDemand, totalMineralProduction, increaseMin*2*incOverTime);
        resources.setRR(totalRRDemand,totalRegionProduction, increaseRR*2*incOverTime);
        resources.updateTotalValue();

        // checkTotalPD();
       // resources.showPrices();
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
        turnNumber++;
    }
    //вызов ивентов дает либо номер ивента, либо -1, если его не надо выбирать
    private int nextEvent;
    private boolean isNextEvent = false;
    public int eventNum(int numPlayer){
        int totalProb = 0;
        if (isNextEvent){
            isNextEvent = false;
            return nextEvent;
        } else {
            for (int i = 0; i < events.length; i++) {
                totalProb += country.get(numPlayer).getEventWeight(i);
            }
            if (Math.random() > BS.basePosEvent) {
                int weight = (int) (Math.random() * totalProb);
                int i = 0;
                int curWeight = 0;
                while (curWeight < weight) {
                    curWeight += country.get(numPlayer).getEventWeight(i);
                    i += 1;
                }
                if (events[i].getIsNextEvent()){
                    isNextEvent = true;
                    nextEvent = events[i].getNextEvent();
                }
                return i;
            } else {
                return -1;
            }
        }
    }
    //дает выбор игрока в гов
    public void eventChoice(int numPlayer, int numEvent, int numChoice){
        country.get(numPlayer).activateModificator(events[numEvent].getModNum(numChoice));
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

    public static Resources getResources() {
        return resources;
    }

    public static void setResources(Resources resources) {
        World.resources = resources;
    }

    public ArrayList<Region> getAllRegions() {
        return allRegions;
    }

    public void setAllRegions(ArrayList<Region> allRegions) {
        this.allRegions = allRegions;
    }

    public int getTurnNumber() {
        return turnNumber;
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