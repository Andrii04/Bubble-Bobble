

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
        speed =4;
    }
    public Benzo( GameStateManager gsm){
        this( 0, 0, true, gsm);
    }
}
