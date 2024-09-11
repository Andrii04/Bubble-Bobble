package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Enemy.SuperDrunk;
import MODEL.Entity;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.SpawnedBubbleView;

import javax.swing.*;
/**
 * Rappresenta una bolla di tipo "Lightning" che viene generata automaticamente.
 *
 * <p>Questa bolla si muove orizzontalmente e infligge danni agli nemici al contatto.
 * La bolla ha una velocità di movimento elevata e cambia effetto quando si interseca
 * con un muro o un nemico.</p>
 */
public class LightningBubble extends SpawnedBubble {
    private int lightningSpeed = 8;

    // Percorso delle immagini per la bolla di tipo Lightning.
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/lightning";}

    /**
     * Crea una nuova istanza di {@code LightningBubble} e imposta la vista della bolla.
     *
     * @param player Il giocatore associato a questa bolla.
     */
    public LightningBubble(Player player) {
        super(player);

        bubbleView = new SpawnedBubbleView(this);
        startFloating();
        bubbleView.setLightningFloatingIMG();
        hitbox.setSize(45, 45);
    }

    /**
     * Avvia l'effetto della bolla di tipo Lightning.
     *
     * <p>Imposta la direzione della bolla a seconda della sua posizione orizzontale
     * e aggiorna l'immagine della bolla per riflettere l'effetto Lightning.</p>
     */
    @Override
    public void startEffect() {
        if (x <= MainFrame.FRAME_WIDTH / 2) {
            facingRight = true;
        } else {
            facingRight = false;
        }
        bubbleView.setLightningIMG();
        hitbox.setSize(45, 45);
        effect = true;
    }

    /**
     * Aggiorna la posizione dell'effetto della bolla di tipo Lightning.
     *
     * <p>Muove la bolla orizzontalmente e gestisce le collisioni con muri e nemici.
     * Se la bolla colpisce un muro, esplode. Se colpisce un nemico, infligge danni
     * o lo elimina a seconda del tipo di nemico.</p>
     */
    @Override
    public void updateEffectLocation() {
        if (facingRight) {
            x += lightningSpeed;
        } else {
            x -= lightningSpeed;
        }
        hitbox.setLocation(x, y);

        if (isSolidTile(x, y)) {
            hitWall = true;
            effect = false;
            explode();
        }

        for (Enemy enemy : currentLevel.getEnemies()) {
            if (enemy != null && !(enemy instanceof SuperDrunk) &&
                    hitbox.intersects(enemy.getHitbox())) {
                bubbledEnemy = enemy;
                enemy.updateAction(MODEL.Entity.Action.DIE);
                effect = false;
            } else if (enemy != null && enemy instanceof SuperDrunk &&
                    hitbox.intersects(enemy.getHitbox())) {
                bubbledEnemy = enemy;
                enemy.updateAction(Entity.Action.HURT);
                effect = false;
            }
        }
    }

    /**
     * Crea una nuova istanza di {@code LightningBubble} con il giocatore specificato.
     *
     * @param player Il giocatore associato a questa nuova bolla.
     * @return Una nuova istanza di {@code LightningBubble}.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new LightningBubble(player);
    }
}