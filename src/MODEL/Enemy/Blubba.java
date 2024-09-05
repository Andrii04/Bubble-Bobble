package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Blubba extends Enemy {

    //vola
    //si muova diagonalmente e colpito un muro gira 90 gradi

    private boolean goUp;
    private final int points = 5000;

    public Blubba( int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 2;
    }
    public Blubba( GameStateManager gsm){
        this( 0, 0, true, gsm);
    }

    public void chasePlayer(){
        if(shouldRetracePath() || shortestPath.isEmpty()){
            findShortestPath();
        }
        else{
            Node nextNode = shortestPath.get(0);
            if(isAtNode(nextNode,x,y)){
                shortestPath.remove(0);
            }
            if(nextNode.y>y) {
                goUp = false;
                if (nextNode.x > x) {
                    updateAction(Action.MOVE_RIGHT);
                } else if (nextNode.x < x) {
                    updateAction(Action.MOVE_LEFT);
                }
            }
            else{
                goUp = true;
                if(nextNode.x>x){
                    updateAction(Action.MOVE_RIGHT);
                }
                else if(nextNode.x<x){
                    updateAction(Action.MOVE_LEFT);
                }
            }
        }
    }
    List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1,-1}, {1,1}, {1,-1}, {-1,1}};
        for(int[] direction: directions){
            int newX = node.x + direction[0]* Block.WIDTH;
            int newY = node.y  + direction[1]* Block.HEIGHT;
            if (newX < 17 || newX > MainFrame.FRAME_WIDTH || newY < 17 || newY > MainFrame.FRAME_HEIGHT){
                continue;
            }
            if(!isSolidTile(newX,newY)){
                neighbors.add(new Node(newX,newY, 0,0,node));
            }
            else{
                if(isSolidTile(node.x,newY- Block.HEIGHT) || isSolidTile(node.x, newY+Block.HEIGHT)){
                    neighbors.add(new Node(newX,-newY, 0,0,node));
                }
                else if (isSolidTile(newX-Block.WIDTH, node.y) || isSolidTile(newX+Block.WIDTH, node.y)){
                    neighbors.add(new Node(-newX,newY, 0,0,node));
                }
            }
        }
        return neighbors;
    }

    public void updateAction(Action action){
        switch (action){
            case MOVE_LEFT:
                x -= speed;
                if(goUp){
                    y -= speed;
                }
                else{
                    y += speed;
                }
                hitbox.setLocation(x,y);
                if(enraged){
                    notifyObservers(Action.RAGE);
                }
                else{
                    notifyObservers(Action.MOVE_LEFT);
                }
                break;
            case MOVE_RIGHT:
                x += speed;
                if(goUp){
                    y -= speed;
                }
                else{
                    y += speed;
                }
                hitbox.setLocation(x,y);
                if(enraged){
                    notifyObservers(Action.RAGE);
                }
                notifyObservers(Action.MOVE_RIGHT);
                break;
            case RAGE:
                rage();
                notifyObservers(Action.RAGE);
                break;
            case BUBBLED:
                bubbled = true;
                rageTimer.start();
                notifyObservers(Action.BUBBLED);
                break;
            case DIE:
                dead = true;
                die();
                player.setPunteggio(player.getPunteggio() + points);
                deathTimer.start();
                notifyObservers(Action.DIE);
                break;
            default:
                if(facingRight){
                    notifyObservers(Action.MOVE_RIGHT);
                }
                else{
                    notifyObservers(Action.MOVE_LEFT);
                }
                break;
        }
    }

    public void die(){
        player.setPunteggio(player.getPunteggio() + points);
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
    public void rage(){
        // comportamenti
    }
}
