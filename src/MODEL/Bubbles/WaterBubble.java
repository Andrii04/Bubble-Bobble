package MODEL.Bubbles;

import MODEL.Block;
import MODEL.Enemy.Enemy;
import MODEL.Enemy.SuperDrunk;
import MODEL.Entity;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.SpawnedBubbleView;

import java.util.ArrayList;
import java.util.Arrays;

public class WaterBubble extends SpawnedBubble {
    {
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/WaterBubble";
    }

    ArrayList<Enemy> bubbledEnemies;
    int bubbledEnemyIndex;
    boolean wave;
    int waveSpeed = 4;
    int waveStartX;
    int waveEndX;
    int waveStartY;
    int waveEndY;
    int fallingSpeed = 4;
    int waveIdxOffset;

    public WaterBubble(Player player) {
        super(player);
        this.bubbleView = new SpawnedBubbleView(this);
        startFloating();
        hitbox.setSize(45, 45);

        bubbledEnemies = new ArrayList<>(Arrays.asList(null, null, null, null, null));
        bubbledEnemyIndex = 0;
        wave = false;
        waveIdxOffset = 0;
    }

    @Override
    public void startEffect() {
        //setta immagine
        bubbleView.setFallingWaterIMG();
        if (x <= MainFrame.FRAME_WIDTH / 2) facingRight = true;
        else facingRight = false;
        effect = true;
    }

    @Override
    public void updateEffectLocation() {
        if (!wave) {
            y += fallingSpeed;
            hitbox.setLocation(x, y);
        }

        if (isSolidTile(x, y) && !wave) {
            //setta immagine
            y -= Block.HEIGHT*2;
            if (facingRight) bubbleView.setWaveIMGright();
            else bubbleView.setWaveIMGleft();
            wave = true;
            //setta hitbox
            waveStartX = x-25;
            waveEndX = x+25;
            waveStartY = y-16;
            waveEndY = y+ Block.HEIGHT;
            hitbox.setLocation(waveStartX, waveStartY);
            hitbox.setSize(85, 55);
        }

        if (wave) {
            if (facingRight) x += waveSpeed;
            else x -= waveSpeed;
            hitbox.setLocation(x, y);

            if (currentLevel.isLevelWall(this, x)) {
                for (Enemy enemy : bubbledEnemies) {
                    if (enemy != null) {
                        enemy.setWave(false);
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
                    enemy.setWave(true);
                    bubbledEnemyIndex++;

                }
            }
            for (Enemy enemy : bubbledEnemies) {
                if (enemy != null) {
                    if (facingRight) {
                        enemy.setPosition(x + waveIdxOffset, enemy.getY());
                    }
                    else {
                        enemy.setPosition(x - waveIdxOffset, enemy.getY());
                    }
                    waveIdxOffset++;
                }
            }
        }
    }

    @Override
    public Bubble newInstance(Player player) {
        return new WaterBubble(player);
    }
}
