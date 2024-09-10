package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Enemy.SuperDrunk;
import MODEL.Entity;
import MODEL.Player;
import VIEW.SpawnedBubbleView;

import java.util.ArrayList;
import java.util.Arrays;

public class WaterBubble extends SpawnedBubble{
    ArrayList<Enemy> bubbledEnemies;
    int bubbledEnemyIndex;
    boolean wave;
    int waveSpeed = 6;
    int waveStartX;
    int waveEndX;
    int waveStartY;
    int waveEndY;
    int fallingSpeed = 4;
    int waterDistanceTraveled;
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/WaterBubble";}
    public WaterBubble(Player player) {
        super(player);
        this.bubbleView = new SpawnedBubbleView(this);
        startFloating();
        hitbox.setSize(45, 45);

        bubbledEnemies = new ArrayList<>(Arrays.asList(null, null, null, null, null));
        bubbledEnemyIndex = 0;
        wave = false;
        waterDistanceTraveled = 0;
    }

    @Override
    public void startEffect() {
        //setta immagine
        effect = true;
    }

    @Override
    public void updateEffectLocation() {
        if (!wave) {
            y += fallingSpeed;
            hitbox.setLocation(x, y);
            waterDistanceTraveled++;
            if (waterDistanceTraveled >= 5 && waterDistanceTraveled < 11) {
                //setta immagine
            }
            else if (waterDistanceTraveled >= 11) {
                //setta immagine
            }
        }

        if (isSolidTile(x, y) && !wave) {
            //setta immagine
            wave = true;
            //setta hitbox
        }

        if (wave) {

            if (isSolidTile(x, y)) {
                for (Enemy enemy : bubbledEnemies) {
                    if (enemy != null) {
                        enemy.updateAction(Entity.Action.DIE);
                        player.setPunteggio(player.getPunteggio() + 500);
                    }
                }
                erase();
                return;
            }

            for (Enemy enemy : currentLevel.getEnemies()) {

                if (enemy != null && bubbledEnemyIndex < bubbledEnemies.size() &&
                        !(enemy instanceof SuperDrunk)
                        && hitbox.intersects(enemy.getHitbox())) {

                    bubbledEnemies.set(bubbledEnemyIndex, enemy);
                    bubbledEnemyIndex++;
                    enemy.updateAction(Entity.Action.BUBBLED);

                }
            }
            for (Enemy enemy : bubbledEnemies) {
                if (enemy != null) enemy.setPosition(enemy.getX()+waveSpeed, enemy.getY());
            }
        }
    }

    @Override
    public Bubble newInstance(Player player) {
        return new WaterBubble(player);
    }
}
