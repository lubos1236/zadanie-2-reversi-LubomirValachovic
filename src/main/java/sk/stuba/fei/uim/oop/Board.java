package sk.stuba.fei.uim.oop;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    private final Reversi REVERSI;
    private int size;
    private Tile[][] tiles;
    private final List<Tile> EMPTY_Tiles;
    @Getter
    private final List<Step> POSSIBLE_STEPS;
    @Getter
    private boolean stopGame;
    private boolean endGame;
    @Getter @Setter
    private boolean playerSupported;
    @Getter @Setter
    private boolean playerTurn;
    @Getter
    private final int[] TOKEN_COUNTER;

    public Board(Reversi reversi) {
        super();
        this.REVERSI=reversi;
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.playerSupported=false;
        this.endGame=false;
        this.TOKEN_COUNTER=new int[]{0,0};
        this.EMPTY_Tiles=new ArrayList<>();
        this.POSSIBLE_STEPS=new ArrayList<>();

        initializeNewBoard();
    }
    public void ResetBoard() {
        for(int x=0;x<size;x++) {
            for(int y=0;y<size;y++) {
                Tile tile = tiles[x][y];
                if(x==(size/2)-1 && y==(size/2)-1 || x==(size/2) && y==(size/2))
                    tile.setPlayerColor(Color.BLACK);
                else if (x==(size/2) && y==(size/2)-1 || x==(size/2)-1 && y==(size/2))
                    tile.setPlayerColor(Color.WHITE);
                else
                    tile.setPlayerColor(null);
                tile.getSTEP().getLIST().clear();
            }
        }
        this.stopGame=false;
        this.endGame=false;
        this.playerTurn=true;
        this.revalidate();
        takeTurn(Color.BLACK,Color.WHITE);

    }
    public void initializeNewBoard() {
        this.size=REVERSI.getBoardSize();
        this.stopGame=false;
        this.removeAll();
        tiles=new Tile[size][size];
        playerTurn=true;

        createBoard();
        takeTurn(Color.BLACK,Color.WHITE);
    }
    public void takeTurn(Color playerColor,Color enemyColor) {
        if (stopGame)
            return;
        createLists();
        this.REVERSI.getMENU().updateLabels(this.playerTurn,TOKEN_COUNTER);
        findAndCalculateSteps(playerColor,enemyColor);
        if (this.POSSIBLE_STEPS.size()==0) {
            skipMove();
        }
        this.REVERSI.getPANEL().repaint();
    }
    public void findAndCalculateSteps(Color playerColor,Color enemyColor) {
        List<Tile> tempList=new ArrayList<>();

        for(Tile t:this.EMPTY_Tiles) {
            tempList.clear();
            Coords coords=t.getCOORD();
            Step step=t.getSTEP();
            boolean canAddToList=false;

            for (int y=coords.getY()-1;y>=0 ;y--) {
                Tile tempTile = tiles[coords.getX()][y];

                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;

            for (int y=coords.getY()+1;y<size ;y++) {
                Tile tempTile = tiles[coords.getX()][y];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;

            for (int x=coords.getX()-1;x>=0 ;x--) {
                Tile tempTile = tiles[x][coords.getY()];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;

            for (int x=coords.getX()+1;x<size ;x++) {
                Tile tempTile = tiles[x][coords.getY()];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;

            for (int x=coords.getX()-1, y=coords.getY()-1; x>=0 && y>=0 ;x--,y--) {
                Tile tempTile = tiles[x][y];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;
            for (int x=coords.getX()+1, y=coords.getY()+1; x<size && y<size;x++,y++) {
                Tile tempTile = tiles[x][y];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;

            for (int x=coords.getX()-1, y=coords.getY()+1; x>=0 && y<size ;x--,y++) {
                Tile tempTile = tiles[x][y];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }
            if (canAddToList)
                step.getLIST().addAll(tempList);
            tempList.clear();
            canAddToList=false;

            for (int x=coords.getX()+1, y=coords.getY()-1; x<size && y>=0 ;x++,y--) {
                Tile tempTile = tiles[x][y];
                if (tempTile.getPlayerColor()==enemyColor)
                    tempList.add(tempTile);
                else {
                    if (tempTile.getPlayerColor()==playerColor)
                        canAddToList=true;
                    break;
                }
            }

            if (canAddToList)
                step.getLIST().addAll(tempList);
            if (step.getLIST().size()>0)
                this.POSSIBLE_STEPS.add(step);
        }
    }
    public void createLists() {
        EMPTY_Tiles.clear();
        POSSIBLE_STEPS.clear();
        this.TOKEN_COUNTER[0]=0;
        this.TOKEN_COUNTER[1]=0;
        for (Tile[] li:tiles) {
            for (Tile t:li) {
                if (t.getPlayerColor()==null)
                    EMPTY_Tiles.add(t);
                else if (t.getPlayerColor()==Color.BLACK)
                    this.TOKEN_COUNTER[0]++;
                else
                    this.TOKEN_COUNTER[1]++;
                t.getSTEP().getLIST().clear();
            }
        }
    }
    public void createBoard() {
        this.setLayout(new GridLayout(size,size));
        for(int x=0;x<size;x++) {
            for(int y=0;y<size;y++) {
                Color backgroundColor=(x+y)%2==0? Reversi.TILE_BACKGROUND_COLORS[0]: Reversi.TILE_BACKGROUND_COLORS[1];
                Tile tile;
                Color c=null;
                if(x==(size/2)-1 && y==(size/2)-1 || x==(size/2) && y==(size/2))
                    c=Color.black;
                else if (x==(size/2) && y==(size/2)-1 || x==(size/2)-1 && y==(size/2))
                    c=Color.white;
                tile=new Tile(REVERSI,backgroundColor,x,y,c);
                this.add(tile);
                tiles[x][y]=tile;
            }
        }
    }
    public void placeToken(Tile t,Color c){
        if (stopGame)
            return;
        if (t.getSTEP().getLIST().size()!=0) {
            t.getSTEP().doStep(c);
            playerTurn=!playerTurn;
            endGame=false;
        }
    }
    public void skipMove() {
        if (!endGame)
            endGame=true;
        else if(!stopGame) {
            this.REVERSI.getMENU().endGameLabel(TOKEN_COUNTER);
            stopGame=true;
        }
        playerTurn=!playerTurn;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}