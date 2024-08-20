package View;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static final int FRAME_WIDTH = 768; //  (256*3)
    public static final int FRAME_HEIGHT = 672; //(224*3)

    private static MenuPanel menuPanel;
    private static PlayPanel playPanel;
    private static PausePanel pausePanel;
    private static LeaderboardPanel leaderboardPanel;
    private static LevelEditorPanel levelEditorPanel;

    public MainFrame() {

    }

    public static void createMenuPanel() {menuPanel = new MenuPanel();}
    public void createPlayPanel() {playPanel = new PlayPanel();}
    public void createPausePanel() {pausePanel = new PausePanel();}
    public void createLeaderboardPanel() {leaderboardPanel = new LeaderboardPanel();}
    public void createLevelEditorPanel() {levelEditorPanel = new LevelEditorPanel();}
}
