package MODEL;

import MODEL.Enemy.Enemy;

import javax.swing.Timer;
import java.util.Arrays;

public class Level {

    private int height;
    private int width;
    private Timer spawnRate;
    private int[][] pattern;
    private Boolean[][] solidCheckPattern; //dev'essere per forza il Tipo Wrapper per ragioni
    private int blockInt;
    private Enemy[] enemies;

    public Level(int height, int width, int[][] pattern, Enemy[] enemies){
        this.height = height;
        this.width = height;
        this.pattern=pattern;
        this.blockInt = pattern[0][0];
        this.enemies = enemies;


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

    public void startSpawnRate() {spawnRate.start();}
    public void stopSpawnRate() {spawnRate.stop();}

    public void spawnEnemies() {


    }
    public int[][] getPattern() {return pattern;}
    public Boolean[][] getSolidCheckPattern() {return solidCheckPattern;}
    public int getBlockInt() {return blockInt;}
    public Enemy[] getEnemies() {return enemies;}
    public int getBlockInt(int y, int x) {
        if (y >= 0 && y < pattern.length && x >= 0 && x < pattern[0].length) return pattern[y][x];
        else return 1;
    }
    public boolean isItSolidBlock(int y, int x) {
        return solidCheckPattern[y][x];
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
}
