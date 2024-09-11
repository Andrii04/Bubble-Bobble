package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Enemy.Enemy;
import java.awt.*;
import MODEL.Enemy.SuperDrunk;
import MODEL.Level;
import VIEW.BubbleView;
import MODEL.Entity.Action;
import MODEL.Player;


/**
 * Classe astratta che rappresenta una bolla nel gioco. Le sottoclassi di Bubble
 * devono implementare i dettagli specifici delle diverse tipologie di bolle.
 */
public abstract class Bubble {

    int x;
    int y;
    BubbleView bubbleView;
    Player player;
    Enemy enemy;
    GameStateManager gsm;
    String skinsPath; //sarà il path della skin senza il numero alla fine
    Level currentLevel;
    boolean erased;

    int shootingSpeed;
    int floatingSpeed;
    Rectangle hitbox;

    Enemy bubbledEnemy;

    boolean effect;

    boolean exploding;
    boolean floating;
    boolean firing;
    boolean encapsulate;
    boolean facingUp;
    boolean pom;
    boolean hitWall;
    boolean facingRight;

    /**
     * Costruttore per inizializzare una bolla associata a un giocatore.
     *
     * @param player Il giocatore associato alla bolla.
     */
    public Bubble(Player player) {
        //gsm = GameStateManager.getInstance();
        //currentLevel = gsm.getCurrentLevel();

        hitbox = new Rectangle(x, y, 17, 18);

        firing = false;
        floating = false;
        encapsulate = false;
        exploding = false;
        facingUp = true;
        pom = false;
        effect = false;

        erased = false;
        hitWall = false;
        facingRight = true;

        this.player = player;
    }

    /**
     * Gestisce l'esplosione della bolla, aggiornando lo stato e la vista.
     */
    public void explode() {
        floating = false;
        firing = false;
        exploding = true;
        encapsulate = false;
        bubbleView.setEncapsulate(false);
        bubbleView.setFloating(false);
        bubbleView.setFiring(false);
        bubbleView.setExploding(true);

        bubbleView.setExplodeIMG();
        if (bubbledEnemy != null) {
            bubbledEnemy.setBubbled(false);
            bubbledEnemy.updateAction(Action.DIE);
        }

        if (this instanceof GreenBubble) player.bubbleExploded();
        else if (this instanceof WaterBubble) player.waterBubbleExploded();
        else if (this instanceof LightningBubble) player.lightningBubblesExploded();

        if (player != null && this instanceof GreenBubble && player.isRedRing()) {
            player.setPunteggio(player.getPunteggio() + 100);
        }
    }


    /**
     * Attiva l'effetto "pom" della bolla, aggiornando lo stato e la vista.
     */
    public void pom() {
        if (bubbledEnemy != null) bubbledEnemy.setExploded(false);

        pom = true;
        bubbleView.setPom(true);

        bubbleView.setPomIMG();
        if (bubbledEnemy != null) player.setPunteggio(player.getPunteggio()+500);
        else player.setPunteggio(player.getPunteggio() + 100);
    }

    /**
     * Incapsula un nemico all'interno della bolla, aggiornando lo stato e la vista.
     *
     * @param enemy Il nemico da incapsulare.
     */
    public void encapsule(Enemy enemy) {
        bubbledEnemy = enemy;
        enemy.setBubbled(true);

        firing = false;
        floating = true;
        encapsulate = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setEncapsulate(true);

        //bubbleView.setEncapsuleIMG();
        hitbox.setSize(bubbleView.getCurrentSkin().getIconWidth(),
                bubbleView.getCurrentSkin().getIconHeight());

        if (!(enemy instanceof SuperDrunk)) enemy.updateAction(Action.BUBBLED);
    }

    /**
     * Inizia il galleggiamento della bolla.
     */
    public void startFloating() {
        firing = false;
        floating = true;
        bubbleView.setFiring(false);
        bubbleView.setFloating(true);
        bubbleView.setFloatingIMG();
        hitbox.setSize(44, 45);

    }

    /**
     * Aggiorna la posizione della bolla e gestisce le collisioni.
     *
     * @param x Nuova coordinata x della bolla.
     * @param y Nuova coordinata y della bolla.
     */
    public void updateLocation(int x, int y) {
        if (currentLevel == null) currentLevel = GameStateManager.getInstance().getCurrentLevel();
        if (player == null) player = GameStateManager.getInstance().getCurrentPlayer();

        try {
            player.getHitbox();
        } catch (NullPointerException e) {
            System.out.println("must assign the player to the Bubble!");
        }

        this.x = x;
        this.y = y;
        hitbox.setLocation(x, y);

        if (firing && currentLevel.isLevelWall(this, x)) {

            if (x == 1) updateLocation(3, y);
            else if (x == currentLevel.getPattern()[0].length-2) updateLocation(
                    currentLevel.getPattern()[0].length-4, y);

            explode();
            pom();
            return;
        }

        if (floating
                && !encapsulate
                && hitbox.intersects(player.getHitbox())
                && player.getCurrentAction().equals(Action.JUMP)) {

            player.setIsOnFloor(true);
            player.updateAction(Action.JUMP);

        }

        if (firing) {
            for (Enemy enemy : currentLevel.getEnemies()) {
                if (enemy != null) {
                    if ( !(enemy instanceof SuperDrunk) &&
                            hitbox.intersects(enemy.getHitbox()) && !enemy.isBubbled()) {
                        if (enemy.isEnraged()) enemy.setEnraged(false);
                        encapsule(enemy);
                        return;
                    }
                }
            }
            for (SpawnedBubble bubble : currentLevel.getSpawnedBubbles()) {
                if (bubble != null && hitbox.intersects(bubble.getHitbox())) {
                    bubble.explode();
                    explode();
                }
            }
        }

        if (floating && encapsulate) {
            if (bubbledEnemy.isEnraged()) {
                bubbledEnemy = null;
                explode();
            }
            else bubbledEnemy.setPosition(x, y);
        }
        if (floating && encapsulate && hitbox.intersects(player.getHitbox())) {
            explode();
        }
    }

    /**
     * Restituisce la coordinata x della bolla.
     *
     * @return La coordinata x della bolla.
     */
    public int getX() {
        return x;
    }

    /**
     * Restituisce la coordinata y della bolla.
     *
     * @return La coordinata y della bolla.
     */
    public int getY() {
        return y;
    }

    /**
     * Restituisce il percorso della skin della bolla.
     *
     * @return Il percorso della skin della bolla.
     */
    public String getSkinsPath() {
        return skinsPath;
    }

    /**
     * Restituisce la vista associata alla bolla.
     *
     * @return La vista della bolla.
     */
    public BubbleView getBubbleView() {
        return bubbleView;
    }

    /**
     * Restituisce il giocatore associato alla bolla.
     *
     * @return Il giocatore associato alla bolla.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Restituisce se la bolla è stata cancellata.
     *
     * @return true se la bolla è cancellata, false altrimenti.
     */
    public boolean getErased() {return erased;}

    /**
     * Imposta il giocatore associato alla bolla.
     *
     * @param player Il nuovo giocatore da associare alla bolla.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Crea una nuova istanza della bolla per il giocatore specificato.
     *
     * @param player Il giocatore per cui creare una nuova bolla.
     * @return Una nuova istanza della bolla.
     */
    public abstract Bubble newInstance(Player player);

    /**
     * Inizia il tiro della bolla, aggiornando la posizione e la vista.
     */
    public void fireBubble() {
        firing = true;

        try {
            updateLocation(getPlayer().getX() + 18, getPlayer().getY() + 20);
        } catch (NullPointerException e)
        {System.out.println("need to set" +
                " the player for the bubble, use method" +
                " bubble.setPlayer(Player");}

        bubbleView.startFiring();
        bubbleView.setFiring(true);
    }

    /**
     * Cancella la bolla, disattivando tutti gli effetti e aggiornando la vista.
     */
    public void erase() {
        exploding = false;
        firing = false;
        floating = false;
        pom = false;
        effect = false;
        bubbleView.setExploding(false);
        bubbleView.setFiring(false);
        bubbleView.setFloating(false);
        bubbleView.setPom(false);

        erased = true;
        if (currentLevel != null) currentLevel.removeBubble(this);
    }

    /**
     * Verifica se un dato punto rappresenta un blocco solido nel livello corrente.
     *
     * @param x Coordinata x del punto da verificare.
     * @param y Coordinata y del punto da verificare.
     * @return true se il punto è un blocco solido, false altrimenti.
     */
    public boolean isSolidTile(int x, int y) {

        currentLevel = GameStateManager.getInstance().getCurrentLevel();
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        if (tileX >= 0 && tileX < currentLevel.getPattern()[0].length && tileY >= 0 && tileY < currentLevel.getPattern().length) {
            if (currentLevel.getBlockInt(tileY, tileX) > 0) {
                return true;
            } else {
                return false;

            }
        }
        return true;
    }

    /**
     * Gestisce la collisione della bolla durante il galleggiamento, aggiornando la posizione e lo stato.
     */
    public void handleFloatingCollision() {
        // Salva le coordinate originali
        currentLevel = GameStateManager.getInstance().getCurrentLevel();
        int originalX = x;
        int originalY = y;

        // Controlla il movimento verticale
        int newY;
        if (facingUp) {
            newY = y - floatingSpeed;
            if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY / Block.HEIGHT, x / Block.WIDTH)) {
                // Se c'è un blocco sopra, inizia a scendere
                facingUp = false;
            } else {
                y = newY;
            }
        } else {
            newY = y + floatingSpeed;
            if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY / Block.HEIGHT, x / Block.WIDTH)) {
                // Se c'è un blocco sotto, inizia a salire
                facingUp = true;
            } else {
                y = newY;
            }
        }

        // Controlla se deve iniziare a muoversi orizzontalmente
        if (isSolidTile(x + floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x + floatingSpeed) / Block.WIDTH)) {
            // Se c'è un blocco a destra, inizia a muoversi a sinistra
            bubbleView.setFacingRight(false);
        } else if (isSolidTile(x - floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x - floatingSpeed) / Block.WIDTH)) {
            // Se c'è un blocco a sinistra, inizia a muoversi a destra
            bubbleView.setFacingRight(true);
        }

        // Movimento orizzontale solo se facingRight è stato impostato
        if (bubbleView.getStartHorizontalMovement() && bubbleView.getFacingRight()) {
            x += floatingSpeed;
        } else if (bubbleView.getStartHorizontalMovement()){
            x -= floatingSpeed;
        }

        // Se la bolla ha un muro sia sopra che a un lato, inizia a strisciare verso il basso lungo il muro
        if ((isSolidTile(x + floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x + floatingSpeed) / Block.WIDTH)) ||
                (isSolidTile(x - floatingSpeed, y) && currentLevel.isItSolidBlock(y / Block.HEIGHT, (x - floatingSpeed) / Block.WIDTH))) {
            newY = y + floatingSpeed;
            if (isSolidTile(x, newY) && currentLevel.isItSolidBlock(newY / Block.HEIGHT, x / Block.WIDTH)) {
                // Se c'è un blocco sotto, rimbalza verso l'alto
                y = originalY - floatingSpeed;
                facingUp = true;
            } else {
                y = newY;
            }
        }

        // Aggiorna la posizione della bolla
        updateLocation(x, y);
    }

    /**
     * Restituisce l'area di collisione della bolla.
     *
     * @return L'area di collisione della bolla.
     */
    public Rectangle getHitbox() {return hitbox;}

    /**
     * Restituisce il nemico incapsulato nella bolla (se presente).
     *
     * @return Il nemico incapsulato, o null se non ce n'è.
     */
    public Enemy getBubbledEnemy() {return bubbledEnemy;}
    /**
     * Interrompe l'esplosione della bolla.
     */
    public void stopExplosion() {
        exploding = false;
        bubbleView.setExploding(false);
    }

    /**
     * Imposta la coordinata x della bolla.
     *
     * @param x La nuova coordinata x della bolla.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Imposta la coordinata y della bolla.
     *
     * @param y La nuova coordinata y della bolla.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Avvia l'effetto speciale della bolla.
     */
    public void startEffect() {
        // Implementazione specifica dell'effetto da parte delle sottoclassi
    }

    /**
     * Aggiorna la posizione dell'effetto speciale della bolla.
     */
    public void updateEffectLocation() {
        // Implementazione specifica dell'aggiornamento della posizione dell'effetto da parte delle sottoclassi
    }

    /**
     * Restituisce se la bolla ha un effetto speciale attivo.
     *
     * @return true se la bolla ha un effetto speciale, false altrimenti.
     */
    public boolean isEffect() {
        return effect;
    }

    /**
     * Imposta il nemico associato alla bolla e aggiorna la vista della bolla.
     *
     * @param enemy Il nemico da associare alla bolla.
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        bubbleView.setEnemy(enemy);
    }

    /**
     * Restituisce se la bolla ha colpito un muro.
     *
     * @return true se la bolla ha colpito un muro, false altrimenti.
     */
    public boolean getHitWall() {
        return hitWall;
    }

    /**
     * Restituisce la velocità di tiro della bolla.
     *
     * @return La velocità di tiro della bolla.
     */
    public int getShootingSpeed() {
        return shootingSpeed;
    }

    /**
     * Restituisce la velocità di galleggiamento della bolla.
     *
     * @return La velocità di galleggiamento della bolla.
     */
    public int getFloatingSpeed() {
        return floatingSpeed;
    }

    /**
     * Metodo vuoto per aggiornare la posizione della bolla.
     * Può essere sovrascritto dalle sottoclassi se necessario.
     */
    public void updateLocation() {
        // Implementazione specifica delle sottoclassi, se necessaria
    }
}
