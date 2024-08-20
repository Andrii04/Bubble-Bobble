package Model;

import javax.swing.Timer;

public class Level {

    private int height;
    private int width;
    private Timer spawnRate;
    char[] pattern;

    public Level(int height, int width, Timer spawnRate, char[] pattern){
        this.height = height;
        this.width = height;
        this.spawnRate=spawnRate;
        this.pattern=pattern;

    }

    public void spawnEnemies() {

    }
}
