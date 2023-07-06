package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Ai{
    private final Reversi REVERSI;

    public Ai(Reversi reversi) {
        this.REVERSI=reversi;

    }
    public void move() {
        if (REVERSI.getBOARD().getPOSSIBLE_STEPS().size()==0 || REVERSI.getBOARD().isStopGame())
            return;
        List<Step> steps=new ArrayList<>(this.REVERSI.getBOARD().getPOSSIBLE_STEPS());
        steps.sort((s1,s2)->Integer.compare(s2.getLIST().size(),s1.getLIST().size()));
        this.REVERSI.getBOARD().placeToken(steps.get(0).getTILE(),Color.WHITE);

    }
}
