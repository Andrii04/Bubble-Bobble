package MODEL;
import javax.swing.ImageIcon;

/**
 * La classe `Block` rappresenta un blocco nel gioco, che può essere utilizzato come parte del livello.
 * Ogni blocco ha una posizione, una dimensione fissa e un'icona grafica che ne determina l'aspetto.
 * I blocchi possono essere solidi, il che significa che possono bloccare il movimento dei personaggi o degli oggetti.
 */
public class Block {

    // L'icona che rappresenta la grafica del blocco
    private ImageIcon skin;

    // Posizione x del blocco
    private int x;

    // Posizione y del blocco
    int y;

    // Larghezza fissa del blocco
    public static final int WIDTH = 16;

    // Altezza fissa del blocco
    public static final int HEIGHT = 16;

    // Indica se il blocco è solido (true se non attraversabile)
    boolean solid;

    /**
     * Costruttore della classe `Block`.
     * Inizializza il blocco con un'icona grafica e lo imposta come solido.
     *
     * @param skin l'immagine che rappresenta il blocco
     */
    public Block(ImageIcon skin) {
        this.skin = skin;
        this.x = x; // la posizione potrebbe essere impostata separatamente
        this.y = y; // la posizione potrebbe essere impostata separatamente
        solid = true;
    }

    /**
     * Restituisce l'icona del blocco.
     *
     * @return l'oggetto `ImageIcon` che rappresenta la grafica del blocco
     */
    public ImageIcon getSkin() {
        return skin;
    }

}
