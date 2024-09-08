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
    boolean isJumping;
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
        else{
            chasePlayer();
            onPlayer();
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
    public void chasePlayer() {
        if(!isOnFloor()) { // fall
            updateAction(Action.MOVE_VERTICALLY);
            return;
        }
        if(shouldRetracePath() || shortestPath.isEmpty()){
            findShortestPath();
        }
        else {
            Node nextNode = shortestPath.get(0);
            if (isAtNode(nextNode, x, y)) {
                shortestPath.remove(0);
            }

            if (nextNode.y < y) { // go up
                updateAction(Action.JUMP);
            } else {
                // horizontal movement
                if (nextNode.x < x) {
                    facingRight = false;
                } else if (nextNode.x > x) {
                    facingRight = true;
                }
                onFloor = nextNode.y == y;
                updateAction(Action.WALK);
            }
        }
    }


    boolean isAtNode(Node node, int targetX, int targetY){

        return node.x/Block.WIDTH == targetX/Block.WIDTH && node.y/Block.HEIGHT == targetY/Block.HEIGHT;
    }
    private String getKey(int x, int y){
        return x + "," + y;
    }
    public void findShortestPath() {
        shortestPath.clear();
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
    List<Node> getNeighbors(Node node){
        // distance player to block under player = 32 ( 2 blocks )
        // distance player to new player position on block above it = 80 ( 5 blocks )
        // distance player to block above it = 48 ( 3 blocks )
        List<Node> neighbors = new ArrayList<>();
        int[][] horizontalDirections = {{speed,0},{-speed,0}};
        for(int[] direction: horizontalDirections){
            int newX = node.x +direction[0];
            int newY = node.y + Block.HEIGHT*2 ;
            if (newX < 17 || newX > MainFrame.FRAME_WIDTH || newY < 17 || newY > MainFrame.FRAME_HEIGHT){
                continue;
            }
            if(isSolidTile(newX,newY) ){  // add !isColliding(newX,node.y) after fixing image size
                neighbors.add(new Node(newX, node.y, 0,0,node));
            }
            else{
                // fall down
                for(int i = 3; i< currentLevel.getPattern().length; i++){
                    if( isSolidTile(newX, node.y + Block.HEIGHT*i)){
                        neighbors.add(new Node(newX, node.y + Block.HEIGHT*(i-2),0,0,node));
                        break;
                    }
                }
            }
        }
            //UP
        if(isSolidTile(node.x,node.y-Block.HEIGHT*3) && isSolidTile(node.x/Block.WIDTH, (node.y-Block.HEIGHT*3)/Block.HEIGHT)){
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
            return true;
        }
        return Math.abs(player.getX() - shortestPath.getLast().x) > 70 || Math.abs(player.getY() - shortestPath.getLast().y) > 100;
    }
    // tile collision

    boolean isNotSolid(){
        if(airSpeed<0 && !(this.x+ airSpeed <0 || this.x+ airSpeed > MainFrame.FRAME_WIDTH || this.y+ airSpeed <0 || this.y+ airSpeed >MainFrame.FRAME_HEIGHT) && isSolidTile(x/Block.WIDTH, y/Block.HEIGHT)){
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
        return tileX >=0 && tileX < currentLevel.getPattern()[0].length &&
            tileY >= 0 && tileY <= currentLevel.getPattern().length-1 &&
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
                if(isOnFloor()){
                    isJumping = true;
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_VERTICALLY:
                // if player is in block (error handling), if player isn't colliding with block, if player is jumping up and hitting a non wall block
                verticalMovement();
                break;
            case WALK:
               walk();
                hitbox.setLocation(x,y);
                if (enraged) {
                    notifyObservers(Action.RAGE);
                } else {
                    notifyObservers(Action.WALK);
                }
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
    void verticalMovement(){
        if(!isColliding(x, y+airSpeed) || isNotSolid()) {
            if (this.y> MainFrame.FRAME_HEIGHT-5){ // top and bottom connected
                this.y = 0;
            }
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
    }
    void walk(){
        if (facingRight) {
            // Moving right
            if (!isColliding(x + speed, y)) {
                if (isOnFloor()) {
                    this.x += speed - 1;
                    if (!isSolidTile(x, y + Block.HEIGHT * 2)) {
                        updateAction(Action.MOVE_VERTICALLY);
                    }
                } else {
                    this.x += airSpeed;
                }
                hitbox.setLocation(this.x, this.y);
            }
        } else {
            // Moving left
            if (!isColliding(x - speed, y)) {
                if (isOnFloor()) {
                    this.x -= (speed - 1);
                    if (!isSolidTile(x, y + Block.HEIGHT * 2)) {
                        updateAction(Action.MOVE_VERTICALLY);
                    }
                } else {
                    this.x -= airSpeed;
                }
                hitbox.setLocation(this.x, this.y);
            }
        }
    }
    void bubbled(){
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);
    }
    void idle(){
        notifyObservers(Action.IDLE);
    }

    public boolean isOnFloor() {
        return onFloor;
    }
    //to be overriden
    public void rage(){
        enraged = true;
        speed *= 2;
        updateAction(Action.IDLE);
    }
    void shoot(){};
    void attack(){
        onFloor = true;
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
}
