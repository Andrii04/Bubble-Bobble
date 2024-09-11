package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.BottleView;

import java.awt.*;

/**
 * Rappresenta una bottiglia lanciata come proiettile da un nemico di tipo SuperDrunk.
 *
 * <p>La bottiglia può seguire diverse traiettorie, che vengono definite tramite l'enum {@code BottleTrajectory}.
 * La velocità di sparo di questa bottiglia è definita da {@code shootingSpeed}.</p>
 */
public class Bottle extends Bubble {

    // Percorso delle immagini per la bottiglia.
    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Superdrunk/drunkBottle/bottle";}
    BottleTrajectory trajectory;
    // Velocità di sparo della bottiglia.
    {shootingSpeed = 4;}

    /**
     * Enum che definisce le diverse traiettorie che la bottiglia può seguire.
     */
    public enum BottleTrajectory {

        HORIZONTAL_RIGHT(0),
        UP_RIGHT(1), UPUP_RIGHT(2),
        UP_LEFT(3), UPUP_LEFT(4),
        DOWN_RIGHT(5), DOWNDOWN_RIGHT(6),
        DOWN_LEFT(7), DOWNDOWN_LEFT(8),
        HORIZONTAL_LEFT(9);

        private int trajectory;

        BottleTrajectory(int trajectory) {
            this.trajectory = trajectory;
        }


        /**
         * Restituisce il valore associato alla traiettoria.
         *
         * @return Il valore della traiettoria.
         */
        public int getTrajectory() {return trajectory;}
    }

    /**
     * Crea una nuova istanza di {@code Bottle} con la traiettoria specificata.
     *
     * @param trajectory La traiettoria che la bottiglia deve seguire.
     * @return Una nuova bottiglia con la traiettoria specificata.
     */
    public Bubble newInstance(BottleTrajectory trajectory) {
        return new Bottle(player, enemy, trajectory);
    }

    /**
     * Crea una nuova bottiglia con le impostazioni specificate.
     *
     * @param player     Il giocatore associato a questa bottiglia.
     * @param enemy      Il nemico che ha lanciato la bottiglia.
     * @param trajectory La traiettoria che la bottiglia deve seguire.
     */
    public Bottle(Player player, Enemy enemy, BottleTrajectory trajectory) {
        super(player);
        this.enemy = enemy;
        this.bubbleView = new BottleView(this);
        super.hitbox = new Rectangle(x, y, 16*2, 16*2);
        this.trajectory = trajectory;
    }

    @Override
    public void updateLocation() {
        // Aggiorna la posizione della bottiglia in base alla traiettoria.
        switch(trajectory.getTrajectory()) {
            case 0 -> x += shootingSpeed;
            case 1 -> {
                x += shootingSpeed;
                y -= shootingSpeed / 2;
            }
            case 2 -> {
                x += shootingSpeed;
                y -= shootingSpeed;
            }
            case 3 -> {
                x -= shootingSpeed;
                y -= shootingSpeed/2;
            }
            case 4 -> {
                x -= shootingSpeed;
                y -= shootingSpeed;
            }
            case 5 -> {
                x += shootingSpeed;
                y += shootingSpeed;
            }
            case 6 -> {
                x += shootingSpeed;
                y += shootingSpeed*2;
            }
            case 7 -> {
                x -= shootingSpeed;
                y += shootingSpeed;
            }
            case 8 -> {
                x -= shootingSpeed;
                y -= shootingSpeed*2;
            }
            case 9 -> x -= shootingSpeed;
        }

        hitbox.setLocation(x, y);

        try {
            if (firing && hitbox.intersects(player.getHitbox())) {
                player.updateAction(Entity.Action.HURT);
                explode();
            }
        } catch (NullPointerException e){
            System.out.println("Player not found");
        }

        if (firing && isSolidTile(x, y)) explode();
    }

    @Override
    public Bubble newInstance(Player player) {
        return null;
    }

    @Override
    public void fireBubble() {
        if(enemy.getCurrentLevelInt() == 24){
            currentLevel = enemy.getCurrentLevel();
        }

        if(!(currentLevel == null)){
            x = enemy.getX() + 5;
            y = enemy.getY() + 5;

            hitbox.setLocation(x, y);

            firing = true;
            bubbleView.setFiring(true);
            bubbleView.startFiring();
        }
    }
}
