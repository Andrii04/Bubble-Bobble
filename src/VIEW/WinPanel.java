package VIEW;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel{

    private Font font = MainFrame.getPixelFont();
    private final JLabel testo = new JLabel("YOU WIN");
    private final JLabel menuBT = new JLabel("MENU");
    private final JLabel exitBT = new JLabel("EXIT");
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD");

    public WinPanel(){

        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);

        //testo you win


        //menuBT


        //exitBT

        //leaderborBT
    }

}
