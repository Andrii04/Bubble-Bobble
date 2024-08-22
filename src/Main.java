import GameStateManager.GameStateManager;
import View.MainFrame;

public class Main {
    public static void main(String[] args) {
        GameStateManager gsm = GameStateManager.createGameStateManager();
        MainFrame frame = new MainFrame();

        //si può provare a settare i vari stati, ho reso i loro rispettivi pannelli
        //di colore diverso così si vede se funziona o no

        gsm.setState(GameStateManager.menuState);
        //gsm.setState(GameStateManager.leaderboardState);
        //gsm.setState(GameStateManager.leveleditorState);
        //gsm.setState(GameStateManager.playState);
    }
}
