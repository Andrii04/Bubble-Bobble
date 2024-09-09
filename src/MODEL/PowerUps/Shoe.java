package MODEL.PowerUps;

import MODEL.PowerUp;

import javax.swing.*;

public class Shoe extends PowerUp {

    public Shoe() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Shoe.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {

    }
}
