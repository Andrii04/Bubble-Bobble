package GAMESTATEMANAGER;

import java.awt.event.*;

public abstract class GameState implements KeyListener, MouseListener, ActionListener {

    public abstract void update();

    public abstract void draw();

}
