package MODEL.Bubbles;

import MODEL.Block;
import MODEL.Enemy.Enemy;
import MODEL.Enemy.SuperDrunk;
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
    int burningStartY;
    int burningEndY;
    int burningTimer;

    public FireBubble(Player player) {
        super(player);
        this.bubbleView = new SpawnedBubbleView(this);
        startFloating();
        hitbox.setSize(45, 45);
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

        if (isSolidTile(x, y) && !burning) {
            y -= Block.HEIGHT*2;
            burning = true;
            bubbleView.setFireIMG4();
            burningStartX = x-25;
            burningEndX = x+25;
            burningStartY = y-16;
            burningEndY = y+Block.HEIGHT;
            hitbox.setLocation(burningStartX, burningStartY);
            hitbox.setSize(95, 55);
        }

        if (burning) {
            //immagine burning sull'area delle coordinate
            for (Enemy enemy : currentLevel.getEnemies()) {
                if (enemy != null && !(enemy instanceof SuperDrunk)
                        && hitbox.intersects(enemy.getHitbox())) {
                    enemy.updateAction(Entity.Action.DIE);
                    player.setPunteggio(player.getPunteggio() + 500);
                }
            }
            burningTimer++;
            if (burningTimer >= 350) {
                effect = false;
                erase();
            }
        }
    }
}

//fatta logica e funziona, da vedere più precisamente l'area di effetto del fuoco e fixxare le immagini.