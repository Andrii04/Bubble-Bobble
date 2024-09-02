package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Enemy.Enemy;

import java.awt.*;

import MODEL.Level;
import VIEW.BubbleView;
import VIEW.PlayPanel;
import MODEL.Entity.Action;
import MODEL.Player;

import javax.swing.*;

public abstract class Bubble {

    private Color color;
    private int points;
    private int x;
    private int y;
    private boolean containEnemy;
    BubbleView bubbleView;
    Player player;
    GameStateManager gsm;
    String skinsPath; //sarà il path della skin senza il numero alla fine
    Level currentLevel;
    boolean erased;

    Rectangle hitbox;

    Enemy bubbledEnemy;

    private int shootingSpeed = 9;
    private int floatingSpeed = 1;

    boolean exploding;
    boolean floating;
    boolean firing;
    boolean encapsulate;
    boolean facingUp;


    public Bubble() {
        this.bubbleView = new BubbleView(this);
        gsm = GameStateManager.getInstance();
        currentLevel = gsm.getCurrentLevel();

        hitbox = new Rectangle(x, y, 17, 18);

        firing = false;
        floating = false;
        encapsulate = false;
        exploding = false;
        facingUp = true;

        erased = false;
    }

    public void explode(Enemy enemy) {
        floating = false;
        encapsulate = false;
        exploding = true;

        bubbleView.setFloating(false);
        bubbleView.setEncapsulate(false);
        bubbleView.setExploding(true);

        if (enemy != null) enemy.updateAction(Action.DIE);


    }

    public void encapsule(Enemy enemy) {
        bubbledEnemy = enemy;
        System.out.println("Enemy bubbled");

        firing = false;
        floating = true;
        encapsulate = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setEncapsulate(true);

        bubbleView.setEncapsuleIMG();
        hitbox.setSize(bubbleView.getCurrentSkin().getIconWidth(),
                bubbleView.getCurrentSkin().getIconHeight());

        enemy.updateAction(Action.BUBBLED);

    }

    public void startFloating() {
        firing = false;
        floating = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setFloatingIMG();
        hitbox.setSize(44, 45);

    }

    public void updateLocation(int x, int y) {

        this.x = x;
        this.y = y;
        hitbox.setLocation(x, y);

        if (floating
                && hitbox.intersects(player.getHitbox())
                && player.getCurrentAction().equals(Action.JUMP)) {

            player.setIsOnFloor(true);
            player.updateAction(Action.JUMP);
        }

        if (firing) {
            for (Enemy enemy : currentLevel.getEnemies()) {
                if (hitbox.intersects(enemy.getHitbox()) && !enemy.isBubbled()) {
                    encapsule(enemy);
                    return;
                }
            }
        }

        if (encapsulate) bubbledEnemy.setPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSkinsPath() {
        return skinsPath;
    }

    public BubbleView getBubbleView() {
        return bubbleView;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getErased() {return erased;}

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract Bubble newInstance();


    public void fireBubble() {
        firing = true;
        updateLocation(getPlayer().getX() + 18, getPlayer().getY() + 20);
        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    public void erase() {
        firing = false;
        floating = false;
        bubbleView.setFiring(false);
        bubbleView.setFloating(false);

        erased = true;
        player.removeBubble(this);
    }

    public boolean isSolidTile(int x, int y) {
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        if (tileX >= 0 && tileX < currentLevel.getPattern()[0].length && tileY >= 0 && tileY < currentLevel.getPattern().length) {
            if (currentLevel.getBlockInt(tileY, tileX) > 0) {
                // test
                System.out.println("Colliding" + " " + tileX + " " + tileY + " " + currentLevel.getBlockInt(tileY, tileX) + " " + currentLevel.isItSolidBlock(tileY, tileX) + " " + currentLevel.getPattern()[tileY][tileX] + " " + currentLevel.getSolidCheckPattern()[tileY][tileX]);
                return true;
            } else {
                return false;

            }
        }
        return true;
    }

    public void handleFloatingCollision() {
        // Salva le coordinate originali
        int originalX = x;
        int originalY = y;

        // Controlla il movimento verticale
        int newY;
        if (facingUp) {
            newY = y - floatingSpeed;
            if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY / Block.HEIGHT, x / Block.WIDTH)) {
                // Se c'è un blocco sopra, inizia a scendere
                facingUp = false;
            } else {
                y = newY;
            }
        } else {
            newY = y + floatingSpeed;
            if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY / Block.HEIGHT, x / Block.WIDTH)) {
                // Se c'è un blocco sotto, inizia a salire
                facingUp = true;
            } else {
                y = newY;
            }
        }

        // Controlla se deve iniziare a muoversi orizzontalmente
        if (isSolidTile(x + floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x + floatingSpeed) / Block.WIDTH)) {
            // Se c'è un blocco a destra, inizia a muoversi a sinistra
            bubbleView.setFacingRight(false);
        } else if (isSolidTile(x - floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x - floatingSpeed) / Block.WIDTH)) {
            // Se c'è un blocco a sinistra, inizia a muoversi a destra
            bubbleView.setFacingRight(true);
        }

        // Movimento orizzontale solo se facingRight è stato impostato
        if (bubbleView.getStartHorizontalMovement() && bubbleView.getFacingRight()) {
            x += floatingSpeed;
        } else if (bubbleView.getStartHorizontalMovement()){
            x -= floatingSpeed;
        }

        // Se la bolla ha un muro sia sopra che a un lato, inizia a strisciare verso il basso lungo il muro
        if ((isSolidTile(x + floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x + floatingSpeed) / Block.WIDTH)) ||
                (isSolidTile(x - floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x - floatingSpeed) / Block.WIDTH))) {
            newY = y + floatingSpeed;
            if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY / Block.HEIGHT, x / Block.WIDTH)) {
                // Se c'è un blocco sotto, rimbalza verso l'alto
                y = originalY - floatingSpeed;
                facingUp = true;
            } else {
                y = newY;
            }
        }

        // Aggiorna la posizione della bolla
        updateLocation(x, y);
    }

    public Rectangle getHitbox() {return hitbox;}

    public Enemy getBubbledEnemy() {return bubbledEnemy;}
}
