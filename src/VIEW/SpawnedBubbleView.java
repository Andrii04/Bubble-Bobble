package VIEW;

import MODEL.Bubbles.Bubble;

import javax.swing.*;
import java.awt.*;

/**
 * La classe SpawnedBubbleView estende BubbleView e rappresenta la visualizzazione
 * di una bolla che è stata generata automaticamente (spawned). Gestisce l'animazione
 * e l'aggiornamento dello stato della bolla durante il gioco.
 */
public class SpawnedBubbleView extends BubbleView {

    /**
     * Costruttore della classe SpawnedBubbleView.
     *
     * @param bubble La bolla da visualizzare.
     */
    public SpawnedBubbleView(Bubble bubble) {
        super(bubble);
        floating = true;  // Imposta la bolla come in fase di flottamento
    }

    /**
     * Aggiorna lo stato della bolla in base alla sua fase attuale:
     * - Se la bolla è in fase di flottamento, gestisce la collisione e incrementa
     *   la distanza percorsa.
     * - Se la bolla è in fase di esplosione, gestisce l'animazione di esplosione.
     * - Se la bolla ha un effetto, aggiorna la posizione dell'effetto.
     * - Se la bolla ha un nemico intrappolato e il nemico è esploso, gestisce la
     *   logica associata all'esplosione del nemico.
     * - Se la bolla è in fase di pom, gestisce l'animazione di pom.
     */
    @Override
    public void update() {
        if (floating) {
            // Gestisce la collisione durante la fase di flottamento e incrementa la distanza percorsa
            bubble.handleFloatingCollision();
            distanceTraveled++;
        } else if (exploding) {
            // Gestisce l'animazione di esplosione
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 20) {
                bubble.stopExplosion();
                if (bubble.getHitWall()) {
                    bubble.erase();  // Cancella la bolla se ha colpito un muro
                } else {
                    bubble.startEffect();  // Avvia l'effetto se la bolla non ha colpito un muro
                }
            }
        } else if (bubble.isEffect()) {
            // Aggiorna la posizione dell'effetto della bolla
            bubble.updateEffectLocation();
        } else if (bubble.getBubbledEnemy() != null && bubble.getBubbledEnemy().isExploded()) {
            // Gestisce la logica quando il nemico intrappolato esplode
            bubble.pom();
        } else if (pom) {
            // Gestisce l'animazione di pom
            pomAnimationTimer++;
            if (pomAnimationTimer >= 80) {
                bubble.erase();  // Cancella la bolla dopo che l'animazione di pom è completata
            }
        }
    }
}