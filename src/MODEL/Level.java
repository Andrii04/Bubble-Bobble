package MODEL;

import javax.swing.Timer;

public class Level {

    private int height;
    private int width;
    private Timer spawnRate;
    int[][] pattern;

    public Level(int height, int width, int[][] pattern){
        this.height = height;
        this.width = height;
        this.pattern=pattern;

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
}
