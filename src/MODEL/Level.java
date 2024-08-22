package MODEL;

import java.util.Timer;

public class Level {

    private int height;
    private int width;
    private Timer spawnRate;
    char[] pattern;

    public Level(int height, int width, char[] pattern, Timer spawnRate){
        this.height = height;
        this.width = height;
        this.spawnRate=spawnRate;
        this.pattern=pattern;

    }

    public void spawnEnemies() {

    }
}
