package com.mygdx.game.Entities.MainComponents.Economy;

import com.mygdx.game.Entities.MainComponents.Gov;
import com.mygdx.game.Entities.MainComponents.GovParts.City;
import com.mygdx.game.Entities.MainComponents.GovParts.Region;

public class Construction {
    public Construction(Gov gov) {
        country = gov;
    }
    private Gov country;
    public boolean posBuild(City city, int numberOfBuilding){
        if (country.CheckMoney(city.CostOfBuilding(numberOfBuilding) * country.getMods()[1] / 100)){
            return true;
        } else{
            return false;
        }
    }
    // строим здание
    public void build(City city, int numberOfBuilding){
        if (posBuild(city, numberOfBuilding)){
            city.Build(numberOfBuilding);
            country.PlusMoney(city.CostOfBuilding(numberOfBuilding) * country.getMods()[1] / 100);
        }
    }
    // улучшаем инфраструктуру города
    public int costInfr(City city){
        return city.CostOfInfrastructure();
    }
    public boolean posBuildCityInfr(City city){
        return country.CheckMoney(costInfr(city));
    }
    public void upgradeCityInfr(City city){
        if (posBuildCityInfr(city)){
            city.UpgradeInfrastructure();
            country.PlusMoney(-costInfr(city));
        }
    }
    // региона
    public int costInf(Region region){
        return region.CostOfInfrastructure();
    }
    public boolean posBuildRegionInfr(Region region){
        return country.CheckMoney(costInf(region));
    }
    public void upgradeRegionInfr(Region region){
        if (posBuildRegionInfr(region)){
            region.UpgradeInfrastructure();
            country.PlusMoney(-costInf(region));
        }
    }
}
