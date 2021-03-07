package snake;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class Snake extends JFrame {

    int widht = 640;
    int height = 480;

    Snake.ImageSnake imageSnake;

    Point snake;
    Point food;

    ArrayList<Point> list = new ArrayList<Point>();

    int widhtPoint = 10;
    int heightPoint = 10;

    int direction = KeyEvent.VK_LEFT;
    long frequency = 60;

    public Snake() {
        setTitle("Snake");

        setSize(widht, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - widht / 2, dim.height / 2 - height / 2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Keys keys = new Keys();
        this.addKeyListener(keys);

        startGame();

        imageSnake = new ImageSnake();
        this.getContentPane().add(imageSnake);

        setVisible(true);

        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }
    public void startGame(){
        food = new Point(200,200 );
        snake = new Point(widht/2,height/2);

        list = new ArrayList<Point>();
        list.add(snake);

        createFood();
    }

    public void createFood(){
        Random rnd = new Random();

        food.x = rnd.nextInt(widht);
        if((food.x % 5) > 0){
            food.x = food.x - (food.x % 5);
        }
        if(food.x < 5){
            food.x = food.x + 10;
        }

        food.y = rnd.nextInt(height);
        if((food.y % 5) > 0){
            food.y = food.y - (food.y % 5);
        }
        if(food.y < 5){
            food.y = food.y + 10;
        }
    }

    public static void main(String[] args) throws Exception {
        Snake s = new Snake();
    }

    public void update() {
    imageSnake.repaint();

        list.add(0, new Point(snake.x, snake.y));
        list.remove(list.size()-1);

        if((snake.x > (food.x-10) && snake.x < (food.x+10)) && (snake.y > (food.y-10) && snake.y < (food.y+10))) {
            list.add(0, new Point(snake.x,snake.y));
            createFood();
        }
    }

    public class ImageSnake extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(new Color(0, 0, 255));
            g.fillRect(snake.x, snake.y, widhtPoint, heightPoint);

            g.setColor(new Color(255,0,0));
            g.fillRect(food.x, food.y, widhtPoint,heightPoint);
        }
    }

    public class Keys extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direction != KeyEvent.VK_DOWN) {
                    direction = KeyEvent.VK_UP;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direction != KeyEvent.VK_UP) {
                    direction = KeyEvent.VK_DOWN;

                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (direction != KeyEvent.VK_RIGHT) {
                        direction = KeyEvent.VK_LEFT;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (direction != KeyEvent.VK_LEFT) {
                        direction = KeyEvent.VK_RIGHT;
                    }
                }//else if (e.getKeyCode() == KeyEvent.VK_N){
                 //gameOver = false;
                 //startGame();
            }
        }
    }

    public class Momento extends Thread {
        long last = 0;

        public void run() {
            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frequency) {

                    if (direction == KeyEvent.VK_UP) {
                        snake.y = snake.y - heightPoint;
                        if (snake.y > height) {
                            snake.y = 0;
                        }
                        if (snake.y < 0) {
                            snake.y = height + heightPoint;
                        }
                    } else if (direction == KeyEvent.VK_DOWN) {
                        snake.y = snake.y + heightPoint;
                        if (snake.y > height) {
                            snake.y = 0;
                        }
                        if (snake.y < 0) {
                            snake.y = height + heightPoint;
                        }
                    }else if (direction == KeyEvent.VK_LEFT) {
                        snake.x = snake.x - widhtPoint;
                        if (snake.x > widht) {
                            snake.x = 0;
                        }
                        if (snake.x < 0) {
                            snake.x = widht - widhtPoint;
                        }
                    }else if (direction == KeyEvent.VK_RIGHT) {
                        snake.x = snake.x + widhtPoint;
                        if (snake.x > widht) {
                            snake.x = 0;
                        }
                        if (snake.x < 0) {
                            snake.x = widht + widhtPoint;
                        }
                    }
                    update();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }
}


