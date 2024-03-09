package com.chocs;

import com.chocs.minigames.rps.RpsGame;
import com.chocs.minigames.snake_game.SnakeGame;
import com.chocs.minigames.tictactoe.TictactoeGame;

import javax.swing.*;
import java.awt.*;

//create minigame class to create a page on which user will navigate
class MinigamePage extends JFrame
{
    //constructor
    MinigamePage()
    {
        this.pack();
        this.setTitle("Minigames");
        this.setSize(400, 500);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setFocusable(false);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10)); // 2 lin  1 col,  10 pixels gap

        JButton tictactoeButton = new JButton("Tic Tac Toe (vs Local Player)");
        tictactoeButton.setPreferredSize(new Dimension(200, 100));
        panel.add(tictactoeButton);
        
        JButton tictactoeButtonBot = new JButton("Tic Tac Toe (vs Bot)");
        tictactoeButtonBot.setPreferredSize(new Dimension(200, 100));
        panel.add(tictactoeButtonBot);

        JButton rpsButton = new JButton("Rock Paper Scissors");
        rpsButton.setPreferredSize(new Dimension(200, 100));
        panel.add(rpsButton);

        JButton snakeButton = new JButton("Snake Game");
        snakeButton.setPreferredSize(new Dimension(200, 100));
        panel.add(snakeButton);

        add(panel);

        tictactoeButton.addActionListener(e -> {
            TictactoeGame tictactoe = new TictactoeGame(true);
        });

        tictactoeButtonBot.addActionListener(e -> {
            TictactoeGame tictactoe = new TictactoeGame(false);
        });

        rpsButton.addActionListener(e -> {
            RpsGame rps = new RpsGame();
        });

        snakeButton.addActionListener(e -> {
            SnakeGame snake = new SnakeGame();
        });
    }
}
