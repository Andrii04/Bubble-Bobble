package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.IncendioFireball;
import MODEL.Entity;

public class Incendio extends Enemy{

    //cammina veloce
    //scarso nel salto
    //spara palle di fuoco
    //continua a camminare quando spara

    private final int points=3000;


    public Incendio(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight,gsm);
        speed = 3;
    }
    public Incendio(GameStateManager gsm){
        super(gsm);
    }

    void bubbled(){
        stop();
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);

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
        if(!isBubbled() && !isDead()){
            if(player.getY() != y && attackTimer.isRunning()){
                attackTimer.stop();
            }
            else if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            onPlayer();
            chasePlayer();
        }
        }
    void shoot(){
        // to do
        IncendioFireball fireball = new IncendioFireball(player, this);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
    }
    void rage(){
        enraged = true;
        bubbled = false;
        speed += 1;
        attackTimer.setDelay(800);
    }
}


