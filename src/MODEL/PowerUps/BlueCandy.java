package MODEL.PowerUps;

import MODEL.Player;

import javax.swing.*;

public class BlueCandy extends PowerUp {

    public BlueCandy() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (3).png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        player.setBubbleSpeed(11);
        player.eatBlueCandy();
        erase();
    }
}
