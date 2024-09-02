import VIEW.LeaderboardPanel;
import VIEW.LosePanel;
import VIEW.MenuPanel;
import VIEW.WinPanel;

import javax.swing.*;
import java.awt.*;

public class FrameProva extends JFrame {
    public static final int FRAME_WIDTH = 768;
    public static final int FRAME_HEIGHT = 672;

    public FrameProva() {
        super("Leaderboard Test");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.BLACK);

        // Crea e aggiungi il LeaderboardPanel
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
        add(leaderboardPanel);
        Object[][] sampleData = {
                {"1", new ImageIcon("path/to/avatar1.png"), "Player1", "5", "1000", "10", "5", "15"},
                {"2", new ImageIcon("path/to/avatar2.png"), "Player2", "4", "800", "8", "6", "14"}
        };
        leaderboardPanel.updateLeaderboard(sampleData);

        // Mostra il frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrameProva::new);
    }
}
