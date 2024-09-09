package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class OrangeUmbrella extends PowerUp {

    public OrangeUmbrella() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaOrange.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
