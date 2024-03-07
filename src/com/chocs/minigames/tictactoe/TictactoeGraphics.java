package com.chocs.minigames.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class TictactoeGraphics extends JPanel implements ActionListener {
    static final int WIDTH = 700;
    static final int LENGTH = 700;

    static final String MARK_X = "X";
    static final String MARK_O = "O";

    boolean isFirstPlayerActive;
    boolean isBotActive;

    final JButton[] tiles = new JButton[9];

    JLabel fillText1 = new JLabel("");
    JLabel fillText2 = new JLabel("");
    JLabel playText = new JLabel("X turn.");

    public TictactoeGraphics(boolean isLocalGame) {
        GridLayout grid = new GridLayout(4, 3);

        isBotActive = !isLocalGame;

        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setLayout(grid);

        playText.setFont(new Font("Arial", Font.BOLD, 32));
        playText.setHorizontalAlignment(JLabel.CENTER);

        this.add(fillText1);
        this.add(playText);
        this.add(fillText2);

        for(int i = 0; i < 9; i++) {
            tiles[i] = new JButton();
            tiles[i].setFont(new Font("Arial", Font.BOLD, 125));
            tiles[i].setFocusable(false);
            tiles[i].addActionListener(this);
            this.add(tiles[i]);
        }

        isFirstPlayerActive = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 9; i++) {
            if(e.getSource() == tiles[i]) {
                if(tiles[i].getText().isEmpty()) {
                    if(isFirstPlayerActive) {
                        tiles[i].setForeground(Color.black);
                        tiles[i].setText(MARK_X);
                        if(!isBotActive) {
                            playText.setText("O turn.");
                            isFirstPlayerActive = false;
                        }
                    } else {
                        tiles[i].setForeground(Color.blue);
                        tiles[i].setText(MARK_O);
                        playText.setText("X turn.");
                        isFirstPlayerActive = true;
                    }

                    if(checkState()) {
                        return;
                    }

                    if(isBotActive) {
                        botPlay();
                        i++;
                    }

                    if(checkState()) {
                        return;
                    }
                }
            }
        }
    }

    protected void botPlay() {
        Random random = new Random();

        while(true) {
            int tile = random.nextInt(9);

            if(tiles[tile].getText().isEmpty()) {
                tiles[tile].setForeground(Color.blue);
                tiles[tile].setText(MARK_O);
                isFirstPlayerActive = true;
                return;
            }
        }
    }

    protected boolean checkState() {
        if(checkMark(MARK_X)) {
            playText.setText("X won.");
            playText.setForeground(Color.black);
            return true;
        }

        if(checkMark(MARK_O)) {
            playText.setText("O won.");
            playText.setForeground(Color.blue);
            return true;
        }

        if(checkDraw()) {
            playText.setText("Draw.");
            playText.setForeground(Color.darkGray);

            for(int i = 0; i < 9; i++) {
                tiles[i].setEnabled(false);
            }

            return true;
        }

        return false;
    }

    protected boolean checkDraw() {
        int count = 0;

        for(int i = 0; i < 9; i++) {
            if(!tiles[i].getText().isEmpty()) {
                count++;
            }
        }

        return count == 9;
    }
    protected boolean checkMark(String mark) {
        boolean isDone;

        isDone = checkDirection(0, 1, 2, mark); // horizontal 1
        if(!isDone) isDone = checkDirection(3, 4, 5, mark); // horizontal 2
        if(!isDone) isDone = checkDirection(6, 7, 8, mark); // horizontal 3

        if(!isDone) isDone = checkDirection(0, 3, 6, mark); // vertical 1
        if(!isDone) isDone = checkDirection(1, 4, 7, mark); // vertical 2
        if(!isDone) isDone = checkDirection(2, 5, 8, mark); // vertical 3

        if(!isDone) isDone = checkDirection(0, 4, 8, mark); // diagonal 1
        if(!isDone) isDone = checkDirection(2, 4, 6, mark); // diagonal 2


        return isDone;
    }

    protected boolean checkDirection(int posA, int posB, int posC, String mark) {
        if(tiles[posA].getText().equals(mark) && tiles[posB].getText().equals(mark) && tiles[posC].getText().equals(mark)) {
            setWinner(posA, posB, posC);
            return true;
        }

        return false;
    }

    protected void setWinner(int posA, int posB, int posC) {
        tiles[posA].setBackground(Color.GREEN);
        tiles[posB].setBackground(Color.GREEN);
        tiles[posC].setBackground(Color.GREEN);

        Arrays.stream(tiles).forEach(t -> t.setEnabled(false));
    }
}
