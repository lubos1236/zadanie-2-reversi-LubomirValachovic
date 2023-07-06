package sk.stuba.fei.uim.oop;

import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Step {
    @Getter
    private final Tile TILE;
    @Getter
    private final List<Tile> LIST;

    public Step(Tile tile) {
        this.TILE = tile;
        LIST=new ArrayList<>();
    }
    public void doStep(Color c) {
        this.TILE.setPlayerColor(c);
        for (Tile t:LIST) {
            t.setPlayerColor(c);
            t.setMarkState(MarkState.TURN);
        }
        this.TILE.setMarkState(MarkState.ADD);
    }

    public void markListTiles() {
        for (Tile t:LIST) {
            t.setBackground(Color.PINK);
        }
    }

    public void umMarkListTiles(){
        for (Tile t:LIST) {
            t.setBackground(t.getBACKGROUND_COLOR());
        }
    }
}
