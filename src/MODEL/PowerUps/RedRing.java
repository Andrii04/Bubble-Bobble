package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class RedRing extends PowerUp {

    public RedRing() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/RingRed.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
