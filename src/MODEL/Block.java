package MODEL;
import javax.swing.ImageIcon;

public class Block {

    private ImageIcon skin;
    private int x;
    int y;
    public static final int WIDTH = 18;
    public static final int HEIGHT = 18;
    boolean solid;

    public Block(ImageIcon skin, boolean solid){

        this.skin=skin;
        this.x=x;
        this.y=y;
        this.solid=solid;
    }
    public ImageIcon getSkin() {return skin;}


}
