package MODEL;

import CONTROLLER.Controller;
import GAMESTATEMANAGER.GameStateManager;

import java.util.Timer;

public class LevelEditor {

    GameStateManager gsm;
    private static LevelEditor instance;
    boolean solidON;
    boolean removeON;
    Level currentLevel;
    int currentLevelIndex;

    final int blockWidth = 16;
    final int blockHeight = 16;

    private LevelEditor() {
        //gsm = GameStateManager.getInstance();
    }
    
    public static LevelEditor getInstance() {
        if (instance == null) instance = new LevelEditor();
        return instance;
    }

    public void setCurrentLevel(Level level) {
        //seleziona il livello da modificare
        if (gsm == null) gsm = GameStateManager.getInstance();
        this.currentLevel = level;
        currentLevelIndex = gsm.getLevels().indexOf(currentLevel);
        System.out.println(currentLevelIndex);
    }
    public void setSolid() {
        solidON = !solidON;
        if (solidON && removeON) removeON = false;
        Controller.getInstance().LEsetButtons(solidON, removeON);
    }
    public void setRemove() {
        removeON = !removeON;
        if (removeON && solidON) solidON = false;
        Controller.getInstance().LEsetButtons(solidON, removeON);
    }
    public boolean getSolid() {return solidON;}
    public boolean getRemove() {return removeON;}
    public Level getCurrentLevel() {return currentLevel;}

    public void handleBlockClick(int mouseX, int mouseY) {

        int blockRow = mouseY / blockHeight;
        int blockCol = mouseX / blockWidth;

        if (blockRow >= 0 && blockRow < currentLevel.getPattern().length &&
                blockCol >= 0 && blockCol < currentLevel.getPattern()[0].length) {

            int[][] levelPattern = currentLevel.getPattern();
            //se solid è attivato, viene switchato solid al contrario di come è settato
            //per quel blocco, i blocchi solidi sono rappresentati nell'array di interi del livello
            //come numeri a due cifre, ad esempio la versione solida del blocco 1 è 11.
            int blockInt = levelPattern[blockRow][blockCol];

            if (blockInt != 0 && solidON) {
                currentLevel.setSolidBlock(blockRow, blockCol);
                Controller.getInstance().LEsetSolid(blockRow, blockCol);
            }
            else if (blockInt != 0 && removeON) {
                currentLevel.removeBlock(blockRow, blockCol);
                Controller.getInstance().LEremoveBlock(blockRow, blockCol);
            }
            else if (blockInt == 0 && !solidON && !removeON) {
                currentLevel.addBlock(blockRow, blockCol);
                Controller.getInstance().LEaddBlock(blockRow, blockCol);
            }
        }
        //qui dovrei far ridisegnare il livello dopo che è stato cambiato il pattern
        Controller.getInstance().LEredrawLevel();
    }
    public void saveLevel() {
        if (gsm == null) gsm = GameStateManager.getInstance();
        System.out.println("levels before modifying " + gsm.getLevels().toString());
        System.out.println("size list levels before modifying " + gsm.getLevels().size());
        gsm.setLevel(currentLevelIndex, currentLevel);
        System.out.println("levels after modifying " + gsm.getLevels().toString());
        System.out.println("size list levels after modifying " + gsm.getLevels().size());
    }
}
