

package MODEL.Enemy;

import MODEL.Entity;
import GAMESTATEMANAGER.GameStateManager;

import javax.swing.Timer;

public class Benzo extends Enemy {

    //non ha mosse di attacco
    //ha salto
    //danno tramite contatto

    private final int points = 500;
    boolean enraged;
    Timer ragetimer;

    public Benzo(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed =5;
    }
    public Benzo( GameStateManager gsm){
        super( gsm);
    }

    @Override
    public boolean getFacingRight() {
        return facingRight;
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
    public void die(){
        player.setPunteggio(player.getPunteggio() + points);
    }
    // to be overriden
    public void attack(){}
    public void rage(){
        // comportamenti
        speed *= 2;
    }

}
