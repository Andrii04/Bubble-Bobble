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


    public Bubble() {
        this.bubbleView = new BubbleView(this);
        gsm = GameStateManager.getInstance();
        currentLevel = gsm.getCurrentLevel();

        firing = false;
        floating = false;
        encapsulate = false;
        exploding = false;
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

    public boolean isSolidTile(int i, int j) {
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

    //da rivedere la collision per le bolle
    public boolean isColliding(int x, int y) {

        for (int i = x; i < x+100; i++) {
            for (int j = y-100; j < y+100; j++) {
                if (isSolidTile(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
