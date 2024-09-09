package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Entity;
import MODEL.Level;
import MODEL.Player;
import VIEW.MainFrame;

import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Enemy extends Observable implements Entity {

    GameStateManager gsm;
    Player player;
    Level currentLevel;

    int x;
    int y;
    int points;
    boolean enraged;
    int speed;
    boolean facingRight;
    boolean bubbled;
    boolean dead;
    boolean explodes;
    Rectangle hitbox;

    // physics
    boolean onFloor;
    float airSpeed = 0f;

    Timer rageTimer;
    Timer deathTimer;
    Timer attackTimer;
    // A* search algorithm for pathfinding
    List<Node> shortestPath;
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
        dead = false;
        this.facingRight = facingRight;
        this.gsm = gsm;
        currentLevel = gsm.getCurrentLevel();
        player = gsm.getCurrentPlayer();
        rageTimer = new Timer(100, e ->updateAction(Action.RAGE));
        rageTimer.setRepeats(false);

        attackTimer = new Timer(1000, e -> shoot());
        attackTimer.setRepeats(true);

        deathTimer = new Timer(600, e -> {
            explodes = true;
            removeEnemy();
        });
        hitbox = new Rectangle(x, y, 32, 32);
        shortestPath = new ArrayList<>();
    }

    public void move(){
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        else if(!isDead() && !isBubbled()){
            onPlayer();
            chasePlayer();
        }
    }

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
    void chasePlayer() {
        if (!isOnFloor() || (!isSolidTile(x,y+Entity.HEIGHT+1) && !isSolidTile(x+Entity.WIDTH+1,y+Entity.HEIGHT+1))) { // fall
            onFloor = false;
            updateAction(Action.MOVE_VERTICALLY);
            return;
        }
        if(!player.isOnFloor()){
            updateAction(Action.IDLE);
            return;
        }
        if (isOnFloor() && shouldRetracePath()) {
            findShortestPath();
            return;
        }
        else {
            onFloor = isSolidTile(x, y + Entity.HEIGHT + 1);
            Node nextNode = shortestPath.get(0);
            if (isAtNode(nextNode, x, y)) {
                shortestPath.remove(0);
            }
            if(nextNode.x == x && nextNode.y > y){
                updateAction(Action.MOVE_VERTICALLY);
                return;
            }
            facingRight= nextNode.x > x;
            if (nextNode.x < x) {
                facingRight = false;
                updateAction(Action.WALK);
                return;
            }
            if (nextNode.x > x) {
                facingRight = true;
                updateAction(Action.WALK);
                return;
            }
            if(nextNode.x == x && nextNode.y < y){
                updateAction(Action.JUMP);
            }
        }
    }

    boolean isAtNode(Node node, int targetX, int targetY){

        return node.x/Block.WIDTH == targetX/Block.WIDTH && node.y/Block.HEIGHT == targetY/Block.HEIGHT;
    }
    String getKey(int x, int y){
        return x + "," + y;
    }
    void findShortestPath() {
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
            if (isAtNode(current, end.x, end.y)){
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
     List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{speed, 0}, {-speed, 0}}; // Horizontal movements: right and left

        // Horizontal neighbors
        for (int[] direction : directions) {
            int newX = node.x + direction[0];
            int newY = node.y;

            if (isWithinBounds(newX, newY)) {
                // jumping scenario
                if (isSolidTile(node.x+ direction[0]*3, newY -Entity.HEIGHT-Block.HEIGHT +1) && !isSolidTile(newX, newY - Entity.HEIGHT*2 +1)) {
                    neighbors.add(new Node(node.x + direction[0]*3, node.y - Entity.HEIGHT*2 - Block.HEIGHT, 0, 0, node));
                }
                // horizontal movement: check if there is a solid tile below and if there isn't a solid tile in front of iit
                if (isSolidTile(newX, newY + Entity.HEIGHT+1) && !isSolidTile(newX,newY)) {
                    neighbors.add(new Node(newX, newY, 0, 0, node));

                }
                else if(!isSolidTile(newX,newY+Entity.HEIGHT+1)) {
                    // Falling down scenario: scan downwards to find the next platform
                    for (int i = 3; i < currentLevel.getPattern().length-1; i++) {
                        int fallY = node.y + Block.HEIGHT * i +1 ;
                        if (isSolidTile(newX, fallY)) {
                            // entity positioned above block
                            if(facingRight){
                                neighbors.add(new Node(node.x+direction[0]*3, fallY - Entity.HEIGHT, 0, 0, node));
                                break;}
                            else{
                                neighbors.add(new Node(newX*3 + Entity.WIDTH, fallY - Entity.HEIGHT, 0, 0, node));
                                break;

                            }
                        }
                    }
                }
            }
        }
        return neighbors;
    }
    boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < MainFrame.FRAME_WIDTH-Block.HEIGHT-Entity.HEIGHT && y >= 0 && y < MainFrame.FRAME_HEIGHT-Block.HEIGHT-Entity.HEIGHT;
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
        if(end == start){
            path.add(start);
        }
        Collections.reverse(path);
        if (path.isEmpty()){
            return new ArrayList<>();
        }
        return path;
    }
     boolean shouldRetracePath(){
        if(shortestPath.isEmpty()){
            updateAction(Action.IDLE);
            return true;
        }
        return Math.abs(player.getX() - shortestPath.getLast().x) > 70 || Math.abs(player.getY() - shortestPath.getLast().y) > 100;
    }
    // tile collision

    // entity can go through tile if isNotSolid
    private boolean isNotSolid(){
       if(airSpeed<0 && y+airSpeed >= Block.HEIGHT){
            return true;
        }
        return false;
    }

    boolean isColliding(int x, float y) {
        int leftTile = x; // Leftmost tile
        int rightTile = x + 2 * Block.WIDTH; // Rightmost tile
        int topTile = (int) y; // Topmost tile
        int bottomTile = (int) y + 2 * Block.HEIGHT; // Bottommost tile
        return isSolidTile(rightTile, topTile) || isSolidTile(leftTile, topTile) || isSolidTile(leftTile, bottomTile) || isSolidTile(rightTile, bottomTile);
    }
    // if a given position's tile is solid
    boolean isSolidTile(int x ,int y){
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        return tileX >=0 && tileX < currentLevel.getPattern()[0].length &&
                tileY >= 0 && tileY < currentLevel.getPattern().length &&
                currentLevel.isItSolidBlock(tileY, tileX);

    }

    public void updateAction(Action action){

        switch(action){
            case JUMP:
                if (!currentLevel.isClock()) {
                    if(isOnFloor()){
                        onFloor = false;
                        airSpeed = jumpSpeed;
                        updateAction(Action.MOVE_VERTICALLY);
                    }
                }
                break;
            case MOVE_VERTICALLY:
                if (!currentLevel.isClock()) {
                    verticalMovement();
                }
                break;
            case WALK:
                if (!currentLevel.isClock()) {
                    walk();
                    hitbox.setLocation(x, y);
                    if (enraged) {
                        notifyObservers(Action.RAGE);
                    } else {
                        notifyObservers(Action.WALK);
                    }
                }
                break;
            case ATTACK:
                attack();
                notifyObservers(Action.ATTACK);
                break;
            case RAGE:
                rage();
                updateAction(Action.WALK);
                break;
            case BUBBLED:
                // comportamenti
                bubbled();
                break;
            case DIE:
                // comportamenti
                die();
                dead = true;
                player.setPunteggio(player.getPunteggio() + points);
                deathTimer.start();
                notifyObservers(Action.DIE);
                break;
            default: // idle
                idle();
                break;
        }
    }
    void verticalMovement() {
        if (!isColliding(x, y + airSpeed) || isNotSolid()) {
            this.y += airSpeed;
            hitbox.setLocation(x, y);
            airSpeed += gravity;
            notifyObservers(Action.MOVE_VERTICALLY);
        }
        // Wrap-around logic to connect top and bottom
        if (y + airSpeed >= MainFrame.FRAME_HEIGHT - Entity.HEIGHT) {
            this.y = 0;
            hitbox.setLocation(x, y);
            onFloor = false;
            notifyObservers(Action.MOVE_VERTICALLY);
        }
        // Player is falling to the ground
        else if (isColliding(x, y + airSpeed) && airSpeed > 0) {
            if (isSolidTile(x, y) || isSolidTile(x + Entity.HEIGHT, y) || isSolidTile(x + Block.WIDTH, y)) {
                this.y += airSpeed;
                hitbox.setLocation(x, y);
                airSpeed += gravity;
                notifyObservers(Action.MOVE_VERTICALLY);
            } else {
                airSpeed = 0;
                onFloor = true;
                notifyObservers(Action.IDLE);
            }
        }
    }
    void walk(){
        if(isOnFloor()){
            if (facingRight) {
                // Moving right
                if (!isColliding(x + speed, y)) {
                    this.x += speed ;
                    if (!isSolidTile(x, y + Entity.HEIGHT+1)) {
                        updateAction(Action.MOVE_VERTICALLY);
                    }
                }
                else if (isColliding(x + speed, y)) {
                    facingRight = false;
                }
            } else {
                // Moving left
                if (!isColliding(x - speed, y)) {
                    this.x -= speed;
                    if (!isSolidTile(x, y + Entity.HEIGHT+1)) {
                        updateAction(Action.MOVE_VERTICALLY);
                    }
                }
                else if (isColliding(x - speed, y)) {
                    facingRight = true;
                }
            }
            hitbox.setLocation(this.x, this.y);
        }
        else{
            updateAction(Action.MOVE_VERTICALLY);

        }
    }
    void bubbled(){
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);
    }
    void idle(){
       updateAction(Action.WALK);
    }

    public boolean isOnFloor() {
        return onFloor;
    }
    //to be overriden
    void rage(){
        enraged = true;
        speed +=1;
    }
    void shoot(){};
    void attack(){
        if(player.getX() < x){
            facingRight = false;
        }
        else{
            facingRight = true;
        }
        if(!attackTimer.isRunning()) {
            attackTimer.start();
        }
    }
public Rectangle getHitbox(){
        return hitbox;
}
    void die(){
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

    public boolean isDead() {
        return dead;}
    public void removeEnemy(){
        currentLevel.removeEnemy(this);
    }
    public boolean isExploded() {return explodes;}
    public void setExploded(boolean bool) {explodes = bool;}
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
}
