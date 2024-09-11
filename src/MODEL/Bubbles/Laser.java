package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.FireballView;
import VIEW.LaserView;
import VIEW.MainFrame;

import java.awt.*;

/**
 * Rappresenta un'arma di tipo laser lanciata da un nemico nel gioco.
 *
 * <p>Il laser è una bolla che si muove orizzontalmente e infligge danni al giocatore
 * se lo colpisce. Il laser viene generato da un nemico e può essere cancellato o
 * esplodere al contatto con i bordi dello schermo.</p>
 */
public class Laser extends Bubble {
    // Percorso delle immagini per il laser.
    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Invader/Attack/laser";}

    // Velocità di sparo del laser.
    {shootingSpeed = 7;}

    /**
     * Crea una nuova istanza di {@code Laser} associata al giocatore e all'enemico specificato.
     *
     * @param player Il giocatore associato a questa bolla.
     * @return Una nuova istanza di {@code Laser}.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new Laser(player, enemy);
    }

    /**
     * Crea una nuova istanza di {@code Laser} con il giocatore e l'enemico specificati.
     *
     * @param player Il giocatore associato a questa bolla.
     * @param enemy  L'enemico che ha sparato il laser.
     */
    public Laser(Player player, Enemy enemy) {
        super(player);
        this.enemy = enemy;
        this.bubbleView = new LaserView(this);
        super.hitbox = new Rectangle(x, y, 16 * 2, 16 * 2);
    }

    /**
     * Aggiorna la posizione del laser e gestisce le collisioni.
     *
     * <p>Se il laser è in movimento e si interseca con il giocatore, infligge danni e esplode.
     * Se il laser raggiunge il bordo inferiore dello schermo, viene cancellato.</p>
     *
     * @param newX La nuova coordinata X del laser.
     * @param newY La nuova coordinata Y del laser.
     */
    @Override
    public void updateLocation(int newX, int newY) {
        x = newX;
        y = newY;
        hitbox.setLocation(x, y);

        if (firing && hitbox.intersects(player.getHitbox())) {
            player.updateAction(Entity.Action.HURT);
            explode();
        }

        if (firing && y >= MainFrame.FRAME_HEIGHT - 18) erase();
    }

    /**
     * Avvia il movimento del laser e lo imposta come attivo.
     *
     * <p>Imposta la posizione iniziale del laser e avvia l'animazione di sparo. Se l'enemico
     * che ha lanciato il laser non è stato impostato, viene visualizzato un messaggio di errore.</p>
     */
    @Override
    public void fireBubble() {
        System.out.println("firing laser");

        try {
            updateLocation(enemy.getX() + 5, enemy.getY() + 5);
        } catch (NullPointerException e) {
            System.out.println("Need to set the shooting " +
                    "enemy for the Laser, " +
                    "use method laser.setEnemy(Enemy)");
        }

        firing = true;
        bubbleView.setFiring(true);
        bubbleView.startFiring();
    }
}
