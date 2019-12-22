package com.mygdx.game.Entities.BaseSettings;

import com.mygdx.game.Entities.Technology.Inv;
import com.mygdx.game.Entities.Technology.SubTech;

public class BS {
    public static int numMod = 23;
    public static String[] nameMod;
    //TODO Считать из файла эти штуки

    public static int baseProfitFromCity = 1;
    public static int baseProfitFromRegion = 1;
    public static int baseProfitFromMineral = 1;
    public static int baseProfitFromProduction = 1;
    public static int baseCostArmy = 1;
    public static int baseCostAdm = 1;
    public static int baseAdvisorCost = 1;
    public static int baseInterest = 3;
    public static int baseAutonomy = 1;
    public static int populationRate = 5;

    public static int numberOfEvent = 1;
    public static int numberOfModificators = 1;

    public static int baseNumberOfEstates = 7;
    public static int baseLoyalityIncrease = 1;

    public static int baseAdm = 5;
    public static int basePrestige = 5;
    public static int baseLegicimacy = 50;


    public static int numberOfCR = 1;
    public static int numberOfRR = 1;
    public static int numberOfMineral = 1;
    public static int numberOfBuildings = 1;
    public static int[] baseCostBuild = new int[numberOfBuildings];
    public static int baseCostPlant = 1;
    public static int baseCostInfrasructure = 1;

    public static int baseMaxMovement = 5;
    public static int[] equipmentOfSquade = {1000, 1200, 1200, 1500, 1700, 2000, 2000, 1000};
    public static int baseDamage = 5;
    public static int[] baseCostCreationSquad;
    public static int baseMobilisation;
    public static int baseCostMobilisation;

    public static int possibleAdvisors = 15;
    public static int infrBase = 3;

    public static int baseChanceOfChangingReligion = 2;
    public static int baseChanceOfChangingCulture = 5;
    public static int techNum = 1;
    public static int[] techCost = {1, 2, 3, 4, 5};
    public static int[][] numBonSubTech;
    public static int[][] bonSubTech;
    public static Inv[][][] inv;
    public static SubTech[] ST = new SubTech[techNum*3];


    public static String[] namesRR = {"Дерево"};
    public static String[] namesCR = {"Сталь"};
    public static String[] namesMineral = {"Железная руда"};
}