package snake;

import javax.swing.*;

public class Snake extends JFrame {

    int widht = 640;
    int height = 480;

    public Snake(){
        setTitle("Snake");

        setSize(widht,height);

        setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        Snake s = new Snake();
    }

}
