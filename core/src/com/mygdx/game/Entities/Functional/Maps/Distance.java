package com.mygdx.game.Entities.Functional.Maps;

import static java.lang.Math.sqrt;

public class Distance {
    public Distance() {
    }
    public int Dist(Position p1, Position p2){
        return (int) sqrt((p1.GetX() - p2.GetX())*(p1.GetX() - p2.GetX()) + (p1.GetY() - p2.GetY())*(p1.GetY() - p2.GetY()));
    }
}
