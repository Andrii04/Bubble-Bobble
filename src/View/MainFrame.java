package View;

import javax.swing.*;

public class MainFrame {
    public static final int FRAME_WIDTH = 768; //  (256*3)
    public static final int FRAME_HEIGHT = 672; //(224*3)

    private static JFrame gameFrame;

    private static MenuPanel menuPanel;
    private static PlayPanel playPanel;
    private static PausePanel pausePanel;
    private static LeaderboardPanel leaderboardPanel;
    private static LevelEditorPanel levelEditorPanel;

    public MainFrame() {
        gameFrame = new JFrame("Bubble Bobble");
        gameFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
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
    public static PlayPanel getPlayPanel() {
        if (playPanel == null) playPanel = new PlayPanel();
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
}
