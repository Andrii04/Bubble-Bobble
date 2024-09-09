package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class BlueRing extends PowerUp {

    public BlueRing() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/RingedBlue.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
