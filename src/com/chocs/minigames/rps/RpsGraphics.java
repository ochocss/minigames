package com.chocs.minigames.rps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RpsGraphics extends JPanel implements ActionListener {

    static final int WIDTH = 700;
    static final int LENGTH = 700;
    String[] rps = {"Rock", "Paper", "Scissors"};
    final JButton[] tiles = new JButton[3];
    JButton botPlay = new JButton();
    JLabel text = new JLabel("The bot is thinking....");
    JLabel resultText = new JLabel("");

    public RpsGraphics() {
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setLayout(new GridLayout(3, 3));
        this.add(text);
        this.add(resultText);

        text.setFont(new Font("Arial", Font.ITALIC, 12));
        resultText.setFont(new Font("Arial", Font.BOLD, 15));

        for(int i = 0; i < 3; i++) {
            tiles[i] = new JButton();
            tiles[i].setFont(new Font("Arial", Font.BOLD, 12));
            tiles[i].setFocusable(false);
            tiles[i].addActionListener(this);
            tiles[i].setText(rps[i]);

            this.add(tiles[i]);
        }
        botPlay = tiles[new Random().nextInt(3)];

        tiles[0].setForeground(Color.darkGray);
        tiles[1].setForeground(Color.orange);
        tiles[2].setForeground(Color.blue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tiles[0].setEnabled(false);
        tiles[1].setEnabled(false);
        tiles[2].setEnabled(false);
        text.setText("Bot played " + botPlay.getText());

        for(int i = 0; i < 3; i++) {
            if(e.getSource() == tiles[i]) {
                if(tiles[i].getText().equals(botPlay.getText())) {
                    resultText.setText("Draw.");
                    resultText.setForeground(Color.black);
                    break;
                } else
                    checkWin(tiles[i].getText());
            }
        }
    }

    private void checkWin(String userPlay) {
        if(userPlay.equals("Scissors")) {
            if(botPlay.getText().equals("Paper")) {
                win();
            }
            if(botPlay.getText().equals("Rock")) {
                loss();
            }
        }
        if(userPlay.equals("Paper")) {
            if(botPlay.getText().equals("Rock")) {
                win();
            }
            if(botPlay.getText().equals("Scissors")) {
                loss();
            }
        }
        if(userPlay.equals("Rock")) {
            if(botPlay.getText().equals("Scissors")) {
                win();
            }
            if(botPlay.getText().equals("Paper")) {
                loss();
            }
        }
    }

    private void win() {
        resultText.setText("You win.");
        resultText.setForeground(Color.green);
    }

    private void loss() {
        resultText.setText("You lose.");
        resultText.setForeground(Color.red);
    }
}