package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.Adv.*;
import com.mygdx.game.Entities.MainComponents.GovComponents.Ruler;

import java.util.ArrayList;

public class GovAdvisors {
    public GovAdvisors() {
    }
    //правитель
    private Ruler ruler;

    // советники
    private ArrayList<Advisor> advList = new ArrayList<>();
    private ArrayList<General> general = new ArrayList<>();
    // призываем советника
    public void createAdvisor(String adv, Boolean... isFree) {
            if (adv.equals("Diplomat")) {
                advList.add(new Diplomat());
            }
            if (adv.equals("Cleric")) {
                advList.add(new Cleric());
            }
            if (adv.equals("Financier")) {
                advList.add(new Financier());
            }
            if (adv.equals("General")) {
                General gen = new General();
                general.add(gen);
                advList.add(gen);
            }
            if (adv.equals("Judge")) {
                advList.add(new Judge());
            }
            if (adv.equals("Scientist")) {
                advList.add(new Scientist());
            }
            if (adv.equals("Spy")) {
                advList.add(new Spy());
            }
    }
    //  }
    // назначаем советника  в ячейку number
    public void assignAdvisor(int adv, int number) {
        if (advisorNumber(number) == -1){
            advList.get(adv).setHaveJob(number);
        } else {
            dismissAdvisor(advisorNumber(number));
            advList.get(adv).setHaveJob(number);
        }
    }
    // убираем советника

    public void dismissAdvisor(int adv){
        advList.get(adv).setHaveJob(-1);
    }
    // убиваем советника
    public void killAdvisor(int adv){
        advList.remove(adv);
    }
    // выдаем список незанятых советников

    public Integer[] getUnasignAdvisors() {
        ArrayList<Integer> ar = new ArrayList<>();
        for (int i = 0; i < advList.size(); i++){
            if (advList.get(i).getHaveJob() == -1){
                ar.add(i);
            }
        }

        //TODO
        Integer[] arr = new Integer[ar.size()];
        arr = ar.toArray(arr);
        return arr;
        //return ar.toArray(new Integer[0]);
    }
    // выдаем советника стоящего на месте number
    public int advisorNumber(int number) {
        for (int i = 0; i < advList.size(); i++){
            if (advList.get(i).getHaveJob() == number){
                return i;
            }
        }
        return -1;
    }
    public Advisor getAdv(int i) {
        if (advisorNumber(i) != -1)
            return advList.get(advisorNumber(i));
        else
            return null;
    }
    // Увеличение возраста всех советников
    public void upAge(){
        int i = 0;
        while (i < advList.size()){
            advList.get(i).AgeUp();
            if (advList.get(i).Death()){
                killAdvisor(i);
            } else {
                i++;
            }
        }
        boolean death = ruler.upAge();
        if (death){
            ruler = new Ruler("Ildar", (int) (Math.random() *40));
        }
    }

    public ArrayList<Advisor> getAdvList() {
        return advList;
    }

    public ArrayList<General> getGeneral() {
        return general;
    }
}
