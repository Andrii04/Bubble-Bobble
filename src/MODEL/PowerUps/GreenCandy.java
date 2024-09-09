package MODEL.PowerUps;

import javax.swing.*;

public class GreenCandy extends PowerUp {

    public GreenCandy() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (2).png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        player.setFireRate(50);
        player.eatGreenCandy();
    }
}
