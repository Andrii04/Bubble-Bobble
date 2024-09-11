package MODEL;

import CONTROLLER.Controller;
import GAMESTATEMANAGER.GameStateManager;

import java.util.Timer;

/**
 * La classe {@code LevelEditor} gestisce la modifica dei livelli del gioco.
 * Permette di selezionare un livello, modificare i blocchi del livello corrente,
 * e salvare le modifiche apportate. Gestisce anche le modalità di modifica come
 * "solid" e "remove".
 */
public class LevelEditor {

    private GameStateManager gsm;
    private static LevelEditor instance;
    private boolean solidON;
    private boolean removeON;
    private Level currentLevel;
    private int currentLevelIndex;

    private final int blockWidth = 16;
    private final int blockHeight = 16;

    /**
     * Costruttore privato per il pattern Singleton.
     */
    private LevelEditor() {
        //gsm = GameStateManager.getInstance();
    }

    /**
     * Restituisce l'unica istanza della classe {@code LevelEditor}.
     * Se l'istanza non esiste, viene creata.
     *
     * @return l'istanza unica di {@code LevelEditor}
     */
    public static LevelEditor getInstance() {
        if (instance == null) instance = new LevelEditor();
        return instance;
    }

    /**
     * Imposta il livello corrente da modificare.
     *
     * @param level il livello da modificare
     */
    public void setCurrentLevel(Level level) {
        if (gsm == null) gsm = GameStateManager.getInstance();
        this.currentLevel = level;
        currentLevelIndex = gsm.getLevels().indexOf(currentLevel);
        System.out.println(currentLevelIndex);
    }

    /**
     * Attiva o disattiva la modalità di blocco solido.
     * Se la modalità solido è attiva e la modalità rimozione è attiva,
     * disattiva la modalità rimozione.
     */
    public void setSolid() {
        solidON = !solidON;
        if (solidON && removeON) removeON = false;
        Controller.getInstance().LEsetButtons(solidON, removeON);
    }

    /**
     * Attiva o disattiva la modalità di rimozione dei blocchi.
     * Se la modalità rimozione è attiva e la modalità solido è attiva,
     * disattiva la modalità solido.
     */
    public void setRemove() {
        removeON = !removeON;
        if (removeON && solidON) solidON = false;
        Controller.getInstance().LEsetButtons(solidON, removeON);
    }

    /**
     * Restituisce lo stato della modalità solido.
     *
     * @return {@code true} se la modalità solido è attiva, {@code false} altrimenti
     */
    public boolean getSolid() {return solidON;}

    /**
     * Restituisce lo stato della modalità di rimozione.
     *
     * @return {@code true} se la modalità di rimozione è attiva, {@code false} altrimenti
     */
    public boolean getRemove() {return removeON;}

    /**
     * Restituisce il livello corrente.
     *
     * @return il livello corrente
     */
    public Level getCurrentLevel() {return currentLevel;}

    /**
     * Gestisce il clic su un blocco nel livello corrente, modificando il blocco in base
     * alla modalità di modifica attiva (solido o rimozione).
     *
     * @param mouseX la coordinata X del clic del mouse
     * @param mouseY la coordinata Y del clic del mouse
     */
    public void handleBlockClick(int mouseX, int mouseY) {
        int blockRow = mouseY / blockHeight;
        int blockCol = mouseX / blockWidth;

        if (blockRow >= 0 && blockRow < currentLevel.getPattern().length &&
                blockCol >= 0 && blockCol < currentLevel.getPattern()[0].length) {

            int[][] levelPattern = currentLevel.getPattern();
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
        // Ridisegna il livello dopo la modifica del pattern
        Controller.getInstance().LEredrawLevel();
    }

    /**
     * Salva il livello corrente nel {@code GameStateManager}.
     * Aggiorna il livello con l'indice corrente con le modifiche apportate.
     */
    public void saveLevel() {
        if (gsm == null) gsm = GameStateManager.getInstance();
        System.out.println("levels before modifying " + gsm.getLevels().toString());
        System.out.println("size list levels before modifying " + gsm.getLevels().size());
        gsm.setLevel(currentLevelIndex, currentLevel);
        System.out.println("levels after modifying " + gsm.getLevels().toString());
        System.out.println("size list levels after modifying " + gsm.getLevels().size());
    }
}