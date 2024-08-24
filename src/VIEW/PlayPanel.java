package VIEW;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PlayPanel extends JPanel implements Runnable{

    private GameStateManager gsm;
    private int TILE_SIZE = MainFrame.TILE_SIZE;
    private int FRAME_HEIGHT = MainFrame.FRAME_HEIGHT;
    private int FRAME_WIDTH = MainFrame.FRAME_WIDTH;

    private boolean isNewLevel = false;

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
    public void drawLevel(Graphics g, char[] pattern) {

        int blocksPerRow = FRAME_WIDTH / Block.WIDTH;
        Map<Character, Block> charBlockMap = gsm.getCharBlockMap();

        for (int i=0; i<pattern.length; i++) {
            //prendo il blocco corrispondente al carattere
            char blockChar = pattern[i];
            Block block = charBlockMap.get(blockChar);

            if (block != null) {
                //prendo l'icona del blocco da disegnare
                ImageIcon skin = block.getSkin();

                //posizione x e y del blocco
                int x = (i % blocksPerRow) * Block.WIDTH;
                int y = (i / blocksPerRow) * Block.HEIGHT;

                g.drawImage(skin.getImage(), x, y, Block.WIDTH, Block.HEIGHT, null);
            }


        }
    }
}
