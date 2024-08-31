package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Enemy.Enemy;

import java.awt.Color;

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

    boolean exploding;
    boolean floating;
    boolean firing;
    boolean encapsulate;
    boolean facingUp;


    public Bubble() {
        this.bubbleView = new BubbleView(this);
        gsm = GameStateManager.getInstance();
        currentLevel = gsm.getCurrentLevel();

        firing = false;
        floating = false;
        encapsulate = false;
        exploding = false;
        facingUp = true;
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

    public void ecncapsule(Enemy enemy) {
        firing = false;
        floating = true;
        encapsulate = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setEncapsulate(true);

    }

    public void startFloating() {
        firing = false;
        floating = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setFloatingIMG();

    }

    public void updateLocation(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void finishedFiring() {
        firing = false;
        player.removeBubble(this);
        bubbleView.setFiring(false);
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

        // Fluttuare verso l'alto
        int newY = y - 3;

        // Controlla la collisione con il blocco sopra
        if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY/Block.HEIGHT, x/Block.WIDTH)) {
            // Se c'è un blocco sopra, la bolla rimbalza verso il basso
            y = originalY + 3;
            facingUp = false;
        }

        newY = y + 3;
        if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY/Block.HEIGHT, x/Block.WIDTH)) {
            // Se c'è un blocco sotto, rimbalza verso l'alto
            y = originalY - 3;
            facingUp = true;
        }

        // Controlla collisione con il blocco a destra
        int newX = x + 3;
        if (isSolidTile(newX, y) && currentLevel.isItSolidBlock(y/Block.HEIGHT, newX/Block.WIDTH)) {
            // Se c'è un blocco a destra, rimbalza a sinistra
            x = originalX - 3;
            bubbleView.setFacingRight(false);
        }

        // Controlla collisione con il blocco a sinistra
        newX = x - 3;
        if (isSolidTile(newX, y) && currentLevel.isItSolidBlock(y/Block.HEIGHT, newX/Block.WIDTH)) {
            // Se c'è un blocco a sinistra, rimbalza a destra
            x = originalX + 3;
            bubbleView.setFacingRight(true);
        }

        if (!((isSolidTile(x, y-3) && currentLevel.isItSolidBlock((y-3)/Block.HEIGHT, x/Block.WIDTH))
                || (isSolidTile(x, y+3) && currentLevel.isItSolidBlock((y+3)/Block.HEIGHT, x/Block.WIDTH)))){

            if (facingUp) y = y-3;
            else y = y+3;
        }

        if (!((isSolidTile(x-3, y) && currentLevel.isItSolidBlock(y/Block.HEIGHT, (x-3)/Block.WIDTH))
                || (isSolidTile(x+3, y) && currentLevel.isItSolidBlock(y/Block.HEIGHT, (x+3)/Block.WIDTH)))){
            boolean facingRight = bubbleView.getFacingRight();
            if (facingRight) x = originalX + 3;
            else x = originalX - 3;
        }


        // Controlla collisione con il blocco sotto

        // Aggiorna la posizione della bolla
        updateLocation(x, y);
    }

}
