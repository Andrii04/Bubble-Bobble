package VIEW;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel{

    private Font font = MainFrame.getPixelFont();
    private final JLabel testo = new JLabel("YOU WIN!");
    private final JLabel menuBT = new JLabel("MENU");
    private final JLabel exitBT = new JLabel("EXIT");
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD");
    private final JLabel cursor = new JLabel();
    private int selectedOption = 0;

    public WinPanel() {

        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);

        //testo you win

        testo.setFont(font.deriveFont(60f));
        testo.setHorizontalAlignment(SwingConstants.CENTER);
        testo.setBounds(MainFrame.FRAME_WIDTH / 2 - 245, 30, 490, 250);
        testo.setForeground(Color.cyan);
        testo.setVisible(true);
        this.add(testo);


        //menuBT
        menuBT.setFont(font.deriveFont(30f));
        menuBT.setHorizontalAlignment(SwingConstants.CENTER);
        menuBT.setBounds((MainFrame.FRAME_WIDTH - 200) / 2, testo.getY() + testo.getHeight(), 200, 50);
        menuBT.setForeground(Color.white);
        menuBT.setVisible(true);
        this.add(menuBT);


        //leaderborBT

        leaderboardBT.setFont(font.deriveFont(30f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 200, menuBT.getY() + menuBT.getHeight() + 15, 400, 50);
        leaderboardBT.setForeground(Color.white);
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);


        //Exit button

        exitBT.setFont(font.deriveFont(30f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 100, leaderboardBT.getY() + leaderboardBT.getHeight() + 15, 200, 50);
        exitBT.setForeground(Color.white);
        exitBT.setVisible(true);
        this.add(exitBT);


        //cursor

        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth() * 2, cursorImg.getIconWidth() * 2, Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(leaderboardBT.getX() - 100, menuBT.getY(), cursorImg.getIconWidth() * 10, menuBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);
    }

        //methods

        public void cursorUp(){
            if (selectedOption > 0) {
                selectedOption--;
                cursor.setLocation(cursor.getX(), cursor.getY() - MainFrame.TILE_SIZE);
            }
        }
        public void cursorDown(){
            if(selectedOption < 2){
                selectedOption++;
                cursor.setLocation(cursor.getX(), cursor.getY() + MainFrame.TILE_SIZE);
            }
        }
        public int getSelectedOption(){
            return selectedOption;
        }



    }


