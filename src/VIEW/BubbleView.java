package VIEW;
import GAMESTATEMANAGER.GameStateManager;
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
    boolean pom;
    int distanceTraveled;
    boolean facingRight;
    boolean startHorizontalMovement;

    int shootingSpeed = 9;
    int floatingSpeed = 1;

    int pomAnimationTimer;
    int explodingAnimationTimer;

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
        pomAnimationTimer = 0;
        explodingAnimationTimer = 0;
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
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(floatingBubbleIMGresized);

    }

    public void setEncapsuleIMG() {
        String currentSkinPath = bubble.getSkinsPath() + "4.png";
        Image encapsuleBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image encapsuleBubbleIMGresized = encapsuleBubbleIMGoriginal.getScaledInstance(
                (int) bubble.getBubbledEnemy().getHitbox().getWidth()+2,
                (int) bubble.getBubbledEnemy().getHitbox().getHeight()+2,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(encapsuleBubbleIMGresized);
    }

    public void setExplodeIMG() {
        String currentSkinPath = "/Resources/Bubble Bobble Resources/Bubbles/BubbleExplode.png";

        Image explodeBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image explodeBubbleIMGresized = explodeBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(explodeBubbleIMGresized);
    }

    public void setPomIMG() {
        String currentSkinPath = "/Resources/Bubble Bobble Resources/Bubbles/BubblePom.png";
        Image pomBubbleIMGoriginal = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image pomBubbleIMGresized = pomBubbleIMGoriginal.getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );
        currentSkin = new ImageIcon(pomBubbleIMGresized);
    }

    public void draw(Graphics2D g2d) {
        if (firing || floating || encapsulate || exploding || pom) {
            g2d.drawImage(currentSkin.getImage(), bubble.getX(), bubble.getY(), null);
            g2d.draw(bubble.getHitbox());
        }
    }

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


            if (distanceTraveled >= 20) bubble.startFloating();
        }

        else if (floating) {
            //System.out.println("Entered floating");
            //System.out.println("originalX = " + bubble.getX() + "originalY = " + bubble.getY());
            bubble.handleFloatingCollision();
            //System.out.println("newX = " + bubble.getX() + "newY = " + bubble.getY());
            distanceTraveled++;
            if (distanceTraveled >= 600) bubble.erase();
        }
        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 10) bubble.pom();
        }
        else if (pom) {
            pomAnimationTimer++;
            if (pomAnimationTimer >= 60) bubble.erase();
        }
    }

    public void setEncapsulate(boolean bool) {
        encapsulate = bool;
    }

    public void setExploding(boolean bool) {
        exploding = bool;
    }

    public void setFiring(boolean bool) {
        firing = bool;
    }

    public void setFloating(boolean bool) {
        floating = bool;
    }

    public void setFacingRight(boolean bool) {
        facingRight = bool;
    }
    public boolean getFacingRight() {return facingRight;}
    public boolean getStartHorizontalMovement() {return startHorizontalMovement;}
    public ImageIcon getCurrentSkin() {return currentSkin;}
    public void setPom(boolean bool) {pom = bool;}
}







