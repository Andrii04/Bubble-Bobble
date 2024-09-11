package MODEL.Bubbles;

import MODEL.Block;
import MODEL.Enemy.Enemy;
import MODEL.Enemy.SuperDrunk;
import MODEL.Entity;
import MODEL.Player;
import VIEW.SpawnedBubbleView;

/**
 * Rappresenta una bolla di fuoco che viene generata automaticamente e può danneggiare i nemici.
 * <p>La bolla di fuoco cade verso il basso, cambia immagine durante la caduta e crea un'area di fuoco quando tocca un muro.</p>
 */
public class FireBubble extends SpawnedBubble {
    private int fireDistanceTraveled;
    private int fallingSpeed = 4;
    private boolean burning;
    private int burningStartX;
    private int burningEndX;
    private int burningStartY;
    private int burningEndY;
    private int burningTimer;

    {
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/fire";
    }

    /**
     * Crea una nuova istanza di {@code FireBubble} associata al giocatore specificato.
     *
     * @param player Il giocatore a cui è associata la bolla.
     */
    public FireBubble(Player player) {
        super(player);
        this.bubbleView = new SpawnedBubbleView(this);
        startFloating();
        hitbox.setSize(45, 45);
        fireDistanceTraveled = 0;
        burning = false;
        burningTimer = 0;
    }

    /**
     * Crea una nuova istanza di {@code FireBubble} associata al giocatore specificato.
     *
     * @param player Il giocatore a cui è associata la nuova bolla.
     * @return Una nuova istanza di {@code FireBubble}.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new FireBubble(player);
    }

    /**
     * Avvia l'effetto della bolla di fuoco, cambiando l'immagine della bolla e impostando l'effetto come attivo.
     */
    @Override
    public void startEffect() {
        bubbleView.setFireIMG1();
        effect = true;
    }

    /**
     * Aggiorna la posizione e l'effetto della bolla di fuoco.
     * <p>La bolla cade verso il basso e cambia immagine in base alla distanza percorsa. Quando tocca un muro, diventa infuocata e crea un'area di fuoco.</p>
     */
    @Override
    public void updateEffectLocation() {
        if (!burning) {
            y += fallingSpeed;
            hitbox.setLocation(x, y);
            fireDistanceTraveled++;
            if (fireDistanceTraveled >= 5 && fireDistanceTraveled < 11) {
                bubbleView.setFireIMG2();
            } else if (fireDistanceTraveled >= 11) {
                bubbleView.setFireIMG4();
            }
        }

        if (isSolidTile(x, y) && !burning) {
            y -= Block.HEIGHT * 2;
            burning = true;
            bubbleView.setFireIMG4();
            burningStartX = x - 25;
            burningEndX = x + 25;
            burningStartY = y - 16;
            burningEndY = y + Block.HEIGHT;
            hitbox.setLocation(burningStartX, burningStartY);
            hitbox.setSize(95, 55);
        }

        if (burning) {
            // Applica l'effetto di incendio sull'area delle coordinate
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