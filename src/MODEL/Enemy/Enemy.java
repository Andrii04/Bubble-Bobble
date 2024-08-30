package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import GAMESTATEMANAGER.PlayState;
import MODEL.Block;
import MODEL.Entity;
import MODEL.Level;
import MODEL.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

public abstract class Enemy extends Observable implements Entity {

     int x;
     int y;
     int points;
     boolean enraged;
     int speed;
     boolean facingRight;
     GameStateManager gsm;
     Player player;
     Level currentLevel;

     // ai pathfinding
    List<Node> shortestPath = new ArrayList<Node>();
    // physics
     boolean onFloor;
     float airSpeed = 0f;

    Timer ragetimer;

    // A* search algorithm for pathfinding
    class Node{
        int x;
        int y;
        int g;
        int h;
        Node parent;
        public Node(int x, int y, int g, int h, Node parent){
            this.x = x;
            this.y = y;
            this.g = g; // g cost = distance from start point
            this.h = h; // h cost = distance from end point
            this.parent = parent;
        }
        public int getF(){
            return g+h; // f cost = total cost
        }
    }

    public Enemy(GameStateManager gsm){
        this.x= 0;
        this.y=0;
        enraged = false;
        facingRight = true;
        this.gsm = gsm;
        currentLevel = gsm.getCurrentLevel();
        player = gsm.getCurrentPlayer();
    }
    public Enemy(int x, int y, boolean facingRight, GameStateManager gsm){
        this.x = x;
        this.y = y;
        enraged = false;
        this.facingRight = facingRight;
        this.gsm = gsm;
    }
    //Ricordare di implementare le classi specifiche non astratte
    //dei nemici


    private void chasePlayer(){
        if (shortestPath.isEmpty()){
            updateAction(Action.IDLE);
        }
        Node nextNode = shortestPath.get(0);
        if(isAtNode(nextNode)){
            shortestPath.remove(0);
        }
        if(nextNode.y<y){
            updateAction(Action.JUMP);

        }
    }

    private boolean isAtNode(Node node){
        return Math.abs(node.x - x) < speed && Math.abs(node.y - y) < speed;
    }
    public boolean isColliding(int x, float y) {
        int left = x+10;
        int right = x +38;
        float top = y+10;
        float bottom = y + 38;

        int bottomInt = (int)bottom;
        int topInt = (int)top;

        for (int i = left; i < right; i++) {
            for (int j = topInt; j < bottomInt; j++) {
                if (isSolidTile(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isSolidTile(int x ,int y){
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        if(tileX >= 0 && tileX < currentLevel.getPattern()[0].length && tileY >= 0 && tileY < currentLevel.getPattern().length){
            if(currentLevel.getBlockInt(tileY, tileX) >0){
                // test
                System.out.println("Colliding" + " " + tileX + " " + tileY + " " + currentLevel.getBlockInt(tileY, tileX) + " " + currentLevel.isItSolidBlock(tileY, tileX) + " " + currentLevel.getPattern()[tileY][tileX] + " " + currentLevel.getSolidCheckPattern()[tileY][tileX] + " x: " + x + " y: " + y);
                return true;
            }
            else{
                return false;

            }
        }
        return true;
    }

    public int getPoints(Enemy enemy){
     return points;
    }

    public void notifyObservers(Action action) {
        setChanged();
        super.notifyObservers(action);
    }

    public Player getPlayer(){
        return player;
    }
    public void updateAction(Action action){
        switch(action){
            case JUMP:
                if(isOnFloor()){
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_VERTICALLY:
                if(!isColliding(x, y+airSpeed)) {
                    this.y += airSpeed;
                    airSpeed += gravity;
                    if(facingRight){
                        notifyObservers(Action.MOVE_RIGHT);
                    }
                    else{
                        notifyObservers(Action.MOVE_LEFT);
                    }
                }
                else if(isColliding(x, y+airSpeed) && airSpeed > 0){
                    airSpeed = 0;
                    onFloor = true;
                    if (facingRight){
                        notifyObservers(Action.MOVE_RIGHT);
                    }
                    else{
                        notifyObservers(Action.MOVE_LEFT);
                    }
                }
                else{
                    airSpeed = 0;
                    onFloor = false;
                    if (facingRight){
                        notifyObservers(Action.MOVE_RIGHT);
                    }
                    else{
                        notifyObservers(Action.MOVE_LEFT);
                    }
                }
                break;
            case MOVE_LEFT:
                if(!isColliding(x-speed, y)){
                    if(isOnFloor()){
                        this.x -= speed;
                    }
                    else{
                        this.x -= airSpeed;
                    }
                }
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                if(!isColliding(x+speed, y)){
                    if(isOnFloor()) {
                        this.x += speed;
                    }
                    else{
                        this.x += airSpeed;
                    }
                }
                facingRight = true;
                notifyObservers(Action.MOVE_RIGHT);
                break;
            case ATTACK:
                attack();
                notifyObservers(Action.ATTACK);
                break;
            case RAGE:
                rage();
                notifyObservers(Action.RAGE);
                break;
            case BUBBLED:
                // comportamenti
                notifyObservers(Action.BUBBLED);
                break;
            case DIE:
                // comportamenti
                notifyObservers(Action.DIE);
                break;
            default: // idle
                if (facingRight) {
                    notifyObservers(Action.MOVE_RIGHT);
                }
                else {
                    notifyObservers(Action.MOVE_LEFT);
                }
            break;
        }
    }

    public boolean isOnFloor() {
        return onFloor;
    }
    //to be overriden
    public void rage(){
    }
    // to be overridden
    public void attack(){
        //implementazione specifica
    }
    public void updatePosition(){
    }
   // public void updateAction(Action action){
        //implementazione specifica
   // }
   // private void updatePosition(){
    // A* search algorithm
    // uses UpdateAction
  //  }


}
