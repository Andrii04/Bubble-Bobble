package VIEW;

import CONTROLLER.Controller;
import GAMESTATEMANAGER.GameStateManager;
import MODEL.Sound;
import MODEL.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MainFrame {
    public static final int FRAME_WIDTH = 768; //  (256*3)
    public static final int FRAME_HEIGHT = 672; //(224*3)
    public static final int TILE_SIZE = 64; // menu etc for spacing

    private static JFrame gameFrame;

    private static MenuPanel menuPanel;
    private static PlayPanel playPanel;
    private static PausePanel pausePanel;
    private static LeaderboardPanel leaderboardPanel;
    private static LevelEditorPanel levelEditorPanel;
    private static UserCreationPanel userCreationPanel;
    private static UserProfile userProfile;
    private static LosePanel losePanel;
    private static WinPanel winPanel;

    private static Sound sound = new Sound();
    public MainFrame() {

        gameFrame = new JFrame("Bubble Bobble");
        gameFrame.getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null); // per centrarlo quando compare il MainFrame
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setFocusable(true);
        gameFrame.setResizable(false);

        gameFrame.addKeyListener(Controller.getInstance());
        gameFrame.addMouseListener(Controller.getInstance());
        gameFrame.setVisible(true);
        GameStateManager gsm = GameStateManager.getInstance();
        gsm.setState(0);
    }
    public static void setUserProfile(UserProfile userProfile) {
        userProfile = userProfile;
    }
    public static UserProfile getUserProfile() {
        return userProfile;
    }

    public static Font getPixelFont() {
        // custom font
        try {
            InputStream fontFile = MainFrame.class.getResourceAsStream("/Resources/Bubble Bobble Resources/General/classic-nes-font.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
public static void setPlayPanel(PlayerView playerView) {
        if(playPanel != null) playPanel = null; // remove the old panel (if it exists
    else{
        playPanel = new PlayPanel(playerView);}
    }
    public static void setPanel(JPanel panel) {
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().add(panel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    public static MenuPanel getMenuPanel() {
        if (menuPanel == null) menuPanel = new MenuPanel();
        return menuPanel;
    }
    public static PlayPanel getPlayPanel(PlayerView playerView) {
        if (playPanel == null) playPanel = new PlayPanel(playerView);
        return playPanel;
    }
    public static PausePanel getPausePanel() {
        if (pausePanel == null) pausePanel = new PausePanel();
        return pausePanel;
    }
    public static LeaderboardPanel getLeaderboardPanel() {
        if (leaderboardPanel == null) leaderboardPanel = new LeaderboardPanel();
        return leaderboardPanel;
    }
    public static LevelEditorPanel getLevelEditorPanel() {
        if (levelEditorPanel == null) levelEditorPanel = new LevelEditorPanel();
        return levelEditorPanel;
    }
    public static UserCreationPanel getUserCreationPanel(){
        if (userCreationPanel == null) userCreationPanel = new UserCreationPanel();
        return userCreationPanel;
    }

    public static LosePanel getLosePanel(){
        if ( losePanel== null) losePanel = new LosePanel();
        return losePanel;

    }

    public static WinPanel getWinPanel(){
        if ( winPanel== null) winPanel = new WinPanel();
        return winPanel;
    }
    public static void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public static void playSound(int i){
        sound.setFile(i);
        sound.play();
    }
    public static void stopSound(){
        sound.stop();
    }
}
