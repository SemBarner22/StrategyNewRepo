package com.mygdx.game.Entities.Technology;

import com.mygdx.game.Entities.BaseSettings.BS;

public class Tech {
    public Tech(int num) {
        subTech = new SubTech[3];
        subTech[0] = BS.ST[num*3];//TODO тут идут ссылки, надо заменить на конструктор
        subTech[1] = BS.ST[num*3 + 1];
        subTech[2] = BS.ST[num*3 + 2];
        this.num = num;
        if (num == 0){
            name = "Инфраструктура и произвоство";
        }
    }
    private int[] bonus = new int[BS.numMod];
    private SubTech[] subTech;
    private String name;
    private int num;
    private void UpdateBonus(){
        for (int i = 0; i < bonus.length; i++){
            bonus[i] = 0;
        }
        for (int i = 0; i < subTech.length; i++) {
            for (int j = 0; j < subTech[i].getBonus().length; j++) {
                bonus[subTech[i].getNumBon()[j]] += subTech[i].getBonus()[j];
            }
            for (int j = 0; j < BS.inv[num][i].length; j++) {
                if (BS.inv[num][i][j].isMade()) {
                    bonus[BS.inv[num][i][j].getNumBon()] += BS.inv[num][i][j].getBon();
                }
            }
        }
    }
    // Донаты в техи. Делаются каждый ход. Возвращает чтобы сделать уведомления
    public int[] Donation(int money){
        int[] res = new int[4];
        for (int i = 0; i < subTech.length; i++) {
            if (Math.random() * money > BS.techCost[subTech[i].getLevel()]) {
                if (subTech[i].Progress()){
                    res[0] = i;
                }
            }
        }
        for (int i = 0; i < subTech.length; i++){
            for (int j = 0; j <= subTech[i].getLevel(); j++){
                for (int k = 0; k < BS.inv[i][j].length; k++) {
                    if (!BS.inv[i][j][k].isMade() && Math.random() * money > BS.inv[i][j][k].getCost()) {
                        BS.inv[i][j][k].Made();
                        res[1] = i;
                        res[2] = j;
                        res[3] = k;
                    }
                }
            }
        }
        UpdateBonus();
        return res;
    }

    public SubTech[] getSubTech() {
        return subTech;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public int[] getBonus() {
        return bonus;
    }
}
