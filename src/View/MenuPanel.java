package View;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JFrame{
    private final JLabel titleBT = new JLabel();
    private final JButton startBT = new JButton();
    private final JButton leaderboardBT = new JButton();
    private final JButton levelEditorBT = new JButton();
    private final JButton exitBT = new JButton();

    public MenuPanel(){

        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //label
        //titleBT = new JLabel("Bubble bobble");
        //titleBT.setOpaque(true);


        //button Start

        //buttonBt leadboard

        //button exit


        this.add(titleBT);
        this.setVisible(true);



    }

    public static void main(String[] args) {
        MenuPanel panel = new MenuPanel();
    }
}
