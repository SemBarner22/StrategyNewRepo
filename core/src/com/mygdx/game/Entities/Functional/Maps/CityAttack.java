package com.mygdx.game.Entities.Functional.Maps;

// этот класс надо аккуратно создавать, потому что если не будут овпадать номера координат и номера позиций, то начнутся дикие баги.
// Также отсюда следует, что между городами должно быть минимум 2 клетки. Особенно надо помнить, если сделаю возможность строить города
public class CityAttack {
    public CityAttack(Position[] cityPosition, CityCoordinate[] cityCoordinate) {
        this.cityPosition = cityPosition;
        this.cityCoordinate = cityCoordinate;
    }

    public CityAttack() {
    }

    private Position[] cityPosition;
    private CityCoordinate[] cityCoordinate;

    public CityCoordinate CheckPositionCityAttack(Position position) {
        for (int i = 0; i < cityPosition.length; i++) {
            if (Math.abs(position.GetX() - cityPosition[i].GetX()) <= 1 && Math.abs(position.GetY() - cityPosition[i].GetY()) <= 1){
                return cityCoordinate[i];
            }
        }
        return null;
    }


    public boolean CheckPosition(Position position){
        for (int i = 0; i < cityCoordinate.length; i++){
            if (position == cityPosition[i]){
                return true;
            }
        }
        return false;
    }

    public Position[] getCityPosition() {
        return cityPosition;
    }

    public void setCityPosition(Position[] cityPosition) {
        this.cityPosition = cityPosition;
    }

    public CityCoordinate[] getCityCoordinate() {
        return cityCoordinate;
    }

    public void setCityCoordinate(CityCoordinate[] cityCoordinate) {
        this.cityCoordinate = cityCoordinate;
    }
}
