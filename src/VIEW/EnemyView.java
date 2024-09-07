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
        currentFrame = 0;
        loadImages();
        enemy.addObserver(this);
        currentAction = Entity.Action.MOVE_RIGHT;
    }
   BufferedImage scaleImage(BufferedImage img) {
        BufferedImage scaledImage = new BufferedImage(img.getWidth() * 2, img.getHeight() * 2, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, img.getWidth() * 2, img.getHeight() * 2, null);
        g2d.dispose();
        return scaledImage;
    }

    void loadImages() {
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Enemy) {
            Enemy enemy = (Enemy) o;
            this.x = enemy.getX();
            this.y = enemy.getY();
        }
        if (arg instanceof Entity.Action) {
            Entity.Action action = (Entity.Action) arg;
            this.currentAction = action;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage[] currentAnimation = getCurrentAnimation();
        if (currentAnimation != null && currentAnimation.length > 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime > frameDelay) {
                currentFrame = (currentFrame + 1) % currentAnimation.length;
                lastTime = currentTime;
            }
            g2d.drawImage(currentAnimation[currentFrame], x, y, null);
            g2d.setColor(Color.RED);
            g2d.draw(enemy.getHitbox());
        }
        else{
            System.out.println("No animation found");
        }
    }

    // to be overriden
    public BufferedImage[] getCurrentAnimation(){
        return null;
    }
}
