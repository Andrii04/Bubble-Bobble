package VIEW;

import MODEL.Enemy.Enemy;
import MODEL.Enemy.SuperDrunk;
import MODEL.Entity;
import MODEL.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe astratta EnemyView che rappresenta la vista grafica di un nemico nel gioco.
 * Implementa il pattern Observer per aggiornare la posizione e l'azione del nemico in base ai cambiamenti nell'oggetto Enemy.
 */
public abstract class EnemyView implements Observer {
    int x; // Posizione X del nemico
    int y; // Posizione Y del nemico
    Enemy enemy; // Oggetto nemico associato
    Player player; // Riferimento al giocatore per eventuali interazioni
    Entity.Action currentAction; // Azione corrente del nemico
    int currentFrame; // Frame corrente dell'animazione
    long lastTime; // Ultimo aggiornamento temporale per il frame
    final int frameDelay = 100; // Ritardo tra i frame dell'animazione in millisecondi

    /**
     * Costruttore della classe EnemyView.
     *
     * @param enemy Oggetto nemico associato a questa vista.
     */
    public EnemyView(Enemy enemy) {
        this.enemy = enemy;
        this.x = enemy.getX();
        this.y = enemy.getY();
        this.player = enemy.getPlayer();
        currentFrame = 0;
        loadImages(); // Metodo per caricare le immagini dell'animazione
        enemy.addObserver(this); // Registra la vista come osservatore del nemico
        currentAction = Entity.Action.MOVE_RIGHT; // Imposta l'azione corrente predefinita
    }

    /**
     * Scala un'immagine raddoppiando le sue dimensioni.
     *
     * @param img L'immagine originale da scalare.
     * @return L'immagine scalata.
     */
    BufferedImage scaleImage(BufferedImage img) {
        BufferedImage scaledImage = new BufferedImage(img.getWidth() * 2, img.getHeight() * 2, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, img.getWidth() * 2, img.getHeight() * 2, null);
        g2d.dispose();
        return scaledImage;
    }

    /**
     * Metodo astratto per caricare le immagini delle animazioni. Da implementare nelle sottoclassi.
     */
    void loadImages() {
    }

    /**
     * Metodo di aggiornamento richiamato quando ci sono cambiamenti nell'oggetto osservato.
     * Aggiorna la posizione e l'azione del nemico in base ai cambiamenti.
     *
     * @param o   L'oggetto osservato (Enemy).
     * @param arg L'argomento passato, tipicamente l'azione corrente del nemico (Entity.Action).
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Enemy) {
            Enemy enemy = (Enemy) o;
            this.x = enemy.getX();
            this.y = enemy.getY();
        }
        if (arg instanceof Entity.Action) {
            Entity.Action action = (Entity.Action) arg;
            this.currentAction = action;
        }
    }

    /**
     * Disegna l'animazione corrente del nemico su una superficie grafica.
     *
     * @param g2d L'oggetto Graphics2D utilizzato per disegnare.
     */
    public void draw(Graphics2D g2d) {
        BufferedImage[] currentAnimation = getCurrentAnimation(); // Ottieni l'animazione corrente
        if (currentAnimation != null && currentAnimation.length > 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime > frameDelay) {
                currentFrame = (currentFrame + 1) % currentAnimation.length; // Cambia frame
                lastTime = currentTime;
            }
            if (currentFrame >= currentAnimation.length) {
                currentFrame = 0;
            }
            g2d.drawImage(currentAnimation[currentFrame], x, y, null); // Disegna il frame corrente
            g2d.draw(enemy.getHitbox()); // Disegna la hitbox del nemico
        } else {
            System.out.println("No animation found"); // Stampa un messaggio se non ci sono animazioni
        }
    }

    /**
     * Metodo astratto per ottenere l'animazione corrente del nemico.
     * Da implementare nelle sottoclassi.
     *
     * @return Un array di BufferedImage che rappresenta i frame dell'animazione corrente.
     */
    public BufferedImage[] getCurrentAnimation() {
        return null;
    }
}