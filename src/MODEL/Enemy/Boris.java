package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Fireball;
import MODEL.Entity;

public class Boris extends Enemy {

    //simile a bonzo nei movimenti
    //può lanciare rocce che uccidono il nemico al contatto
    //le rocce si sbriciolano a contatto co muri e piattaforme
    //salta e ha buon movimento


    private final int points=2000;

    public Boris( int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 2;
    }
    public Boris( GameStateManager gsm){
        this( 0, 0, true, gsm);
    }

    public void move(){
        if(player.getLives()<=0){
            stop();
        }
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
            else{
                if(attackTimer.isRunning()){
                    attackTimer.stop();
                }
                onPlayer();
                chasePlayer();
            }
        }
    }

    boolean shouldRetracePath(){
        if (shortestPath.isEmpty()){
            updateAction(Action.IDLE);
            return true;
        }
        return Math.abs(player.getY() - shortestPath.getLast().y )>80;
    }

    void rage(){
        enraged = true;
        bubbled = false;
        speed += 1;
        attackTimer.setDelay(800);
    }
    void shoot(){
        Fireball fireball = new Fireball(player, this);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
    }
    void bubbled(){
        stop();
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);

    }
}
