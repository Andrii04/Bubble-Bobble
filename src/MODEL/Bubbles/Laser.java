package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.FireballView;
import VIEW.LaserView;
import VIEW.MainFrame;

import java.awt.*;

public class Laser extends Bubble{
    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Invader/Attack/laser";}
    {shootingSpeed = 7;}
    @Override
    public Bubble newInstance(Player player) {
        return new Laser(player, enemy);
    }

    public Laser(Player player, Enemy enemy) {
        super(player);
        this.enemy = enemy;
        this.bubbleView = new LaserView(this);
        super.hitbox = new Rectangle(x, y, 16*2, 16*2);
    }

    @Override
    public void updateLocation(int newX, int newY) {
        x = newX;
        y = newY;
        hitbox.setLocation(x, y);

        if (firing && hitbox.intersects(player.getHitbox())) {
            player.updateAction(Entity.Action.HURT);
            explode();
        }

        if (firing && y >= MainFrame.FRAME_HEIGHT-18) erase();
    }

    @Override
    public void fireBubble() {
        System.out.println("firing laser");

        try {
            updateLocation(enemy.getX() + 5, enemy.getY() + 5);
        } catch (NullPointerException e) {
            System.out.println("Need to set the shooting " +
                    "enemy for the Laser," +
                    " use method laser.setEnemy(Enemy)");
        }

        firing = true;
        bubbleView.setFiring(true);
        bubbleView.startFiring();
    }
}
