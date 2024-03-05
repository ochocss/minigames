package com.chocs.minigames.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

public class TictactoeGraphics extends JPanel implements ActionListener {
    static final int WIDTH = 700;
    static final int LENGTH = 700;

    static final String MARK_X = "X";
    static final String MARK_O = "O";

    boolean isFirstPlayerActive;

    final JButton[] tiles = new JButton[9];

    public TictactoeGraphics() {
        GridLayout grid = new GridLayout(3, 3);

        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setLayout(grid);

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
                        isFirstPlayerActive = false;
                    } else {
                        tiles[i].setForeground(Color.blue);
                        tiles[i].setText(MARK_O);
                        isFirstPlayerActive = true;
                    }

                    checkState();
                }
            }
        }
    }

    protected void checkState() {
        if(checkMark(MARK_X)) {
            return;
        }

        if(checkMark(MARK_O)) {
            return;
        }

        if(checkDraw()) {
            return;
        }
    }

    protected boolean checkDraw() {
        int i = 0;
        while(!tiles[i].getText().isEmpty()) {
            if(i == tiles.length - 1) {
                Arrays.stream(tiles).forEach(t -> t.setEnabled(false));
            }
            i++;
        }

        return i == tiles.length -1;
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