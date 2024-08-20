package Model;

import javax.swing.ImageIcon;

public abstract class Power {

    ImageIcon skin;
    private int x;
    private int y;

    public Power(ImageIcon skin){

        this.skin=skin;

    }

    public void spawn(){}

}
