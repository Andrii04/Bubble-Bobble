package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Enemy.Boris;
import MODEL.Enemy.Enemy;

import java.awt.*;

import MODEL.Enemy.SuperDrunk;
import MODEL.Level;
import VIEW.BubbleView;
import VIEW.MainFrame;
import VIEW.PlayPanel;
import MODEL.Entity.Action;
import MODEL.Player;

import javax.swing.*;

public abstract class Bubble {

    private Color color;
    private int points;
    int x;
    int y;
    private boolean containEnemy;
    BubbleView bubbleView;
    Player player;
    Enemy enemy;
    GameStateManager gsm;
    String skinsPath; //sarà il path della skin senza il numero alla fine
    Level currentLevel;
    boolean erased;

    int shootingSpeed;
    int floatingSpeed;
    Rectangle hitbox;

    Enemy bubbledEnemy;

    boolean effect;

    boolean exploding;
    boolean floating;
    boolean firing;
    boolean encapsulate;
    boolean facingUp;
    boolean pom;
    boolean hitWall;
    boolean facingRight;

    public Bubble(Player player) {
        //gsm = GameStateManager.getInstance();
        //currentLevel = gsm.getCurrentLevel();

        hitbox = new Rectangle(x, y, 17, 18);

        firing = false;
        floating = false;
        encapsulate = false;
        exploding = false;
        facingUp = true;
        pom = false;
        effect = false;

        erased = false;
        hitWall = false;
        facingRight = true;

        this.player = player;
    }

    public void explode() {
        floating = false;
        firing = false;
        exploding = true;
        encapsulate = false;
        bubbleView.setEncapsulate(false);
        bubbleView.setFloating(false);
        bubbleView.setFiring(false);
        bubbleView.setExploding(true);

        bubbleView.setExplodeIMG();
        if (bubbledEnemy != null) {
            bubbledEnemy.setBubbled(false);
            bubbledEnemy.updateAction(Action.DIE);
        }

        if (this instanceof GreenBubble) player.bubbleExploded();
        else if (this instanceof WaterBubble) player.waterBubbleExploded();
        else if (this instanceof LightningBubble) player.lightningBubblesExploded();

        if (player != null && this instanceof GreenBubble && player.isRedRing()) {
            player.setPunteggio(player.getPunteggio() + 100);
        }
    }



    public void pom() {
        if (bubbledEnemy != null) bubbledEnemy.setExploded(false);
        System.out.println("POMM!!! :D");

        pom = true;
        bubbleView.setPom(true);

        bubbleView.setPomIMG();
        if (bubbledEnemy != null) player.setPunteggio(player.getPunteggio()+500);
        else player.setPunteggio(player.getPunteggio() + 100);
    }

    public void encapsule(Enemy enemy) {
        bubbledEnemy = enemy;
        enemy.setBubbled(true);

        firing = false;
        floating = true;
        encapsulate = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setEncapsulate(true);

        //bubbleView.setEncapsuleIMG();
        hitbox.setSize(bubbleView.getCurrentSkin().getIconWidth(),
                bubbleView.getCurrentSkin().getIconHeight());

        if (!(enemy instanceof SuperDrunk)) enemy.updateAction(Action.BUBBLED);
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
        if (currentLevel == null) currentLevel = GameStateManager.getInstance().getCurrentLevel();
        if (player == null) player = GameStateManager.getInstance().getCurrentPlayer();

        try {
            player.getHitbox();
        } catch (NullPointerException e) {
            System.out.println("must assign the player to the Bubble!");
        }

        this.x = x;
        this.y = y;
        hitbox.setLocation(x, y);

        if (firing && currentLevel.isLevelWall(this, x)) {

            if (x == 1) updateLocation(3, y);
            else if (x == currentLevel.getPattern()[0].length-2) updateLocation(
                    currentLevel.getPattern()[0].length-4, y);

            explode();
            pom();
            return;
        }

        if (floating
                && !encapsulate
                && hitbox.intersects(player.getHitbox())
                && player.getCurrentAction().equals(Action.JUMP)) {

            player.setIsOnFloor(true);
            player.updateAction(Action.JUMP);

        }

        if (firing) {
            for (Enemy enemy : currentLevel.getEnemies()) {
                if (enemy != null) {
                    if ( !(enemy instanceof SuperDrunk) &&
                            hitbox.intersects(enemy.getHitbox()) && !enemy.isBubbled()) {
                        if (enemy.isEnraged()) enemy.setEnraged(false);
                        encapsule(enemy);
                        return;
                    }
                }
            }
            for (SpawnedBubble bubble : currentLevel.getSpawnedBubbles()) {
                if (bubble != null && hitbox.intersects(bubble.getHitbox())) {
                    bubble.explode();
                    explode();
                }
            }
        }

        if (floating && encapsulate) {
            if (bubbledEnemy.isEnraged()) {
                bubbledEnemy = null;
                explode();
            }
            else bubbledEnemy.setPosition(x, y);
        }
        if (floating && encapsulate && hitbox.intersects(player.getHitbox())) {
            explode();
        }
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

    public abstract Bubble newInstance(Player player);


    public void fireBubble() {
        firing = true;

        try {
            updateLocation(getPlayer().getX() + 18, getPlayer().getY() + 20);
        } catch (NullPointerException e)
        {System.out.println("need to set" +
                " the player for the bubble, use method" +
                " bubble.setPlayer(Player");}

        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    public void erase() {
        exploding = false;
        firing = false;
        floating = false;
        pom = false;
        effect = false;
        bubbleView.setExploding(false);
        bubbleView.setFiring(false);
        bubbleView.setFloating(false);
        bubbleView.setPom(false);

        erased = true;
        if (currentLevel != null) currentLevel.removeBubble(this);
    }

    public boolean isSolidTile(int x, int y) {

        currentLevel = GameStateManager.getInstance().getCurrentLevel();
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        if (tileX >= 0 && tileX < currentLevel.getPattern()[0].length && tileY >= 0 && tileY < currentLevel.getPattern().length) {
            if (currentLevel.getBlockInt(tileY, tileX) > 0) {
                // test
                //System.out.println("Colliding" + " " + tileX + " " + tileY + " " + currentLevel.getBlockInt(tileY, tileX) + " " + currentLevel.isItSolidBlock(tileY, tileX) + " " + currentLevel.getPattern()[tileY][tileX] + " " + currentLevel.getSolidCheckPattern()[tileY][tileX]);
                return true;
            } else {
                return false;

            }
        }
        return true;
    }

    public void handleFloatingCollision() {
        // Salva le coordinate originali
        currentLevel = GameStateManager.getInstance().getCurrentLevel();
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

    public void stopExplosion() {
        exploding = false;
        bubbleView.setExploding(false);
    }

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public void startEffect() {
    }
    public void updateEffectLocation() {

    }
    public boolean isEffect() {return effect;}

    public void setEnemy(Enemy enemy) {
        System.out.println("setting enemy");
        this.enemy = enemy;
        bubbleView.setEnemy(enemy);
    }
    public boolean getHitWall() {return hitWall;}
    public int getShootingSpeed() {return shootingSpeed;}
    public int getFloatingSpeed() {return floatingSpeed;}
    public void updateLocation() {}
}
