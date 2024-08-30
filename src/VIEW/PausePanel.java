package VIEW;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {

    private Font font = MainFrame.getPixelFont();
    private final JLabel testo = new JLabel("PAUSE");
    private final JLabel gameBT = new JLabel("CONTINUE");
    private final JLabel exitSaveBT = new JLabel("EXIT & SAVE");
    private final JLabel exitBT = new JLabel("EXIT");
    private final JLabel cursor = new JLabel();
    private int selectedOption = 0;

    public PausePanel() {

        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);

        // testo

        testo.setFont(font.deriveFont(60f));
        testo.setHorizontalAlignment(SwingConstants.CENTER);
        testo.setBounds(MainFrame.FRAME_WIDTH / 2 - 150, 30, 300, 250);
        testo.setForeground(Color.YELLOW);
        testo.setVisible(true);
        this.add(testo);


        //Game Button
        gameBT.setFont(font.deriveFont(30f));
        gameBT.setHorizontalAlignment(SwingConstants.CENTER);
        gameBT.setBounds((MainFrame.FRAME_WIDTH) / 2-150, testo.getY() + testo.getHeight(), 300, 50);
        gameBT.setForeground(Color.white);
        gameBT.setVisible(true);
        this.add(gameBT);


        //exit & save Button

        exitSaveBT.setFont(font.deriveFont(30f));
        exitSaveBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitSaveBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 200, gameBT.getY() + gameBT.getHeight() + 15, 400, 50);
        exitSaveBT.setForeground(Color.white);
        exitSaveBT.setVisible(true);
        this.add(exitSaveBT);


        //Exit button

        exitBT.setFont(font.deriveFont(30f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 100, exitSaveBT.getY() + exitSaveBT.getHeight() + 15, 200, 50);
        exitBT.setForeground(Color.white);
        exitBT.setVisible(true);
        this.add(exitBT);


        //cursor

        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth() * 2, cursorImg.getIconWidth() * 2, Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(exitSaveBT.getX() - 100, gameBT.getY(), cursorImg.getIconWidth() * 10, gameBT.getHeight());
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

