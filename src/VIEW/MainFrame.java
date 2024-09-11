package VIEW;

import CONTROLLER.Controller;
import GAMESTATEMANAGER.GameStateManager;
import MODEL.Sound;
import MODEL.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * La classe {@code MainFrame} gestisce la finestra principale del gioco "Bubble Bobble".
 * <p>
 * Questa classe è responsabile per l'inizializzazione e la gestione dei pannelli di gioco,
 * del profilo utente e delle impostazioni sonore. Crea e visualizza la finestra principale
 * del gioco, e gestisce le transizioni tra i diversi stati del gioco.
 * </p>
 */
public class MainFrame {

    public static final int FRAME_WIDTH = 768; // Larghezza della finestra (256*3)
    public static final int FRAME_HEIGHT = 672; // Altezza della finestra (224*3)
    public static final int TILE_SIZE = 64; // Dimensione dei tile per menu e spaziatura

    private static JFrame gameFrame; // La finestra principale del gioco

    private static MenuPanel menuPanel;
    private static PlayPanel playPanel;
    private static PausePanel pausePanel;
    private static LeaderboardPanel leaderboardPanel;
    private static LevelEditorPanel levelEditorPanel;
    private static UserCreationPanel userCreationPanel;
    private static UserProfile userProfile;
    private static LosePanel losePanel;
    private static WinPanel winPanel;

    private static Sound sound = new Sound(); // Gestore dei suoni

    /**
     * Costruisce e inizializza la finestra principale del gioco.
     * <p>
     * Imposta le dimensioni della finestra, il titolo, la chiusura e la visibilità,
     * e aggiunge i listener per gli eventi di input. Imposta anche lo stato iniziale
     * del gestore dello stato del gioco.
     * </p>
     */
    public MainFrame() {

        gameFrame = new JFrame("Bubble Bobble");
        gameFrame.setIconImage(new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/General/icon.png")).getImage());
        gameFrame.getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null); // Centra la finestra sullo schermo
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setFocusable(true);
        gameFrame.setResizable(false);

        gameFrame.addKeyListener(Controller.getInstance());
        gameFrame.addMouseListener(Controller.getInstance());
        gameFrame.setVisible(true);
        GameStateManager gsm = GameStateManager.getInstance();
        gsm.setState(0); // Imposta lo stato iniziale del gioco
    }

    /**
     * Imposta il profilo utente.
     *
     * @param userProfile Il profilo utente da impostare.
     */
    public static void setUserProfile(UserProfile userProfile) {
        MainFrame.userProfile = userProfile;
    }

    /**
     * Restituisce il profilo utente attuale.
     *
     * @return Il profilo utente attuale.
     */
    public static UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Restituisce il font personalizzato utilizzato nel gioco.
     * <p>
     * Carica il font "classic-nes-font.ttf" dalle risorse del gioco.
     * </p>
     *
     * @return Il font personalizzato, oppure {@code null} se il font non può essere caricato.
     */
    public static Font getPixelFont() {
        try {
            InputStream fontFile = MainFrame.class.getResourceAsStream("/Resources/Bubble Bobble Resources/General/classic-nes-font.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Imposta il pannello di gioco.
     * <p>
     * Se esiste già un pannello di gioco, viene rimosso e sostituito con il nuovo pannello.
     * </p>
     *
     * @param playerView La vista del giocatore da visualizzare nel pannello di gioco.
     */
    public static void setPlayPanel(PlayerView playerView) {
        if (playPanel != null) {
            playPanel = null; // Rimuove il pannello esistente (se esiste)
        } else {
            playPanel = new PlayPanel(playerView);
        }
    }

    /**
     * Imposta il pannello visualizzato nella finestra principale.
     *
     * @param panel Il pannello da visualizzare.
     */
    public static void setPanel(JPanel panel) {
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().add(panel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    /**
     * Restituisce il pannello del menu principale.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello del menu principale.
     */
    public static MenuPanel getMenuPanel() {
        if (menuPanel == null) menuPanel = new MenuPanel();
        return menuPanel;
    }

    /**
     * Restituisce il pannello di gioco.
     * <p>
     * Se il pannello non esiste, viene creato con la vista del giocatore fornita.
     * </p>
     *
     * @param playerView La vista del giocatore da visualizzare.
     * @return Il pannello di gioco.
     */
    public static PlayPanel getPlayPanel(PlayerView playerView) {
        if (playPanel == null) playPanel = new PlayPanel(playerView);
        return playPanel;
    }

    /**
     * Restituisce il pannello di pausa.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello di pausa.
     */
    public static PausePanel getPausePanel() {
        if (pausePanel == null) pausePanel = new PausePanel();
        return pausePanel;
    }

    /**
     * Restituisce il pannello della classifica.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello della classifica.
     */
    public static LeaderboardPanel getLeaderboardPanel() {
        if (leaderboardPanel == null) leaderboardPanel = new LeaderboardPanel();
        return leaderboardPanel;
    }

    /**
     * Restituisce il pannello dell'editor di livelli.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello dell'editor di livelli.
     */
    public static LevelEditorPanel getLevelEditorPanel() {
        if (levelEditorPanel == null) levelEditorPanel = new LevelEditorPanel();
        return levelEditorPanel;
    }

    /**
     * Restituisce il pannello di creazione utente.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello di creazione utente.
     */
    public static UserCreationPanel getUserCreationPanel() {
        if (userCreationPanel == null) userCreationPanel = new UserCreationPanel();
        return userCreationPanel;
    }

    /**
     * Restituisce il pannello di sconfitta.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello di sconfitta.
     */
    public static LosePanel getLosePanel() {
        if (losePanel == null) losePanel = new LosePanel();
        return losePanel;
    }

    /**
     * Restituisce il pannello di vittoria.
     * <p>
     * Se il pannello non esiste, viene creato.
     * </p>
     *
     * @return Il pannello di vittoria.
     */
    public static WinPanel getWinPanel() {
        if (winPanel == null) winPanel = new WinPanel();
        return winPanel;
    }

    /**
     * Riproduce la musica di sottofondo.
     * <p>
     * Imposta il file audio da riprodurre e avvia la riproduzione in loop.
     * </p>
     *
     * @param i L'indice del file audio da riprodurre.
     */
    public static void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    /**
     * Riproduce un effetto sonoro.
     * <p>
     * Imposta il file audio da riprodurre e avvia la riproduzione.
     * </p>
     *
     * @param i L'indice del file audio da riprodurre.
     */
    public static void playSound(int i) {
        sound.setFile(i);
        sound.play();
    }

    /**
     * Ferma la riproduzione del suono corrente.
     */
    public static void stopSound() {
        sound.stop();
    }
}