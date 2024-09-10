package MODEL.PowerUps;

import javax.swing.*;

public class Clock extends PowerUp {

    public Clock() {
        super();
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Clock.png";
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    @Override
    public void activateEffect() {
        currentLevel.setClock(true);
        erase();
    }
}
