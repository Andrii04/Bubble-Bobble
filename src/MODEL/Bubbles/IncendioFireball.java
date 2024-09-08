package MODEL.Bubbles;

import VIEW.FireballView;

public class IncendioFireball extends Fireball{
    private int shootingSpeed = 6;

    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Incendio/Attack/fireRock";}

    public IncendioFireball() {
        super();
        this.bubbleView = new FireballView(this);
    }
}
