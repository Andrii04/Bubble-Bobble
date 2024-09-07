package MODEL.Bubbles;

import MODEL.Block;
import MODEL.Enemy.Boris;
import MODEL.Entity;
import MODEL.Player;
import VIEW.FireballView;

import java.awt.*;

public class Fireball extends Bubble{
    private int shootingSpeed = 9;
    private int floatingSpeed = 1;

    public Fireball() {
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/";
        this.bubbleView = new FireballView(this);
        super.hitbox = new Rectangle(x, y, 16*2, 16*2);
    }

    @Override
    public Bubble newInstance() {
        return new Fireball();
    }

    @Override
    public void updateLocation(int newX, int newY) {

        try {
            player.getHitbox();
        } catch (NullPointerException e) {
            System.out.println("must assign player to the bubble!");
        }


        try {
            boris.getHitbox();
        } catch (NullPointerException e) {
            System.out.println("must assign boris to the bubble!");
        }

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
        firing = true;
        updateLocation(boris.getX(), boris.getY());
        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    public Boris getBoris() {return boris;}
}
