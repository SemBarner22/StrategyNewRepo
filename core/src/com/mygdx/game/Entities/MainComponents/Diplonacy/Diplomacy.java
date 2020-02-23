package com.mygdx.game.Entities.MainComponents.Diplonacy;
/*
Суть дипломатии заключается в чем. Есть таблица взаимоотношений государств. То есть квадратная матрица, каждый
элемент которой является подкласом дипломатии, который содержит все возможные взаимоотношения государств
Первый индекс - тот кто ходит сооветственно.
 */
public class Diplomacy {
    public Diplomacy(int numCountry) {
        subdip = new Subdip[numCountry][numCountry];
        for (int i = 0; i < numCountry; i++){
            for (int j = 0; j < numCountry; j++){
                subdip[i][j] = new Subdip();
            }
        }
    }

    private Subdip[][] subdip;

    public void turn(int country){
        for (Subdip value: subdip[country]){
            value.preTurn();
        }
    }

    public void declareWar(int player, int enemy){
        if (!subdip[player][enemy].isWar() && !subdip[player][enemy].isAlly()){
            subdip[enemy][player].setDeclareWar(true);
            subdip[player][enemy].setDeclareWar(true);
        }
    }
    public void askForPeace(int player, int enemy){
        if (subdip[player][enemy].isWar()){
            subdip[enemy][player].setAskForPeace(true);
        }
    }
    public void askForAlly(int player, int enemy){
        if (subdip[player][enemy].posAlly()){
            subdip[enemy][player].setAskForAlly(true);
        }
    }
    public void breakAlly(int player, int enemy){
        subdip[enemy][player].breakAlly();
        subdip[player][enemy].breakAlly();
    }
    public void pay(int player, int enemy, double ratio){
        subdip[enemy][player].setMod((int) (50*ratio), 10, "Gift");
    }
    public boolean isInWar(int player, int enemy){
        return subdip[player][enemy].isWar();
    }
}
