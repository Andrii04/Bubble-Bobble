package VIEW;

import CONTROLLER.Controller;
import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Level;

import javax.swing.*;
import javax.swing.text.Caret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LevelEditorPanel extends JPanel {
    //uso di Graphics e Graphics 2D per disegnare i livelli
    //i blocchi saranno disegnati, non saranno ciascuno un label
    int FRAME_WIDTH = MainFrame.FRAME_WIDTH;
    int FRAME_HEIGHT = MainFrame.FRAME_HEIGHT;
    int TILE_SIZE = MainFrame.TILE_SIZE;

    GameStateManager gsm = GameStateManager.getInstance();
    Controller controller = Controller.getInstance();

    Font font;

    JLayeredPane layeredPane;
    JPanel buttonsLayer;
    JPanel levelLayer;

    final int levelLayerWidth = 698;
    final int levelLayerHeight = 602;
    final int blockWidth = 16;
    final int blockHeight = 16;

    JLabel solidBT;
    JLabel removeBT;
    JLabel saveLevel;
    JLabel menu;

    Level currentLevel;
    JLabel selectLevel;
    JLabel InsertLevelNumber;

    JTextField levelNfield;
    boolean selectingLevel = false;
    boolean redrawingLevel = false;

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
        saveLevel = new JLabel("SAVE");
        menu = new JLabel("MENU");
        removeBT.setFont(font.deriveFont(12f));
        solidBT.setFont(font.deriveFont(12f));
        selectLevel.setFont(font.deriveFont(12f));
        saveLevel.setFont(font.deriveFont(12f));
        menu.setFont(font.deriveFont(12f));
        removeBT.addMouseListener(controller);
        solidBT.addMouseListener(controller);
        selectLevel.addMouseListener(controller);
        saveLevel.addMouseListener(controller);
        menu.addMouseListener(controller);

        buttonsLayer = new JPanel();
        buttonsLayer.setLayout(null);

        removeBT.setBounds(40, FRAME_HEIGHT-40, 80, 35);

        removeBT.setForeground(Color.green);

        solidBT.setBounds(removeBT.getX() + 150, FRAME_HEIGHT-40, 80, 35);

        solidBT.setForeground(Color.green);
        selectLevel.setBounds(solidBT.getX() + 130, FRAME_HEIGHT-40, 160, 35);

        selectLevel.setForeground(Color.green);

        saveLevel.setBounds(selectLevel.getX() + 200, FRAME_HEIGHT-40, 80, 35);
        saveLevel.setForeground(Color.green);

        menu.setBounds(saveLevel.getX() + 120, FRAME_HEIGHT-40, 80, 35);
        menu.setForeground(Color.green);

        removeBT.setVisible(true);
        selectLevel.setVisible(true);
        solidBT.setVisible(true);
        saveLevel.setVisible(true);
        menu.setVisible(true);
        buttonsLayer.add(removeBT);
        buttonsLayer.add(selectLevel);
        buttonsLayer.add(solidBT);
        buttonsLayer.add(saveLevel);
        buttonsLayer.add(menu);


        levelLayer = new JPanel();
        levelLayer.setLayout(null);
        levelLayer.setBackground(Color.white);


        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0,0, FRAME_WIDTH, FRAME_HEIGHT);




        buttonsLayer.setOpaque(false);
        levelLayer.setOpaque(false);
        layeredPane.add(buttonsLayer, JLayeredPane.PALETTE_LAYER);
        buttonsLayer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        buttonsLayer.setVisible(true);
        layeredPane.add(levelLayer, JLayeredPane.DEFAULT_LAYER);
        levelLayer.setBounds(0, 0, levelLayerWidth, levelLayerHeight);
        levelLayer.setVisible(true);

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
                        controller.LEloadLevel(num);
                        levelNfield.setText("");
                        buttonsLayer.remove(levelNfield);
                        buttonsLayer.remove(InsertLevelNumber);
                        buttonsLayer.revalidate();
                        buttonsLayer.repaint();

                        selectingLevel = true;
                        repaint();
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

    public void drawCurrentLevel(Graphics g) {

        Map<Integer, Block> intBlockMap = gsm.getIntBlockMap();
        int[][] pattern = currentLevel.getPattern();

        for (int i = 0; i < 37; i++) {
            //prendo il blocco corrispondente al carattere
            for (int j = 0; j < 42; j++) {

                int blockInt = pattern[i][j];
                Block block = intBlockMap.get(blockInt);

                if (blockInt != 0 && block != null) {
                    //prendo l'icona del blocco da disegnare
                    ImageIcon skin = block.getSkin();

                    //posizione x e y del blocco
                    int x = j * blockWidth; //200 per test
                    int y = i * blockHeight; //200 per test
                    //chiama il metodo ma non disegna sul frame (appare nero).
                    g.drawImage(skin.getImage(), x, y, blockWidth, blockHeight, null);
                }

            }
        }
        selectingLevel = false;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (selectingLevel) {
            drawCurrentLevel(g);
            selectingLevel = false;
        }
        else if (redrawingLevel) {
            drawCurrentLevel(g);
            redrawingLevel = false;
        }
    }

    public void setRedrawingLevel(boolean bool) {redrawingLevel = bool;}

}
