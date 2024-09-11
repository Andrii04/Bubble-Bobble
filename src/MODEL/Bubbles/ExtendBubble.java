package MODEL.Bubbles;

import MODEL.Player;
import VIEW.SpawnedBubbleView;

/**
 * Rappresenta una bolla speciale che viene generata automaticamente e ha una lettera associata.
 * <p>Questa bolla si comporta come una {@link SpawnedBubble} e viene utilizzata per collezionare lettere che possono influenzare il gioco.</p>
 */
public class ExtendBubble extends SpawnedBubble {
    private String letter;

    {
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/extend";
    }

    /**
     * Crea una nuova istanza di {@code ExtendBubble} associata al giocatore specificato e alla lettera data.
     *
     * @param player Il giocatore a cui è associata la bolla.
     * @param letter La lettera associata alla bolla.
     */
    public ExtendBubble(Player player, String letter) {
        super(player);
        this.letter = letter;
        this.bubbleView = new SpawnedBubbleView(this);
        bubbleView.setExtendBubbleSkin(letter);
        floating = true;
        hitbox.setSize(45, 45);
    }

    /**
     * Avvia l'effetto della bolla, aggiungendola alla collezione del giocatore e poi rimuovendo la bolla.
     */
    @Override
    public void startEffect() {
        player.addExtendBubble(this); // Aggiungi la bolla alla collezione del giocatore
        erase();
    }

    /**
     * Aggiorna la posizione dell'effetto della bolla.
     * <p>In questa implementazione, il metodo non ha effetto e non esegue alcuna operazione.</p>
     */
    @Override
    public void updateEffectLocation() {
    }

    /**
     * Crea una nuova istanza di {@code ExtendBubble} associata al giocatore specificato e alla stessa lettera.
     *
     * @param player Il giocatore a cui è associata la nuova bolla.
     * @return Una nuova istanza di {@code ExtendBubble} con la stessa lettera.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new ExtendBubble(player, letter); // Crea nuova bolla con stessa lettera
    }

    /**
     * Restituisce la lettera associata alla bolla.
     *
     * @return La lettera associata alla bolla.
     */
    public String getLetter() {
        return letter;
    }
}
