package com.chocs.minigames.snake_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

class SnakeGraphics extends JPanel implements ActionListener {

    static final int WIDTH = 1000;
    static final int LENGTH = 1000;
    static final int TILE_SIZE = 40;

    Random random;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile apple;

    Timer loop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;

    Action restartAction;

    private static class Tile {
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    SnakeGraphics() {
        this.setPreferredSize(new Dimension(WIDTH, LENGTH));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.requestFocusInWindow();

        random = new Random();

        snakeHead = new Tile(12, 12);
        snakeBody = new ArrayList<>();

        apple = new Tile(0, 0);
        placeApple();

        velocityX = 0;
        velocityY = 0;

        loop = new Timer(100, this);
        loop.start();

        upAction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();
        restartAction = new RestartAction();

        //adding key binding
        this.getInputMap().put(KeyStroke.getKeyStroke('w'), "upAction");
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", upAction);

        this.getInputMap().put(KeyStroke.getKeyStroke('s'), "downAction");
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", downAction);

        this.getInputMap().put(KeyStroke.getKeyStroke('a'), "leftAction");
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", leftAction);

        this.getInputMap().put(KeyStroke.getKeyStroke('d'), "rightAction");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", rightAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "restartAction");
        this.getActionMap().put("restartAction", restartAction);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //grid
        for(int i = 0; i < WIDTH/TILE_SIZE; i++) {
            //        (x1, y1, x2, y2)
            g.drawLine(i*TILE_SIZE, 0,i*TILE_SIZE, LENGTH);
            g.drawLine(0, i*TILE_SIZE, WIDTH, i*TILE_SIZE);
        }

        //apple
        g.setColor(Color.RED);
        g.fillRect(apple.x * TILE_SIZE, apple.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        //snake head
        g.setColor(new Color(4, 60, 42));
        g.fillRect(snakeHead.x * TILE_SIZE, snakeHead.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        //snake body
        for (Tile snakePart : snakeBody) {
            g.setColor(Color.GREEN);
            g.fillRect(snakePart.x * TILE_SIZE, snakePart.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if(gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over. Press SPACE to restart. Final score: " + snakeBody.size(), TILE_SIZE - 18, TILE_SIZE);
        } else {
            g.setColor(Color.GRAY);
            g.drawString("Score: " + snakeBody.size(), TILE_SIZE - 18, TILE_SIZE);
        }
    }

    public void placeApple() {
        apple.x = random.nextInt(WIDTH/TILE_SIZE); // 0-24
        apple.y = random.nextInt(LENGTH/TILE_SIZE);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (snakeHead.x < 0 || snakeHead.x >= WIDTH / TILE_SIZE ||
            snakeHead.y < 0 || snakeHead.y >= LENGTH / TILE_SIZE) {
            gameOver = true;
            return;
        }

        //eat
        if(collision(snakeHead, apple)) {
            snakeBody.add(new Tile(apple.x, apple.y));
            placeApple();
        }

        //snake body
        for(int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if(i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
    }

    //loop
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver) {
            loop.stop();
            return;
        }

        move();
        repaint();
    }


    //key binds classes
    public class UpAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
            }
        }
    }

    public class DownAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
            }
        }
    }

    private class LeftAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
            }
        }
    }

    public class RightAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
            }
        }
    }

    public class RestartAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameOver) {
                snakeBody.clear();

                snakeHead.x = 12;
                snakeHead.y = 12;

                velocityX = 0;
                velocityY = 0;

                gameOver = false;
                loop.start();
            }
        }
    }
}
