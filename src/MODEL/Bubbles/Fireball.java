package MODEL.Bubbles;

import MODEL.Block;
import MODEL.Enemy.Boris;
import MODEL.Entity;
import MODEL.Player;
import VIEW.FireballView;

public class Fireball extends Bubble{
    private int shootingSpeed = 9;
    private int floatingSpeed = 1;

    public Fireball() {
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/fire";
        this.bubbleView = new FireballView(this);
    }

    @Override
    public Bubble newInstance() {
        return new Fireball();
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
        firing = true;
        updateLocation(boris.getX(), boris.getY());
        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    public Boris getBoris() {return boris;}
}
