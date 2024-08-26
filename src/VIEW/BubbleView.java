package VIEW;
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

   /* public void fireBubble() {
        bubble.updateLocation(bubble.getPlayer().getX()+20, bubble.getPlayer().getY()-15);
        firing = true;
        //viaggiano 6 blocchi, ogni 2 blocchi cambia l'immagine
        int distanceTraveled = 0;


        while (distanceTraveled < 6) {
            if (distanceTraveled > 2 && distanceTraveled < 5) {
                currentSkinPath = bubble.getSkinsPath() + "2.png";
                currentSkin = new ImageIcon(currentSkinPath);
            } else if (distanceTraveled > 4) {
                currentSkinPath = bubble.getSkinsPath() + "3.png";
                currentSkin = new ImageIcon(currentSkinPath);
            }
            distanceTraveled++;
            bubble.updateLocation(bubble.getX() + 1, bubble.getY() + 1);
            this.x = bubble.getX();
            this.y = bubble.getY();

        }
    }*/
   public void fireBubble() {
       bubble.updateLocation(bubble.getPlayer().getX() + 20, bubble.getPlayer().getY() - 15);
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
        if (firing && distanceTraveled < 6) {
            // Aggiorna la posizione della bolla
            bubble.updateLocation(bubble.getX() + 1, bubble.getY() + 1);
            this.x = bubble.getX();
            this.y = bubble.getY();

            // Aggiorna l'immagine della bolla in base alla distanza percorsa
            if (distanceTraveled >= 2 && distanceTraveled < 4) {
                currentSkinPath = bubble.getSkinsPath() + "2.png";
                currentSkin = new ImageIcon(currentSkinPath);
            } else if (distanceTraveled >= 4) {
                currentSkinPath = bubble.getSkinsPath() + "3.png";
                currentSkin = new ImageIcon(currentSkinPath);
            }

            distanceTraveled++;  // Incrementa la distanza percorsa

            // Ferma l'animazione una volta che la bolla ha completato il suo viaggio
            if (distanceTraveled >= 6) {
                firing = false;
            }
        }
    }


    }





