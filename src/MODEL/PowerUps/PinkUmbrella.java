package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class PinkUmbrella extends PowerUp {

    public PinkUmbrella() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaPink.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
