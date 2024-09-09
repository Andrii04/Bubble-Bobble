package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.SpawnedBubbleView;

public class FireBubble extends SpawnedBubble{
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/fire";}
    int fireDistanceTraveled;
    int fallingSpeed = 4;
    boolean burning;
    int burningStartX;
    int burningEndX;
    int burningTimer;

    public FireBubble(Player player) {
        super(player);
        this.bubbleView = new SpawnedBubbleView(this);
        hitbox.setSize(44, 45);
        fireDistanceTraveled = 0;
        burning = false;
        burningTimer = 0;
    }

    @Override
    public Bubble newInstance(Player player) {
        return new FireBubble(player);
    }

    @Override

    public void startEffect() {
        bubbleView.setFireIMG1();
        effect = true;

    }

    @Override
    public void updateEffectLocation() {
        if (!burning) {
            y += fallingSpeed;
            hitbox.setLocation(x, y);
            fireDistanceTraveled++;
            if (fireDistanceTraveled >= 5 && fireDistanceTraveled < 11) bubbleView.setFireIMG2();
            else if (fireDistanceTraveled >= 11) bubbleView.setFireIMG4();
        }

        if (isSolidTile(x, y)) {
            burning = true;
            burningStartX = x-50;
            burningEndX = x+50;
        }

        if (burning) {
            //immagine burning sull'area delle coordinate
            for (Enemy enemy : currentLevel.getEnemies()) {
                if (enemy != null && enemy.getX() >= burningStartX && enemy.getX() <= burningEndX
                 && enemy.getY() >= y-50 && enemy.getY() <= y+50) {
                    enemy.updateAction(Entity.Action.DIE);
                }
            }
            burningTimer++;
            if (burningTimer >= 200) erase();
        }
    }
}

//fatta logica e funziona, da vedere più precisamente l'area di effetto del fuoco e fixxare le immagini.