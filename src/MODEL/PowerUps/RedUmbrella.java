package MODEL.PowerUps;

import javax.swing.*;

public class RedUmbrella extends PowerUp {

    public RedUmbrella() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaRed.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        int nextLevelInt;
        if (gsm.getNextLevelInt() + 8 < 24) nextLevelInt = gsm.getNextLevelInt() + 8;
        else nextLevelInt = 24;

        gsm.setNextLevelInt(nextLevelInt);
    }
}
