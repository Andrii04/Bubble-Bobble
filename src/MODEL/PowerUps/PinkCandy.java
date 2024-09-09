package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class PinkCandy extends PowerUp {

    public PinkCandy() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (1).png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
