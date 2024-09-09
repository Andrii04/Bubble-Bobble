package MODEL.PowerUps;

import javax.swing.*;

public class OrangeUmbrella extends PowerUp {

    public OrangeUmbrella() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaOrange.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        int nextLevelInt;
        if (gsm.getNextLevelInt() + 4 < 24) nextLevelInt = gsm.getNextLevelInt() + 4;
        else nextLevelInt = 24;

        gsm.setNextLevelInt(nextLevelInt);
    }
}
