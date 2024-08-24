package VIEW;

import CONTROLLER.Controller;
import MODEL.Block;
import MODEL.Level;

import javax.swing.*;
import javax.swing.text.Caret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

public class LevelEditorPanel extends JPanel {
    //uso di Graphics e Graphics 2D per disegnare i livelli
    //i blocchi saranno disegnati, non saranno ciascuno un label
    int FRAME_WIDTH = MainFrame.FRAME_WIDTH;
    int FRAME_HEIGHT = MainFrame.FRAME_HEIGHT;
    int TILE_SIZE = MainFrame.TILE_SIZE;

    Controller controller = Controller.getInstance();

    Font font;

    JLayeredPane layeredPane;
    JPanel buttonsLayer;
    JPanel levelLayer;

    JLabel solidBT;
    JLabel removeBT;

    Level currentLevel;
    JLabel selectLevel;
    JLabel InsertLevelNumber;

    JTextField levelNfield;

    public LevelEditorPanel() {

        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        //custom font
        try{
            InputStream fontFile= getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/General/classic-nes-font.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        removeBT = new JLabel("REMOVE");
        solidBT = new JLabel("SOLID");
        selectLevel = new JLabel("SELECT LEVEL");
        removeBT.setFont(font.deriveFont(12f));
        solidBT.setFont(font.deriveFont(12f));
        selectLevel.setFont(font.deriveFont(12f));
        removeBT.addMouseListener(controller);
        solidBT.addMouseListener(controller);
        selectLevel.addMouseListener(controller);

        buttonsLayer = new JPanel();
        buttonsLayer.setLayout(null);

        removeBT.setBounds(115, FRAME_HEIGHT-70, 80, 35);
        //removeBT.setBackground(Color.white);
        //removeBT.setOpaque(true);
        removeBT.setForeground(Color.green);
        //selectBlock.setBackground(Color.white);
        //selectBlock.setOpaque(true);
        solidBT.setBounds(removeBT.getX() + 200, FRAME_HEIGHT-70, 80, 35);
        //solidBT.setBackground(Color.white);
        //solidBT.setOpaque(true);
        solidBT.setForeground(Color.green);
        selectLevel.setBounds(solidBT.getX() + 155, FRAME_HEIGHT-70, 160, 35);
        //selectLevel.setBackground(Color.white);
        selectLevel.setForeground(Color.green);
        //selectLevel.setOpaque(true);

        removeBT.setVisible(true);
        selectLevel.setVisible(true);
        solidBT.setVisible(true);
        buttonsLayer.add(removeBT);
        buttonsLayer.add(selectLevel);
        buttonsLayer.add(solidBT);


        levelLayer = new JPanel();
        levelLayer.setLayout(null);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0,0, FRAME_WIDTH, FRAME_HEIGHT);
        buttonsLayer.setVisible(true);
        levelLayer.setVisible(true);
        buttonsLayer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        levelLayer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        buttonsLayer.setOpaque(false);
        levelLayer.setOpaque(false);
        layeredPane.add(levelLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttonsLayer, JLayeredPane.PALETTE_LAYER);
        layeredPane.setVisible(true);

        levelNfield = new JTextField();
        levelNfield.setBounds(solidBT.getX()-12, FRAME_WIDTH/2 - 50, 80, 80);
        levelNfield.setFont(MainFrame.getPixelFont().deriveFont(20f));
        levelNfield.setBackground(Color.black);
        levelNfield.setForeground(Color.white);
        levelNfield.setFocusable(true);
        levelNfield.requestFocusInWindow();
        levelNfield.setVisible(true);
        levelNfield.setCaretColor(Color.white);

        InsertLevelNumber = new JLabel("Insert Level Number");
        InsertLevelNumber.setFont(MainFrame.getPixelFont().deriveFont(25f));
        InsertLevelNumber.setBounds(levelNfield.getX()-180, levelNfield.getY()
        - levelNfield.getHeight() - 50, 600, 200);
        InsertLevelNumber.setBackground(Color.black);
        InsertLevelNumber.setForeground(Color.white);
        InsertLevelNumber.setVisible(true);

        //logica textField per scegliere il livello da modificare
        KeyListener textFieldKL = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isDigit(e.getKeyChar()))) e.consume();
                if (levelNfield.getText().length() >= 2) e.consume();

                if (Character.isDigit(e.getKeyChar())) {
                    Integer digit = Character.getNumericValue(e.getKeyChar());
                    String levelNum = levelNfield.getText() + digit;
                    Integer fullNum = Integer.parseInt(levelNum);
                    if (fullNum > 24) {
                        e.consume();
                        levelNfield.setText("24");
                    }
                    else if (fullNum == 0) {
                        e.consume();
                        levelNfield.setText("1");
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (!(levelNfield.getText().isEmpty())) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        int num = Integer.parseInt(levelNfield.getText());
                        controller.loadLevelEditorLevel(num);
                        buttonsLayer.remove(levelNfield);
                        buttonsLayer.remove(InsertLevelNumber);
                        drawCurrentLevel();
                        buttonsLayer.revalidate();
                        buttonsLayer.repaint();
                        }
                    }
                }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        levelNfield.addKeyListener(textFieldKL);

        this.add(layeredPane);
    }
    public void chooseLevel() {
        //apre text field che ti fa scegliere il numero del livello da modificare
        buttonsLayer.add(levelNfield);
        buttonsLayer.add(InsertLevelNumber);
        buttonsLayer.revalidate();
        buttonsLayer.repaint();
    }
    public void setCurrentLevel(Level level) {this.currentLevel = level;}

    public void drawCurrentLevel() {
        //draws currentLevel
    }

}
