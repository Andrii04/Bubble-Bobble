package VIEW;

import MODEL.Block;
import MODEL.Level;

import javax.swing.*;
import java.awt.*;

public class LevelEditorPanel extends JPanel {
    //uso di Graphics e Graphics 2D per disegnare i livelli
    //i blocchi saranno disegnati, non saranno ciascuno un label
    int FRAME_WIDTH = MainFrame.FRAME_WIDTH;
    int FRAME_HEIGHT = MainFrame.FRAME_HEIGHT;

    JLayeredPane layeredPane;
    JPanel buttonsLayer;
    JPanel levelLayer;

    JButton solidBT;
    boolean solidON;
    JButton removeBT;
    boolean removeON;

    Block currentBlock;
    JButton selectBlock;

    Level currentLevel;
    JButton selectLevel;

    JLabel testBlock;

    public LevelEditorPanel() {

        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.white);
        this.setLayout(null);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        buttonsLayer = new JPanel();
        buttonsLayer.setLayout(new GridLayout(1, 4));
        solidBT = new JButton();
        removeBT = new JButton();
        selectBlock = new JButton();
        selectLevel = new JButton();

        solidBT.setText("SOLID");
        removeBT.setText("REMOVE");
        selectBlock.setText("SELECT BLOCK");
        selectLevel.setText("SELECT LEVEL");

        solidBT.setVisible(true);
        removeBT.setVisible(true);
        selectBlock.setVisible(true);
        selectLevel.setVisible(true);
        buttonsLayer.add(solidBT);
        buttonsLayer.add(removeBT);
        buttonsLayer.add(selectBlock);
        buttonsLayer.add(selectLevel);
        buttonsLayer.setBounds(0, FRAME_HEIGHT-67, 500, 35);
        buttonsLayer.setVisible(true);

        levelLayer = new JPanel();
        levelLayer.setLayout(null);
        testBlock = new JLabel();
        testBlock.setBounds(250,250,16,8);
        testBlock.setBackground(Color.red);
        testBlock.setOpaque(true);
        testBlock.setForeground(Color.white);
        testBlock.setVisible(true);
        levelLayer.add(testBlock);
        levelLayer.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        levelLayer.setVisible(true);

        layeredPane.add(levelLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttonsLayer, JLayeredPane.PALETTE_LAYER);
        layeredPane.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        layeredPane.setVisible(true);

        this.add(layeredPane);
        this.setVisible(true);
    }
}
