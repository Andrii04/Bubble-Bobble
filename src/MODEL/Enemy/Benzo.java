

package MODEL.Enemy;

import MODEL.Entity;
import GAMESTATEMANAGER.GameStateManager;

public class Benzo extends Enemy {

    //non ha mosse di attacco
    //ha salto
    //danno tramite contatto

    private final int points = 500;

    public Benzo(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed =5;
    }
    public Benzo( GameStateManager gsm){
        this( 0, 0, true, gsm);
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
    }

}
