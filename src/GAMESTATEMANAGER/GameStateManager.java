package GAMESTATEMANAGER;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import MODEL.*;
import MODEL.Bubbles.*;
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
        levels = new ArrayList<>();
        levels.add(null);
        levels.addAll(loadLevelsFromFile());
        //così che i livelli incominciano dall'index 1
        generateLevels();
        currentLevel = 1;
        nextLevelInt = 2;

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
        currentLevel = 1;
        nextLevelInt = 2;
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
        //48, 42

            addEnemies();
            addSpecialBubbles();


        //for che crea 24 livelli tutti con i muri attorno e dentro vuoti
        //(i blocchi del livello ovviamento sono gli interi associati al numero del livello
        //ad esempio il livello 2 avra nell'array appunto i muri fatti dal suo blocco, ovvero l'intero 2

        //Creeremo i livelli in se poi con il LevelEditor (sarà divertente actually che ride)
    }

    public void addSpecialBubbles() {

        ArrayList<SpawnedBubble> spawnedBubbles = new ArrayList<>();

        ExtendBubble extendE1 = new ExtendBubble(currentPlayer, "E");
        ExtendBubble extendE2 = new ExtendBubble(currentPlayer, "E");

        spawnedBubbles.add(extendE1);
        spawnedBubbles.add(extendE2);

        ExtendBubble extendX1 = new ExtendBubble(currentPlayer, "X");

        spawnedBubbles.add(extendX1);


        ExtendBubble extendT1 = new ExtendBubble(currentPlayer, "T");

        spawnedBubbles.add(extendT1);


        ExtendBubble extendN1 = new ExtendBubble(currentPlayer, "N");

        spawnedBubbles.add(extendN1);

        ExtendBubble extendD1 = new ExtendBubble(currentPlayer, "D");
        spawnedBubbles.add(extendD1);


        WaterBubble water1 = new WaterBubble(currentPlayer);
        WaterBubble water2 = new WaterBubble(currentPlayer);
        WaterBubble water3 = new WaterBubble(currentPlayer);
        WaterBubble water4 = new WaterBubble(currentPlayer);
        WaterBubble water5 = new WaterBubble(currentPlayer);
        WaterBubble water6 = new WaterBubble(currentPlayer);
        WaterBubble water7 = new WaterBubble(currentPlayer);
        WaterBubble water8 = new WaterBubble(currentPlayer);
        WaterBubble water9 = new WaterBubble(currentPlayer);
        WaterBubble water10 = new WaterBubble(currentPlayer);
        WaterBubble water11 = new WaterBubble(currentPlayer);
        WaterBubble water12 = new WaterBubble(currentPlayer);
        WaterBubble water13 = new WaterBubble(currentPlayer);
        WaterBubble water14 = new WaterBubble(currentPlayer);
        WaterBubble water15 = new WaterBubble(currentPlayer);
        WaterBubble water16 = new WaterBubble(currentPlayer);

        spawnedBubbles.add(water1);
        spawnedBubbles.add(water2);
        spawnedBubbles.add(water3);
        spawnedBubbles.add(water4);
        spawnedBubbles.add(water5);
        spawnedBubbles.add(water6);
        spawnedBubbles.add(water7);
        spawnedBubbles.add(water8);
        spawnedBubbles.add(water9);
        spawnedBubbles.add(water10);
        spawnedBubbles.add(water11);
        spawnedBubbles.add(water12);
        spawnedBubbles.add(water13);
        spawnedBubbles.add(water14);
        spawnedBubbles.add(water15);
        spawnedBubbles.add(water16);

        FireBubble fire1 = new FireBubble(currentPlayer);
        FireBubble fire2 = new FireBubble(currentPlayer);
        FireBubble fire3 = new FireBubble(currentPlayer);
        FireBubble fire4 = new FireBubble(currentPlayer);
        FireBubble fire5 = new FireBubble(currentPlayer);
        FireBubble fire6 = new FireBubble(currentPlayer);
        FireBubble fire7 = new FireBubble(currentPlayer);
        FireBubble fire8 = new FireBubble(currentPlayer);
        FireBubble fire9 = new FireBubble(currentPlayer);
        FireBubble fire10 = new FireBubble(currentPlayer);
        FireBubble fire11 = new FireBubble(currentPlayer);
        FireBubble fire12 = new FireBubble(currentPlayer);
        FireBubble fire13 = new FireBubble(currentPlayer);
        FireBubble fire14 = new FireBubble(currentPlayer);
        FireBubble fire15 = new FireBubble(currentPlayer);
        FireBubble fire16 = new FireBubble(currentPlayer);
        FireBubble fire17 = new FireBubble(currentPlayer);
        FireBubble fire18 = new FireBubble(currentPlayer);
        FireBubble fire19 = new FireBubble(currentPlayer);
        FireBubble fire20 = new FireBubble(currentPlayer);
        FireBubble fire21 = new FireBubble(currentPlayer);
        FireBubble fire22 = new FireBubble(currentPlayer);
        FireBubble fire23 = new FireBubble(currentPlayer);

        spawnedBubbles.add(fire1);
        spawnedBubbles.add(fire2);
        spawnedBubbles.add(fire3);
        spawnedBubbles.add(fire4);
        spawnedBubbles.add(fire5);
        spawnedBubbles.add(fire6);
        spawnedBubbles.add(fire7);
        spawnedBubbles.add(fire8);
        spawnedBubbles.add(fire9);
        spawnedBubbles.add(fire10);
        spawnedBubbles.add(fire11);
        spawnedBubbles.add(fire12);
        spawnedBubbles.add(fire13);
        spawnedBubbles.add(fire14);
        spawnedBubbles.add(fire15);
        spawnedBubbles.add(fire15);
        spawnedBubbles.add(fire16);
        spawnedBubbles.add(fire17);
        spawnedBubbles.add(fire18);
        spawnedBubbles.add(fire19);
        spawnedBubbles.add(fire20);
        spawnedBubbles.add(fire21);
        spawnedBubbles.add(fire22);
        spawnedBubbles.add(fire23);


        LightningBubble lightning1 = new LightningBubble(currentPlayer);
        LightningBubble lightning2 = new LightningBubble(currentPlayer);
        LightningBubble lightning3 = new LightningBubble(currentPlayer);
        LightningBubble lightning4 = new LightningBubble(currentPlayer);
        LightningBubble lightning5 = new LightningBubble(currentPlayer);
        LightningBubble lightning6 = new LightningBubble(currentPlayer);
        LightningBubble lightning7 = new LightningBubble(currentPlayer);
        LightningBubble lightning8 = new LightningBubble(currentPlayer);
        LightningBubble lightning9 = new LightningBubble(currentPlayer);
        LightningBubble lightning10 = new LightningBubble(currentPlayer);
        LightningBubble lightning11 = new LightningBubble(currentPlayer);
        LightningBubble lightning12 = new LightningBubble(currentPlayer);
        LightningBubble lightning13 = new LightningBubble(currentPlayer);
        LightningBubble lightning14 = new LightningBubble(currentPlayer);
        LightningBubble lightning15 = new LightningBubble(currentPlayer);
        LightningBubble lightning16 = new LightningBubble(currentPlayer);
        LightningBubble lightning17 = new LightningBubble(currentPlayer);
        LightningBubble lightning18 = new LightningBubble(currentPlayer);
        LightningBubble lightning19 = new LightningBubble(currentPlayer);
        LightningBubble lightning20 = new LightningBubble(currentPlayer);
        LightningBubble lightning21 = new LightningBubble(currentPlayer);
        LightningBubble lightning22 = new LightningBubble(currentPlayer);
        LightningBubble lightning23 = new LightningBubble(currentPlayer);
        LightningBubble lightning24 = new LightningBubble(currentPlayer);
        LightningBubble lightning25 = new LightningBubble(currentPlayer);
        LightningBubble lightning26 = new LightningBubble(currentPlayer);
        LightningBubble lightning27 = new LightningBubble(currentPlayer);
        LightningBubble lightning28 = new LightningBubble(currentPlayer);
        LightningBubble lightning29 = new LightningBubble(currentPlayer);
        LightningBubble lightning30 = new LightningBubble(currentPlayer);
        LightningBubble lightning31 = new LightningBubble(currentPlayer);
        LightningBubble lightning32 = new LightningBubble(currentPlayer);

        spawnedBubbles.add(lightning1);
        spawnedBubbles.add(lightning2);
        spawnedBubbles.add(lightning3);
        spawnedBubbles.add(lightning4);
        spawnedBubbles.add(lightning5);
        spawnedBubbles.add(lightning6);
        spawnedBubbles.add(lightning7);
        spawnedBubbles.add(lightning8);
        spawnedBubbles.add(lightning9);
        spawnedBubbles.add(lightning10);
        spawnedBubbles.add(lightning11);
        spawnedBubbles.add(lightning12);
        spawnedBubbles.add(lightning13);
        spawnedBubbles.add(lightning14);
        spawnedBubbles.add(lightning15);
        spawnedBubbles.add(lightning16);
        spawnedBubbles.add(lightning17);
        spawnedBubbles.add(lightning18);
        spawnedBubbles.add(lightning19);
        spawnedBubbles.add(lightning20);
        spawnedBubbles.add(lightning21);
        spawnedBubbles.add(lightning22);
        spawnedBubbles.add(lightning23);
        spawnedBubbles.add(lightning24);
        spawnedBubbles.add(lightning25);
        spawnedBubbles.add(lightning26);
        spawnedBubbles.add(lightning27);
        spawnedBubbles.add(lightning28);
        spawnedBubbles.add(lightning29);
        spawnedBubbles.add(lightning30);
        spawnedBubbles.add(lightning31);
        spawnedBubbles.add(lightning32);


        levels.get(1).addSpawnedBubble(lightning1);
        levels.get(1).addSpawnedBubble(lightning2);
        levels.get(1).addSpawnedBubble(lightning3);
        levels.get(1).addSpawnedBubble(lightning4);
        levels.get(1).addSpawnedBubble(extendE1);
        levels.get(1).addSpawnedBubble(extendX1);
        levels.get(1).spawnBubbles();

        levels.get(2).addSpawnedBubble(lightning1);
        levels.get(2).addSpawnedBubble(lightning2);
        levels.get(2).addSpawnedBubble(lightning3);
        levels.get(2).addSpawnedBubble(lightning4);
        levels.get(2).addSpawnedBubble(extendT1);
        levels.get(2).addSpawnedBubble(extendE1);
        levels.get(2).spawnBubbles();

        levels.get(3).addSpawnedBubble(lightning1);
        levels.get(3).addSpawnedBubble(lightning2);
        levels.get(3).addSpawnedBubble(lightning3);
        levels.get(3).addSpawnedBubble(lightning4);
        levels.get(3).addSpawnedBubble(extendN1);
        levels.get(3).addSpawnedBubble(extendD1);
        levels.get(3).spawnBubbles();

        levels.get(4).addSpawnedBubble(fire1);
        levels.get(4).addSpawnedBubble(fire2);
        levels.get(4).addSpawnedBubble(fire3);
        levels.get(4).addSpawnedBubble(fire4);
        levels.get(4).addSpawnedBubble(fire5);
        levels.get(4).addSpawnedBubble(fire6);
        levels.get(4).addSpawnedBubble(extendN1);
        levels.get(4).addSpawnedBubble(extendD1);
        levels.get(4).addSpawnedBubble(water1);
        levels.get(4).addSpawnedBubble(water2);
        levels.get(4).spawnBubbles();

        levels.get(5).addSpawnedBubble(lightning1);
        levels.get(5).addSpawnedBubble(lightning2);
        levels.get(5).addSpawnedBubble(lightning3);
        levels.get(5).addSpawnedBubble(lightning4);
        levels.get(5).addSpawnedBubble(lightning3);
        levels.get(5).addSpawnedBubble(lightning4);
        levels.get(5).addSpawnedBubble(lightning3);
        levels.get(5).addSpawnedBubble(lightning4);
        levels.get(5).addSpawnedBubble(water1);
        levels.get(5).addSpawnedBubble(water2);
        levels.get(5).addSpawnedBubble(water3);
        levels.get(5).addSpawnedBubble(water4);
        levels.get(5).addSpawnedBubble(extendE1);
        levels.get(5).addSpawnedBubble(extendX1);
        levels.get(5).spawnBubbles();

        levels.get(6).addSpawnedBubble(fire1);
        levels.get(6).addSpawnedBubble(fire2);
        levels.get(6).addSpawnedBubble(fire3);
        levels.get(6).addSpawnedBubble(fire4);
        levels.get(6).addSpawnedBubble(lightning1);
        levels.get(6).addSpawnedBubble(lightning2);
        levels.get(6).addSpawnedBubble(lightning3);
        levels.get(6).addSpawnedBubble(lightning4);
        levels.get(6).addSpawnedBubble(water1);
        levels.get(6).addSpawnedBubble(water2);
        levels.get(6).addSpawnedBubble(water3);
        levels.get(6).addSpawnedBubble(water4);
        levels.get(6).addSpawnedBubble(extendE1);
        levels.get(6).addSpawnedBubble(extendN1);
        levels.get(6).spawnBubbles();

        levels.get(7).addSpawnedBubble(lightning1);
        levels.get(7).addSpawnedBubble(lightning2);
        levels.get(7).addSpawnedBubble(lightning3);
        levels.get(7).addSpawnedBubble(lightning4);
        levels.get(7).addSpawnedBubble(lightning3);
        levels.get(7).addSpawnedBubble(lightning4);
        levels.get(7).addSpawnedBubble(lightning3);
        levels.get(7).addSpawnedBubble(lightning4);
        levels.get(7).addSpawnedBubble(water1);
        levels.get(7).addSpawnedBubble(water2);
        levels.get(7).addSpawnedBubble(water3);
        levels.get(7).addSpawnedBubble(water4);
        levels.get(7).addSpawnedBubble(water5);
        levels.get(7).addSpawnedBubble(water6);
        levels.get(7).addSpawnedBubble(water7);
        levels.get(7).addSpawnedBubble(water8);
        levels.get(7).addSpawnedBubble(extendE1);
        levels.get(7).addSpawnedBubble(extendX1);
        levels.get(7).spawnBubbles();

        levels.get(8).addSpawnedBubble(lightning1);
        levels.get(8).addSpawnedBubble(lightning2);
        levels.get(8).addSpawnedBubble(lightning3);
        levels.get(8).addSpawnedBubble(lightning4);
        levels.get(8).addSpawnedBubble(lightning3);
        levels.get(8).addSpawnedBubble(lightning4);
        levels.get(8).addSpawnedBubble(lightning3);
        levels.get(8).addSpawnedBubble(lightning4);
        levels.get(8).addSpawnedBubble(water1);
        levels.get(8).addSpawnedBubble(water2);
        levels.get(8).addSpawnedBubble(water3);
        levels.get(8).addSpawnedBubble(water4);
        levels.get(8).addSpawnedBubble(water5);
        levels.get(8).addSpawnedBubble(water6);
        levels.get(8).addSpawnedBubble(water7);
        levels.get(8).addSpawnedBubble(water8);
        levels.get(8).addSpawnedBubble(extendT1);
        levels.get(8).addSpawnedBubble(extendE1);
        levels.get(8).spawnBubbles();

        levels.get(9).addSpawnedBubble(lightning1);
        levels.get(9).addSpawnedBubble(lightning2);
        levels.get(9).addSpawnedBubble(lightning3);
        levels.get(9).addSpawnedBubble(lightning4);
        levels.get(9).addSpawnedBubble(lightning3);
        levels.get(9).addSpawnedBubble(lightning4);
        levels.get(9).addSpawnedBubble(lightning3);
        levels.get(9).addSpawnedBubble(lightning4);
        levels.get(9).addSpawnedBubble(water1);
        levels.get(9).addSpawnedBubble(water2);
        levels.get(9).addSpawnedBubble(water3);
        levels.get(9).addSpawnedBubble(water4);
        levels.get(9).addSpawnedBubble(water5);
        levels.get(9).addSpawnedBubble(water6);
        levels.get(9).addSpawnedBubble(water7);
        levels.get(9).addSpawnedBubble(water8);
        levels.get(9).addSpawnedBubble(extendN1);
        levels.get(9).addSpawnedBubble(extendD1);
        levels.get(9).spawnBubbles();

        levels.get(10).addSpawnedBubble(lightning1);
        levels.get(10).addSpawnedBubble(lightning2);
        levels.get(10).addSpawnedBubble(water1);
        levels.get(10).addSpawnedBubble(water2);
        levels.get(10).addSpawnedBubble(water3);
        levels.get(10).addSpawnedBubble(water4);
        levels.get(10).addSpawnedBubble(extendE1);
        levels.get(10).addSpawnedBubble(extendD1);
        levels.get(10).spawnBubbles();

        levels.get(11).addSpawnedBubble(water1);
        levels.get(11).addSpawnedBubble(water2);
        levels.get(11).addSpawnedBubble(water3);
        levels.get(11).addSpawnedBubble(water4);
        levels.get(11).addSpawnedBubble(water4);
        levels.get(11).addSpawnedBubble(water5);
        levels.get(11).addSpawnedBubble(extendE1);
        levels.get(11).addSpawnedBubble(extendN1);
        levels.get(11).spawnBubbles();

        levels.get(12).addSpawnedBubble(water1);
        levels.get(12).addSpawnedBubble(water2);
        levels.get(12).addSpawnedBubble(water3);
        levels.get(12).addSpawnedBubble(water4);
        levels.get(12).addSpawnedBubble(water4);
        levels.get(12).addSpawnedBubble(water5);
        levels.get(12).addSpawnedBubble(water6);
        levels.get(12).addSpawnedBubble(water7);
        levels.get(12).addSpawnedBubble(water8);
        levels.get(12).addSpawnedBubble(water9);
        levels.get(12).addSpawnedBubble(water10);
        levels.get(12).addSpawnedBubble(fire1);
        levels.get(12).addSpawnedBubble(fire2);
        levels.get(12).addSpawnedBubble(fire3);
        levels.get(12).addSpawnedBubble(fire4);
        levels.get(12).addSpawnedBubble(fire5);
        levels.get(12).addSpawnedBubble(extendE1);
        levels.get(12).addSpawnedBubble(extendX1);
        levels.get(12).addSpawnedBubble(extendT1);
        levels.get(12).addSpawnedBubble(extendD1);
        levels.get(12).spawnBubbles();

        levels.get(13).addSpawnedBubble(fire1);
        levels.get(13).addSpawnedBubble(fire2);
        levels.get(13).addSpawnedBubble(fire3);
        levels.get(13).addSpawnedBubble(fire4);
        levels.get(13).addSpawnedBubble(fire4);
        levels.get(13).addSpawnedBubble(fire5);
        levels.get(13).addSpawnedBubble(fire6);
        levels.get(13).addSpawnedBubble(fire7);
        levels.get(13).addSpawnedBubble(fire8);
        levels.get(13).addSpawnedBubble(fire9);
        levels.get(13).addSpawnedBubble(fire10);
        levels.get(13).addSpawnedBubble(water1);
        levels.get(13).addSpawnedBubble(water2);
        levels.get(13).addSpawnedBubble(water3);
        levels.get(13).addSpawnedBubble(water4);
        levels.get(13).addSpawnedBubble(water5);
        levels.get(13).addSpawnedBubble(extendE1);
        levels.get(13).addSpawnedBubble(extendN1);
        levels.get(13).addSpawnedBubble(extendT1);
        levels.get(13).addSpawnedBubble(extendD1);
        levels.get(13).spawnBubbles();

        levels.get(14).addSpawnedBubble(water1);
        levels.get(14).addSpawnedBubble(water2);
        levels.get(14).addSpawnedBubble(water3);
        levels.get(14).addSpawnedBubble(water4);
        levels.get(14).addSpawnedBubble(water4);
        levels.get(14).addSpawnedBubble(water5);
        levels.get(14).addSpawnedBubble(water6);
        levels.get(14).addSpawnedBubble(water7);
        levels.get(14).addSpawnedBubble(water8);
        levels.get(14).addSpawnedBubble(water9);
        levels.get(14).addSpawnedBubble(water10);
        levels.get(14).addSpawnedBubble(water11);
        levels.get(14).addSpawnedBubble(water12);
        levels.get(14).addSpawnedBubble(water13);
        levels.get(14).addSpawnedBubble(water14);
        levels.get(14).addSpawnedBubble(water15);
        levels.get(14).addSpawnedBubble(lightning11);
        levels.get(14).addSpawnedBubble(lightning22);
        levels.get(14).addSpawnedBubble(lightning3);
        levels.get(14).addSpawnedBubble(lightning4);
        levels.get(14).addSpawnedBubble(lightning4);
        levels.get(14).addSpawnedBubble(lightning5);
        levels.get(14).addSpawnedBubble(extendE1);
        levels.get(14).addSpawnedBubble(extendX1);
        levels.get(14).addSpawnedBubble(extendE2);
        levels.get(14).addSpawnedBubble(extendD1);
        levels.get(14).spawnBubbles();

        levels.get(15).addSpawnedBubble(water1);
        levels.get(15).addSpawnedBubble(water2);
        levels.get(15).addSpawnedBubble(water3);
        levels.get(15).addSpawnedBubble(water4);
        levels.get(15).addSpawnedBubble(water4);
        levels.get(15).addSpawnedBubble(water5);
        levels.get(15).addSpawnedBubble(water6);
        levels.get(15).addSpawnedBubble(water7);
        levels.get(15).addSpawnedBubble(water8);
        levels.get(15).addSpawnedBubble(water9);
        levels.get(15).addSpawnedBubble(water10);
        levels.get(15).addSpawnedBubble(water11);
        levels.get(15).addSpawnedBubble(water12);
        levels.get(15).addSpawnedBubble(water13);
        levels.get(15).addSpawnedBubble(water14);
        levels.get(15).addSpawnedBubble(water15);
        levels.get(15).addSpawnedBubble(lightning11);
        levels.get(15).addSpawnedBubble(lightning22);
        levels.get(15).addSpawnedBubble(lightning3);
        levels.get(15).addSpawnedBubble(lightning4);
        levels.get(15).addSpawnedBubble(lightning4);
        levels.get(15).addSpawnedBubble(lightning5);
        levels.get(15).addSpawnedBubble(extendT1);
        levels.get(15).addSpawnedBubble(extendX1);
        levels.get(15).addSpawnedBubble(extendN1);
        levels.get(15).addSpawnedBubble(extendD1);
        levels.get(15).spawnBubbles();

        levels.get(16).addSpawnedBubble(water1);
        levels.get(16).addSpawnedBubble(water2);
        levels.get(16).addSpawnedBubble(water3);
        levels.get(16).addSpawnedBubble(water4);
        levels.get(16).addSpawnedBubble(water4);
        levels.get(16).addSpawnedBubble(water5);
        levels.get(16).addSpawnedBubble(water6);
        levels.get(16).addSpawnedBubble(water7);
        levels.get(16).addSpawnedBubble(water8);
        levels.get(15).addSpawnedBubble(water9);
        levels.get(16).addSpawnedBubble(water10);
        levels.get(16).addSpawnedBubble(lightning1);
        levels.get(16).addSpawnedBubble(lightning2);
        levels.get(16).addSpawnedBubble(lightning3);
        levels.get(16).addSpawnedBubble(lightning4);
        levels.get(16).addSpawnedBubble(lightning5);
        levels.get(16).addSpawnedBubble(lightning6);
        levels.get(16).addSpawnedBubble(lightning7);
        levels.get(16).addSpawnedBubble(lightning8);
        levels.get(16).addSpawnedBubble(lightning9);
        levels.get(16).addSpawnedBubble(extendE1);
        levels.get(16).addSpawnedBubble(extendE1);
        levels.get(16).addSpawnedBubble(extendT1);
        levels.get(16).addSpawnedBubble(extendX1);
        levels.get(16).spawnBubbles();

        levels.get(17).addSpawnedBubble(water1);
        levels.get(17).addSpawnedBubble(water2);
        levels.get(17).addSpawnedBubble(water3);
        levels.get(17).addSpawnedBubble(water4);
        levels.get(17).addSpawnedBubble(water4);
        levels.get(17).addSpawnedBubble(water5);
        levels.get(17).addSpawnedBubble(water6);
        levels.get(17).addSpawnedBubble(water7);
        levels.get(17).addSpawnedBubble(water8);
        levels.get(17).addSpawnedBubble(water9);
        levels.get(17).addSpawnedBubble(water10);
        levels.get(17).addSpawnedBubble(lightning1);
        levels.get(17).addSpawnedBubble(lightning2);
        levels.get(17).addSpawnedBubble(lightning3);
        levels.get(17).addSpawnedBubble(lightning4);
        levels.get(17).addSpawnedBubble(lightning5);
        levels.get(17).addSpawnedBubble(lightning6);
        levels.get(17).addSpawnedBubble(lightning7);
        levels.get(17).addSpawnedBubble(lightning8);
        levels.get(17).addSpawnedBubble(lightning9);
        levels.get(17).addSpawnedBubble(lightning10);
        levels.get(17).addSpawnedBubble(lightning11);
        levels.get(17).addSpawnedBubble(lightning12);
        levels.get(17).addSpawnedBubble(lightning13);
        levels.get(17).addSpawnedBubble(extendE1);
        levels.get(17).addSpawnedBubble(extendT1);
        levels.get(17).addSpawnedBubble(extendN1);
        levels.get(17).addSpawnedBubble(extendD1);
        levels.get(17).spawnBubbles();

        levels.get(18).addSpawnedBubble(fire1);
        levels.get(18).addSpawnedBubble(fire2);
        levels.get(18).addSpawnedBubble(fire3);
        levels.get(18).addSpawnedBubble(fire4);
        levels.get(18).addSpawnedBubble(water2);
        levels.get(18).addSpawnedBubble(water3);
        levels.get(18).addSpawnedBubble(water4);
        levels.get(18).addSpawnedBubble(water5);
        levels.get(18).addSpawnedBubble(water6);
        levels.get(18).addSpawnedBubble(water7);
        levels.get(18).addSpawnedBubble(water8);
        levels.get(18).addSpawnedBubble(water9);
        levels.get(18).addSpawnedBubble(water10);
        levels.get(18).addSpawnedBubble(lightning1);
        levels.get(18).addSpawnedBubble(lightning2);
        levels.get(18).addSpawnedBubble(lightning3);
        levels.get(18).addSpawnedBubble(lightning4);
        levels.get(18).addSpawnedBubble(lightning5);
        levels.get(18).addSpawnedBubble(lightning6);
        levels.get(18).addSpawnedBubble(lightning7);
        levels.get(18).addSpawnedBubble(lightning8);
        levels.get(18).addSpawnedBubble(lightning9);
        levels.get(18).addSpawnedBubble(lightning10);
        levels.get(18).addSpawnedBubble(lightning11);
        levels.get(18).addSpawnedBubble(lightning12);
        levels.get(18).addSpawnedBubble(lightning13);
        levels.get(18).addSpawnedBubble(extendE1);
        levels.get(18).addSpawnedBubble(extendX1);
        levels.get(18).addSpawnedBubble(extendT1);
        levels.get(18).addSpawnedBubble(extendD1);
        levels.get(18).spawnBubbles();

        levels.get(19).addSpawnedBubble(water1);
        levels.get(19).addSpawnedBubble(water2);
        levels.get(19).addSpawnedBubble(water3);
        levels.get(19).addSpawnedBubble(water4);
        levels.get(19).addSpawnedBubble(water5);
        levels.get(19).addSpawnedBubble(water6);
        levels.get(19).addSpawnedBubble(water7);
        levels.get(19).addSpawnedBubble(water8);
        levels.get(19).addSpawnedBubble(water9);
        levels.get(19).addSpawnedBubble(water10);
        levels.get(19).addSpawnedBubble(lightning1);
        levels.get(19).addSpawnedBubble(lightning2);
        levels.get(19).addSpawnedBubble(lightning3);
        levels.get(19).addSpawnedBubble(lightning4);
        levels.get(19).addSpawnedBubble(lightning5);
        levels.get(19).addSpawnedBubble(lightning6);
        levels.get(19).addSpawnedBubble(lightning7);
        levels.get(19).addSpawnedBubble(lightning8);
        levels.get(19).addSpawnedBubble(lightning9);
        levels.get(19).addSpawnedBubble(lightning10);
        levels.get(19).addSpawnedBubble(lightning11);
        levels.get(19).addSpawnedBubble(lightning12);
        levels.get(19).addSpawnedBubble(lightning13);
        levels.get(19).addSpawnedBubble(extendE1);
        levels.get(19).addSpawnedBubble(extendX1);
        levels.get(19).addSpawnedBubble(extendT1);
        levels.get(19).addSpawnedBubble(extendD1);
        levels.get(19).spawnBubbles();

        levels.get(20).addSpawnedBubble(fire1);
        levels.get(20).addSpawnedBubble(fire2);
        levels.get(20).addSpawnedBubble(fire3);
        levels.get(20).addSpawnedBubble(fire4);
        levels.get(20).addSpawnedBubble(fire5);
        levels.get(20).addSpawnedBubble(fire6);
        levels.get(20).addSpawnedBubble(fire7);
        levels.get(20).addSpawnedBubble(fire8);
        levels.get(20).addSpawnedBubble(fire9);
        levels.get(20).addSpawnedBubble(fire10);
        levels.get(20).addSpawnedBubble(lightning1);
        levels.get(20).addSpawnedBubble(lightning2);
        levels.get(20).addSpawnedBubble(lightning3);
        levels.get(20).addSpawnedBubble(lightning4);
        levels.get(20).addSpawnedBubble(lightning5);
        levels.get(20).addSpawnedBubble(lightning6);
        levels.get(20).addSpawnedBubble(lightning7);
        levels.get(20).addSpawnedBubble(lightning8);
        levels.get(20).addSpawnedBubble(lightning9);
        levels.get(20).addSpawnedBubble(lightning10);
        levels.get(20).addSpawnedBubble(lightning11);
        levels.get(20).addSpawnedBubble(lightning12);
        levels.get(20).addSpawnedBubble(lightning14);
        levels.get(20).addSpawnedBubble(lightning15);
        levels.get(20).addSpawnedBubble(lightning16);
        levels.get(20).addSpawnedBubble(lightning17);
        levels.get(20).addSpawnedBubble(lightning18);
        levels.get(20).addSpawnedBubble(extendE1);
        levels.get(20).addSpawnedBubble(extendE2);
        levels.get(20).addSpawnedBubble(extendN1);
        levels.get(20).addSpawnedBubble(extendX1);
        levels.get(20).spawnBubbles();

        levels.get(21).addSpawnedBubble(fire1);
        levels.get(21).addSpawnedBubble(fire2);
        levels.get(21).addSpawnedBubble(fire3);
        levels.get(21).addSpawnedBubble(fire4);
        levels.get(21).addSpawnedBubble(fire5);
        levels.get(21).addSpawnedBubble(fire6);
        levels.get(21).addSpawnedBubble(fire7);
        levels.get(21).addSpawnedBubble(fire8);
        levels.get(21).addSpawnedBubble(fire9);
        levels.get(21).addSpawnedBubble(fire10);
        levels.get(21).addSpawnedBubble(lightning1);
        levels.get(21).addSpawnedBubble(lightning2);
        levels.get(21).addSpawnedBubble(lightning3);
        levels.get(21).addSpawnedBubble(lightning4);
        levels.get(21).addSpawnedBubble(lightning5);
        levels.get(21).addSpawnedBubble(lightning6);
        levels.get(21).addSpawnedBubble(lightning7);
        levels.get(21).addSpawnedBubble(lightning8);
        levels.get(21).addSpawnedBubble(lightning9);
        levels.get(21).addSpawnedBubble(lightning10);
        levels.get(21).addSpawnedBubble(lightning11);
        levels.get(21).addSpawnedBubble(lightning12);
        levels.get(21).addSpawnedBubble(lightning14);
        levels.get(21).addSpawnedBubble(lightning15);
        levels.get(21).addSpawnedBubble(lightning16);
        levels.get(21).addSpawnedBubble(lightning17);
        levels.get(21).addSpawnedBubble(lightning18);
        levels.get(21).addSpawnedBubble(lightning19);
        levels.get(21).addSpawnedBubble(lightning20);
        levels.get(21).addSpawnedBubble(lightning21);
        levels.get(21).addSpawnedBubble(extendD1);
        levels.get(21).addSpawnedBubble(extendE1);
        levels.get(21).addSpawnedBubble(extendT1);
        levels.get(21).addSpawnedBubble(extendX1);
        levels.get(21).spawnBubbles();

        levels.get(22).addSpawnedBubble(water1);
        levels.get(22).addSpawnedBubble(water2);
        levels.get(22).addSpawnedBubble(water3);
        levels.get(22).addSpawnedBubble(water4);
        levels.get(22).addSpawnedBubble(water5);
        levels.get(22).addSpawnedBubble(water6);
        levels.get(22).addSpawnedBubble(water7);
        levels.get(22).addSpawnedBubble(water8);
        levels.get(22).addSpawnedBubble(water9);
        levels.get(22).addSpawnedBubble(water10);
        levels.get(22).addSpawnedBubble(lightning1);
        levels.get(22).addSpawnedBubble(lightning2);
        levels.get(22).addSpawnedBubble(lightning3);
        levels.get(22).addSpawnedBubble(lightning4);
        levels.get(22).addSpawnedBubble(lightning5);
        levels.get(22).addSpawnedBubble(lightning6);
        levels.get(22).addSpawnedBubble(lightning7);
        levels.get(22).addSpawnedBubble(lightning8);
        levels.get(22).addSpawnedBubble(lightning9);
        levels.get(22).addSpawnedBubble(lightning10);
        levels.get(22).addSpawnedBubble(lightning11);
        levels.get(22).addSpawnedBubble(lightning12);
        levels.get(22).addSpawnedBubble(lightning14);
        levels.get(22).addSpawnedBubble(lightning15);
        levels.get(22).addSpawnedBubble(lightning16);
        levels.get(22).addSpawnedBubble(lightning17);
        levels.get(22).addSpawnedBubble(lightning18);
        levels.get(22).addSpawnedBubble(lightning19);
        levels.get(22).addSpawnedBubble(lightning20);
        levels.get(22).addSpawnedBubble(lightning21);
        levels.get(22).addSpawnedBubble(extendN1);
        levels.get(22).addSpawnedBubble(extendE1);
        levels.get(22).addSpawnedBubble(extendE2);
        levels.get(22).addSpawnedBubble(extendX1);
        levels.get(22).spawnBubbles();

        levels.get(23).addSpawnedBubble(water1);
        levels.get(23).addSpawnedBubble(water2);
        levels.get(23).addSpawnedBubble(water3);
        levels.get(23).addSpawnedBubble(water4);
        levels.get(23).addSpawnedBubble(water5);
        levels.get(23).addSpawnedBubble(water6);
        levels.get(23).addSpawnedBubble(water7);
        levels.get(23).addSpawnedBubble(water8);
        levels.get(23).addSpawnedBubble(water9);
        levels.get(23).addSpawnedBubble(water10);
        levels.get(23).addSpawnedBubble(lightning1);
        levels.get(23).addSpawnedBubble(lightning2);
        levels.get(23).addSpawnedBubble(lightning3);
        levels.get(23).addSpawnedBubble(lightning4);
        levels.get(23).addSpawnedBubble(lightning5);
        levels.get(23).addSpawnedBubble(lightning6);
        levels.get(23).addSpawnedBubble(lightning7);
        levels.get(23).addSpawnedBubble(lightning8);
        levels.get(23).addSpawnedBubble(lightning9);
        levels.get(23).addSpawnedBubble(lightning10);
        levels.get(23).addSpawnedBubble(lightning11);
        levels.get(23).addSpawnedBubble(lightning12);
        levels.get(23).addSpawnedBubble(lightning14);
        levels.get(23).addSpawnedBubble(lightning15);
        levels.get(23).addSpawnedBubble(lightning16);
        levels.get(23).addSpawnedBubble(lightning17);
        levels.get(23).addSpawnedBubble(lightning18);
        levels.get(23).addSpawnedBubble(lightning19);
        levels.get(23).addSpawnedBubble(lightning20);
        levels.get(23).addSpawnedBubble(lightning21);
        levels.get(23).addSpawnedBubble(lightning22);
        levels.get(23).addSpawnedBubble(lightning23);
        levels.get(23).addSpawnedBubble(lightning24);
        levels.get(23).addSpawnedBubble(lightning25);
        levels.get(23).addSpawnedBubble(extendT1);
        levels.get(23).addSpawnedBubble(extendE1);
        levels.get(23).addSpawnedBubble(extendN1);
        levels.get(23).addSpawnedBubble(extendX1);
        levels.get(23).spawnBubbles();

        levels.get(24).addSpawnedBubble(water1);
        levels.get(24).addSpawnedBubble(water2);
        levels.get(24).addSpawnedBubble(water3);
        levels.get(24).addSpawnedBubble(water4);
        levels.get(24).addSpawnedBubble(water5);
        levels.get(24).addSpawnedBubble(water6);
        levels.get(24).addSpawnedBubble(water7);
        levels.get(24).addSpawnedBubble(water8);
        levels.get(24).addSpawnedBubble(water9);
        levels.get(24).addSpawnedBubble(water10);
        levels.get(24).addSpawnedBubble(lightning1);
        levels.get(24).addSpawnedBubble(lightning2);
        levels.get(24).addSpawnedBubble(lightning3);
        levels.get(24).addSpawnedBubble(lightning4);
        levels.get(24).addSpawnedBubble(lightning5);
        levels.get(24).addSpawnedBubble(lightning6);
        levels.get(24).addSpawnedBubble(lightning7);
        levels.get(24).addSpawnedBubble(lightning8);
        levels.get(24).addSpawnedBubble(lightning9);
        levels.get(24).addSpawnedBubble(lightning10);
        levels.get(24).addSpawnedBubble(lightning11);
        levels.get(24).addSpawnedBubble(lightning12);
        levels.get(24).addSpawnedBubble(lightning14);
        levels.get(24).addSpawnedBubble(lightning15);
        levels.get(24).addSpawnedBubble(lightning16);
        levels.get(24).addSpawnedBubble(lightning17);
        levels.get(24).addSpawnedBubble(lightning18);
        levels.get(24).addSpawnedBubble(lightning19);
        levels.get(24).addSpawnedBubble(lightning20);
        levels.get(24).addSpawnedBubble(lightning21);
        levels.get(24).addSpawnedBubble(lightning22);
        levels.get(24).addSpawnedBubble(lightning23);
        levels.get(24).addSpawnedBubble(lightning24);
        levels.get(24).addSpawnedBubble(lightning25);
        levels.get(24).addSpawnedBubble(lightning26);
        levels.get(24).addSpawnedBubble(lightning27);
        levels.get(24).addSpawnedBubble(lightning28);
        levels.get(24).addSpawnedBubble(lightning29);
        levels.get(24).addSpawnedBubble(lightning30);
        levels.get(24).addSpawnedBubble(lightning31);
        levels.get(24).addSpawnedBubble(lightning32);
        levels.get(24).addSpawnedBubble(extendT1);
        levels.get(24).addSpawnedBubble(extendE1);
        levels.get(24).addSpawnedBubble(extendD1);
        levels.get(24).addSpawnedBubble(extendX1);
        levels.get(24).spawnBubbles();
    }
    public void addEnemies(){
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
        enemies24.add(new Blubba( 105, 543, true, this ));
        enemies24.add(new Blubba( 650, 543, true, this ));
        enemies24.add(new Incendio(105, 623, true, this ));
        enemies24.add(new Incendio(400, 453, true, this ));
        ArrayList<Enemy> enemies25 = new ArrayList<>();
        enemies25.add(new BoaBoa(105, 543, true, this ));
        enemies25.add(new Incendio(650, 543, true, this ));
        enemies25.add(new Incendio(105, 623, true, this ));
        enemies25.add(new Incendio(400, 453, true, this ));
        ArrayList<Enemy> enemies26 = new ArrayList<>();
        enemies26.add(new SuperDrunk(105, 303, true, this ));

    List<ArrayList<Enemy>> enemiesList = Arrays.asList(enemies2, enemies3, enemies4, enemies5, enemies6, enemies7, enemies8, enemies9, enemies10, enemies11, enemies12, enemies13, enemies14, enemies15, enemies16, enemies17, enemies18, enemies19, enemies20, enemies21, enemies22, enemies23, enemies24,enemies25,enemies26);
        for (int i = 1; i < 25; i++) {
            levels.get(i).setEnemies(enemiesList.get(i));
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