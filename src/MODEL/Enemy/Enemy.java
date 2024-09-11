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

    boolean wave;

    // physics
    boolean onFloor;
    float airSpeed = 0f;

    Timer rageTimer;
    Timer deathTimer;
    Timer attackTimer;

    // pathfinding
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
            this.g = g; // g cost = distanza dal nodo iniziale
            this.h = h; // h cost = distanza dal nodo finale
            this.parent = parent;
        }
        public int getF(){
            return g+h; // f cost
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
        rageTimer = new Timer(4000, e ->updateAction(Action.RAGE));
        rageTimer.setRepeats(false);

        attackTimer = new Timer(1000, e -> shoot());
        attackTimer.setRepeats(true);

        deathTimer = new Timer(600, e -> {
            explodes = true;
            removeEnemy();
        });
        hitbox = new Rectangle(x, y, 32, 32);
        shortestPath = new ArrayList<>();

        wave = false;
    }

    // movimento generale
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

    // quando interseca il player
    public void onPlayer(){
        System.out.println(bubbled);
        if (hitbox.intersects(player.getHitbox())){
            System.out.println("Intersects");
            System.out.println(bubbled);
            if (bubbled) {
                System.out.println("HIT");
                updateAction(Action.DIE);
            }
            else{
                player.updateAction(Action.HURT);
                if(player.getLives()<=0 && attackTimer.isRunning()){
                    attackTimer.stop();

                }
            }
        }
    }

    //  pathfinding
    void chasePlayer() {
        if (!isOnFloor() || (!isSolidTile(x,y+Entity.HEIGHT+1) && !isSolidTile(x+Entity.WIDTH+1,y+Entity.HEIGHT+1))) { // se non è sul pavimento cade
            onFloor = false;
            updateAction(Action.MOVE_VERTICALLY);
            return;
        }
        if(!player.isOnFloor()){ // se il player non è sul pavimento il nemico va in idle
            updateAction(Action.IDLE);
            return;
        }
        if (isOnFloor() && shouldRetracePath()) { // se il nemico è sul pavimento e deve ricalcolare il percorso: ricalcola il percorso
            findShortestPath();
            return;
        }
        else {
            onFloor = isSolidTile(x, y + Entity.HEIGHT + 1); // setta onFloor a true se il nemico è sul pavimento
            Node nextNode = shortestPath.get(0); // prende il prossimo nodo
            if (isAtNode(nextNode, x, y)) { // se il nemico è arrivato al nodo toglie il nodo dalla lista
                shortestPath.remove(0);
            }
            if(nextNode.x == x && nextNode.y > y){ // se il nodo è sotto il nemico allora cade
                updateAction(Action.MOVE_VERTICALLY);
                return;
            }// se il nodo è a destra del nemico allora il nemico guarda a destra e cammina a destra
            if (nextNode.x < x) {
                facingRight = false;
                updateAction(Action.WALK);
                return;
            }
            // se il nodo è a sinistra del nemico allora il nemico guarda a sinistra e cammina a sinistra
            if (nextNode.x > x) {
                facingRight = true;
                updateAction(Action.WALK);
                return;
            }
            // se il nodo è sopra il nemico allora il nemico salta
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
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::getF)); // coda con priorità: nodo con il minor f cost
        HashMap<String, Node> openMap = new HashMap<>(); // "x,y" : Node
        HashSet<String> closed = new HashSet<>(); // "x,y" , contiene solo le chiavi della mappa

        Node start = new Node(x, y, 0, Math.abs(player.getX() - x) + Math.abs(player.getY() - y), null); // posizione corrente
        Node end = new Node(player.getX(), player.getY(), 0, 0, null); // posizione del player
        open.add(start);
        openMap.put(getKey(start.x, start.y), start);

        while (!open.isEmpty()) {
            Node current = open.poll(); // prende il primo nodo ( con il minor f cost)
            openMap.remove(getKey(current.x, current.y));
            closed.add(getKey(current.x, current.y));
            if (isAtNode(current, end.x, end.y)){ // ricorsione si ferma quando il nodo corrente è uguale al nodo finale
                shortestPath = retracePath(start, current);
               return;
            }
            for (Node neighbor : getNeighbors(current)) { // prende i vicini del nodo corrente
                String neighborKey = getKey(neighbor.x, neighbor.y);
                if (closed.contains(neighborKey)) { // se è presente nella lista closed va avanti
                    continue;
                }
                int distanceToNeighbor = current.g + getDistance(current, neighbor); // g cost del vicino
                boolean isBetterPath = !openMap.containsKey(neighborKey) || distanceToNeighbor < neighbor.g; // se il vicino non è presente nella lista open o il g cost è minore allora è il percorso migliore
                if (isBetterPath) { // aggiunge il vicino ( percorso migliore ) alla lista open
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
        int[][] directions = {{speed, 0}, {-speed, 0}}; // sinistra e destra

        // sinistra e destra
        for (int[] direction : directions) {
            int newX = node.x + direction[0];
            int newY = node.y;

            if (isWithinBounds(newX, newY)) {
                // caso in cui c'è un blocco solido sopra a jump distance
                if (isSolidTile(node.x+ direction[0]*3, newY -Entity.HEIGHT-Block.HEIGHT +1) && !isSolidTile(newX, newY - Entity.HEIGHT*2 +1)) {
                    neighbors.add(new Node(node.x + direction[0]*3, node.y - Entity.HEIGHT*2 - Block.HEIGHT, 0, 0, node));
                }
                // caso in cui c'è un blocco solido sotto e non c'è un blocco solido di fronte
                if (isSolidTile(newX, newY + Entity.HEIGHT+1) && !isSolidTile(newX,newY)) {
                    neighbors.add(new Node(newX, newY, 0, 0, node));

                }
                // caso in cui non c'è un blocco solido sotto
                else if(!isSolidTile(newX,newY+Entity.HEIGHT+1)) {
                    // cerca la piattaforma più vicina
                    for (int i = 3; i < currentLevel.getPattern().length-1; i++) {
                        int fallY = node.y + Block.HEIGHT * i +1 ;
                        if (isSolidTile(newX, fallY)) {
                            // aggiunge il nemico sopra la piattaforma
                            // 2 casi per assicurare che il nemico non si incastra al bordo della piattaforma
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
    } // controlla se la posizione è all'interno dei limiti dello schermo
    private int getDistance(Node a, Node b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    private List<Node> retracePath(Node start, Node end){
        // ritorna il percorso più breve = shortestPath
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
     boolean shouldRetracePath(){ // caso in cui il nemico deve ricalcolare il percorso
        if(shortestPath.isEmpty()){ // se non è raggiungibile allora IDLE
            updateAction(Action.IDLE);
            return true;
        } // se il player si allontana di troppo ricalcola
        return Math.abs(player.getX() - shortestPath.getLast().x) > 70 || Math.abs(player.getY() - shortestPath.getLast().y) > 100;
    }



    private boolean isNotSolid(){
       if(airSpeed<0 ){
            return true;
        }
        return false;
    }     // entity può passare per i blocchi solidi se sta saltando
    boolean isColliding(int x, float y) {
        int leftTile = x; // Leftmost tile
        int rightTile = x + 2 * Block.WIDTH; // Rightmost tile
        int topTile = (int) y; // Topmost tile
        int bottomTile = (int) y + 2 * Block.HEIGHT; // Bottommost tile
        return isSolidTile(rightTile, topTile) || isSolidTile(leftTile, topTile) || isSolidTile(leftTile, bottomTile) || isSolidTile(rightTile, bottomTile);
    }     // controlla se entity è in collisione con un blocco solido
    boolean isSolidTile(int x ,int y){
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        return tileX >=0 && tileX < currentLevel.getPattern()[0].length &&
                tileY >= 0 && tileY < currentLevel.getPattern().length &&
                currentLevel.isItSolidBlock(tileY, tileX);

    }     // controlla se il blocco di una certa posizione è solido

    public void updateAction(Action action){

        switch(action){
            case JUMP:
                if (!currentLevel.isClock() && !wave) {
                    if(isOnFloor()){
                        onFloor = false;
                        airSpeed = jumpSpeed;
                        updateAction(Action.MOVE_VERTICALLY);
                    }
                }
                break;
            case MOVE_VERTICALLY:
                if (!currentLevel.isClock() && !wave) {
                    verticalMovement();
                }
                break;
            case WALK:
                if (!currentLevel.isClock() && !wave) {
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
                    wave = false;
                    player.setPunteggio(player.getPunteggio() + points);
                    deathTimer.start();
                    MainFrame.playSound(5);
                    notifyObservers(Action.DIE);
                break;
            default: // idle
                idle();
                break;
        }
    }

    // movimenti e comportamenti dei nemici
    // possono essere implementati in modo diverso per ogni nemico
    void verticalMovement() {
        // ll salto / la caduta
        if (!isColliding(x, y + airSpeed) || isNotSolid()) {
            this.y += airSpeed;
            hitbox.setLocation(x, y);
            airSpeed += gravity;
            notifyObservers(Action.MOVE_VERTICALLY);
        }
        // se cade dal fondo dello schermo riappare in cima
        if (y + airSpeed >= MainFrame.FRAME_HEIGHT - Entity.HEIGHT) {
            this.y = 0;
            hitbox.setLocation(x, y);
            onFloor = false;
            notifyObservers(Action.MOVE_VERTICALLY);
        }
        // condizione per fermare la caduta
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
                    if (!isSolidTile(x-Entity.WIDTH, y + Entity.HEIGHT+1)) {
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
    } // movimento di default
    void rage(){
        bubbled = false;
        enraged = true;
        speed +=1;
    } // comportamento di default rage = aumenta la velocità di uno
    void bubbled(){
        enraged = false;
        bubbled = true;
        rageTimer.start();
        System.out.println("Bubbled");
        notifyObservers(Action.BUBBLED);
    } // uguali per tutti i nemici
    void shoot(){}; // qui si implementano i proiettili
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
    } // comportameto di attacco default per quelli nemici che sparano
    void die(){
    } // override per stoppare timer di attacco di alcuni nemici
    void idle(){
        updateAction(Action.WALK);
    }
    //metodi per fare i test per quando il nemico è bubbled, Tiff poi cambiali/toglili se serve, basta che me lo
    //scrivi poi sul commit su Git
    public void removeEnemy() { // elimina il nemico dalla lista di nemici del livello
        currentLevel.removeEnemy(this);
    }

    public void notifyObservers(Action action) {
        setChanged();
        super.notifyObservers(action);
    }

    // metodi setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        hitbox.setLocation(x, y);
    }
    public void setBubbled(boolean bubbled){
        this.bubbled = bubbled;
    }
    public void setExploded(boolean bool) {explodes = bool;}
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setCurrentLevel(Level currentLevel){
        this.currentLevel = currentLevel;
    }
    public void setEnraged(boolean bool) {enraged = bool;}


    // metodi getters
    public boolean isOnFloor() {
        return onFloor;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public boolean isBubbled() {return bubbled;}
    public boolean isDead() {
        return dead;}
    public int getCurrentLevelInt(){
        return gsm.getCurrentLevelInt();
    }
    public Level getCurrentLevel(){
        return currentLevel;
    }
    public boolean getFacingRight(){
        return facingRight;
    }
    public boolean isExploded() {return explodes;}
    public Player getPlayer(){
        return player;
    }
    public boolean isEnraged() {return enraged;}
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setWave(boolean bool) {wave = bool;}
}
