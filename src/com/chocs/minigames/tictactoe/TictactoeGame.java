package com.chocs.minigames.tictactoe;

import javax.swing.JFrame;

public class TictactoeGame extends JFrame {
    public TictactoeGame(boolean isLocalGame) {
        this.add(new TictactoeGraphics(isLocalGame));
        this.pack();
        this.setTitle("Tic Tac Toe");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
