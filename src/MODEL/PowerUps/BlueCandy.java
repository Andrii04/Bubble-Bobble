package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class BlueCandy extends PowerUp {

    public BlueCandy() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (3).png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
