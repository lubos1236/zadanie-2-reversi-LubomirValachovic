package sk.stuba.fei.uim.oop;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class Tile extends Adapter{
    private final Reversi REVERSI;
    @Getter
    private final Coords COORD;
    @Getter @Setter
    private Color playerColor;
    @Getter
    private final Step STEP;
    @Getter
    private final Color BACKGROUND_COLOR;
    private final Timer TIMER;
    private static final BasicStroke BASIC_STROKE=new BasicStroke(3);
    private static final BasicStroke MARK_BASIC_STROKE=new BasicStroke(5);
    @Setter
    private MarkState markState;

    public Tile(Reversi reversi,Color backgroundColor,int x,int y,Color playerColor) {
        super();
        this.REVERSI=reversi;
        this.playerColor=playerColor;
        this.COORD=new Coords(x,y);
        this.TIMER=new Timer(1000,this);
        this.TIMER.setRepeats(false);
        this.markState=MarkState.NONE;
        this.BACKGROUND_COLOR=backgroundColor;
        this.setBackground(backgroundColor);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.STEP=new Step(this);
        this.addMouseListener(this);
    }
    private Font getNewFONT() {
        int boardSize=this.REVERSI.getBoardSize();
        Font font = null;
        switch (boardSize)
        {
            case 6: font= Reversi.FONT[0];break;
            case 8: font= Reversi.FONT[1];break;
            case 10: font= Reversi.FONT[2];break;
            case 12: font= Reversi.FONT[3];break;
        }
        return font;
    }
    private void drawText(int h,int w,Graphics2D g2d) {
        int boardSize=this.REVERSI.getBoardSize();
        int y=(w/2)+10;
        int x=STEP.getLIST().size()<9 ? (h/2)-10:(h/2)-22;
        switch (boardSize) {
            case 8:
                y=(w/2)+10;
                x=STEP.getLIST().size()<9 ? (h/2)-6:(h/2)-18;
                break;
            case 10:
                y=(w/2)+5;
                x=STEP.getLIST().size()<9 ? (h/2)-3:(h/2)-11;
                break;
            case 12:
                y=(w/2)+3;
                x=STEP.getLIST().size()<9 ? (h/2):(h/2)-4;
        }
        g2d.drawString(String.valueOf(STEP.getLIST().size()),x,y);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = this.getSize().height;
        int w = this.getSize().width;
        if (this.STEP.getLIST().size() != 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLUE);
            g2d.setStroke(BASIC_STROKE);
            g2d.drawOval(15, 10, h - 20, h - 20);

            if (this.REVERSI.getBOARD().isPlayerSupported()) {
                g2d.setColor(Color.YELLOW);
                Font font = getNewFONT();
                g2d.setFont(font);
                drawText(h, w, g2d);
            }

        }
        else {
            if (playerColor != null) {
                g.setColor(playerColor);
                g.fillOval(15, 10, h - 20, h - 20);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(MARK_BASIC_STROKE);

                if (markState==MarkState.ADD)
                {
                    g2d.setColor(Color.RED);
                    g2d.drawOval(15, 10, h - 20, h - 20);

                }
                else if (markState==MarkState.TURN)
                {
                    g2d.setColor(Color.MAGENTA);
                    g2d.drawOval(15, 10, h - 20, h - 20);
                }
                markState=MarkState.NONE;
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (STEP.getLIST().size()==0 || !this.REVERSI.getBOARD().isPlayerTurn())
            return;
        this.setBackground(BACKGROUND_COLOR);
        this.STEP.umMarkListTiles();
        this.REVERSI.getBOARD().placeToken(this,Color.BLACK);
        this.REVERSI.getBOARD().takeTurn(Color.WHITE,Color.BLACK);
        this.TIMER.start();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if(this.STEP.getLIST().size() !=0 && this.REVERSI.getBOARD().isPlayerTurn())
        {
            this.setBackground(Color.RED);
            if (this.REVERSI.getBOARD().isPlayerSupported())
                STEP.markListTiles();
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(BACKGROUND_COLOR);
        STEP.umMarkListTiles();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.REVERSI.getBOARD().isPlayerTurn())
            this.REVERSI.getAI().move();
        if (this.REVERSI.getBOARD().isStopGame())
            return;
        this.REVERSI.getBOARD().takeTurn(Color.BLACK,Color.WHITE);
        if(this.REVERSI.getBOARD().getPOSSIBLE_STEPS().size()==0) {
            this.REVERSI.getBOARD().takeTurn(Color.WHITE,Color.BLACK);
            if(this.REVERSI.getBOARD().getPOSSIBLE_STEPS().size()==0)
                this.TIMER.start();
        }
    }
}
