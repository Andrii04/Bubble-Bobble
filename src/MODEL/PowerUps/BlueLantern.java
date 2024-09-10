package MODEL.PowerUps;

import javax.swing.*;

public class BlueLantern extends PowerUp {

    public BlueLantern() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/LanternBlue.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        player.setPinkRing(true);
        player.setBlueRing(true);
        player.setRedRing(true);
        erase();
    }
}
