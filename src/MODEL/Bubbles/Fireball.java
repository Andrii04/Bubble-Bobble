package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.FireballView;

import java.awt.*;

/**
 * Rappresenta una bolla di fuoco lanciata da un nemico.
 * <p>La bolla di fuoco viene sparata dal nemico e può danneggiare il giocatore e interagire con gli oggetti solidi nel gioco.</p>
 */
public class Fireball extends Bubble {
    private Enemy enemy;

    {
        skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball";
    }

    /**
     * Crea una nuova istanza di {@code Fireball} associata al giocatore e al nemico specificati.
     *
     * @param player Il giocatore a cui è associata la bolla.
     * @param enemy  Il nemico che ha sparato la bolla.
     */
    public Fireball(Player player, Enemy enemy) {
        super(player);

        shootingSpeed = 7;
        floatingSpeed = 1;
        this.enemy = enemy;
        this.bubbleView = new FireballView(this, enemy);
        super.hitbox = new Rectangle(x, y, 16 * 2, 16 * 2);
    }

    /**
     * Crea una nuova istanza di {@code Fireball} associata al giocatore specificato.
     *
     * @param player Il giocatore a cui è associata la nuova bolla.
     * @return Una nuova istanza di {@code Fireball}.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new Fireball(player, enemy);
    }

    /**
     * Aggiorna la posizione della bolla di fuoco e verifica le collisioni.
     * <p>Se la bolla colpisce il giocatore o un oggetto solido, esplode e causa danni.</p>
     *
     * @param newX La nuova coordinata X.
     * @param newY La nuova coordinata Y.
     */
    @Override
    public void updateLocation(int newX, int newY) {
        x = newX;
        y = newY;
        hitbox.setLocation(x, y);

        if (firing && hitbox.intersects(player.getHitbox())) {
            explode();
            player.updateAction(Entity.Action.HURT);
        } else if (firing && isSolidTile(x, y)) {
            explode();
        }
    }

    /**
     * Avvia il lancio della bolla di fuoco.
     * <p>Imposta la bolla come in movimento e aggiorna la sua posizione in base alla posizione del nemico che l'ha lanciata.</p>
     */
    @Override
    public void fireBubble() {
        firing = true;

        try {
            updateLocation(enemy.getX(), enemy.getY());
        } catch (NullPointerException e) {
            System.out.println("Need to set the shooting " +
                    "enemy for the Fireball," +
                    " use method fireball.setEnemy(Enemy)");
        }

        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    /**
     * Restituisce il nemico che ha lanciato la bolla di fuoco.
     *
     * @return Il nemico che ha lanciato la bolla.
     */
    public Enemy getEnemy() {
        return enemy;
    }
}