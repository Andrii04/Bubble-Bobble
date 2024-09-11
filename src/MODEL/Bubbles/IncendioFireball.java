package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Player;
import VIEW.FireballView;

/**
 * Rappresenta una palla di fuoco lanciata dall'enemico di tipo Incendio.
 *
 * <p>La palla di fuoco si muove orizzontalmente e ha un effetto visivo specifico per l'enemico
 * Incendio. La velocità di sparo di questa palla di fuoco è definita da {@code shootingSpeed}.</p>
 */
public class IncendioFireball extends Fireball {
    // Velocità di sparo della palla di fuoco.
    private int shootingSpeed = 6;

    // Percorso delle immagini per la palla di fuoco.
    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Incendio/Attack/fireRock";}

    /**
     * Crea una nuova istanza di {@code IncendioFireball} associata al giocatore e all'enemico specificato.
     *
     * @param player Il giocatore associato a questa palla di fuoco.
     * @param enemy  L'enemico che ha lanciato la palla di fuoco.
     */
    public IncendioFireball(Player player, Enemy enemy) {
        super(player, enemy);
        this.bubbleView = new FireballView(this, enemy);
    }
}
