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

/**
 * La classe {@code WaterBubble} rappresenta una bolla d'acqua che può essere lanciata dal giocatore.
 * Questa bolla ha la capacità di intrappolare nemici e generare un'onda quando tocca un blocco solido.
 */
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

    /**
     * Costruttore della bolla d'acqua.
     *
     * @param player Il giocatore che ha lanciato la bolla.
     */
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


    /**
     * Avvia l'effetto della bolla d'acqua.
     * Imposta l'immagine della bolla e determina la direzione in cui sta andando.
     */
    @Override
    public void startEffect() {
        //setta immagine
        bubbleView.setFallingWaterIMG();
        if (x <= MainFrame.FRAME_WIDTH / 2) facingRight = true;
        else facingRight = false;
        effect = true;
    }

    /**
     * Aggiorna la posizione e l'effetto della bolla d'acqua.
     * Gestisce la caduta della bolla e l'attivazione dell'onda quando tocca un blocco solido.
     */
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

    /**
     * Crea una nuova istanza di {@code WaterBubble} per il giocatore specificato.
     *
     * @param player Il giocatore per cui creare la nuova bolla.
     * @return Una nuova istanza di {@code WaterBubble}.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new WaterBubble(player);
    }
}
