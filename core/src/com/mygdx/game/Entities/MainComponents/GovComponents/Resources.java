package com.mygdx.game.Entities.MainComponents.GovComponents;


import com.mygdx.game.Entities.BaseSettings.BS;

import java.text.DecimalFormat;
import java.util.ArrayList;

//этот класс нужен для работы со всеми ресурсами. Он хранит в себе информацию о том, что откуда берется и какой ресурс
// из чего делается.
public class Resources {
    public Resources() {
        updateTotalValue();
    }

    private static Resource[] CR = new Resource[BS.numberOfCR];
    private static Resource[] RR = new Resource[BS.numberOfRR];
    private static Resource[] Mineral = new Resource[BS.numberOfMineral];
    private static double totalValue;
    private static double avMin;
    private static double avRR;
    private static double avCR;
    public static void updateAv(){
        double totMin = 0;
        for (Resource res: Mineral){
            totMin += res.getValue();
        }
        avMin = totMin/Mineral.length;
        double totRR = 0;
        for (Resource res: RR){
            totRR += res.getValue();
        }
        avRR = totRR/RR.length;
        double totCR = 0;
        for (Resource res: CR){
            totCR += res.getValue();
        }
        avCR = totCR/CR.length;
    }
    static {
        for (int i = 0; i <CR.length; i++){
            CR[i] = new Resource(0, 0, 1, 0);
        }
        CR[0].changeValue(1);
        CR[1].changeValue(-1);
        for (int i = 0; i <RR.length; i++){
            RR[i] = new Resource();
        }
        for (int i = 0; i <Mineral.length; i++){
            Mineral[i] = new Resource();
        }
    }

    //Маркет в мире это вызов этих методов
    public void setCR(int[] demand, int[] supply, double increaseOverTime) {
        for (int i = 0; i<CR.length;i++){
            CR[i].setDemand((int) (increaseOverTime*demand[i]));
            CR[i].setSupply(supply[i]);
            CR[i].countValue();
            /*System.out.println("Resource " + i + " demand "+ (int) (increaseOverTime*demand[i])
             + " supply "+ supply[i]);*/
        }
    }

    public void setRR(int demand[], int supply[], double increaseOverTime) {
        for (int i = 0; i<RR.length;i++){
            RR[i].setDemand((int) (increaseOverTime*demand[i]));
            RR[i].setSupply(supply[i]);
            RR[i].countValue();
            /*System.out.println("Resource RR" + " demand "+ (int) (increaseOverTime*demand[i])
                    + " supply "+ supply[i]);*/
        }
    }

    public void setMineral(int[] demand, int[] supply, double increaseOverTime) {
        for (int i = 0; i<Mineral.length;i++){
            Mineral[i].setDemand((int) (increaseOverTime*demand[i]));
            Mineral[i].setSupply(supply[i]);
            Mineral[i].countValue();
            //System.out.println("Resource Min" + " demand "+ (int) (increaseOverTime*demand[i])
             //       + " supply "+ supply[i]);
        }
    }

    public void updateTotalValue(){
        totalValue = 0;
        updateAv();
        for (int i = 0; i <RR.length;i++){
            totalValue +=getValueRR(i);
        }
        for (int i = 0; i <CR.length;i++){
            totalValue +=getValueCR(i);
        }
        for (int i = 0; i <Mineral.length;i++){
            totalValue +=getValueMineral(i);
        }
        //System.out.println("Total value " + totalValue);
    }

    public static double getValueRR(int i){
        return RR[i].getValue();
    }
    public static double getValueCR(int i){
        return CR[i].getValue();
    }
    public static double getValueMineral(int i){
        return Mineral[i].getValue();
    }

    public static double getTotalValue() {
        return totalValue;
    }
    public static int[] investCR(int i){
        return CR[i].investCR();
    }
    DecimalFormat decimalFormat = new DecimalFormat("#.###");
    public ArrayList<String> showPrices() {
        ArrayList<String> res = new ArrayList<>();
        String str = "";
        for (Resource value: CR){
            str = str + decimalFormat.format(value.getValue()) +" ";
        }
        res.add("CR Value "+ str);
        //System.out.println("CR Value "+ str);
        str = " ";
        for (Resource value: RR){
            str= str + decimalFormat.format(value.getValue()) + " ";
        }
        res.add("RR Value "+ str);
        //System.out.println("RR Value "+str);
        str = " ";
        for (Resource value: Mineral){
            str= str+ decimalFormat.format(value.getValue()) +" ";
        }
        res.add("Mineral Value "+ str);
        //System.out.println("Mineral Value "+str);
        return res;
    }

    public static double getAvMin() {
        return avMin;
    }

    public static double getAvRR() {
        return avRR;
    }

    public static double getAvCR() {
        return avCR;
    }
}
