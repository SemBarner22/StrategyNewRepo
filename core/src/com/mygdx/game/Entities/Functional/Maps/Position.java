package com.mygdx.game.Entities.Functional.Maps;

public class Position {
    private int x;
    private int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int GetX(){
        return x;
    }
    public int GetY(){
        return y;
    }
}
