package MODEL.Bubbles;

import MODEL.Block;
import MODEL.Enemy.Boris;
import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.FireballView;

import java.awt.*;

public class Fireball extends Bubble {

    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball";}

    public Fireball(Player player, Enemy enemy) {
        super(player);

        shootingSpeed = 7;
        floatingSpeed = 1;
        this.enemy = enemy;
        this.bubbleView = new FireballView(this, enemy);
        super.hitbox = new Rectangle(x, y, 16*2, 16*2);
    }

    @Override
    public Bubble newInstance(Player player) {
        return new Fireball(player, enemy);
    }

    @Override
    public void updateLocation(int newX, int newY) {

        x = newX;
        y = newY;
        hitbox.setLocation(x, y);

        if (firing && hitbox.intersects(player.getHitbox())) {
            explode();
            player.updateAction(Entity.Action.HURT);
        }
        else if (firing && isSolidTile(x, y)) explode();
    }

    @Override
    public void fireBubble() {
        System.out.println("firing");
        firing = true;

        try {
            updateLocation(enemy.getX(), enemy.getY());
        } catch (NullPointerException e) {
            System.out.println("Need to set the shooting " +
                    "enemy for the Fireball," +
                    " use method fireball.setEnemy(Enemy)");
        }

        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    public Enemy getEnemy() {return enemy;}
}
