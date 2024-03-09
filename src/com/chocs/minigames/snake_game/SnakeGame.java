package com.chocs.minigames.snake_game;

import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        this.add(new SnakeGraphics());
        this.pack();
        this.setTitle("Snake Game");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
