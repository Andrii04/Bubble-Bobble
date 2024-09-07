package MODEL;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.SpawnedBubble;
import MODEL.Enemy.*;
import VIEW.*;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Level {

    private int height;
    private int width;
    private Timer spawnRate;
    private int[][] pattern;
    private Boolean[][] solidCheckPattern; //dev'essere per forza il Tipo Wrapper per ragioni
    private int blockInt;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyView> enemyViews;
    private ArrayList<Bubble> bubbles;
    private ArrayList<SpawnedBubble> spawnedBubbles;
    //private Integer lastBubbleY;
    //private Integer lastBubbleX;

    public Level(int height, int width, int[][] pattern,
                 ArrayList<Enemy> enemies, ArrayList<SpawnedBubble> spawnedBubbles) {
        this.height = height;
        this.width = height;
        this.pattern = pattern;
        this.blockInt = pattern[0][0];


        this.bubbles = new ArrayList<>();
        this.spawnedBubbles = spawnedBubbles;

        this.enemies = enemies;
        this.enemyViews = enemies.stream().
                map(enemy -> {
                    if (enemy instanceof Benzo) return new BenzoView(enemy);
                    if(enemy instanceof Blubba) return new BlubbaView(enemy);
                    if(enemy instanceof Boris) return new BorisView(enemy);
                    if(enemy instanceof BoaBoa) return new BoaBoaView(enemy);
                    if(enemy instanceof SuperDrunk) return new SuperDrunkView(enemy);
                    //altri casi

                    return null;
                })
                .collect(Collectors.toCollection(ArrayList<EnemyView>::new));

        //creazione pattern con tutti i blocchi esterni solidi (true) e il vuoto non solido (false)
        //utile poi per la logica di collisione per controllare se un blocco è solido o no.
        solidCheckPattern = Arrays.stream(pattern).
                map(row -> Arrays.stream(row)
                        .mapToObj(col -> col > 0)
                        .toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

    }

    public void setSpawnRate(int timerDelay) {
        Timer spawnRate = new Timer(timerDelay, a -> spawnEnemies());
        this.spawnRate = spawnRate;
    }

    public void startSpawnRate() {
        spawnRate.start();
    }

    public void stopSpawnRate() {
        spawnRate.stop();
    }

    public void spawnEnemies() {


    }

    public int[][] getPattern() {
        return pattern;
    }

    public Boolean[][] getSolidCheckPattern() {
        return solidCheckPattern;
    }

    public int getBlockInt() {
        return blockInt;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getBlockInt(int y, int x) {
        if (y >= 0 && y < pattern.length && x >= 0 && x < pattern[0].length) return pattern[y][x];
        else return 1;
    }

    public boolean isItSolidBlock(int y, int x) {
        if (y >= 0
                && y < solidCheckPattern.length
                && x >= 0
                && x < solidCheckPattern[0].length) return solidCheckPattern[y][x];

        else return true;
    }

    public void setSolidBlock(int y, int x) {
        solidCheckPattern[y][x] = !solidCheckPattern[y][x];
    }

    public void removeBlock(int y, int x) {
        pattern[y][x] = 0;
    }

    public void addBlock(int y, int x) {
        pattern[y][x] = blockInt;
        solidCheckPattern[y][x] = true;
    }

    //non serve più, le bolle non sono più solide

    /*public void handleBubble(Bubble bubble, int newBubbleY, int newBubbleX) {

        if (!bubble.getErased()) {

            if (pattern[newBubbleY][newBubbleX] == 0) {
                solidCheckPattern[newBubbleY][newBubbleX] = true;
            }
            if (lastBubbleY != null && lastBubbleX != null
                    && pattern[lastBubbleY][lastBubbleX] == 0) solidCheckPattern[lastBubbleY][lastBubbleX] = false;

            lastBubbleY = newBubbleY;
            lastBubbleX = newBubbleX;
        }
        else if (bubble.getErased() && lastBubbleY != null && lastBubbleX != null
        && pattern[lastBubbleY][lastBubbleX] == 0) solidCheckPattern[lastBubbleY][lastBubbleX] = false;
    }*/

    public ArrayList<EnemyView> getEnemyViews() {
        return enemyViews;
    }

    public boolean isLevelWall(Bubble bubble, int x) {
        int arrayX = x / Block.WIDTH;

        if (arrayX < pattern[0].length && arrayX >= 0) {
            if (arrayX == pattern[0].length - 2 || arrayX == 1) {
                return true;
            } else return false;
        }
        return false;
    }
    public void removeEnemy(Enemy enemy) {
        int enemyIDX = enemies.indexOf(enemy);
        if (enemyIDX != -1) {
            enemies.set(enemyIDX, null);
            enemyViews.set(enemyIDX, null);
        }
    }

    public void addBubble(Bubble bubble) {
        this.bubbles.add(bubble);
    }

    public void removeBubble(Bubble bubble) {
        this.bubbles.set(bubbles.indexOf(bubble), null);
    }

    public ArrayList<Bubble> getBubbles() {
        return bubbles;
    }

    public void spawnBubbles() {bubbles.addAll(spawnedBubbles);}

}


