import GAMESTATEMANAGER.GameStateManager;
import VIEW.LosePanel;
import VIEW.MainFrame;

import javax.swing.*;

public class Main {
    private static JPanel LosePanel;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        GameStateManager gsm = GameStateManager.getInstance();
        gsm.generateLevels();
        //si può provare a settare i vari stati, ho reso i loro rispettivi pannelli
        //di colore diverso così si vede se funziona o no

        //gsm.setState(GameStateManager.menuState);
        //gsm.setState(GameStateManager.leaderboardState);
        //gsm.setState(GameStateManager.leveleditorState);
        //gsm.setState(GameStateManager.playState);
        //gsm.setState(GameStateManager.loseState); -> non credo che tutto questo sia giusto poi vedo





    }
}
