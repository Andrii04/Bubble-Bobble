package VIEW;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.*;
import MODEL.Enemy.*;
import javax.swing.*;
import java.awt.*;

/**
 * Rappresenta la vista di una bolla nel gioco, gestendo la sua visualizzazione e animazione.
 */
public class BubbleView {

    Bubble bubble;
    ImageIcon currentSkin;
    String currentSkinPath;
    boolean firing;
    boolean encapsulate;
    boolean exploding;
    boolean floating;
    boolean pom;
    int distanceTraveled;
    boolean facingRight;
    boolean startHorizontalMovement;
    Enemy enemy;

    int shootingSpeed;
    int floatingSpeed;

    int pomAnimationTimer;
    int explodingAnimationTimer;
    int encapsulateTimer;

    /**
     * Costruttore per la vista della bolla.
     *
     * @param bubble La bolla che questa vista rappresenta.
     */
    public BubbleView(Bubble bubble) {
        this.bubble = bubble;
        this.shootingSpeed = bubble.getShootingSpeed();
        this.floatingSpeed = bubble.getFloatingSpeed();
        // test

        firing = false;
        exploding = false;
        floating = false;
        encapsulate = false;
        distanceTraveled = 0;
        pomAnimationTimer = 0;
        explodingAnimationTimer = 0;
        encapsulateTimer = 0;

        this.currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(currentSkinPath);
        //System.out.println(currentSkinPath); //null ?
    }

    /**
     * Avvia il processo di sparo della bolla, impostando l'immagine iniziale.
     */
    public void startFiring() {
        distanceTraveled = 0;  // Reset della distanza percorsa
        facingRight = bubble.getPlayer().getFacingRight();
        currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));  // Imposta l'immagine iniziale
    }


    /**
     * Imposta l'immagine della bolla quando è in stato di galleggiamento.
     */
    public void setFloatingIMG() {
        currentSkinPath = bubble.getSkinsPath() + "3.png";
        Image floatingBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image floatingBubbleIMGresized = floatingBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(floatingBubbleIMGresized);

    }

    /**
     * Imposta l'immagine della bolla quando è in stato di incapsulamento.
     */
    public void setEncapsuleIMG() {
        currentSkinPath = bubble.getSkinsPath() + "4.png";
        Image encapsuleBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image encapsuleBubbleIMGresized = encapsuleBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(encapsuleBubbleIMGresized);
    }

    /**
     * Imposta l'immagine della bolla quando esplode.
     */
    public void setExplodeIMG() {
        currentSkinPath = "/Resources/Bubble Bobble Resources/Bubbles/BubbleExplode.png";

        Image explodeBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image explodeBubbleIMGresized = explodeBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(explodeBubbleIMGresized);
    }

    /**
     * Imposta l'immagine della bolla quando è in stato di pom.
     */
    public void setPomIMG() {
        currentSkinPath = "/Resources/Bubble Bobble Resources/Bubbles/BubblePom.png";
        Image pomBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image pomBubbleIMGresized = pomBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(pomBubbleIMGresized);
    }

    /**
     * Imposta l'immagine della bolla di fulmine.
     */
    public void setLightningIMG() {
        currentSkinPath = bubble.getSkinsPath() + "3.png";
        Image lightningIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image lightningIMGresized = lightningIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(lightningIMGresized);
    }

    /**
     * Imposta l'immagine della bolla di fuoco per il primo stato.
     */
    public void setFireIMG1() {
        currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
        setSkin(currentSkin);
    }

    /**
     * Imposta l'immagine della bolla di fuoco nel secondo stato.
     */
    public void setFireIMG2() {
        currentSkinPath = bubble.getSkinsPath() + "2.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
        setSkin(currentSkin);
    }

    /**
     * Imposta l'immagine di fuoco in un altro stato.
     */
    public void setFireIMG4() {
        currentSkinPath = bubble.getSkinsPath() + "4.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
        setSkin(currentSkin);
    }

    /**
     * Imposta l'immagine della bolla di fulmine in stato di galleggiamento.
     */
    public void setLightningFloatingIMG() {
        currentSkinPath = bubble.getSkinsPath() + "1.png";
        Image floatingBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image floatingBubbleIMGresized = floatingBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(floatingBubbleIMGresized);
    }

    /**
     * Imposta l'immagine della bolla in base all'oggetto ImageIcon fornito.
     *
     * @param skin L'immagine dell'icona da impostare.
     */
    public void setSkin(ImageIcon skin) {
        Image resizedSkin = skin.getImage().getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(resizedSkin);
    }

    /**
     * Imposta l'immagine della bolla estesa in base alla lettera fornita.
     *
     * @param letter La lettera che determina l'immagine della bolla estesa.
     */
    public void setExtendBubbleSkin(String letter) {
        switch (letter) {
            case "E" -> currentSkinPath = bubble.getSkinsPath() + "E.png";
            case "X" -> currentSkinPath = bubble.getSkinsPath() + "X.png";
            case "T" -> currentSkinPath = bubble.getSkinsPath() + "T.png";
            case "N" -> currentSkinPath = bubble.getSkinsPath() + "N.png";
            case "D" -> currentSkinPath = bubble.getSkinsPath() + "D.png";
        }
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
        setSkin(currentSkin);
    }

    /**
     * Imposta l'immagine della bolla di acqua che cade.
     */
    public void setFallingWaterIMG() {
        currentSkinPath = bubble.getSkinsPath() + "2.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
        setSkin(currentSkin);
    }

    /**
     * Imposta l'immagine della bolla d'onda verso destra.
     */
    public void setWaveIMGright() {
        currentSkinPath = bubble.getSkinsPath() + "4.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));

        Image resizedImage = currentSkin.getImage().getScaledInstance(
                80,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(resizedImage);
    }

    /**
     * Imposta l'immagine della bolla d'onda verso sinistra.
     */
    public void setWaveIMGleft() {
        currentSkinPath = bubble.getSkinsPath() + "5.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
        Image resizedImage = currentSkin.getImage().getScaledInstance(
                80,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(resizedImage);
    }

    /**
     * Disegna la bolla utilizzando l'oggetto Graphics2D fornito.
     *
     * @param g2d L'oggetto Graphics2D utilizzato per il disegno.
     */
    public void draw(Graphics2D g2d) {
        if (!bubble.getErased() &&
                (firing || (floating && !encapsulate) || exploding || pom || bubble.isEffect())) {
            g2d.drawImage(currentSkin.getImage(), bubble.getX(), bubble.getY(), null);
        }
    }

    /**
     * Aggiorna lo stato della bolla, inclusi il movimento e l'animazione.
     */
    public void update() {
        if (firing && distanceTraveled < MainFrame.FRAME_WIDTH - Block.WIDTH - 400) {
            // Aggiorna la posizione della bolla
            if (facingRight) {

                //se collision con enemy, setta encapsulate a true
                if (bubble.isSolidTile(bubble.getX() + shootingSpeed, bubble.getY())
                && !GameStateManager.getInstance().getCurrentLevel().isLevelWall(
                        bubble, bubble.getX()
                ))
                {
                    bubble.updateLocation(bubble.getX() - shootingSpeed, bubble.getY());
                    facingRight = false;
                    startHorizontalMovement = true;
                    bubble.startFloating();

                }
                else bubble.updateLocation(bubble.getX() + shootingSpeed, bubble.getY());

            } else if (!facingRight) {
                //se collision con enemy, setta encapsulate a true
                if (bubble.isSolidTile(bubble.getX() - shootingSpeed, bubble.getY())) {
                    bubble.updateLocation(bubble.getX() + shootingSpeed, bubble.getY());
                    facingRight = true;
                    startHorizontalMovement = true;
                    bubble.startFloating();

                } else bubble.updateLocation(bubble.getX() - shootingSpeed, bubble.getY());
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


            if (distanceTraveled >= bubble.getPlayer().getMaxBubbleDistance()) bubble.startFloating();
        }

        else if (floating) {
            //System.out.println("Entered floating");
            //System.out.println("originalX = " + bubble.getX() + "originalY = " + bubble.getY());
            bubble.handleFloatingCollision();
            //System.out.println("newX = " + bubble.getX() + "newY = " + bubble.getY());
            distanceTraveled++;
            if (distanceTraveled >= 400) bubble.explode();
        }

        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 10) {
                bubble.stopExplosion();
                if (bubble.getBubbledEnemy() == null) bubble.erase();
            }
        }

        else if (bubble.getBubbledEnemy() != null && bubble.getBubbledEnemy().isExploded()) {
            bubble.pom();
        }

        else if (pom) {
            pomAnimationTimer++;
            if (pomAnimationTimer >= 80) bubble.erase();
        }

        }

    /**
     * Imposta lo stato di incapsulamento della bolla.
     *
     * @param bool Lo stato di incapsulamento.
     */
    public void setEncapsulate(boolean bool) {
        encapsulate = bool;
    }

    /**
     * Imposta lo stato di esplosione della bolla.
     *
     * @param bool Lo stato di esplosione.
     */
    public void setExploding(boolean bool) {
        exploding = bool;
    }

    /**
     * Imposta lo stato di sparo della bolla.
     *
     * @param bool Lo stato di sparo.
     */
    public void setFiring(boolean bool) {
        firing = bool;
    }

    /**
     * Imposta lo stato di galleggiamento della bolla.
     *
     * @param bool Lo stato di galleggiamento.
     */
    public void setFloating(boolean bool) {
        floating = bool;
    }

    /**
     * Imposta la direzione in cui la bolla sta andando.
     *
     * @param bool La direzione (true per destra, false per sinistra).
     */
    public void setFacingRight(boolean bool) {
        facingRight = bool;
    }
    public boolean getFacingRight() {return facingRight;}
    public boolean getStartHorizontalMovement() {return startHorizontalMovement;}
    public ImageIcon getCurrentSkin() {return currentSkin;}
    public void setPom(boolean bool) {pom = bool;}
    public void setEnemy(Enemy enemy) {this.enemy = enemy;}

    public void setCurrentSkin(ImageIcon skin) {this.currentSkin = skin;}
}







