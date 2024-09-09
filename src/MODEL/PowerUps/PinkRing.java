package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class PinkRing extends PowerUp {

    public PinkRing() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/RingPink.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
