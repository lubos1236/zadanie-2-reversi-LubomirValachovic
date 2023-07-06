package sk.stuba.fei.uim.oop;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Reversi{
    private final JFrame WINDOW;
    @Getter @Setter
    private int boardSize=6;
    @Getter
    private final Menu MENU;
    @Getter
    private final Board BOARD;
    @Getter
    private final JPanel PANEL;
    @Getter
    private final Ai AI;
    public static final Font[] FONT = new Font[]{
            new Font("TimesRoman", Font.PLAIN, 50),
            new Font("TimesRoman", Font.PLAIN, 40),
            new Font("TimesRoman", Font.PLAIN, 28),
            new Font("TimesRoman", Font.PLAIN, 15)};
    public static final Color[] TILE_BACKGROUND_COLORS = new Color[]{
            new Color(0,136,30),
            new Color(0,156,27)};

    public Reversi() {
        this.WINDOW=new JFrame();
        this.WINDOW.setResizable(false);
        this.WINDOW.setDefaultCloseOperation(WINDOW.EXIT_ON_CLOSE);
        this.PANEL=new JPanel(new BorderLayout());
        this.WINDOW.add(PANEL);
        this.MENU=new Menu(this);
        this.BOARD=new Board(this);
        this.AI=new Ai(this);

        PANEL.add(this.MENU,BorderLayout.PAGE_START);
        PANEL.add(this.BOARD,BorderLayout.CENTER);

        WINDOW.setSize(700,700);
        WINDOW.setVisible(true);
    }
    public void createNewBoard() {
        BOARD.ResetBoard();
        BOARD.initializeNewBoard();
        WINDOW.revalidate();
    }
    public void quit()
    {
        WINDOW.dispose();
    }
}
