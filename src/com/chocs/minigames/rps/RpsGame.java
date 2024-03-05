package com.chocs.minigames.rps;

import javax.swing.*;

public class RpsGame extends JFrame {
    public RpsGame() {
        this.add(new RpsGraphics());
        this.pack();
        this.setTitle("Rock, Paper, Scissors");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
