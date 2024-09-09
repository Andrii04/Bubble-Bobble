package VIEW;

import javax.swing.*;
import java.awt.*;

/**
 * La classe PausePanel rappresenta il pannello di pausa del gioco.
 * Viene visualizzato quando il gioco è in pausa e offre opzioni
 * come continuare e uscire dal gioco.
 */
public class PausePanel extends JPanel {

    private Font font = MainFrame.getPixelFont(); // Font personalizzato usato nel pannello
    private final JLabel testo = new JLabel("PAUSE"); // Label per il testo "PAUSE"
    private final JLabel gameBT = new JLabel("CONTINUE"); // Pulsante per continuare il gioco
    private final JLabel exitBT = new JLabel("EXIT"); // Pulsante per uscire senza salvare
    private final JLabel cursor = new JLabel(); // Label per il cursore di selezione
    private int selectedOption = 0; // Opzione selezionata attualmente

    /**
     * Costruttore della classe PausePanel.
     * Imposta la dimensione, il layout, e inizializza i componenti grafici
     * come pulsanti e testo.
     */
    public PausePanel() {

        // Imposta le dimensioni e le proprietà del pannello
        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.black);
        this.setLayout(null); // Layout libero per il posizionamento manuale dei componenti
        this.setVisible(true);

        // Impostazione del testo "PAUSE"
        testo.setFont(font.deriveFont(60f)); // Font dimensionato per il titolo
        testo.setHorizontalAlignment(SwingConstants.CENTER); // Allineamento centrato
        testo.setBounds(MainFrame.FRAME_WIDTH / 2 - 150, 30, 300, 250); // Posizione e dimensione
        testo.setForeground(Color.YELLOW); // Colore del testo
        testo.setVisible(true);
        this.add(testo);

        // Impostazione del pulsante "CONTINUE"
        gameBT.setFont(font.deriveFont(30f));
        gameBT.setHorizontalAlignment(SwingConstants.CENTER);
        gameBT.setBounds((MainFrame.FRAME_WIDTH) / 2 - 150, testo.getY() + testo.getHeight(), 300, 50);
        gameBT.setForeground(Color.white);
        gameBT.setVisible(true);
        this.add(gameBT);


        // Impostazione del pulsante "EXIT"
        exitBT.setFont(font.deriveFont(30f));
        exitBT.setHorizontalAlignment(SwingConstants.CENTER);
        exitBT.setBounds(MainFrame.FRAME_WIDTH / 2 - 100, gameBT.getY() + gameBT.getHeight() + 15, 200, 50);
        exitBT.setForeground(Color.white);
        exitBT.setVisible(true);
        this.add(exitBT);

        // Impostazione del cursore
        ImageIcon cursorImg = new ImageIcon(getClass().getResource("/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        Image cursorScaled = cursorImg.getImage().getScaledInstance(cursorImg.getIconWidth() * 2, cursorImg.getIconWidth() * 2, Image.SCALE_SMOOTH);
        cursor.setIcon(new ImageIcon(cursorScaled));
        cursor.setHorizontalAlignment(SwingConstants.CENTER);
        cursor.setBounds(gameBT.getX() - 100, gameBT.getY(), cursorImg.getIconWidth() * 10, gameBT.getHeight());
        cursor.setVisible(true);
        this.add(cursor);
    }

    /**
     * Metodo per spostare il cursore verso l'alto.
     * Seleziona l'opzione precedente nella lista.
     */
    public void cursorUp(){
        if (selectedOption > 0) {
            selectedOption--; // Aggiorna l'opzione selezionata
            cursor.setLocation(cursor.getX(), cursor.getY() - MainFrame.TILE_SIZE); // Sposta il cursore
        }
    }

    /**
     * Metodo per spostare il cursore verso il basso.
     * Seleziona l'opzione successiva nella lista.
     */
    public void cursorDown(){
        if (selectedOption < 2) {
            selectedOption++; // Aggiorna l'opzione selezionata
            cursor.setLocation(cursor.getX(), cursor.getY() + MainFrame.TILE_SIZE); // Sposta il cursore
        }
    }

    /**
     * Restituisce l'opzione attualmente selezionata.
     *
     * @return un intero rappresentante l'opzione selezionata
     */
    public int getSelectedOption(){
        return selectedOption;
    }

}

