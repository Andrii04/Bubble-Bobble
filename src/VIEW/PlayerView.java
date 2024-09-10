package VIEW;

import MODEL.Entity;
import MODEL.Player;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import static MODEL.Entity.Action.*;

/**
 * Visualizzazione del giocatore nel gioco.
 *
 * Questa classe gestisce la rappresentazione grafica del giocatore,
 * inclusi i vari stati animati come camminare, saltare, attaccare e morire.
 * Implementa l'interfaccia {@link Observer} per aggiornare la visualizzazione
 * in base agli stati del giocatore.
 */
public class PlayerView implements Observer {
    int x;
    int y;
    Player player;
    private BufferedImage[] walkLeft;
    private BufferedImage[] walkRight;
    private BufferedImage[] idleRight;
    private BufferedImage[] idleLeft;
    private BufferedImage[] jumpUpRight;
    private BufferedImage[] jumpUpLeft;
    private BufferedImage[] jumpDownRight;
    private BufferedImage[] jumpDownLeft;
    private BufferedImage[] die;
    private BufferedImage[] attackRight;
    private BufferedImage[] attackLeft;
    private BufferedImage[] hurt;
    private Entity.Action currentAction = IDLE;
    private int currentFrame;
    private long lastTime;
    private final int frameDelay = 100;

    /**
     * Costruttore della vista del giocatore.
     *
     * Inizializza la visualizzazione del giocatore e carica le immagini
     * necessarie per le animazioni. Registra questo oggetto come osservatore
     * del giocatore per aggiornamenti dello stato.
     *
     * @param player L'oggetto {@link Player} associato a questa vista.
     */
    public PlayerView(Player player) {
        this.player = player;
        this.x = player.getX();
        this.y = player.getY();
        player.addObserver(this);
        loadImages();
    }

    /**
     * Scala un'immagine a dimensioni doppie.
     *
     * @param img L'immagine da scalare.
     * @return L'immagine scalata.
     */
    public BufferedImage scaleImage(BufferedImage img) {
        BufferedImage scaledImage = new BufferedImage(img.getWidth()*2, img.getHeight()*2, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, img.getWidth()*2, img.getHeight()*2, null);
        g2d.dispose();
        return scaledImage;
    }

    /**
     * Carica le immagini per tutte le animazioni del giocatore.
     *
     * Le immagini vengono caricate da file e scalate se necessario.
     * Viene gestito il caricamento delle immagini per camminare, stare fermi,
     * saltare, attaccare, e morire.
     *
     * @throws RuntimeException Se si verifica un errore durante il caricamento delle immagini.
     */
    public void loadImages(){
        try {
            walkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run4.png")))};
            idleRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Bub&Bob1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Bub&Bob2.png")))};
            idleLeft = new BufferedImage[]{
                   scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//Bub&Bob1Left.png"))),
                   scaleImage( ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/Bub&Bob2Left.png")))};
            walkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/RunLeft2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/Bub&Bob4.png")))};
            jumpUpRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run6.png")))};
            jumpUpLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft6.png")))};
            jumpDownRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run8.png")))};
            jumpDownLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft8.png")))};
            attackRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Attack/CharacterAttack.png"))),
            };
            attackLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Attack/CharacterAttack1.png")))
            };
            hurt = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob17.png")))
            };
            die = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob16.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob17.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob18.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob19.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob20.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob21.png")))};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Aggiorna la visualizzazione in base agli aggiornamenti ricevuti dal giocatore.
     *
     * Modifica la posizione e l'azione del giocatore in base agli argomenti
     * passati all'aggiornamento. Questo metodo è chiamato ogni volta che il giocatore
     * cambia stato.
     *
     * @param o L'oggetto osservabile, in questo caso il {@link Player}.
     * @param arg L'argomento passato con l'aggiornamento, di tipo {@link Entity.Action}.
     */
    public void update(Observable o, Object arg) {
        if(o instanceof Player){
            Player player = (Player) o;
            if(arg instanceof Entity.Action){
                Entity.Action action = (Entity.Action) arg;
                this.currentAction = action;
                x = player.getX();
                y = player.getY();
            }
        }
    }

    /**
     * Disegna l'animazione corrente del giocatore.
     *
     * Se esiste un'animazione per l'azione corrente del giocatore, questa viene disegnata
     * sul {@link Graphics2D} fornito. La selezione del frame corrente è gestita in base
     * al tempo trascorso e alla durata del frame.
     *
     * @param g2d Il {@link Graphics2D} su cui disegnare l'animazione del giocatore.
     */
    public void draw(Graphics2D g2d){
        BufferedImage[] currentAnimation = getCurrentAnimation();
        if (currentAnimation != null){
            long currentTime = System.currentTimeMillis();
            if(currentTime-lastTime > frameDelay){
                lastTime = currentTime;
                    currentFrame = (currentFrame + 1) % currentAnimation.length;
                }
            }
            currentFrame = Math.min(currentFrame, currentAnimation.length - 1);
            g2d.drawImage(currentAnimation[currentFrame], x, y, null);
        }


    /**
     * Restituisce l'animazione corrente basata sull'azione del giocatore.
     *
     * @return L'array di immagini per l'animazione corrente.
     */
    private BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            case WALK:
                return player.getFacingRight() ? walkRight : walkLeft;
            case MOVE_VERTICALLY:
                if (player.getAirSpeed() < 0) {
                    return player.getFacingRight() ? jumpUpRight : jumpUpLeft;
                } else {
                    return player.getFacingRight() ? jumpDownRight : jumpDownLeft;
                }
            case ATTACK:
                return player.getFacingRight() ? attackRight : attackLeft;
            case DIE:
                return die;
            case HURT:
                return hurt;
            default:
                return player.getFacingRight() ? idleRight : idleLeft;
        }
    }

    /**
     * Restituisce il giocatore associato a questa vista.
     *
     * Questo metodo è utile per accedere all'oggetto {@link Player} che
     * è controllato da questa vista, permettendo di ottenere informazioni
     * dettagliate sul giocatore o di interagire con esso direttamente.
     *
     * @return Il giocatore associato a questa vista.
     */
    public Player getPlayer() {return player;}
}
