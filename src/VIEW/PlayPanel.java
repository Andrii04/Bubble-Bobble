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


/**
 * La classe PlayPanel gestisce la visualizzazione e l'aggiornamento dello stato del gioco.
 * Estende JPanel e implementa Runnable per il game loop.
 */
public class PlayPanel extends JPanel implements Runnable {

    private static final ImageIcon LIFE_ICON = new ImageIcon("src/Resources/Bubble Bobble Resources/Bubbles/GreenBubble3.png");
    private GameStateManager gsm;  // Gestore dello stato del gioco
    private int TILE_SIZE = MainFrame.TILE_SIZE;  // Dimensione dei tile
    private int FRAME_HEIGHT = MainFrame.FRAME_HEIGHT;  // Altezza del frame
    private int FRAME_WIDTH = MainFrame.FRAME_WIDTH;  // Larghezza del frame
    private PlayerView playerView;  // Vista del giocatore
    private int currentLevelView = 1;  // Indice del livello corrente
    private int arrayWidth = 48;  // Larghezza dell'array del livello
    private int arrayHeight = 42;  // Altezza dell'array del livello
    private boolean isNewLevel = true;  // Flag per determinare se è un nuovo livello
    private ArrayList<EnemyView> enemyViews;  // Vista degli nemici
    private LivesPanel livesPanel;  // Pannello delle vite
    private int lives;  // Numero di vite del giocatore
    private Player player;  // Oggetto Player
    private ScorePanel scorePanel;  // Pannello del punteggio
    private ExtendPanel extendPanel;  // Pannello delle estensioni

    private final int FPS = 60;  // Frame per secondo
    private Thread gameThread;  // Thread del gioco

    /**
     * Costruttore della classe PlayPanel.
     *
     * @param playerView La vista del giocatore.
     */
    public PlayPanel(PlayerView playerView) {
        this.playerView = playerView;
        this.player = playerView.getPlayer();
        this.lives = player.getLives();
        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setOpaque(true);
        this.setVisible(true);

        gsm = GameStateManager.getInstance();
        enemyViews = gsm.getCurrentLevel().getEnemyViews();

        this.scorePanel = new ScorePanel(player);  // Inizializza il pannello del punteggio
        this.add(scorePanel);
        scorePanel.setBounds(10, 10, MainFrame.FRAME_WIDTH / 2, scorePanel.getPreferredSize().height);

        this.extendPanel = new ExtendPanel(player);  // Inizializza il pannello delle estensioni
        this.add(extendPanel);
        extendPanel.setBounds(10, 100, 50, 300);  // Posiziona il pannello delle estensioni sulla destra

        this.livesPanel = new LivesPanel(player);  // Inizializza il pannello delle vite
        this.add(livesPanel);
        livesPanel.setBounds(10, MainFrame.FRAME_HEIGHT - 22, livesPanel.getPreferredSize().width, livesPanel.getPreferredSize().height);

        startGameThread();  // Avvia il thread del gioco
    }

    /**
     * Avvia il thread del gioco.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Ciclo di gioco eseguito nel thread.
     */
    public void run() {
        double interval = 1000000000 / FPS;  // Intervallo tra i frame in nanosecondi
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();  // Aggiorna lo stato del gioco
                repaint();  // Ridisegna il pannello
                delta--;
            }
        }
    }

    /**
     * Aggiorna lo stato del pannello di gioco, incluso il punteggio e le vite.
     */
    public void update() {
        livesPanel.updateLives();  // Aggiorna il pannello delle vite
        scorePanel.updateScore();  // Aggiorna il pannello del punteggio
    }

    /**
     * Disegna il pannello di gioco.
     *
     * @param g L'oggetto Graphics utilizzato per disegnare sul pannello.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        playerView.draw(g2d);  // Disegna la vista del giocatore

        // Disegna le bolle
        for (Bubble bubble : gsm.getCurrentLevel().getBubbles()) {
            if (bubble != null) {
                bubble.getBubbleView().update();
                bubble.getBubbleView().draw(g2d);
            }
        }

        // Se la lista di nemici è vuota, ricarica la lista
        if (enemyViews.isEmpty()) {
            enemyViews = gsm.getCurrentLevel().getEnemyViews();
        }

        // Disegna gli nemici
        for (EnemyView enemyView : enemyViews) {
            if (enemyView != null) enemyView.draw(g2d);
        }

        // Disegna i power-up
        for (PowerUp powerUp : gsm.getCurrentLevel().getPowerUps()) {
            if (powerUp != null) {
                powerUp.getPowerUpView().update();
                powerUp.getPowerUpView().draw(g2d);
            }
        }

        // Disegna il livello se è un nuovo livello
        if (isNewLevel) {
            drawLevel(g2d, gsm.getCurrentLevel().getPattern());
            // isNewLevel = false; // Commentato per evitare che il livello venga cancellato
        } else {
            // Metodi per aggiornare giocatore, nemici, bolle, power-up, ecc.
        }

        gsm.update();  // Aggiorna lo stato del gestore dello stato del gioco
    }

    /**
     * Disegna il livello sulla superficie grafica.
     *
     * @param g L'oggetto Graphics utilizzato per disegnare sul pannello.
     * @param pattern La matrice che rappresenta il pattern del livello.
     */
    public void drawLevel(Graphics g, int[][] pattern) {
        Graphics2D g2d = (Graphics2D) g;
        Map<Integer, Block> intBlockMap = gsm.getIntBlockMap();

        for (int i = 0; i < arrayHeight; i++) {
            // Disegna i blocchi per ogni cella del livello
            for (int j = 0; j < arrayWidth; j++) {
                int blockInt = pattern[i][j];
                Block block = intBlockMap.get(blockInt);

                if (blockInt != 0 && block != null) {
                    ImageIcon skin = block.getSkin();  // Ottiene l'icona del blocco
                    int x = j * Block.WIDTH;  // Calcola la posizione x del blocco
                    int y = i * Block.HEIGHT;  // Calcola la posizione y del blocco
                    g2d.drawImage(skin.getImage(), x, y, Block.WIDTH, Block.HEIGHT, null);  // Disegna il blocco
                }
            }
        }
    }
}
