package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import GAMESTATEMANAGER.PlayState;
import MODEL.Block;
import MODEL.Entity;
import MODEL.Level;
import MODEL.Player;
import VIEW.MainFrame;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Enemy extends Observable implements Entity {

    int x;
    int y;
    int points;
    boolean enraged;
    int speed;
    boolean facingRight;
    boolean isJumping;
    boolean bubbled;
    GameStateManager gsm;
    Player player;
    Level currentLevel;
    Rectangle hitbox;
    boolean isDead;
    boolean explodes;
    // ai pathfinding
    List<Node> shortestPath = new ArrayList<>();
    // physics
    boolean onFloor;
    float airSpeed = 0f;

    Timer rageTimer;
    Timer deathTimer;
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
        this(0,0,true,gsm);
    }
    public Enemy(int x, int y, boolean facingRight, GameStateManager gsm){
        this.x = x;
        this.y = y;
        enraged = false;
        isDead = false;
        this.facingRight = facingRight;
        this.gsm = gsm;
        currentLevel = gsm.getCurrentLevel();
        player = gsm.getCurrentPlayer();
        rageTimer = new Timer(10000, e ->updateAction(Action.RAGE));
        rageTimer.setRepeats(false);

        deathTimer = new Timer(600, e -> {
            explodes = true;
            removeEnemy();
        }); // remove enemy and start pom animation in bubble
        hitbox = new Rectangle(x, y, 32, 32);
        shortestPath = new ArrayList<>();
    }
    //Ricordare di implementare le classi specifiche non astratte
    //dei nemici

    public void onPlayer(){
        if (hitbox.intersects(player.getHitbox())){
            if (bubbled) {
                updateAction(Action.DIE);
            }
            else{
                player.updateAction(Action.HURT);
            }
        }
    }
    //  pathfinding
    public void chasePlayer() {
        if(!isOnFloor()) {
            updateAction(Action.MOVE_VERTICALLY);
            return;
        }
        if(shouldRetracePath() || shortestPath.isEmpty()){
            findShortestPath();
            System.out.println("path: ");
            for(Node node: shortestPath){
                System.out.println(node.x + " " + node.y);
            }
        }
        else {
            Node nextNode = shortestPath.get(0);
            //System.out.println("nextNode " + nextNode.x +" "+ nextNode.y);
            if (isAtNode(nextNode)) {
                shortestPath.remove(0);
            }
            if (nextNode.y < y) {
                updateAction(Action.JUMP);
            }
            else {
                    if (nextNode.x < x) {
                        System.out.println("Moving to x: " + nextNode.x + " y: " + nextNode.y + " from x: " + x + " y: " + y);
                        updateAction(Action.MOVE_LEFT);
                        onFloor = false;
                    } else {
                        updateAction(Action.MOVE_RIGHT);
                        onFloor = false;
                    }
            }
        }
    }


    private boolean isAtNode(Node node){

        return Math.abs(x - node.x) < speed && Math.abs(y - node.y) < speed;
    }
    private String getKey(int x, int y){
        return x + "," + y;
    }
    public void findShortestPath() {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        HashMap<String, Node> openMap = new HashMap<>(); // "x,y" -> Node
        HashSet<String> closed = new HashSet<>(); // "x,y"

        Node start = new Node(x, y, 0, Math.abs(player.getX() - x) + Math.abs(player.getY() - y), null);
        Node end = new Node(player.getX(), player.getY(), 0, 0, null);
        open.add(start);
        openMap.put(getKey(start.x, start.y), start);

        while (!open.isEmpty()) {
            Node current = open.poll(); // get lowest F
            openMap.remove(getKey(current.x, current.y));
            closed.add(getKey(current.x, current.y));
            if (Math.abs(current.x - end.x) < speed && Math.abs(current.y - end.y) < speed){
                shortestPath = retracePath(start, current);
                return;
            }
            for (Node neighbor : getNeighbors(current)) {
                String neighborKey = getKey(neighbor.x, neighbor.y);
                if (closed.contains(neighborKey)) {
                    continue;
                }
                int distanceToNeighbor = current.g + getDistance(current, neighbor);
                boolean isBetterPath = !openMap.containsKey(neighborKey) || distanceToNeighbor < neighbor.g;
                if (isBetterPath) {
                    neighbor.g = distanceToNeighbor;
                    neighbor.h = getDistance(neighbor, end);
                    neighbor.parent = current;
                    if (!openMap.containsKey(neighborKey)) {
                        open.add(neighbor);
                        openMap.put(neighborKey, neighbor);
                    }
                }
            }
        }
    }
    private List<Node> getNeighbors(Node node){
        // distance player to block under player = 32 ( 2 blocks )
        // distance player to new player position on block above it = 80 ( 5 blocks )
        // distance player to block above it = 48 ( 3 blocks )
        List<Node> neighbors = new ArrayList<>();
            int[][] horizontalDirections = {{speed,0},{-speed,0}};
            for(int[] direction: horizontalDirections){
                int newX = node.x +direction[0];
                int newY = node.y + Block.HEIGHT*2 ;
                if (newX < 0 || newX >= MainFrame.FRAME_WIDTH || newY < 0 || newY >= MainFrame.FRAME_HEIGHT){
                    continue;
                }
                if(isSolidTile(newX,newY)){
                    neighbors.add(new Node(newX, node.y, 0,0,node));
                }
                else{

                    // fall down
                    for(int i = 3; i< currentLevel.getPattern().length; i++){
                        if(isSolidTile(newX, node.y + Block.HEIGHT*i)){
                            neighbors.add(new Node(newX, node.y + Block.HEIGHT*(i-2),0,0,node));
                            break;
                        }
                    }
                }
            }
            //UP
        if(isSolidTile(node.x,node.y-Block.HEIGHT*3)){
            neighbors.add(new Node(node.x,node.y- Block.HEIGHT*5,0,0,node));
        }
      return neighbors;
    }
    private int getDistance(Node a, Node b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    private List<Node> retracePath(Node start, Node end){
        List<Node> path = new ArrayList<>();
        while(end != start && end != null){
            path.add(end);
            end = end.parent;
        }
        if(Math.abs(end.x - start.x) < speed && Math.abs(end.y - start.y) < speed){
            path.add(start);
        }
        Collections.reverse(path);
        if (path.isEmpty()){
            return new ArrayList<>();
        }
        return path;
    }

    private boolean shouldRetracePath(){
        if(shortestPath.isEmpty()){
            return true;
        }
        return Math.abs(player.getX() - shortestPath.get(shortestPath.size() - 1).x) > 20 || Math.abs(player.getY() - shortestPath.get(shortestPath.size() - 1).y) > 20;
    }
    // tile collision

    private boolean isNotSolid(){
        if(airSpeed<0 && !(this.x+ airSpeed <0 || this.x+ airSpeed > 800 || this.y+ airSpeed <0 || this.y+ airSpeed > 600)){
            return true;
        }
        return false;
    }

    public boolean isColliding(int x, float y) {
        int left = x;
        int right = x +32;
        float top = y;
        float bottom = y + 32;

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
        return tileX >= 0 && tileX < currentLevel.getPattern()[0].length &&
            tileY >= 0 && tileY < currentLevel.getPattern().length &&
            currentLevel.isItSolidBlock(tileY, tileX);

    }

    public void setPlayer(Player player){
        this.player = player;
    }
    public void setCurrentLevel(Level currentLevel){
        this.currentLevel = currentLevel;
    }
    public int getPoints(Enemy enemy){
        return points;
    }
    public boolean getFacingRight(){
        return facingRight;
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
                isJumping = true;
                if(isOnFloor()){
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_VERTICALLY:
                // if player is in block (error handling), if player isn't colliding with block, if player is jumping up and hitting a non wall block
                if(!isColliding(x, y+airSpeed) || isNotSolid()) {
                    this.y += airSpeed;
                    hitbox.setLocation(x, y);
                    airSpeed += gravity;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                //player is falling to the ground
                else if(isColliding(x, y+airSpeed) && airSpeed > 0){
                    isJumping = false;
                    airSpeed = 0;
                    onFloor = true;
                    notifyObservers(Action.IDLE);
                }
                // when player hits something fall down
                else{
                    isJumping = false;
                    airSpeed = 0;
                    onFloor = false;
                    notifyObservers(Action.MOVE_VERTICALLY);
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
                    hitbox.setLocation(this.x,this. y);
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
                    hitbox.setLocation(this.x, this.y);
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
                System.out.println("X: " + getX() + "Y: " + getY());
                bubbled = true;
                rageTimer.start();
                notifyObservers(Action.BUBBLED);
                break;
            case DIE:
                // comportamenti
                isDead = true;
                deathTimer.start();
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
public Rectangle getHitbox(){
        return hitbox;
}
    public void die(){
    }
    public void updatePosition(){
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }

    }

    //metodi per fare i test per quando il nemico è bubbled, Tiff poi cambiali/toglili se serve, basta che me lo
    //scrivi poi sul commit su Git
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox.setLocation(x, y);
    }
    public void setBubbled(boolean bubbled){
        this.bubbled = bubbled;
    }
    public boolean isBubbled() {return bubbled;}
    // public void updateAction(Action action){
    //implementazione specifica
    // }
    // private void updatePosition(){
    // A* search algorithm
    // uses UpdateAction
    //  }

    public boolean isDead() {return isDead;}
    public void removeEnemy(){
        currentLevel.removeEnemy(this);
    }
    public boolean isExploded() {return explodes;}
    public void setExploded(boolean bool) {explodes = bool;}
}
