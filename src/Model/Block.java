package Model;
import javax.swing.ImageIcon;

public abstract class Block {

    private ImageIcon skin;
    private int x;
    int y;
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;
    boolean solid;

    public Block(ImageIcon skin, boolean solid){

        this.skin=skin;
        this.x=x;
        this.y=y;
        this.solid=solid;
    }


}
