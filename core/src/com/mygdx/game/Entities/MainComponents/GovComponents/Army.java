package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.Adv.General;
import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.World;

public class Army {
    // настройки
    private static int baseMaxMorale = 10000;
    private static int baseMaxOrganisation = 1000;

    private int country;
    private int[] armyMan;
    private int amount = 0;
    private int morale = 0;
    private int maxMorale = 0;
    private int tactic;
    private int organization;
    private int maxOrganisation;
    private int maxEquipment;
    private int totalEquipment;
    private int equipment; // это средняя экипированность на тысячу солдат. В начале игры будет где то 1000, к концу дойдет до 2000.
    // Увеличивает как урон от огня, так и защиту. Рассчитывается как средняя экипированность делить на количество тысяч солдат
    private int movement;
    private int maxMovement = BS.baseMaxMovement;
    private int prof; //2 если ополчение, 3 если наемная армия
    private General general = new General();
    private Position position;
    private int fire;
    private int shock;

    private int modFire = 1;
    private int modShock = 1;

    public void Lose(int i){
        armyMan[0] =  (armyMan[0] * i / 100);
        armyMan[1] =  (armyMan[1] * i / 100);
        armyMan[2] =  (armyMan[2] * i / 100);
        armyMan[3] =  (armyMan[3] * i / 100);
        armyMan[5] =  (armyMan[5] * i / 100);
        armyMan[6] =  (armyMan[6] * i / 100);
        armyMan[7] =  (armyMan[7] * i / 100);
        armyMan[8] =  (armyMan[8] * i / 100);
        morale = morale * (100 - 3 * (100 - i)) * 100 / maxMorale ;
        organization = organization * i / 100;
    }
    public void LoseEquipment(int i){
        equipment = equipment * i / 100;
    }
    public void WinBattle(){
        morale += maxMorale / 2 + (int) (Math.random() * 1000);
        if (morale > maxMorale){
            morale = maxMorale;
        }
    }
    public int LoseBattle(){
        organization -= (int) (Math.random() * 20);
        if (organization > 70){
            return 1;
        } else if (organization > 50){
            return 2;
        } else if (organization > 25){
            return 3;
        } else {
            return 4;
        }
    }

    public boolean CheckMove(Position pos){
        if (Math.sqrt(Math.pow(pos.GetX() - position.GetX(), 2) + Math.pow(pos.GetY() - position.GetY(), 2)) * 10 > movement){
            return false;
        } else {
            return true;
        }
    }
    public void Move(Position pos) {
        position = pos;
        movement -= (int) Math.sqrt(Math.pow(pos.GetX() - position.GetX(), 2) + Math.pow(pos.GetY() - position.GetY(), 2)) * 10;
        World.mof.moveArmy(position, pos);
    }
    // для создания профессиональных армий
    public Army(int country, int modMorale, int modOrganisation, Position position, int prof) {
        this.country = country;
        armyMan = new int[8];
        UpdateMaxArmy(modMorale, modOrganisation);
        maxEquipment = 0;
        this.morale = maxMorale * 60/100;
        this.organization = 70;
        this.position = position;
        this.prof = prof;
    }
    // для создания ополчения
    public Army(int[] armyMan, int country, int modMorale, int modOrganisation, Position position, int prof, double equipment) {
        this.country = country;
        this.armyMan = armyMan;
        UpdateMaxArmy(modMorale, modOrganisation);
        UpdateMaxEquipment();
        totalEquipment = (int) (equipment * maxEquipment);
        UpdateEquipment();
        this.morale = maxMorale * 80/100;
        this.organization = 70;
        this.position = position;
        this.prof = prof;
        this.armyMan = armyMan;
    }

    public void UpdateSF(){
        amount = armyMan[7] + armyMan[0] + armyMan[1] + armyMan[2] + armyMan[3] +armyMan[4] + armyMan[5] + armyMan[6];
        shock = (armyMan[0] * 5 + armyMan[1] * 6 + armyMan[2] * 5 + (armyMan[3] * 15 + armyMan[4] * 12 + armyMan[5] * 18)
                * (10 + 3 * general.getBonus()[2]) / 10) / 100 * ((morale + 500) / 100)  * modShock
                * (100 + general.getShock())* (10 + general.getBonus()[0]) * (25 + tactic) * prof / 5000000;

        fire = ((armyMan[0] * 5 + armyMan[1] * 6 + armyMan[2] * 7) * (10 + 2 * general.getBonus()[3]) / 10
                + armyMan[4] * 4 + armyMan[5] * 3 + armyMan[6] * 22 + armyMan[7] * 10) / 100
                * modFire * organization / 100 * (100 + general.getFire()) * (10 + general.getBonus()[1])
                * (25 + tactic) * prof  /50000;
    }

    //каждый ход отсюда
    public void UpdateTactic(int govTac){
        tactic = (govTac + general.getTactic()) * 4;
    }
    public void UpdateMaxArmy(int modMorale, int modOrganisation){
        UpdateMaxEquipment();
        maxMorale = baseMaxMorale * (100 + modMorale) * (50 + general.getMobility()) / 5000;
        maxOrganisation = baseMaxOrganisation * (100 + modOrganisation) / 100 + general.getTactic()*30;
        if (prof == 3){
            maxOrganisation+= 500;
        }
    }
    private void UpdateMaxEquipment(){
        maxEquipment = 0;
        for (int i = 0; i < BS.equipmentOfSquade.length; i++){
            maxEquipment += BS.equipmentOfSquade[i] * armyMan[i];
        }
    }
    public void UpdateOrganisation(int mod){
        organization += 100 + mod;
        if (organization > maxOrganisation){
            organization = maxOrganisation;
        }
    }
    public void UpdateMorale(int modIncrease){
        morale += 10 * (100 + modIncrease);
        if (morale > maxMorale){
            morale = maxMorale;
        }
        movement = maxMovement;
    }
    private void UpdateEquipment(){
        equipment = totalEquipment * 1000 / amount;
    }
    //до сюда

    // увеличивает численность определнного контингента на 1000
    public void Employ(int num){
        armyMan[num] +=1000;
        UpdateSF();
        maxEquipment += BS.equipmentOfSquade[num];
        totalEquipment += BS.equipmentOfSquade[num];
        UpdateEquipment();
    }

    public int getMorale() {
        return morale;
    }

    public int getTactic() {
        return tactic;
    }

    public int getOrganization() {
        return organization;
    }

    public int getEquipment() {
        return equipment;
    }

    public int GetCost(){
        return ((armyMan[0] + armyMan[1] + armyMan[2]) * 10 + (armyMan[3] + armyMan[4] + armyMan[5]) * 15
                + (armyMan[6] + armyMan[7]) * 20) * (1 +prof);
    }
    public int getFire() {
        return fire;
    }

    public int getShock() {
        return shock;
    }

    public int getMovement() {
        return movement;
    }

    public General getGeneral() {
        return general;
    }

    public Position getPosition() {
        return position;
    }

    public void setModFire(int modFire) {
        this.modFire = modFire;
    }

    public void setModShock(int modShock) {
        this.modShock = modShock;
    }

    public int getAmount() {
        return amount;
    }

    public int getCountry() {
        return country;
    }

    public int getMaxEquipment() {
        return maxEquipment;
    }

    public int getTotalEquipment() {
        return totalEquipment;
    }

    public void setOrganization(int organization) {
        this.organization = organization;
    }

    public void setGeneral(General general) {
        this.general = general;
    }
}
