package Model;

import java.util.Timer;

public class LevelEditor {

    private static LevelEditor instance;

    private LevelEditor() {
        
    }
    
    public LevelEditor createLevelEditor() {
        if (instance == null) instance = new LevelEditor();
        return instance;
    }

    public void createLevel(int height, int width, char[] pattern, Timer spawnRate) {
        //aggiunge il nuovo livello alla lista con tutti i livelli
    }
    public void removeLevel(Level level) {
        //rimuove il livello dalla lista con tutti i livelli
    }
    public void modifyLevel(Level level) {
        //seleziona il livello da modificare
    }
}
