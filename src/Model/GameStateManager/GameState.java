package Model.GameStateManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class GameState implements KeyListener{

    public abstract void update();
    public abstract void draw();

    @Override
    public abstract void keyTyped(KeyEvent k);

    @Override
    public abstract void keyPressed(KeyEvent k);

    @Override
    public abstract void keyReleased(KeyEvent k);
    }

