package MODEL.PowerUps;

import javax.swing.*;

public class PinkUmbrella extends PowerUp {

    public PinkUmbrella() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaPink.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        int nextLevelInt;
        if (gsm.getNextLevelInt() + 6 < 24) nextLevelInt = gsm.getNextLevelInt() + 6;
        else nextLevelInt = 24;

        gsm.setNextLevelInt(nextLevelInt);
    }
}
