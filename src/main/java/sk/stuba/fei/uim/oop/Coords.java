package sk.stuba.fei.uim.oop;

import lombok.Getter;

public class Coords {
    @Getter
    private final int X,Y;
    public Coords(int x,int y) {
        this.X=x;
        this.Y=y;
    }

    @Override
    public String toString() {
        return "Coords{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
