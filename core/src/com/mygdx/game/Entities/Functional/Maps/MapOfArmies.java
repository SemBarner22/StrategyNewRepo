package com.mygdx.game.Entities.Functional.Maps;


import java.util.ArrayList;

// видимо тут показывается армия какой страны находится в этой позиции
public class MapOfArmies {
    private int[][] map;
    public MapOfArmies(int n, int m){
        map = new int[n][m];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                map[i][j] = -1;
            }
        }
    }
    private ArrayList<CityCoordinate> cityCoordinates = new ArrayList<>();
    public void addCity(Position position, int unic, int country){
        cityCoordinates.add(new CityCoordinate(country, unic, position));
    }
    public boolean posOccupy(Position position){
        int num = getCity(position);
        int country = cityCoordinates.get(num).getCountry();
        //TODO сделать перебор всех координат с проверкой, есть ли армия
        Position pos = cityCoordinates.get(num).getPosition();
        int xmax = pos.GetX()+2;
        int xmin = pos.GetX()-1;
        int ymax = pos.GetY()+2;
        int ymin = pos.GetY()-1;
        for (int i = xmin; i < xmax+1; i++){
            for (int j = ymin; j < ymax+1; j++){
                if (map[i][j] == country){
                    return false;
                }
            }
        }
        return true;
    }
    public int getCity(Position position){
        for (int i = 0; i <cityCoordinates.size(); i++){
            if (cityCoordinates.get(i).isThis(position)){
                return i;
            }
        }
        System.out.println("THERE NO CITY THERE. CHECK getCity IN MoF AND TRY TO FIX IT");
        return -1;
    }
    public int[] getCityCoordinates(Position position){
        for (int i = 0; i <cityCoordinates.size(); i++){
            if (cityCoordinates.get(i).isThis(position)){
                int[] result = new int[2];
                result[0] = cityCoordinates.get(i).getCountry();
                result[1] = cityCoordinates.get(i).getUnicNumber();
                return result;
            }
        }
        System.out.println("THERE NO CITY THERE. CHECK getCityCoordinates IN MoF AND TRY TO FIX IT");
        return null;
    }
    public void AddArmy(int countryTag, Position position){
        map[position.GetX()][position.GetY()] = countryTag;
    }
    public int CheckPosition(Position position){
        return map[position.GetX()][position.GetY()];
    }
    public boolean checkArmy(Position position){
        return map[position.GetX()][position.GetY()] > -1;
    }
    public boolean checkCity(Position position){
        int x = position.GetX();
        int y = position.GetY();
        if ((map[x+1][y] == -2) || (map[x+1][y+1] == -2) || (map[x+1][y-1] == -2) || (map[x][y+1] == -2) || (map[x][y-1] == -2) |
        (map[x-1][y] == -2) || (map[x-1][y+1] == -2) || (map[x-1][y-1] == -2)
        ){
            if (map[x][y] != -2){
                return true;
            }
        }
        return false;
    }
    public void moveArmy(Position first, Position last){
        //TODO
        map[last.GetX()][last.GetY()] = map[first.GetX()][first.GetY()];
        map[first.GetX()][first.GetY()] = -1;
    }

}
