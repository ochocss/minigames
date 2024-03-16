package com.chocs.minigames.rps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class RpsGraphics extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int LENGTH = 400;
    String[] rps = {"Rock", "Paper", "Scissors"};
    final JButton[] tiles = new JButton[3];
    JButton botPlay = new JButton();
    JLabel botPlayText = new JLabel("");
    JLabel resultText = new JLabel("");
    JLabel userPlayText = new JLabel("");

    Action restartAction;

    public RpsGraphics() {
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setLayout(new GridLayout(2, 3));

        this.add(botPlayText);
        this.add(resultText);
        this.add(userPlayText);

        restartAction = new RestartAction();

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "restartAction");
        this.getActionMap().put("restartAction", restartAction);

        botPlayText.setHorizontalAlignment(JLabel.CENTER);
        resultText.setHorizontalAlignment(JLabel.CENTER);
        userPlayText.setHorizontalAlignment(JLabel.CENTER);

        botPlayText.setFont(new Font("Arial", Font.ITALIC, 16));
        resultText.setFont(new Font("Arial", Font.BOLD, 28));
        userPlayText.setFont(new Font("Arial", Font.ITALIC, 16));

        for(int i = 0; i < 3; i++) {
            tiles[i] = new JButton();
            tiles[i].setFont(new Font("Arial", Font.BOLD, 28));
            tiles[i].setFocusable(false);
            tiles[i].addActionListener(this);
            tiles[i].setText(rps[i]);

            this.add(tiles[i]);
        }

        tiles[0].setForeground(Color.darkGray);
        tiles[1].setForeground(Color.orange);
        tiles[2].setForeground(Color.blue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tiles[0].setEnabled(false);
        tiles[1].setEnabled(false);
        tiles[2].setEnabled(false);

        botPlay = tiles[new Random().nextInt(3)];

        botPlayText.setText("Bot played " + botPlay.getText());
        setBotTextColor();

        for(int i = 0; i < 3; i++) {
            if(e.getSource() == tiles[i]) {
                userPlayText.setText("You played " + tiles[i].getText());
                setUserTextColor(tiles[i]);

                checkWin(tiles[i].getText());
            }
        }
    }

    private void setUserTextColor(JButton playButton) {
        if(playButton.getText().equals("Rock")) {
            userPlayText.setForeground(Color.darkGray);
        }
        if(playButton.getText().equals("Paper")) {
            userPlayText.setForeground(Color.orange);
        }
        if(playButton.getText().equals("Scissors")) {
            userPlayText.setForeground(Color.blue);
        }
    }

    private void setBotTextColor() {
        if(botPlay.getText().equals("Rock")) {
            botPlayText.setForeground(Color.darkGray);
        }
        if(botPlay.getText().equals("Paper")) {
            botPlayText.setForeground(Color.orange);
        }
        if(botPlay.getText().equals("Scissors")) {
            botPlayText.setForeground(Color.blue);
        }
    }

    private void checkWin(String userPlay) {
        if(userPlay.equals("Scissors")) {
            if(botPlay.getText().equals("Paper")) {
                win();
                return;
            }
            if(botPlay.getText().equals("Rock")) {
                loss();
                return;
            }
        }

        if(userPlay.equals("Paper")) {
            if(botPlay.getText().equals("Rock")) {
                win();
                return;
            }
            if(botPlay.getText().equals("Scissors")) {
                loss();
                return;
            }
        }

        if(userPlay.equals("Rock")) {
            if(botPlay.getText().equals("Scissors")) {
                win();
                return;
            }
            if(botPlay.getText().equals("Paper")) {
                loss();
                return;
            }
        }
        if(userPlay.equals(botPlay.getText())) {
            draw();
        }
    }

    private void win() {
        resultText.setText("You win.");
        resultText.setForeground(Color.green);
    }

    private void draw() {
        resultText.setText("Draw.");
        resultText.setForeground(Color.orange);
    }

    private void loss() {
        resultText.setText("You lose.");
        resultText.setForeground(Color.red);
    }

    public class RestartAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!resultText.getText().isEmpty()) {
                botPlayText.setText("");
                resultText.setText("");
                userPlayText.setText("");

                tiles[0].setEnabled(true);
                tiles[1].setEnabled(true);
                tiles[2].setEnabled(true);
            }
        }
    }
}
