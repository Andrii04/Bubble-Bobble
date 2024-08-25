package VIEW;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class LosePanel extends JPanel {

    private Font font = MainFrame.getPixelFont();
    private final JLabel testo = new JLabel("YOU LOSE");
    private final JLabel menuBT = new JLabel("MENU");
    private final JLabel exitBT = new JLabel("EXIT");
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD");

    public LosePanel(){


        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);


        //label
        testo.setFont(font.deriveFont(60f));
        testo.setHorizontalAlignment(SwingConstants.CENTER);
        testo.setBounds(MainFrame.FRAME_WIDTH/2-240,30,480,250);
        testo.setForeground(Color.red);
        testo.setVisible(true);
        this.add(testo);


        //button Return to Menu

        menuBT.setFont(font.deriveFont(30f));
        menuBT.setHorizontalAlignment(SwingConstants.CENTER);
        menuBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, testo.getY() + testo.getHeight(), 200 , 50);
        menuBT.setForeground(Color.white);
        menuBT.setVisible(true);
        this.add(menuBT);


        //Leadbord Button

        leaderboardBT.setFont(font.deriveFont(30f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds(MainFrame.FRAME_WIDTH/2-200, menuBT.getY()+menuBT.getHeight()+15, 400 , 50);
        leaderboardBT.setForeground(Color.white);
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);




        //Exit button

        exitBT.setFont(font.deriveFont(30f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds(MainFrame.FRAME_WIDTH/2-100 , leaderboardBT.getY()+ leaderboardBT.getHeight()+15, 200 , 50);
        exitBT.setForeground(Color.white);
        exitBT.setVisible(true);
        this.add(exitBT);

    }




}
