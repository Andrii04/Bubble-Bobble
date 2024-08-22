package VIEW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;

public class MenuPanel extends JPanel {

    private final JLabel titleBT = new JLabel();
    private final JLabel startBT = new JLabel("START GAME");
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD");
    private final JLabel levelEditorBT = new JLabel("LEVEL EDITOR");
    private final JLabel exitBT = new JLabel("EXIT GAME");
    private final JLabel cursor = new JLabel();
    private Font font = MainFrame.getPixelFont();
    private int selectedOption = 0; // default ( Start Game )

    public MenuPanel() {

        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);

        // display title animation

        ArrayList<ImageIcon> titleAnimation = new ArrayList<>();
        for ( int i = 1; i <= 6; i++){
            String source = "/Resources/Bubble Bobble Resources/Title/BubbleBobbleTitle" + i + ".png";
            ImageIcon title = new ImageIcon(getClass().getResource(source));
            Image titleScaled = title.getImage().getScaledInstance(title.getIconWidth()*2,title.getIconHeight()*2,Image.SCALE_SMOOTH);
            titleAnimation.add(new ImageIcon(titleScaled));
        }

        Timer timer = new Timer(100, e -> {
            int frame = (int) ((System.currentTimeMillis()/ 100 % 6));
            ImageIcon title = titleAnimation.get(frame);
            titleBT.setIcon(title);
            titleBT.setBounds((MainFrame.FRAME_WIDTH - title.getIconWidth())/2 ,  0, title.getIconWidth() , title.getIconHeight());
        } );
        timer.start();
        titleBT.setVisible(true);
        this.add(titleBT);


        //button Start
        startBT.setFont(font.deriveFont(20f));
        startBT.setHorizontalAlignment(SwingConstants.CENTER);
        startBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, titleBT.getY()+MainFrame.TILE_SIZE*5, 200 , 25);
        startBT.setForeground(Color.WHITE);
        startBT.setVisible(true);
        this.add(startBT);

        //leaderboard button

        leaderboardBT.setFont(font.deriveFont(20f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds((MainFrame.FRAME_WIDTH-300)/2, startBT.getY()+MainFrame.TILE_SIZE, 300 , 25);
        leaderboardBT.setForeground(Color.WHITE);
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);

        //level editor button
        levelEditorBT.setFont(font.deriveFont(20f));
        levelEditorBT.setHorizontalAlignment(SwingConstants.CENTER);
        levelEditorBT.setBounds((MainFrame.FRAME_WIDTH-300)/2, leaderboardBT.getY()+MainFrame.TILE_SIZE, 300 , 25);
        levelEditorBT.setForeground(Color.WHITE);
        levelEditorBT.setVisible(true);
        this.add(levelEditorBT);

        //button exit
        exitBT.setFont(font.deriveFont(20f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds((MainFrame.FRAME_WIDTH-200)/2, levelEditorBT.getY()+MainFrame.TILE_SIZE, 200 , 25);
        exitBT.setForeground(Color.WHITE);
        exitBT.setVisible(true);
        this.add(exitBT);

        // cursor
        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/BubbleType4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth()*2, startBT.getHeight(), Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(levelEditorBT.getX()   - MainFrame.TILE_SIZE/2, startBT.getY(), cursorImg.getIconWidth()*2, startBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);



    }

    public void cursorUp(){
        if (selectedOption > 0) {
            selectedOption--;
            cursor.setLocation(cursor.getX(), cursor.getY() - MainFrame.TILE_SIZE);
        }
    }
    public void cursorDown(){
        if(selectedOption < 3){
            selectedOption++;
            cursor.setLocation(cursor.getX(), cursor.getY() + MainFrame.TILE_SIZE);
        }
    }
    public int getSelectedOption(){
        return selectedOption;
    }
}
