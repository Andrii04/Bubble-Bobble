package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import GAMESTATEMANAGER.PlayState;
import MODEL.Block;
import MODEL.Entity;
import MODEL.Level;
import MODEL.Player;

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
        if (shortestPath.isEmpty()) {
            updateAction(Action.IDLE);
            return;
        }
        if(shouldRetracePath()){
            findShortestPath();
        }
        else {
            Node nextNode = shortestPath.get(0);
            //System.out.println("nextNode " + nextNode.x +" "+ nextNode.y);
            if (isAtNode(nextNode)) {
                shortestPath.remove(0);
            }
            if (nextNode.y < y) {
                updateAction(Action.JUMP);
            } else if (nextNode.y > y) {
                updateAction(Action.MOVE_VERTICALLY);
            } else {
                if (nextNode.x < x) {
                    updateAction(Action.MOVE_LEFT);
                } else {
                    updateAction(Action.MOVE_RIGHT);
                }
            }
        }
    }


    private boolean isAtNode(Node node){

        return Math.abs(node.x - x) <= 50&& Math.abs(node.y - y) <= 50;
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
            if (current.x == end.x && current.y == end.y) {
                shortestPath = retracePath(start, current);
                return;
            }
            for (Node neighbor : getNeighbors(current)) {
                String neighborKey = getKey(neighbor.x, neighbor.y);
                System.out.println("Neighbor: (" + neighbor.x + ", " + neighbor.y + ")");
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
            int[][] horizontalDirections = {{1,0},{-1,0}};
            for(int[] direction: horizontalDirections){
                int newX = node.x +Block.WIDTH*direction[0];
                int newY = node.y + Block.HEIGHT*2 ;
                if(isSolidTile(newX,newY)){
                    neighbors.add(new Node(newX, node.y, 0,0,node));
                }
                else{
                    // fall down
                    for(int i = 1; i< currentLevel.getPattern().length; i+=3){
                        if(isSolidTile(newX, node.y + Block.HEIGHT*i)){
                            neighbors.add(new Node(newX, node.y + Block.HEIGHT*(i+2),0,0,node));
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
        Node current = end;
        while(current != start && current != null){
            path.add(current);
            current = current.parent;
        }
        if(current == start){
            path.add(start);
        }
        Collections.reverse(path);
        if (path.isEmpty() || path.get(0) != start){
            return new ArrayList<>();
        }
        System.out.println("Shortest Path:");
        for (Node node : path) {
            System.out.println("Node: (" + node.x + ", " + node.y + ")");
        }
        return path;
    }

    private boolean shouldRetracePath(){
        if(shortestPath.isEmpty()){
            return true;
        }
        Node nextNode = shortestPath.get(0);
        return Math.abs(player.getX() - shortestPath.get(shortestPath.size() - 1).x) > 50 || Math.abs(player.getY() - shortestPath.get(shortestPath.size() - 1).y) > 50;
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
    public void setShortestPath(List<Node> shortestPath){
        this.shortestPath = shortestPath;
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
