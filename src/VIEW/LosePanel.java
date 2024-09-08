package VIEW;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class LosePanel extends JPanel {

    private Font font = MainFrame.getPixelFont();
    private final JLabel testo = new JLabel("YOU LOSE");
    private final JLabel menuBT = new JLabel("MENU");
    private final JLabel nplaytBT = new JLabel("NEW GAME");
    private final JLabel restartBT = new JLabel("RETRY");
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD");
    private final JLabel exitBT = new JLabel("EXIT");
    private final JLabel cursor = new JLabel();
    private int selectedOption = 0;

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

        //button nplaytBT

        nplaytBT.setFont(font.deriveFont(30f));
        nplaytBT.setHorizontalAlignment(SwingConstants.CENTER);
        nplaytBT.setBounds(MainFrame.FRAME_WIDTH/2-125, menuBT.getY()+menuBT.getHeight()+15, 250 , 50);
        nplaytBT.setForeground(Color.white);
        nplaytBT.setVisible(true);
        this.add(nplaytBT);

        //button restartBT

        restartBT.setFont(font.deriveFont(30f));
        restartBT.setHorizontalAlignment(SwingConstants.CENTER);
        restartBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, nplaytBT.getY()+nplaytBT.getHeight()+15, 200 , 50);
        restartBT.setForeground(Color.white);
        restartBT.setVisible(true);
        this.add(restartBT);



        //Leadbord Button

        leaderboardBT.setFont(font.deriveFont(30f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds(MainFrame.FRAME_WIDTH/2-200, restartBT.getY()+restartBT.getHeight()+15, 400 , 50);
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

        //cursor

        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth()*2, cursorImg.getIconWidth()*2, Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(leaderboardBT.getX() - 100, menuBT.getY(), cursorImg.getIconWidth()*10, menuBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);
    }


        public void cursorUp()
        {
            if (selectedOption > 0) {
                selectedOption--;
                cursor.setLocation(cursor.getX(), cursor.getY() - 65);
            }
        }


        public void cursorDown(){
            if(selectedOption < 4){
                selectedOption++;
                cursor.setLocation(cursor.getX(), cursor.getY() + 65);
            }
        }
        public int getSelectedOption(){
            return selectedOption;
        }
    }


