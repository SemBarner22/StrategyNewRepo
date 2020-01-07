package com.mygdx.game.Entities.Functional.Maps;

public class CityCoordinate {
    private int country;
    private int region;
    private int city;
    private Position position;
    private int unicNumber;

    public CityCoordinate(int country, int region, int city) {
        this.country = country;
        this.region = region;
        this.city = city;
    }

    public CityCoordinate(int country, int unic, Position position) {
        this.country = country;
        unicNumber = unic;
        this.position = position;
    }

    public boolean isThis(Position pos){
        int xmax = position.GetX()+2;
        int xmin = position.GetX()-1;
        int ymax = position.GetY()+2;
        int ymin = position.GetY()-1;
        if (pos.GetY()<ymin || pos.GetY()>ymax || pos.GetX() >xmax || pos.GetX() < xmin){
            return false;
        } else{
            return true;
        }
    }

    public int getCountry() {
        return country;
    }
    public int getRegion() {
        return region;
    }
    public int getCity() {
        return city;
    }
    public Position getPosition(){return position;}

    public int getUnicNumber() {
        return unicNumber;
    }
}
