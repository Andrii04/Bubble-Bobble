package VIEW;
import MODEL.Block;
import MODEL.Bubbles.*;
import MODEL.Enemy.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BubbleView {

    Bubble bubble;
    ImageIcon currentSkin;
    String currentSkinPath;
    boolean firing;
    boolean encapsulate;
    boolean exploding;
    boolean floating;
    int distanceTraveled;
    boolean facingRight;

    int shootingSpeed = 9;
    int floatingSpeed = 3;

    public BubbleView(Bubble bubble) {
        this.bubble = bubble;
        String currentSkinPath = bubble.getSkinsPath() + "1.png";
        // test
        System.out.println(currentSkinPath);  // null

        currentSkin = new ImageIcon(currentSkinPath); // getClass().getResource(currentSkinPath)
        firing = false;
        exploding = false;
        floating = false;
        encapsulate = false;
        distanceTraveled = 0;
    }

    public void startFiring() {
        distanceTraveled = 0;  // Reset della distanza percorsa
        facingRight = bubble.getPlayer().getFacingRight();
        currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));  // Imposta l'immagine iniziale
    }

    public void setFloatingIMG() {
        String currentSkinPath = bubble.getSkinsPath() + "3.png";
        Image floatingBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image floatingBubbleIMGresized = floatingBubbleIMGoriginal.getScaledInstance(
                50,
                50,
                Image.SCALE_DEFAULT
        );
        currentSkin = new ImageIcon(floatingBubbleIMGresized);
    }


    public void draw(Graphics2D g2d) {
        if (firing) g2d.drawImage(currentSkin.getImage(), bubble.getX(), bubble.getY(), null);
    }

    public void update() {
        if (firing && distanceTraveled < MainFrame.FRAME_WIDTH - Block.WIDTH - 400) {
            // Aggiorna la posizione della bolla
            if (facingRight) {
                //aggiungere che se collision con blocco -> jumpa a if floating
                //e se collision con enemy -> jumpa a if encapsulate
                //posso fare che da qua chiamo un metodo di Bubble che checka se sto per
                //avere una collision con un blocco o un nemico, e poi setta
                //floating o encapsulate di conseguenza, e qua jumpa all'if rispettivo.
                bubble.updateLocation(bubble.getX() + shootingSpeed, bubble.getY());
            } else if (!facingRight) {
                //aggiungere che se collision con blocco -> jumpa a if floating
                //e se collision con enemy -> jumpa a if encapsulate
                bubble.updateLocation(bubble.getX() - shootingSpeed, bubble.getY());
            }

            // Aggiorna l'immagine della bolla in base alla distanza percorsa
            if (distanceTraveled >= 3 && distanceTraveled < 7) {
                currentSkinPath = bubble.getSkinsPath() + "2.png";
                currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            } else if (distanceTraveled >= 7) {
                currentSkinPath = bubble.getSkinsPath() + "3.png";
                currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            }

            distanceTraveled++;  // Incrementa la distanza percorsa

            // Ferma l'animazione una volta che la bolla ha completato il suo viaggio
            if (distanceTraveled >= MainFrame.FRAME_WIDTH - Block.WIDTH - 400) {
                firing = false;
                //bubble.finishedFiring(); questo metodo fa smettere l'animazione della bolla

                bubble.startFloating();
                //jumpare a if floating
            }
        }
        //jumpa a questo if se la bolla incomincia a floatare lenta
        else if (floating && bubble.getX() < MainFrame.FRAME_WIDTH - 400 && bubble.getY() < MainFrame.FRAME_HEIGHT - 400) {

            //faccio il codice delle collision in Bubble e lo uso come check nella BubbleView
            //oppure faccio che viene checkato tutto in Bubble e qua faccio semplicemente updateLocation()

            //se Collida sopra
                //se collida a destra
                    bubble.updateLocation(bubble.getX()-floatingSpeed, bubble.getY());
                    facingRight = false;
                //se collida a sinistra
                    bubble.updateLocation(bubble.getX()+floatingSpeed, bubble.getY());
                    facingRight = true;
                //else
                    //se facingRight
                        bubble.updateLocation(bubble.getX()+floatingSpeed, bubble.getY());
                    //se !facingRight
                        bubble.updateLocation(bubble.getX()-floatingSpeed, bubble.getY());

            //se Collida sotto
        }

        //jumpa a questo if se incapsula un nemico
        else if (encapsulate) {

        }

        //jumpa a questo if se la bolla esplode
        else if (exploding) {

        }
    }

    public void setEncapsulate(boolean bool) {encapsulate = bool;}
    public void setExploding(boolean bool) {exploding = bool;}
    public void setFiring(boolean bool) {firing = bool;}
    public void setFloating(boolean bool) {floating = bool;}
}






