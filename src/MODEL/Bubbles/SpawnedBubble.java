package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.SpawnedBubbleView;

import java.util.Random;

/**
 * Classe astratta per le bolle che vengono generate automaticamente e non sparate dal giocatore.
 *
 * <p>Queste bolle hanno una velocità di movimento e una velocità di galleggiamento predefinite e
 * si spostano in posizioni casuali all'interno del frame del gioco.</p>
 */
public abstract class SpawnedBubble extends Bubble {

    /**
     * Crea un'istanza di {@code SpawnedBubble} con una posizione casuale e
     * imposta la velocità di sparo e la velocità di galleggiamento predefinite.
     *
     * @param player Il giocatore associato a questa bolla.
     */
    public SpawnedBubble(Player player) {
        super(player);
        shootingSpeed = 7;
        floatingSpeed = 1;
        // this.bubbleView = new SpawnedBubbleView(this);
        // ricordare di settarla nelle sottoclassi

        Random random = new Random();
        x = random.nextInt(Block.WIDTH + 30, MainFrame.FRAME_HEIGHT - Block.WIDTH - 30);
        y = random.nextInt(Block.HEIGHT + 30, MainFrame.FRAME_HEIGHT - Block.HEIGHT - 30);

        // updateLocation che setta le coordinate dove spawnano (prob random)
    }

    /**
     * Inizia l'effetto associato alla bolla.
     *
     * <p>Questo metodo deve essere implementato nelle sottoclassi per definire l'effetto
     * che la bolla avrà quando viene generata.</p>
     */
    public abstract void startEffect();

    /**
     * Aggiorna la posizione dell'effetto associato alla bolla.
     *
     * <p>Questo metodo deve essere implementato nelle sottoclassi per aggiornare
     * la posizione dell'effetto della bolla durante il gioco.</p>
     */
    public abstract void updateEffectLocation();

    /**
     * Aggiorna la posizione della bolla e gestisce l'interazione con il giocatore.
     *
     * <p>Se la bolla è in stato di galleggiamento e interseca il giocatore,
     * viene chiamato il metodo {@code explode()}.</p>
     *
     * @param newX La nuova coordinata x della bolla.
     * @param newY La nuova coordinata y della bolla.
     */
    @Override
    public void updateLocation(int newX, int newY) {
        if (currentLevel == null) currentLevel = GameStateManager.getInstance().getCurrentLevel();
        x = newX;
        y = newY;
        hitbox.setLocation(x, y);

        if (player == null) player = GameStateManager.getInstance().getCurrentPlayer();

        if (floating && player != null) {
            if (hitbox.intersects(player.getHitbox())) explode();
        }
    }
}