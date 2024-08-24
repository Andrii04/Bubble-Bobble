package VIEW;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class LosePanel extends JPanel {

    private Font font = MainFrame.getPixelFont();
    private final JLabel testo = new JLabel("YOU LOSE");
    private final JButton menuBT = new JButton("MENU");
    private final JButton exitBT = new JButton("EXIT");
    private final JButton leaderboardBT = new JButton("LEADERBOARD");

    public LosePanel(){


        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);

        //custom font
        try{
            InputStream fontFile= getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/General/classic-nes-font.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //label
        testo.setFont(font.deriveFont(12f));
        testo.setHorizontalAlignment(SwingConstants.CENTER);
        testo.setBounds(50,50,100,80);
        testo.setOpaque(true);
        this.add(testo);


        //button Return to Menu

        menuBT.setFont(font.deriveFont(20f));
        menuBT.setHorizontalAlignment(SwingConstants.CENTER);
        menuBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, menuBT.getY()+MainFrame.TILE_SIZE*5, 200 , 25);
        menuBT.setForeground(Color.white);
        menuBT.setVisible(true);
        this.add(menuBT);


        //Leadbord Button

        leaderboardBT.setFont(font.deriveFont(20f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        //menuBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, menuBT.getY()+MainFrame.TILE_SIZE*5, 200 , 25);// li devo sistemare
        leaderboardBT.setForeground(Color.white);
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);




        //Exit button

        exitBT.setFont(font.deriveFont(20f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        //menuBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, menuBT.getY()+MainFrame.TILE_SIZE*5, 200 , 25);// li devo sistemare
        exitBT.setForeground(Color.white);
        exitBT.setVisible(true);
        this.add(exitBT);

    }




}
