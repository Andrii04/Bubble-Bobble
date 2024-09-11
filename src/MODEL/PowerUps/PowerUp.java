package MODEL.PowerUps;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Level;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.PowerUpView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * La classe PowerUp è una classe astratta che rappresenta un PowerUp nel gioco.
 * Un PowerUp è un oggetto che può essere raccolto dal giocatore e fornisce effetti speciali.
 */
public abstract class PowerUp {

    // Coordinate del PowerUp
    int x;
    int y;
    // Icona che rappresenta il PowerUp
    ImageIcon skin;
    // Riferimento al giocatore corrente
    Player player;
    // Stato di attivazione del PowerUp
    boolean active;
    // Rettangolo di collisione per il PowerUp
    Rectangle hitbox;
    // Stato di esplosione del PowerUp
    boolean explode;
    // Velocità di caduta del PowerUp
    int fallingSpeed = 4;
    // Riferimento al livello corrente
    Level currentLevel;
    // Stato del PowerUp riguardo al suolo
    boolean onFloor;
    // Stato di rimozione del PowerUp
    boolean erased;
    // Vista del PowerUp
    PowerUpView powerUpView;
    // Riferimento al gestore dello stato del gioco
    GameStateManager gsm;

    /**
     * Costruttore della classe PowerUp. Inizializza le variabili e posiziona il PowerUp casualmente.
     */
    public PowerUp() {
        gsm = GameStateManager.getInstance(); // Ottiene l'istanza del gestore dello stato del gioco
        currentLevel = gsm.getCurrentLevel(); // Ottiene il livello corrente
        player = gsm.getCurrentPlayer(); // Ottiene il giocatore corrente

        Random random = new Random();
        // Posiziona il PowerUp in una posizione casuale all'interno del livello
        x = random.nextInt(Block.WIDTH + 30, MainFrame.FRAME_HEIGHT - Block.WIDTH - 30);
        y = random.nextInt(Block.HEIGHT + 30, MainFrame.FRAME_HEIGHT - Block.HEIGHT - 30);
        active = false;
        explode = false;
        onFloor = false;
        erased = false;
        hitbox = new Rectangle(x, y, 30, 30); // Crea il rettangolo di collisione
        powerUpView = new PowerUpView(this); // Crea la vista del PowerUp
    }

    /**
     * Metodo astratto per attivare l'effetto del PowerUp. Deve essere implementato dalle sottoclassi.
     */
    public abstract void activateEffect();

    // Getter per le coordinate X
    public int getX() { return x; }

    // Getter per le coordinate Y
    public int getY() { return y; }

    // Getter per l'icona del PowerUp
    public ImageIcon getSkin() { return skin; }

    // Getter per lo stato di attivazione del PowerUp
    public boolean isActive() { return active; }

    // Getter per il rettangolo di collisione
    public Rectangle getHitbox() { return hitbox; }

    // Getter per lo stato di esplosione del PowerUp
    public boolean isExploding() { return explode; }

    // Getter per la velocità di caduta del PowerUp
    public int getFallingSpeed() { return fallingSpeed; }

    // Getter per lo stato del PowerUp riguardo al suolo
    public boolean isOnFloor() { return onFloor; }

    // Getter per lo stato di rimozione del PowerUp
    public boolean isErased() { return erased; }

    /**
     * Aggiorna la posizione del PowerUp. Gestisce la caduta e la collisione con il suolo.
     * Attiva l'effetto del PowerUp se interagisce con il giocatore.
     */
    public void updateLocation() {
        if(gsm.getPlayer() != null && player == null) {
            player = gsm.getPlayer(); // Riferimento al giocatore se non è inizializzato
        }
        if (!onFloor) {
            y += fallingSpeed; // Aggiorna la posizione verticale del PowerUp
            hitbox.setLocation(x, y); // Aggiorna la posizione del rettangolo di collisione
        }

        // Verifica se il PowerUp ha raggiunto un blocco solido e imposta il PowerUp come "sul suolo"
        if (!onFloor && currentLevel.isItSolidBlock(y / Block.HEIGHT, x / Block.WIDTH)) {
            y -= Block.HEIGHT * 2;
            hitbox.setLocation(x, y);
            onFloor = true;
        }

        // Controlla la collisione con il giocatore e attiva l'effetto se c'è una collisione
        if (hitbox.intersects(player.getHitbox())) {
            explode();
            activateEffect();
        }
    }

    /**
     * Rimuove il PowerUp dal gioco e lo segna come eliminato.
     */
    public void erase() {
        explode = false;
        erased = true;
        currentLevel.despawnPowerUp(this); // Rimuove il PowerUp dal livello corrente
    }

    /**
     * Imposta lo stato di esplosione del PowerUp e aggiorna la vista di esplosione.
     */
    public void explode() {
        explode = true;
        powerUpView.setExplodeIMG(); // Aggiorna la vista per mostrare l'esplosione
    }

    /**
     * Imposta l'icona del PowerUp, ridimensionandola se necessario.
     * @param skin L'icona da impostare
     */
    public void setSkin(ImageIcon skin) {
        Image resizedSkin = skin.getImage().getScaledInstance(
                30,
                30,
                Image.SCALE_SMOOTH
        );
        this.skin = new ImageIcon(resizedSkin);
    }

    // Getter per la vista del PowerUp
    public PowerUpView getPowerUpView() { return powerUpView; }

    /**
     * Ferma l'effetto di esplosione del PowerUp.
     */
    public void stopExplosion() {
        explode = false;
    }
}