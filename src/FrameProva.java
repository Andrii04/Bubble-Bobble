import VIEW.LosePanel;
import VIEW.MenuPanel;
import VIEW.WinPanel;

import javax.swing.*;
import java.awt.*;

public class FrameProva extends JFrame {
    public static final int FRAME_WIDTH = 768; //  (256*3)
    public static final int FRAME_HEIGHT = 672; //(224*3)
    public static final int TILE_SIZE = 64;
    private final LosePanel p = new LosePanel();

    public FrameProva(){

        super("Prova");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setBackground(Color.BLACK);
        setResizable(false);

        add(p);
        p.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        p.setVisible(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        FrameProva wind= new FrameProva();
    }


}
