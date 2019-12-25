package com.mygdx.game.Entities.MainComponents.GovComponents;


import com.mygdx.game.Entities.BaseSettings.BS;

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
    static {
        for (int i = 0; i <CR.length; i++){
            CR[i] = new Resource(0, 0, 1, 0);
        }
        for (int i = 0; i <RR.length; i++){
            RR[i] = new Resource();
        }
        for (int i = 0; i <Mineral.length; i++){
            Mineral[i] = new Resource();
        }
    }

    //Маркет в мире это вызов этих методов
    public void setCR(int[] demand, int[] supply) {
        for (int i = 0; i<CR.length;i++){
            CR[i].setDemand(demand[i]);
            CR[i].setSupply(supply[i]);
            CR[i].countValue();
        }
    }

    public void setRR(int demand[], int supply[]) {
        for (int i = 0; i<RR.length;i++){
            RR[i].setDemand(demand[i]);
            RR[i].setSupply(supply[i]);
            RR[i].countValue();
        }
    }

    public void setMineral(int[] demand, int[] supply) {
        for (int i = 0; i<Mineral.length;i++){
            Mineral[i].setDemand(demand[i]);
            Mineral[i].setSupply(supply[i]);
            Mineral[i].countValue();
        }
    }

    public void updateTotalValue(){
        totalValue = 0;
        for (int i = 0; i <RR.length;i++){
            totalValue +=getValueRR(i);
        }
        for (int i = 0; i <CR.length;i++){
            totalValue +=getValueCR(i);
        }
        for (int i = 0; i <Mineral.length;i++){
            totalValue +=getValueMineral(i);
        }
        System.out.println("Total value " + totalValue);
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
    public void showPrices(){
        String str = "";
        for (Resource value: CR){
            str = str+ value.getValue()+" ";
        }
        System.out.println("CR Value "+str);
        str = " ";
        for (Resource value: RR){
            str= str+ value.getValue()+" ";
        }
        System.out.println("RR Value "+str);
        str = " ";
        for (Resource value: Mineral){
            str= str+ value.getValue()+" ";
        }
        System.out.println("Mineral Value "+str);
        str = " ";

    }
}
