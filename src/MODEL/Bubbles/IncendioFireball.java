package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Player;
import VIEW.FireballView;

public class IncendioFireball extends Fireball{
    private int shootingSpeed = 6;

    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Incendio/Attack/fireRock";}

    public IncendioFireball(Player player, Enemy enemy) {
        super(player, enemy);
        this.bubbleView = new FireballView(this, enemy);
    }
}
