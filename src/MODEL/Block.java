package MODEL;
import javax.swing.ImageIcon;

public class Block {

    private ImageIcon skin;
    private int x;
    int y;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    boolean solid;

    public Block(ImageIcon skin){

        this.skin=skin;
        this.x=x;
        this.y=y;
        solid = true;
    }
    public ImageIcon getSkin() {return skin;}


}
