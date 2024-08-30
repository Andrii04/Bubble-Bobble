package VIEW;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public abstract class EnemyView implements Observer {
    int x;
    int y;
    Enemy enemy;
    Player player;
    Entity.Action currentAction;
    int currentFrame;
    long lastTime;
    final int frameDelay = 100;

    public EnemyView(Enemy enemy) {
        this.enemy = enemy;
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.player = enemy.getPlayer();
        enemy.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Enemy) {
            Enemy enemy = (Enemy) o;
            this.x = enemy.getX();
            this.y = enemy.getY();
        }
        if(arg instanceof Entity.Action){
            Entity.Action action = (Entity.Action) arg;
            this.currentAction = action;
        }
    }
}
