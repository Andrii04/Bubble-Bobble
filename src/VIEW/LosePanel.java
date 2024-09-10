package VIEW;

import javax.swing.*;
import java.awt.*;


/**
 * La classe `LosePanel` rappresenta il pannello che viene visualizzato quando il giocatore perde la partita.
 * Fornisce opzioni come tornare al menu principale, iniziare una nuova partita, ritentare, visualizzare la leaderboard, o uscire dal gioco.
 * Il pannello include un cursore grafico per indicare l'opzione selezionata.
 */
public class LosePanel extends JPanel {

    // Il font utilizzato per il testo e i pulsanti
    private Font font = MainFrame.getPixelFont();

    // Etichetta che mostra il messaggio "YOU LOSE"
    private final JLabel testo = new JLabel("YOU LOSE");

    // Pulsante per tornare al menu principale
    private final JLabel menuBT = new JLabel("MENU");

    // Pulsante per accedere alla leaderboard
    private final JLabel leaderboardBT = new JLabel("LEADERBOARD");

    // Pulsante per uscire dal gioco
    private final JLabel exitBT = new JLabel("EXIT");

    // Icona che rappresenta il cursore per selezionare le opzioni
    private final JLabel cursor = new JLabel();

    // Indice dell'opzione attualmente selezionata (0 = prima opzione)
    private int selectedOption = 0;

    /**
     * Costruttore della classe `LosePanel`.
     * Imposta la dimensione del pannello, il colore di sfondo, la disposizione degli elementi e crea le etichette e il cursore.
     */
    public LosePanel() {

        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setVisible(true);


        // Configurazione dell'etichetta "YOU LOSE"
        testo.setFont(font.deriveFont(60f));
        testo.setHorizontalAlignment(SwingConstants.CENTER);
        testo.setBounds(MainFrame.FRAME_WIDTH / 2 - 240, 30, 480, 250);
        testo.setForeground(Color.red);
        testo.setVisible(true);
        this.add(testo);

        // Configurazione del pulsante "MENU"
        menuBT.setFont(font.deriveFont(30f));
        menuBT.setHorizontalAlignment(SwingConstants.CENTER);
        menuBT.setBounds((MainFrame.FRAME_WIDTH - 200) / 2, testo.getY() + testo.getHeight(), 200, 50);
        menuBT.setForeground(Color.white);
        menuBT.setVisible(true);
        this.add(menuBT);

        // Configurazione del pulsante "LEADERBOARD"
        leaderboardBT.setFont(font.deriveFont(30f));
        leaderboardBT.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 200, menuBT.getY() + menuBT.getHeight() + 15, 400, 50);
        leaderboardBT.setForeground(Color.white);
        leaderboardBT.setVisible(true);
        this.add(leaderboardBT);

        // Configurazione del pulsante "EXIT"
        exitBT.setFont(font.deriveFont(30f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 100, leaderboardBT.getY() + leaderboardBT.getHeight() + 15, 200, 50);
        exitBT.setForeground(Color.white);
        exitBT.setVisible(true);
        this.add(exitBT);

        // Configurazione del cursore
        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth() * 2, cursorImg.getIconWidth() * 2, Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(leaderboardBT.getX() - 100, menuBT.getY(), cursorImg.getIconWidth() * 10, menuBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);
    }

    /**
     * Sposta il cursore in alto, selezionando l'opzione precedente.
     * Il cursore si muove solo se non si trova già sulla prima opzione.
     */
    public void cursorUp() {
        if (selectedOption > 0) {
            selectedOption--;
            cursor.setLocation(cursor.getX(), cursor.getY() - 65);
        }
    }

    /**
     * Sposta il cursore in basso, selezionando l'opzione successiva.
     * Il cursore si muove solo se non si trova già sull'ultima opzione.
     */
    public void cursorDown() {
        if (selectedOption < 2) {
            selectedOption++;
            cursor.setLocation(cursor.getX(), cursor.getY() + 65);
        }
    }

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
     *
     * @return l'indice dell'opzione selezionata, da 0 a 4
     */
    public int getSelectedOption() {
        return selectedOption;
    }
}

