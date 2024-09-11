package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.BottleView;

import java.awt.*;

public class Bottle extends Bubble{

    {skinsPath = "/Resources/Bubble Bobble Resources/Enemies/Superdrunk/drunkBottle/bottle";}
    BottleTrajectory trajectory;
    {shootingSpeed = 4;}

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
        public int getTrajectory() {return trajectory;}
    }

    public Bubble newInstance(BottleTrajectory trajectory) {
        return new Bottle(player, enemy, trajectory);
    }
    //fare in modo che SuperDrunk spari 5 bottiglie di traiettorie diverse
    //se facingRight tutte quelle RIGHT, altrimenti tutte quelle LEFT
    public Bottle(Player player, Enemy enemy, BottleTrajectory trajectory) {
        super(player);
        this.enemy = enemy;
        this.bubbleView = new BottleView(this);
        super.hitbox = new Rectangle(x, y, 16*2, 16*2);
        this.trajectory = trajectory;
    }

    @Override
    public void updateLocation() {

        switch(trajectory.getTrajectory()) {
            case 0 -> x += shootingSpeed;
            case 1 -> {
                x += shootingSpeed;
                y -= shootingSpeed/2;
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
