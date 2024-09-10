package MODEL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


/**
 * Classe per gestire la riproduzione dei suoni e della musica nel gioco.
 * <p>
 * Questa classe utilizza la libreria Java Sound API per caricare e riprodurre
 * file audio. Supporta la riproduzione sia di effetti sonori che di musica di sottofondo.
 * </p>
 */
public class Sound {
    private Clip clip;
    private URL soundUrl[] = new URL[8];

    /**
     * Costruisce un'istanza della classe {@code Sound} e carica gli URL dei file audio.
     * Inizializza un array di URL per i diversi effetti sonori e musiche.
     */
    public Sound() {
        soundUrl[0] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_bubble.wav");
        soundUrl[1] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_death.wav");
        soundUrl[2] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_hit.wav");
        soundUrl[3] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_jump.wav");
        soundUrl[4] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_lose.wav");
        soundUrl[5] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_pickup.wav");
        soundUrl[6] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_theme.wav");
        soundUrl[7] = getClass().getResource("/Resources/Bubble Bobble Resources/Sounds/assets_win.wav");
    }

    /**
     * Imposta il file audio da riprodurre in base all'indice fornito.
     * <p>
     * Carica il file audio specificato nell'indice e lo prepara per la riproduzione.
     * </p>
     *
     * @param i L'indice del file audio da caricare.
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Avvia la riproduzione del file audio attualmente impostato.
     * <p>
     * Questo metodo riproduce il file audio caricato a partire dalla posizione attuale.
     * </p>
     */
    public void play() {
        clip.start();
    }

    /**
     * Avvia la riproduzione continua del file audio attualmente impostato.
     * <p>
     * Questo metodo riproduce il file audio in loop continuo.
     * </p>
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Ferma la riproduzione del file audio attualmente in corso.
     * <p>
     * Questo metodo interrompe la riproduzione del file audio.
     * </p>
     */
    public void stop() {
        clip.stop();
    }

    /**
     * Carica e riproduce la musica specificata dall'indice, facendola riprodurre in loop.
     *
     * @param i L'indice del file audio della musica da riprodurre in loop.
     */
    public void playMusic(int i) {
        setFile(i);
        play();
        loop();
    }

    /**
     * Carica e riproduce l'effetto sonoro specificato dall'indice.
     *
     * @param i L'indice del file audio dell'effetto sonoro da riprodurre.
     */
    public void playSound(int i) {
        setFile(i);
        play();
    }

    /**
     * Ferma la riproduzione dell'effetto sonoro o della musica attualmente in corso.
     * <p>
     * Questo metodo può essere utilizzato per fermare qualsiasi audio in riproduzione.
     * </p>
     */
    public void stopSound() {
        stop();
    }
}