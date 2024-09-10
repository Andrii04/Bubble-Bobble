package MODEL.PowerUps;

import javax.swing.*;

public class Shoe extends PowerUp {

    public Shoe() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Shoe.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        player.setSpeed(32);
        erase();
    }
}
