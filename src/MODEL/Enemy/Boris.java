package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.Fireball;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Boris extends Enemy {

    //simile a bonzo nei movimenti
    //può lanciare rocce che uccidono il nemico al contatto
    //le rocce si sbriciolano a contatto co muri e piattaforme
    //salta e ha buon movimento


    private final int points=2000;

    public Boris( int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 3;
    }
    public Boris( GameStateManager gsm){
        this( 0, 0, true, gsm);
    }

    public void move(){
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        if (!isBubbled() && !isDead()) {
            if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            else{onPlayer();
                chasePlayer();
            }
        }
    }
    @Override
    public void chasePlayer() {
        if(shortestPath.isEmpty()){
            updateAction(Action.IDLE);
        }
        if(!isOnFloor()){
            updateAction(Action.MOVE_VERTICALLY);
            return;
        }
        else if(player.getY() != y && attackTimer.isRunning()){
            attackTimer.stop();
        }
        if(shouldRetracePath()){
            findShortestPath();
        }
        else{
            Node nextNode = shortestPath.get(0);
            if(isAtNode(nextNode,x,y)){
                shortestPath.remove(0);
            }
            if(nextNode.y<y) {
                updateAction(Action.JUMP);}
                else{
                    if (nextNode.x < x) {
                        if(isSolidTile(nextNode.x,nextNode.y)){
                            updateAction(Action.IDLE);
                            return;
                        }
                        updateAction(Action.WALK);
                        if (nextNode.y == y) {
                            onFloor = true;
                        } else {
                            onFloor = false;
                        }
                    }
                    else if (nextNode.x > x){
                        if(isSolidTile(nextNode.x+Block.WIDTH,nextNode.y)){
                            updateAction(Action.IDLE);
                            System.out.println("solid tile");
                            return;
                        }
                        updateAction(Action.WALK);
                        if(nextNode.y == y){
                            onFloor = true;
                    }
                        else{
                            onFloor = false;
                        }
                }

            }
        }
    }
    boolean shouldRetracePath(){
        if (shortestPath.isEmpty()){
            return true;
        }
        return Math.abs(player.getY() - shortestPath.getLast().y )>80;
    }

    void shoot(){
        Fireball fireball = new Fireball();
        fireball.setBoris(this);
        fireball.setPlayer(player);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
    }
    void idle(){
        if(isSolidTile(x+Block.WIDTH, y)){
            facingRight = false;
        }
        else if(isSolidTile(x-Block.WIDTH, y)){
            facingRight = true;
        }
        updateAction(Action.WALK);
    }
    void bubbled(){
        if(attackTimer.isRunning()){
            attackTimer.stop();
        }
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);

    }
    void die(){
        if(attackTimer.isRunning()){
            attackTimer.stop();
        }
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int compareLocation(Entity entity) {
        return 0;
    }

    @Override
    public void spawn() {

    }

    @Override
    public int getPoints(Enemy enemy){
        return points;
    }

}
