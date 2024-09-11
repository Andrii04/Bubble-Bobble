package VIEW;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.Bubble;
import MODEL.Player;
import MODEL.PowerUps.PowerUp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;


public class PlayPanel extends JPanel implements Runnable {

    private static final ImageIcon LIFE_ICON =new ImageIcon("src/Resources/Bubble Bobble Resources/Bubbles/GreenBubble3.png"); ;
    private GameStateManager gsm;
    private int TILE_SIZE = MainFrame.TILE_SIZE;
    private int FRAME_HEIGHT = MainFrame.FRAME_HEIGHT;
    private int FRAME_WIDTH = MainFrame.FRAME_WIDTH;
    private PlayerView playerView;
    private int currentLevelView = 1;
    private int arrayWidth = 48;
    private int arrayHeight = 42;
    private boolean isNewLevel = true;
    ArrayList<EnemyView> enemyViews;
    private LivesPanel livesPanel; // LivesPanel
    private int lives;
    private Player player;
    private ScorePanel scorePanel;
    private ExtendPanel extendPanel;



    private final int FPS = 60;
    Thread gameThread;

    public PlayPanel(PlayerView playerView) {

        this.playerView = playerView;
        this.player=playerView.getPlayer();
        this.lives= player.getLives();
        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);

        gsm = GameStateManager.getInstance();
        enemyViews = gsm.getCurrentLevel().getEnemyViews();

        this.scorePanel = new ScorePanel(player); //Score Panel
        this.add(scorePanel);
        scorePanel.setBounds(10, 10, MainFrame.FRAME_WIDTH/2, scorePanel.getPreferredSize().height);

        this.extendPanel = new ExtendPanel(player);
        this.add(extendPanel);
        // Posizionamento in verticale sul lato destro
        extendPanel.setBounds(10, 100, 50, 300); // Posizionato sulla destra


        this.livesPanel = new LivesPanel(player);
        this.add(livesPanel);
        livesPanel.setBounds(10,  MainFrame.FRAME_HEIGHT-22, livesPanel.getPreferredSize().width, livesPanel.getPreferredSize().height);
        startGameThread();

    }

    // game loop
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double interval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        // test
        //System.out.println("running");
        livesPanel.updateLives();
        scorePanel.updateScore();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        playerView.draw(g2d);
        for (Bubble bubble : gsm.getCurrentLevel().getBubbles()) {
            if (bubble != null) {
                bubble.getBubbleView().update();
                bubble.getBubbleView().draw(g2d);
            }
        }
        if (enemyViews.isEmpty()){
            enemyViews = gsm.getCurrentLevel().getEnemyViews();
        }
        for (EnemyView enemyView : enemyViews) {
            if (enemyView != null) enemyView.draw(g2d);
        }
        for (PowerUp powerUp : gsm.getCurrentLevel().getPowerUps()) {
            if (powerUp != null) {
                powerUp.getPowerUpView().update();
                powerUp.getPowerUpView().draw(g2d);
            }
        }
/*        for (int i = 0; i < lives; i++) {
            g.drawImage(LIFE_ICON.getImage(), i * LIFE_ICON.getIconWidth(), 0, this);
        }
*/
        if (isNewLevel) {
            drawLevel(g2d, gsm.getCurrentLevel().getPattern());
            // isNewLevel = false;    SE SI METTE QUESTO IL LIVELLO VIENE CANCELLATO, da fixxare
        } else {
            // metodi per aggiornare giocatore nemici bolle powerup etc, non il livello
        }

        gsm.update();
    }

    public void drawLevel(Graphics g, int[][] pattern) {
        Graphics2D g2d = (Graphics2D) g;
        Map<Integer, Block> intBlockMap = gsm.getIntBlockMap();

        for (int i = 0; i < arrayHeight; i++) {
            //prendo il blocco corrispondente al carattere
            for (int j = 0; j < arrayWidth; j++) {

                int blockInt = pattern[i][j];
                Block block = intBlockMap.get(blockInt);

                if (blockInt != 0 && block != null) {
                    //prendo l'icona del blocco da disegnare
                    ImageIcon skin = block.getSkin();

                    //posizione x e y del blocco
                    int x = j * Block.WIDTH; //200 per test
                    int y = i * Block.HEIGHT; //200 per test
                    //chiama il metodo ma non disegna sul frame (appare nero).
                    g2d.drawImage(skin.getImage(), x, y, Block.WIDTH, Block.HEIGHT, null);
                }

            }
        }
    }
}

