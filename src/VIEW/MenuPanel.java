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

/**
 * La classe MenuPanel rappresenta il pannello del menu principale del gioco.
 * Consente di selezionare tra diverse opzioni: avviare il gioco, visualizzare la classifica,
 * aprire l'editor di livelli o uscire dal gioco.
 */
public class MenuPanel extends JPanel {

    private final JLabel titleBT = new JLabel(); // Etichetta per il titolo animato
    private final JLabel startBT = new JLabel("START GAME"); // Pulsante per avviare il gioco
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD"); // Pulsante per visualizzare la classifica
    private final JLabel levelEditorBT = new JLabel("LEVEL EDITOR"); // Pulsante per aprire l'editor di livelli
    private final JLabel exitBT = new JLabel("EXIT GAME"); // Pulsante per uscire dal gioco
    private final JLabel cursor = new JLabel(); // Cursore per la selezione delle opzioni
    private Font font = MainFrame.getPixelFont(); // Font utilizzato per il testo
    private int selectedOption = 0; // Opzione selezionata (0 = Start Game di default)

    /**
     * Costruttore della classe MenuPanel.
     * Inizializza il pannello del menu e tutti i suoi componenti grafici,
     * inclusa l'animazione del titolo e i pulsanti.
     */
    public MenuPanel() {
        // Imposta la dimensione e altre proprietà del pannello
        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black); // Sfondo nero
        this.setLayout(null); // Layout manuale
        this.setVisible(true); // Rende il pannello visibile

        // Animazione del titolo
        ArrayList<ImageIcon> titleAnimation = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            String source = "/Resources/Bubble Bobble Resources/Title/BubbleBobbleTitle" + i + ".png";
            ImageIcon title = new ImageIcon(getClass().getResource(source));
            Image titleScaled = title.getImage().getScaledInstance(title.getIconWidth() * 2, title.getIconHeight() * 2, Image.SCALE_SMOOTH);
            titleAnimation.add(new ImageIcon(titleScaled));
        }

        // Timer per l'animazione del titolo
        Timer timer = new Timer(100, e -> {
            int frame = (int) ((System.currentTimeMillis() / 100) % 6); // Calcola il frame corrente
            ImageIcon title = titleAnimation.get(frame);
            titleBT.setIcon(title);
            titleBT.setBounds((MainFrame.FRAME_WIDTH - title.getIconWidth()) / 2, 0, title.getIconWidth(), title.getIconHeight());
        });
        timer.start();
        titleBT.setVisible(true);
        this.add(titleBT);

        // Pulsante "START GAME"
        startBT.setFont(font.deriveFont(20f));
        startBT.setHorizontalAlignment(SwingConstants.CENTER);
        startBT.setBounds((MainFrame.FRAME_WIDTH - 200) / 2, titleBT.getY() + MainFrame.TILE_SIZE * 5, 200, 25);
        startBT.setForeground(Color.WHITE);
        startBT.setVisible(true);
        this.add(startBT);

        // Pulsante "LEADERBOARD"
        leaderboardBT.setFont(font.deriveFont(20f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds((MainFrame.FRAME_WIDTH - 300) / 2, startBT.getY() + MainFrame.TILE_SIZE, 300, 25);
        leaderboardBT.setForeground(Color.WHITE);
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);

        // Pulsante "LEVEL EDITOR"
        levelEditorBT.setFont(font.deriveFont(20f));
        levelEditorBT.setHorizontalAlignment(SwingConstants.CENTER);
        levelEditorBT.setBounds((MainFrame.FRAME_WIDTH - 300) / 2, leaderboardBT.getY() + MainFrame.TILE_SIZE, 300, 25);
        levelEditorBT.setForeground(Color.WHITE);
        levelEditorBT.setVisible(true);
        this.add(levelEditorBT);

        // Pulsante "EXIT GAME"
        exitBT.setFont(font.deriveFont(20f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds((MainFrame.FRAME_WIDTH - 200) / 2, levelEditorBT.getY() + MainFrame.TILE_SIZE, 200, 25);
        exitBT.setForeground(Color.WHITE);
        exitBT.setVisible(true);
        this.add(exitBT);

        // Impostazione del cursore
        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth() * 2, startBT.getHeight(), Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(levelEditorBT.getX() - MainFrame.TILE_SIZE / 2, startBT.getY(), cursorImg.getIconWidth() * 2, startBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);
    }

    /**
     * Metodo per spostare il cursore verso l'alto.
     * Cambia l'opzione selezionata a quella precedente.
     */
    public void cursorUp() {
        if (selectedOption > 0) {
            selectedOption--; // Aggiorna l'opzione selezionata
            cursor.setLocation(cursor.getX(), cursor.getY() - MainFrame.TILE_SIZE); // Sposta il cursore verso l'alto
        }
    }

    /**
     * Metodo per spostare il cursore verso il basso.
     * Cambia l'opzione selezionata a quella successiva.
     */
    public void cursorDown() {
        if (selectedOption < 3) {
            selectedOption++; // Aggiorna l'opzione selezionata
            cursor.setLocation(cursor.getX(), cursor.getY() + MainFrame.TILE_SIZE); // Sposta il cursore verso il basso
        }
    }

    /**
     * Restituisce l'opzione attualmente selezionata.
     *
     * @return un intero che rappresenta l'indice dell'opzione selezionata (0-3)
     */
    public int getSelectedOption() {
        return selectedOption;
    }
}
