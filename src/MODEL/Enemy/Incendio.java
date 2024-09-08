package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Entity;

import javax.swing.*;

public class Incendio extends Enemy{

    //cammina veloce
    //scarso nel salto
    //spara palle di fuoco
    //continua a camminare quando spara

    private final int points=3000;


    public Incendio(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight,gsm);
        speed = 4;
    }
    public Incendio(GameStateManager gsm){
        super(gsm);
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
    public void move(){
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        if(!isBubbled() && !isDead()){
            if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            onPlayer();
            chasePlayer();
        }
        }

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
                    if(isSolidTile(nextNode.x+ Block.WIDTH,nextNode.y)){
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

    void shoot(){
        // to do
        System.out.println("Incendio shoot");
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


