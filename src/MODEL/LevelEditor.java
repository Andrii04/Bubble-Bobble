package MODEL;

import GAMESTATEMANAGER.GameStateManager;

import java.util.Timer;

public class LevelEditor {

    GameStateManager gsm;
    private static LevelEditor instance;

    private LevelEditor() {
        gsm = GameStateManager.getInstance();
    }
    
    public LevelEditor getLevelEditor() {
        if (instance == null) instance = new LevelEditor();
        return instance;
    }

    public void createLevel(int height, int width, char[] pattern, Timer spawnRate) {
        //aggiunge il nuovo livello alla lista con tutti i livelli
        gsm.addLevel(new Level(height, width,  pattern, spawnRate));
    }
    public void removeLevel(Level level) {
        //rimuove il livello dalla lista con tutti i livelli
    }
    public void modifyLevel(Level level) {
        //seleziona il livello da modificare
    }
}
