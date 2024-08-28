package VIEW;
import MODEL.Block;
import MODEL.Bubbles.*;
import MODEL.Enemy.*;

import javax.swing.*;
import java.awt.*;

public class BubbleView {
    int x;
    int y;
    Bubble bubble;
    ImageIcon currentSkin;
    String currentSkinPath;
    boolean firing;
    int distanceTraveled;
    boolean facingRight;

    public BubbleView(Bubble bubble) {
        this.bubble = bubble;
        x = bubble.getX();
        y = bubble.getY();
        String currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(currentSkinPath);
        firing = false;
        distanceTraveled = 0;
    }

    public void encapsule(Enemy enemy) {

    }

    public void explode(Enemy enemy) {


        firing = false;
    }

    public void fireBubble() {
        facingRight = bubble.getPlayer().getFacingRight();
        bubble.updateLocation(bubble.getPlayer().getX() + 23, bubble.getPlayer().getY() + 20);
        firing = true;
        distanceTraveled = 0;  // Reset della distanza percorsa
        currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(currentSkinPath);  // Imposta l'immagine iniziale
    }


    public void draw(Graphics2D g2d) {
        if (firing) g2d.drawImage(currentSkin.getImage(), x, y, null);
    }

    public void updateLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        System.out.println("firing " + "= " + firing);
        if (firing && distanceTraveled < MainFrame.FRAME_WIDTH - Block.WIDTH-252) {
            // Aggiorna la posizione della bolla
            if (facingRight) {
                bubble.updateLocation(bubble.getX() + 3, bubble.getY());
                this.x = bubble.getX();
                this.y = bubble.getY();
            } else if (!facingRight) {
                bubble.updateLocation(bubble.getX() - 3, bubble.getY());
                this.x = bubble.getX();
                this.y = bubble.getY();
            }

            // Aggiorna l'immagine della bolla in base alla distanza percorsa
            if (distanceTraveled >= 21 && distanceTraveled < 36) {
                currentSkinPath = bubble.getSkinsPath() + "2.png";
                currentSkin = new ImageIcon(currentSkinPath);
            } else if (distanceTraveled >= 36) {
                currentSkinPath = bubble.getSkinsPath() + "3.png";
                currentSkin = new ImageIcon(currentSkinPath);
            }

            distanceTraveled++;  // Incrementa la distanza percorsa

            // Ferma l'animazione una volta che la bolla ha completato il suo viaggio
            if (distanceTraveled >= MainFrame.FRAME_WIDTH - Block.WIDTH - 252) {
                firing = false;
            }
        }
    }
}






