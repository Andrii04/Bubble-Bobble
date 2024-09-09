package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class RedUmbrella extends PowerUp {

    public RedUmbrella() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaRed.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
