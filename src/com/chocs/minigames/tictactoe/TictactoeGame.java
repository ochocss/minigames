package com.chocs.minigames.tictactoe;

import javax.swing.JFrame;

public class TictactoeGame extends JFrame {
    public TictactoeGame() {
        this.add(new TictactoeGraphics());
        this.pack();
        this.setTitle("Tic Tac Toe");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
