package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Estate.Estate;

import java.util.ArrayList;

/*
Вообще с законами должно быть довольно просто пока что, но муторно. Фактически у каждого закона есть 2 состояния -
принят и нет. Также, за каждое состояние должны бороться сословия. То есть мы можем принять тот или иной закон, только
если это сословие находится в лобби. Также, если закон принят, а сословие не в лобби, то есть вероятность, что закон
будет отменен

Структура будет примерно такой - будет 4 массива законов. 3 из них связанны с соответсвующими парами сословий и 1 можно
принимать всегда. Есть класс low, который будет содержать модификаторы и состояние (принят или нет). Класс лоу может
вернуть массив со всеми модификаторами (со всеми, но некоторые равны 0)

Этот класс имеет метод, которые просто суммирует вообще все модификаторы, которые есть и возвращает их сумму (надо для
подсчета эффекта на гов).

Так как в этом классе важно, кто в лобби, то надо будет сетать постоянно. Этот класс работает, только если 3 пары
сословий
 */
public class Laws {
    /*TODO с тебя сделать инициализатор законов. Это можно сделать в гове. Эстеты это просто те же, что и у гова (то ес
    ть массив из 6 эстейтов. А законы создаются из файла по конструктору
     */
    public Laws(Estate[] estates, Law[] laws) {
        this.estates = estates;
        this.laws = laws;
    }
    private Estate[] estates;
    private Law[] laws;
    private int coolDown = 0;

    public int[] getModif(){
        int[] result = new int[BS.numMod];
        for (int i = 0; i < laws.length; i++){
            if (laws[i].isActivation()){
                for (int j = 0; j < result.length; j++){
                    result[j] += laws[i].getModif()[j];
                }
            }
        }
        return result;
    }

    public void turn(){
        if (Math.random() < BS.probConflict) {
            conflictPro();
        }
        if (Math.random() < BS.probConflict) {
            conflictAgainst();
        }
        decreaseCD();
    }

    private void decreaseCD(){
        coolDown -= 1;
        if (coolDown < 0){
            coolDown = 1;
        }
    }

    private void conflictPro(){
        int numEst = (int) (Math.random() * 6);
        ArrayList<Law> curConfl = new ArrayList();
        for (Law value: laws){
            if (!value.isActivation() && value.getWhoNeed().equals(estates[numEst].getName())){
                curConfl.add(value);
            }
        }
        if (curConfl.size() > 0) {
            int numLaw = (int) (Math.random() * (curConfl.size()-1));
            curConfl.get(numLaw).setActivation(true);
        }
    }
    private void conflictAgainst(){
        int numEst = (int) (Math.random() * 6);
        ArrayList<Law> curConfl = new ArrayList();
        for (Law value: laws){
            if (!value.isActivation() && value.getWhoDontNeed().equals(estates[numEst].getName())){
                curConfl.add(value);
            }
        }
        if (curConfl.size() > 0) {
            int numLaw = (int) (Math.random() * (curConfl.size()-1));
            curConfl.get(numLaw).setActivation(false);
        }
    }

    private boolean isInLobby(String name){
        for (Estate estate: estates){
            if (estate.getName().equals(name)){
                if (estate.getIsInLobby()){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean posActivate(int numLaw){
        if (isInLobby(laws[numLaw].getWhoNeed()) && coolDown == 0 && !laws[numLaw].isActivation()){
            return true;
        } else {
            return false;
        }
    }

    public void activateLaw(int num){
        if (posActivate(num)){
            laws[num].setActivation(true);
            coolDown = 10;
        }
    }

    public boolean posDeactivate(int numLaw){
        if (!isInLobby(laws[numLaw].getWhoNeed()) && coolDown == 0 && laws[numLaw].isActivation()){
            return true;
        } else {
            return false;
        }
    }

    public void deactivateLaw(int num){
        if (posDeactivate(num)){
            laws[num].setActivation(true);
            coolDown = 10;
        }
    }

    public String getCoolDown(){
        return "Turns before next law " + coolDown;
    }

    public int getNumLaws(){
        return laws.length;
    }

}
