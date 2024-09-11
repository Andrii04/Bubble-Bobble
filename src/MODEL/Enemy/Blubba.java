package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Blubba} rappresenta un nemico volante che si muove diagonalmente e cambia direzione di 90 gradi quando colpisce un muro.
 *
 * <p>La classe estende {@code Enemy} e implementa un nemico che può volare e muoversi in diagonale, cambiando direzione se incontra ostacoli.
 */
public class Blubba extends Enemy {
    // vola
    // si muove diagonalmente e colpito un muro gira 90 gradi

    private boolean goUp;
    private final int points = 5000;

    /**
     * Crea un'istanza di {@code Blubba} con la posizione, la direzione e il gestore dello stato del gioco specificati.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public Blubba(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 2;
    }

    /**
     * Crea un'istanza di {@code Blubba} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public Blubba(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }

    /**
     * Gestisce l'inseguimento del giocatore.
     * Se è necessario ricalcolare il percorso più breve o se il percorso più breve è vuoto, trova un nuovo percorso.
     * Altrimenti, segue il percorso e aggiorna la direzione e l'azione in base alla posizione del nodo successivo.
     */
    void chasePlayer() {
        if (shouldRetracePath() || shortestPath.isEmpty()) {
            findShortestPath();
        } else {
            Node nextNode = shortestPath.get(0);
            if (isAtNode(nextNode, x, y)) {
                shortestPath.remove(0);
            }
            if (nextNode.y > y) {
                goUp = false;
            } else if (nextNode.y < y) {
                goUp = true;
            }
            if (nextNode.x > x) {
                facingRight = true;
            } else if (nextNode.x < x) {
                facingRight = false;
            }
            updateAction(Action.WALK);
        }
    }

    /**
     * Restituisce i vicini di un nodo dato.
     *
     * @param node Il nodo di cui trovare i vicini.
     * @return Una lista di nodi vicini.
     */
    List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, -1}, {1, 1}, {1, -1}, {-1, 1}};
        for (int[] direction : directions) {
            int newX = node.x + direction[0] * Block.WIDTH;
            int newY = node.y + direction[1] * Block.HEIGHT;
            if (isWithinBounds(newX, newY)) {
                if (!isSolidTile(newX, newY)) {
                    neighbors.add(new Node(newX, newY, 0, 0, node));
                } else {
                    if (isSolidTile(node.x, newY - Block.HEIGHT) || isSolidTile(node.x, newY + Block.HEIGHT)) {
                        neighbors.add(new Node(newX, -newY, 0, 0, node));
                    } else if (isSolidTile(newX - Block.WIDTH, node.y) || isSolidTile(newX + Block.WIDTH, node.y)) {
                        neighbors.add(new Node(-newX, newY, 0, 0, node));
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Gestisce il movimento del nemico.
     * Aggiorna la posizione del nemico in base alla direzione e alla variabile {@code goUp}.
     * Imposta la posizione dell'area di collisione e notifica gli osservatori dell'azione di camminata.
     */
    void walk() {
        if (facingRight) {
            x += speed;
        } else {
            x -= speed;
        }
        if (goUp) {
            y -= speed;
        } else {
            y += speed;
        }
        hitbox.setLocation(x, y);
        if (enraged) {
            notifyObservers(Action.RAGE);
        } else {
            notifyObservers(Action.WALK);
        }
    }
}