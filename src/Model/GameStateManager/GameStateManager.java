package Model.GameStateManager;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
        public static final int menuState = 0;
        public static final int playState = 1;
        public static final int pauseState = 2;
        public static final int leaderboardState = 3;
        public static final int leveleditorState = 4;

        protected List<GameState> gsm;

        public GameStateManager() {

            gsm = new ArrayList<GameState>();

    }
}
