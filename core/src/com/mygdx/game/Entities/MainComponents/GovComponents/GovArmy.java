package com.mygdx.game.Entities.MainComponents.GovComponents;

import com.mygdx.game.Entities.BaseSettings.BS;
import com.mygdx.game.Entities.Functional.Maps.Position;
import com.mygdx.game.Entities.MainComponents.Gov;
import com.mygdx.game.Entities.MainComponents.GovParts.City;
import com.mygdx.game.Entities.MainComponents.GovParts.Region;
import com.mygdx.game.Entities.MainComponents.World;

public class GovArmy {
    private Gov country;
    private int totalArmy;
    public GovArmy(Gov country) {
        this.country = country;
    }

    public void upgradeArmy(Position position, int armyMen){
        Army arm = getArmyPos(position);
        if (arm != null && country.getMaxArmy()-999 < totalArmy) {
            arm.Employ(armyMen);
            country.PlusMoney(BS.baseCostCreationSquad[armyMen]);
        }
    }
    public void createArmy(City cit){
        if (cit.checkPosition() &&
                country.CheckMoney(BS.baseCostCreationSquad[0] * (100 + country.getMods()[2])) &&
                totalArmy < country.getMaxArmy() - 999){
            Army newArmy = new Army(country.getCounryNum(), country.getMods()[2], country.getMods()[16],
                    cit.getPosArmy(), 3);
            newArmy.Employ(0);
            country.getArmy().add(newArmy);
            World.mof.AddArmy(country.getCounryNum(), cit.getPosArmy());
            country.PlusMoney(-BS.baseCostCreationSquad[0] * (100 + country.getMods()[2]));
        }
    }
    // cетаем генерала
    public void setGeneral(int arm, int genera){
        if (country.getArmy().get(arm).getGeneral() != null){
            RemoveGeneral(arm);
        }
        country.getArmy().get(arm).setGeneral(country.getGovAdvisors().getGeneral().get(genera));
        country.getGovAdvisors().getGeneral().get(genera).setInArmy(arm);
    }
    public void RemoveGeneral(int arm){
        country.getArmy().get(arm).getGeneral().setInArmy(-1);
        country.getArmy().get(arm).setGeneral(null);
    }
    public void DeleteArmy(Army arm){
        country.getArmy().remove(arm);
    }
    private int maxMobilisationArmy = 0;
    private void UpdateMobilisationArmy(){
        maxMobilisationArmy = 0;
        for (Region value : country.getRegionControl()){
            for (City cit: value.getCity()){
                for (int ammount : cit.Mobilisation()){
                    maxMobilisationArmy += ammount;
                }
            }
        }
    }
    private int equipment = 0;
    public void buyEquipment(int m){
        equipment += m;
        country.PlusMoney(-m);
    }
    private int maxEquipment = 0;
    private void MobilisationCity(City cit){
        double partEqp = 1.0 * equipment/ maxEquipment;
        if (partEqp > 1){
            partEqp = 1;
        }
        Army arm = new Army(cit.Mobilisation(), country.getCounryNum(), country.getMods()[15], country.getMods()[16],
                cit.getPosArmy(), 2, partEqp);
        if (cit.checkPosition() && !cit.isMobilisation() &&
                country.CheckMoney(arm.getMaxEquipment() * BS.baseCostMobilisation)) {
            country.mobilisateArmy.add(arm);
            cit.setMobilisation(true);
            country.PlusMoney(arm.getMaxEquipment() * BS.baseCostMobilisation);
            maxEquipment -= arm.getMaxEquipment();
            equipment -= arm.getTotalEquipment();
            World.mof.AddArmy(country.getCounryNum(), cit.getPosArmy());
        }
    }
    public void Mobilisation(){
        for (Region value : country.getRegionControl()) {
            for (int j = 0; j < value.getCity().length; j++) {
                MobilisationCity(value.getCity()[j]);
            }
        }
    }
    public void Demobilisation(){
        while (country.mobilisateArmy.size()>0){
            maxEquipment += country.mobilisateArmy.get(0).getMaxEquipment();
            equipment += country.mobilisateArmy.get(0).getTotalEquipment();
            country.mobilisateArmy.remove(0);
        }
        for (Region value : country.getRegionControl()) {
            for (int j = 0; j < value.getCity().length; j++) {
                value.getCity()[j].setMobilisation(false);
            }
        }
    }
    //обновляем мораль и организованность должно использоваться каждый ход
    public void UpdateArmy(){
        UpdateMobilisationArmy();
        totalArmy = 0;
        for (Army value : country.getArmy()) {
            value.UpdateMaxArmy(country.getMods()[15], country.getMods()[16]);
            value.UpdateMorale(country.getMods()[9]);
            value.UpdateOrganisation(country.getMods()[10]);
            value.UpdateTactic(country.getMods()[14]);
            totalArmy += value.getAmount();
        }
        for (Army value : country.mobilisateArmy) {
            value.UpdateMaxArmy(country.getMods()[15], country.getMods()[16]);
            value.UpdateMorale(country.getMods()[9]);
            value.UpdateOrganisation(country.getMods()[10]);
            value.UpdateTactic(country.getMods()[14]);
        }
    }
    // возвращает армию, которая стоит на какой то позиции
    public Army getArmyPos(Position pos){
        for (Army value : country.getArmy()){
            if (value.getPosition().equals(pos)){
                return value;
            }
        }
        for (Army value : country.mobilisateArmy){
            if (value.getPosition().equals(pos)){
                return value;
            }
        }
        return null;
    }


}
