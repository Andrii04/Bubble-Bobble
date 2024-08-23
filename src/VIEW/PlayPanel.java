package VIEW;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel implements Runnable{

    private final int FPS = 60;
    Thread gameThread;
    public PlayPanel() {
        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.green);
        this.setVisible(true);

        startGameThread();
    }


    // game loop
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run(){
        double interval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        // test
        // System.out.println("running");
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // add player etc
        g2d.dispose();
    }
}
