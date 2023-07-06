package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

public class Menu extends Adapter {
    private final Reversi REVERSI;
    private final JLabel SIZE_LABEL;
    private final JLabel TURN_LABEL;
    private final JLabel SCORE_NAME;
    private final JLabel SCORE_SCORE;
    public Menu(Reversi reversi) {
        this.REVERSI=reversi;
        JPanel scorePanel = new JPanel(new GridLayout(2, 1));
        this.SCORE_NAME=new JLabel();
        this.SCORE_NAME.setHorizontalAlignment(JLabel.CENTER);
        scorePanel.add(SCORE_NAME);

        this.SCORE_SCORE=new JLabel();
        scorePanel.add(SCORE_SCORE);
        this.SCORE_SCORE.setHorizontalAlignment(JLabel.CENTER);

        this.SIZE_LABEL=new JLabel();
        SIZE_LABEL.setHorizontalAlignment(JLabel.CENTER);

        this.TURN_LABEL=new JLabel();
        TURN_LABEL.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new GridLayout(1,6));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        this.setFocusable(true);

        this.add(SIZE_LABEL,BorderLayout.CENTER);
        int[] values={6,8,10,12};
        String[] items = { "6", "8", "10", "12" };
        JComboBox<String> sizeBox=new JComboBox<>(items);
        sizeBox.addItemListener(this);
        sizeBox.setFocusable(false);
        this.add(sizeBox);

        JCheckBox checkBox=new JCheckBox("Player support");
        checkBox.addItemListener(this);
        checkBox.setFocusable(false);
        this.add(checkBox);

        this.add(TURN_LABEL);
        this.add(scorePanel);

        updateLabels(true,2,2);

        JButton resetButton=new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        this.add(resetButton);
        this.addKeyListener(this);
    }
    public void updateLabels(boolean playerTurn,int ... scores) {
        this.SIZE_LABEL.setText("Size: "+ this.REVERSI.getBoardSize() +"x"+ this.REVERSI.getBoardSize());
        String turn=playerTurn ? "Black":"White";
        this.TURN_LABEL.setText("Turn: "+turn);
        this.SCORE_NAME.setText("Player: AI");
        this.SCORE_SCORE.setText(scores[0]+" : "+scores[1]);

    }
    public void endGameLabel(int ... scores){
        String s;
        if (scores[0]==scores[1])
            s="DRAW";
        else
            s= scores[0]>scores[1]?"Player WIN":"AI WIN";
        this.TURN_LABEL.setText(s);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() instanceof JCheckBox) {
            this.REVERSI.getBOARD().setPlayerSupported(!this.REVERSI.getBOARD().isPlayerSupported());
            this.REVERSI.getBOARD().repaint();
        }
        else if (REVERSI.getBoardSize()!=Integer.parseInt((String)e.getItem())) {

                REVERSI.setBoardSize(Integer.parseInt((String)e.getItem()));
                REVERSI.createNewBoard();
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        REVERSI.createNewBoard();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar()==KeyEvent.VK_ESCAPE)
            REVERSI.quit();
        else if (Character.toUpperCase(e.getKeyChar())==KeyEvent.VK_R)
            REVERSI.createNewBoard();
    }
}