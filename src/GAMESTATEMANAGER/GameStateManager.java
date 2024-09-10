package GAMESTATEMANAGER;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import MODEL.*;
import MODEL.Bubbles.FireBubble;
import MODEL.Bubbles.GreenBubble;
import MODEL.Bubbles.LightningBubble;
import MODEL.Bubbles.SpawnedBubble;
import MODEL.Enemy.*;
import VIEW.*;

import javax.swing.*;

import static java.rmi.server.LogStream.parseLevel;

public class GameStateManager implements KeyListener, MouseListener, ActionListener {

    public static final int menuState = 0;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int leaderboardState = 3;
    public static final int leveleditorState = 4;
    public static final int userCreationState = 5;
    public static final int loseState = 6;
    public static final int winState = 7;

    private static GameStateManager instance;
    private List<GameState> gsm;
    private GameState currentState;
    private Player currentPlayer;
    private int stateNum;
    private PlayState savedPlayState;
    private LevelEditor levelEditor;
    private List<Level> levels;
    private int currentLevel;
    private int nextLevelInt;
    private static final String FILE_PATH = "levels.txt"; // Percorso del file di testo

    private Map<Integer, Block> intBlockMap;

    private int levelNumCols = 42;
    private int levelNumRows = 37;

    private GameStateManager() {

        gsm = new ArrayList<GameState>();

        gsm.add(new MenuState(this));
        gsm.add(null); // PlayState is added later
        gsm.add(new PauseState(this));
        gsm.add(new LeaderboardState(this));
        gsm.add(new LevelEditorState());
        gsm.add(new UserCreationState(this));
        gsm.add(new LoseState(this));
        gsm.add(new WinState(this));

        //levelEditor = LevelEditor.getInstance();

        stateNum = menuState;
        setState(GameStateManager.menuState);
        levels = loadLevelsFromFile();
        generateLevels();
        currentLevel = 0;
        nextLevelInt = 1;

    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    public int getCurrentLevelInt() {
        return currentLevel;
    }
    public static GameStateManager getInstance() {

        if (instance == null) instance = new GameStateManager();
        return instance;
    }

    public int getStateNum() {
        return stateNum;
    }

    public Map<Integer, Block> getIntBlockMap() {

        if (intBlockMap == null) {

            intBlockMap = new HashMap<>();

            for (int i = 1; i < 25; i++) {
                // ora funziona senza absolute path
                ImageIcon blockSkin = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Tiles/" +
                        "tile" + i + ".png")); //sta roba è strana e cambia per ogni pc, a me va solo se metto
                //il path completo

                Block block = new Block(blockSkin);
                intBlockMap.put(i, block);
            }
        }

        return intBlockMap;
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevel);
    }

    public Level getLevel(int levelID) {

        if (levelID < 1 || levelID > 24) {
            throw new IllegalArgumentException(
                    "Level doesn't exist, please choose a Level that ranges from 1 to 24");
        }
        return levels.get(levelID);

    }

    public void resetGame() {
        System.out.println("Game reset");
        MainFrame.setPlayPanel(null);
        savedPlayState = null;
        currentPlayer = null;
        currentLevel = 0;
        nextLevelInt = 1;
    }
    public void startGame(UserProfile userProfile) {
        if (savedPlayState == null) {
            generateLevels();
            currentPlayer = new Player(userProfile);
            savedPlayState = new PlayState(this);
            currentState = savedPlayState;
            currentState.draw();
        } else {
            throw new IllegalStateException("Game already started");
        }
    }

    public void continueGame() {
        if (savedPlayState != null) {
            currentState = savedPlayState;
            stateNum = 1;
            currentState.draw();
        } else {
            throw new IllegalStateException("No game to continue");
        }
    }

    public void setSavedPlayState(PlayState savedPlayState) {
        this.savedPlayState = savedPlayState;
    }
    public PlayState getSavedPlayState() {
        return savedPlayState;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setState(int state) {
        if (state == 1) {
            throw new IllegalStateException("Use startGame() to start the game or continueGame() to continue");
        } else {
            currentState = gsm.get(state);
            if (currentState == null) {
                throw new IllegalMonitorStateException("The requested state is not initialized.");
            }
            stateNum = state;
            currentState.draw();
        }
    }
    public void generateLevels() {
        // width = 48,  height = 42
        this.levels =  loadLevelsFromFile();
        addEnemies();
        addSpecialBubbles();
        ArrayList<SpawnedBubble> spawnedBubbles = new ArrayList<>();
        //spawnedBubbles.add(new LightningBubble());
        //aggiungere bolle che spawnano automaticamente nel livello qua

        LightningBubble lightning1 = new LightningBubble(currentPlayer);
        LightningBubble lightning2 = new LightningBubble(currentPlayer);
        spawnedBubbles.add(lightning1);
        spawnedBubbles.add(lightning2);
        FireBubble fire1 = new FireBubble(currentPlayer);
        FireBubble fire2 = new FireBubble(currentPlayer);
        spawnedBubbles.add(fire1);
        spawnedBubbles.add(fire2);
        LightningBubble lightning3 = new LightningBubble(currentPlayer);
        LightningBubble lightning4 = new LightningBubble(currentPlayer);
        LightningBubble lightning5 = new LightningBubble(currentPlayer);
        LightningBubble lightning6 = new LightningBubble(currentPlayer);
        LightningBubble lightning7 = new LightningBubble(currentPlayer);
        LightningBubble lightning8 = new LightningBubble(currentPlayer);
        levels.get(1).addSpawnedBubble(lightning5);
        levels.get(1).addSpawnedBubble(lightning6);
        levels.get(1).addSpawnedBubble(lightning7);
        levels.get(1).addSpawnedBubble(lightning8);
        levels.get(1).addSpawnedBubble(lightning1);
        levels.get(1).addSpawnedBubble(lightning2);
        levels.get(1).addSpawnedBubble(lightning3);
        levels.get(2).addSpawnedBubble(lightning4);
        levels.get(1).spawnBubbles();

        //for che crea 24 livelli tutti con i muri attorno e dentro vuoti
        //(i blocchi del livello ovviamento sono gli interi associati al numero del livello
        //ad esempio il livello 2 avra nell'array appunto i muri fatti dal suo blocco, ovvero l'intero 2

        //Creeremo i livelli in se poi con il LevelEditor (sarà divertente actually che ride)
    }

    public void addSpecialBubbles() {
        ArrayList<SpawnedBubble> spawnedBubbles = new ArrayList<>();

        LightningBubble lightning1 = new LightningBubble(currentPlayer);
        LightningBubble lightning2 = new LightningBubble(currentPlayer);
        spawnedBubbles.add(lightning1);
        spawnedBubbles.add(lightning2);
        FireBubble fire1 = new FireBubble(currentPlayer);
        FireBubble fire2 = new FireBubble(currentPlayer);
        spawnedBubbles.add(fire1);
        spawnedBubbles.add(fire2);
        LightningBubble lightning3 = new LightningBubble(currentPlayer);
        LightningBubble lightning4 = new LightningBubble(currentPlayer);
        LightningBubble lightning5 = new LightningBubble(currentPlayer);
        LightningBubble lightning6 = new LightningBubble(currentPlayer);
        LightningBubble lightning7 = new LightningBubble(currentPlayer);
        LightningBubble lightning8 = new LightningBubble(currentPlayer);
        levels.get(1).addSpawnedBubble(lightning5);
        levels.get(1).addSpawnedBubble(lightning6);
        levels.get(1).addSpawnedBubble(lightning7);
        levels.get(1).addSpawnedBubble(lightning8);
        levels.get(1).addSpawnedBubble(lightning1);
        levels.get(1).addSpawnedBubble(lightning2);
        levels.get(1).addSpawnedBubble(lightning3);
        levels.get(2).addSpawnedBubble(lightning4);
        levels.get(1).spawnBubbles();
    }
    public void addEnemies(){
        ArrayList<Enemy> enemies1 = new ArrayList<>();
        enemies1.add(new Benzo(50, 623, true, this ));
        enemies1.add(new Benzo(400, 623, true, this ));
        enemies1.add(new Benzo(600,623,true,this));

        ArrayList<Enemy> enemies2 = new ArrayList<>();
        enemies2.add(new Incendio(105, 543, true, this ));
        enemies2.add(new Incendio(600, 543, true, this ));
        enemies2.add(new Incendio(400, 453, true, this ));
        ArrayList<Enemy> enemies3 = new ArrayList<>();
        enemies3.add(new Benzo(105, 543, true, this ));
        enemies3.add(new Benzo(650, 543, true, this ));
        enemies3.add(new Incendio(105, 623, true, this ));
        ArrayList<Enemy> enemies4 = new ArrayList<>();
        enemies4.add(new Boris(105, 543, true, this ));
        enemies4.add(new Boris(650, 543, true, this ));
        enemies4.add(new Boris(400, 453, true, this ));
        ArrayList<Enemy> enemies5 = new ArrayList<>();
        enemies5.add(new Boris(105, 543, true, this ));
        enemies5.add(new Boris(650, 543, true, this ));
        enemies5.add(new Incendio(105, 623, true, this ));
        ArrayList<Enemy> enemies6 = new ArrayList<>();
        enemies6.add(new Benzo(105, 543, true, this ));
        enemies6.add(new Blubba(650, 50, true, this ));
        enemies6.add (new Blubba(100, 100, true, this));
        enemies6.add(new Benzo(400, 453, true, this ));
        ArrayList<Enemy> enemies7 = new ArrayList<>();
        enemies7.add(new Blubba(105, 543, true, this ));
        enemies7.add(new Blubba(650, 543, true, this ));
        enemies7.add(new Blubba(105, 623, true, this ));
        enemies7.add(new Blubba(400, 453, true, this ));
        ArrayList<Enemy> enemies8 = new ArrayList<>();
        enemies8.add(new Incendio(650, 543, true, this ));
        enemies8.add(new Boris(105, 623, true, this ));
        enemies8.add(new Boris(400, 453, true, this ));
        enemies8.add(new Blubba(105, 543, true, this ));
        ArrayList<Enemy> enemies9 = new ArrayList<>();
        enemies9.add(new Benzo(105, 543, true, this ));
        enemies9.add(new Benzo(650, 543, true, this ));
        enemies9.add(new BoaBoa(600,40,true,this));
        enemies9.add(new BoaBoa(100,40,true,this));
        ArrayList<Enemy> enemies10 = new ArrayList<>();
        enemies10.add(new BoaBoa(105, 20, true, this ));
        enemies10.add(new BoaBoa(650, 543, true, this ));
        enemies10.add(new BoaBoa(105, 623, true, this ));
        enemies10.add(new BoaBoa(400, 20, true, this ));
        ArrayList<Enemy> enemies11 = new ArrayList<>();
        enemies11.add(new Blubba(105, 543, true, this ));
        enemies11.add(new Blubba(105, 623, true, this ));
        enemies11.add(new BoaBoa( 650, 543, true, this ));
        enemies11.add(new BoaBoa( 400, 453, true, this ));
        enemies11.add(new BoaBoa( 105, 543, true, this ));
        ArrayList<Enemy> enemies12 = new ArrayList<>();
        enemies12.add(new Incendio(105, 543, true, this ));
        enemies12.add(new Incendio(650, 543, true, this ));
        enemies12.add(new Incendio(105, 623, true, this ));
        enemies12.add(new Incendio(400, 453, true, this ));
        ArrayList<Enemy> enemies13 = new ArrayList<>();
        enemies13.add(new Invader(105, 543, true, this ));
        enemies13.add(new Invader(650, 543, true, this ));
        enemies13.add(new Invader(105, 623, true, this ));
        enemies13.add(new Invader(400, 453, true, this ));
        ArrayList<Enemy> enemies14 = new ArrayList<>();
        enemies14.add(new Benzo(105, 543, true, this ));
        enemies14.add(new Benzo(650, 543, true, this ));
        enemies14.add(new Boris(105, 623, true, this ));
        enemies14.add(new Boris(400, 453, true, this ));
        ArrayList<Enemy> enemies15 = new ArrayList<>();
        enemies15.add(new Boris(105, 543, true, this ));
        enemies15.add(new Boris(650, 543, true, this ));
        enemies15.add(new Boris(105, 623, true, this ));
        enemies15.add(new Boris(400, 453, true, this ));
        ArrayList<Enemy> enemies16 = new ArrayList<>();
        enemies16.add(new BoaBoa(105, 543, true, this ));
        enemies16.add(new BoaBoa(650, 543, true, this ));
        enemies16.add(new BoaBoa(105, 623, true, this ));
        enemies16.add(new BoaBoa(400, 453, true, this ));
        ArrayList<Enemy> enemies17 = new ArrayList<>();
        enemies17.add(new Invader(105, 543, true, this ));
        enemies17.add(new Invader(650, 543, true, this ));
        enemies17.add(new Invader(105, 623, true, this ));
        enemies17.add(new BoaBoa(400, 453, true, this));
        ArrayList<Enemy> enemies18 = new ArrayList<>();
        enemies18.add(new Benzo(105, 543, true, this ));
        enemies18.add(new Benzo(650, 543, true, this ));
        enemies18.add(new Invader(105, 383, true, this ));
        enemies18.add(new Invader(400, 453, true, this ));
        enemies18.add(new Invader(105, 543, true, this ));
        ArrayList<Enemy> enemies19 = new ArrayList<>();
        enemies19.add(new Benzo(105, 543, true, this ));
        enemies19.add(new Boris(650, 543, true, this ));
        enemies19.add(new Boris(105, 623, true, this ));
        enemies19.add(new Blubba(400, 453, true, this ));
        enemies19.add(new Blubba(105, 543, true, this ));
        ArrayList<Enemy> enemies20 = new ArrayList<>();
        enemies20.add(new BoaBoa(105, 543, true, this ));
        enemies20.add(new BoaBoa(650, 543, true, this ));
        enemies20.add(new Blubba(105, 623, true, this ));
        enemies20.add(new Blubba(400, 453, true, this ));
        enemies20.add(new Blubba(105, 543, true, this ));
        ArrayList<Enemy> enemies21 = new ArrayList<>();
        enemies21.add(new Incendio(105, 543, true, this ));
        enemies21.add(new Incendio(650, 543, true, this ));
        enemies21.add(new Incendio(105, 623, true, this ));
        enemies21.add(new Benzo(105, 543, true, this ));
        ArrayList<Enemy> enemies22 = new ArrayList<>();
        enemies22.add(new Invader(105, 543, true, this ));
        enemies22.add(new Invader(650, 543, true, this ));
        enemies22.add(new Invader(105, 623, true, this ));
        enemies22.add(new Incendio(400, 453, true, this ));
        enemies22.add(new Incendio(105, 543, true, this ));
        ArrayList<Enemy> enemies23 = new ArrayList<>();
        enemies23.add(new Boris(105, 543, true, this ));
        enemies23.add(new Boris(650, 543, true, this ));
        enemies23.add(new Incendio(105, 623, true, this ));
        enemies23.add(new Incendio(400, 453, true, this ));
        enemies23.add(new Incendio(105, 543, true, this ));
        ArrayList<Enemy> enemies24 = new ArrayList<>();
        enemies24.add(new SuperDrunk(105, 303, true, this ));

    List<ArrayList<Enemy>> enemiesList = Arrays.asList(enemies1, enemies2, enemies3, enemies4, enemies5, enemies6, enemies7, enemies8, enemies9, enemies10, enemies11, enemies12, enemies13, enemies14, enemies15, enemies16, enemies17, enemies18, enemies19, enemies20, enemies21, enemies22, enemies23, enemies24);
        for (int i = 1; i < 24; i++) {
            levels.get(i-1).setEnemies(enemiesList.get(i));
        }
    }
    public void addLevel(Level level) {
        levels.add(level);
        //Probabilmente qua verrà chiamato un metodo del Controller che
        //Dice alla View di aggiungere il livello alla rispettiva lista
        //nel LevelEditor
    }

    public void removeLevel(Level level) {
        levels.remove(level);
        //Probabilmente qua verrà chiamato un metodo del Controller che
        //Dice alla View di togliere il livello dalla rispettiva lista
        //nel LevelEditor
    }

    public void update() {
        currentState.update();
    }

    public void draw() {
        currentState.draw();
    }

    @Override
    public void keyTyped(KeyEvent k) {
        currentState.keyTyped(k);
    }

    @Override
    public void keyPressed(KeyEvent k) {
        currentState.keyPressed(k);
    }

    @Override
    public void keyReleased(KeyEvent k) {
        currentState.keyReleased(k);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentState.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentState.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentState.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        currentState.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        currentState.mouseExited(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentState.actionPerformed(e);
    }

    public Player getPlayer() {
        return currentPlayer;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevel(int index, Level level) {
        if (index >= 0 && index < levels.size()) {
            levels.set(index, level); // Aggiorna il livello
            saveLevelsToFile(levels); // Salva tutti i livelli aggiornati
        } else {
            System.err.println("Indice del livello non valido: " + index);
        }
    }

    private List<Level> loadLevelsFromFile() {
        List<Level> levels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Supponiamo che i livelli siano separati da una riga vuota
                if (line.trim().isEmpty()) {
                    levels.add(parseLevel(reader));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return levels;
    }


    private Level parseLevel(BufferedReader reader) throws IOException {
        // Supponiamo che il livello sia rappresentato come una matrice di interi
        // e che la dimensione del livello sia fissa, ad esempio 10x10

        int height = 42; // Altezza fissa
        int width = 48;  // Larghezza fissa
        int[][] pattern = new int[height][width];
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<SpawnedBubble> spawnedBubbles = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(" ");
                for (int j = 0; j < width; j++) {
                    pattern[i][j] = Integer.parseInt(parts[j]);
                }
            }
        }
        return new Level(height, width, pattern, enemies, spawnedBubbles);
    }

    private void saveLevelsToFile(List<Level> levels) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Level level : levels) {
                if (level != null) {
                    int[][] pattern = level.getPattern();
                    for (int[] row : pattern) {
                        for (int val : row) {
                            writer.write(val + " ");
                        }
                        writer.newLine();
                    }
                    writer.newLine(); // Separatore tra livelli
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei livelli: " + e.getMessage());
        }
    }

    public int getNextLevelInt() {return nextLevelInt;}
    public void setNextLevelInt(int num) {nextLevelInt = num;}

}