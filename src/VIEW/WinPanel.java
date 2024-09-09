package VIEW;

import javax.swing.*;
import java.awt.*;

/**
 * WinPanel rappresenta il pannello che viene mostrato quando il giocatore vince il gioco.
 * Contiene diversi pulsanti per navigare verso il menu principale, visualizzare la classifica,
 * o uscire dal gioco. Include anche un cursore che si sposta tra le opzioni.
 */
public class WinPanel extends JPanel {

    private Font font = MainFrame.getPixelFont(); // Font personalizzato utilizzato per il testo
    private final JLabel testo = new JLabel("YOU WIN!"); // Testo principale che indica la vittoria
    private final JLabel menuBT = new JLabel("MENU"); // Pulsante per tornare al menu principale
    private final JLabel exitBT = new JLabel("EXIT"); // Pulsante per uscire dal gioco
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD"); // Pulsante per visualizzare la classifica
    private final JLabel cursor = new JLabel(); // Cursore che si muove tra le opzioni
    private int selectedOption = 0; // Opzione attualmente selezionata (0 = Menu, 1 = Leaderboard, 2 = Exit)

    /**
     * Costruttore della classe WinPanel.
     * Inizializza il pannello impostando le sue dimensioni, il layout, lo sfondo
     * e aggiungendo i vari componenti visivi, come i pulsanti e il cursore.
     */
    public WinPanel() {

        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black); // Imposta lo sfondo del pannello
        this.setLayout(null); // Layout nullo per posizionamento personalizzato dei componenti
        this.setVisible(true);

        // Imposta il testo "YOU WIN!"
        testo.setFont(font.deriveFont(60f)); // Font con dimensione di 60
        testo.setHorizontalAlignment(SwingConstants.CENTER);
        testo.setBounds(MainFrame.FRAME_WIDTH / 2 - 245, 30, 490, 250);
        testo.setForeground(Color.cyan); // Testo color ciano
        testo.setVisible(true);
        this.add(testo);

        // Imposta il pulsante "MENU"
        menuBT.setFont(font.deriveFont(30f)); // Font con dimensione di 30
        menuBT.setHorizontalAlignment(SwingConstants.CENTER);
        menuBT.setBounds((MainFrame.FRAME_WIDTH - 200) / 2, testo.getY() + testo.getHeight(), 200, 50);
        menuBT.setForeground(Color.white); // Testo color bianco
        menuBT.setVisible(true);
        this.add(menuBT);

        // Imposta il pulsante "LEADERBOARD"
        leaderboardBT.setFont(font.deriveFont(30f)); // Font con dimensione di 30
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 200, menuBT.getY() + menuBT.getHeight() + 15, 400, 50);
        leaderboardBT.setForeground(Color.white); // Testo color bianco
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);

        // Imposta il pulsante "EXIT"
        exitBT.setFont(font.deriveFont(30f)); // Font con dimensione di 30
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 100, leaderboardBT.getY() + leaderboardBT.getHeight() + 15, 200, 50);
        exitBT.setForeground(Color.white); // Testo color bianco
        exitBT.setVisible(true);
        this.add(exitBT);

        // Imposta il cursore (icona della bolla verde)
        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth() * 2, cursorImg.getIconWidth() * 2, Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(leaderboardBT.getX() - 100, menuBT.getY(), cursorImg.getIconWidth() * 10, menuBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);
    }

    // Metodi

    /**
     * Sposta il cursore verso l'alto tra le opzioni. Se è possibile, decrementa
     * l'indice dell'opzione selezionata e aggiorna la posizione del cursore.
     */
    public void cursorUp() {
        if (selectedOption > 0) {
            selectedOption--; // Muove il cursore verso l'opzione precedente
            cursor.setLocation(cursor.getX(), cursor.getY() - MainFrame.TILE_SIZE);
        }
    }

    /**
     * Sposta il cursore verso il basso tra le opzioni. Se è possibile, incrementa
     * l'indice dell'opzione selezionata e aggiorna la posizione del cursore.
     */
    public void cursorDown() {
        if (selectedOption < 2) {
            selectedOption++; // Muove il cursore verso l'opzione successiva
            cursor.setLocation(cursor.getX(), cursor.getY() + MainFrame.TILE_SIZE);
        }
    }

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
     *
     * @return Un intero che rappresenta l'opzione selezionata (0 = Menu, 1 = Leaderboard, 2 = Exit).
     */
    public int getSelectedOption() {
        return selectedOption;
    }

}

