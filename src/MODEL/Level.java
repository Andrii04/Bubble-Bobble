package MODEL;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.SpawnedBubble;
import MODEL.Enemy.*;
import MODEL.PowerUps.PowerUp;
import VIEW.*;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * La classe {@code Level} rappresenta un livello di gioco, gestendo vari elementi
 * come nemici, bolle, potenziamenti e la logica associata al pattern del livello.
 */
public class Level {

    private int height;
    private int width;
    private Timer spawnRate;
    private int[][] pattern;
    private Boolean[][] solidCheckPattern; // deve essere di tipo Wrapper per ragioni specifiche
    private int blockInt;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyView> enemyViews;
    private ArrayList<Bubble> bubbles;
    private ArrayList<SpawnedBubble> spawnedBubbles;
    private ArrayList<PowerUp> powerUps;
    private int currentPowerUpIndex;
    private boolean clock;

    /**
     * Costruisce un nuovo livello con le specifiche dimensioni e pattern.
     *
     * @param height         altezza del livello
     * @param width          larghezza del livello
     * @param pattern        pattern del livello (matrice 2D di blocchi)
     * @param enemies        lista di nemici presenti nel livello
     * @param spawnedBubbles lista di bolle generate nel livello
     */
    public Level(int height, int width, int[][] pattern,
                 ArrayList<Enemy> enemies, ArrayList<SpawnedBubble> spawnedBubbles) {
        this.height = height;
        this.width = width;
        this.pattern = pattern;
        this.blockInt = pattern[0][0];

        this.bubbles = new ArrayList<>();
        this.powerUps = new ArrayList<>(Arrays.asList(
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null));

        currentPowerUpIndex = 0;
        this.spawnedBubbles = spawnedBubbles;

        this.enemies = enemies;

        // Creazione delle viste per i nemici.
        this.enemyViews = enemies.stream().
                map(enemy -> {
                    if (enemy instanceof Benzo) return new BenzoView(enemy);
                    if (enemy instanceof Blubba) return new BlubbaView(enemy);
                    if (enemy instanceof Boris) return new BorisView(enemy);
                    if (enemy instanceof BoaBoa) return new BoaBoaView(enemy);
                    if (enemy instanceof Incendio) return new IncendioView(enemy);
                    if (enemy instanceof Invader) return new InvaderView(enemy);
                    if (enemy instanceof SuperDrunk) return new SuperDrunkView(enemy);
                    return null;
                })
                .collect(Collectors.toCollection(ArrayList<EnemyView>::new));

        // Creazione del pattern che controlla i blocchi solidi (true) o non solidi (false).
        solidCheckPattern = Arrays.stream(pattern).
                map(row -> Arrays.stream(row)
                        .mapToObj(col -> col > 0)
                        .toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        clock = false;
    }

    /**
     * Imposta la frequenza di spawn dei nemici utilizzando un {@code Timer}.
     *
     * @param timerDelay il ritardo tra uno spawn e l'altro (in millisecondi)
     */
    public void setSpawnRate(int timerDelay) {
        Timer spawnRate = new Timer(timerDelay, a -> spawnEnemies());
        this.spawnRate = spawnRate;
    }

    /**
     * Avvia il timer per lo spawn dei nemici.
     */
    public void startSpawnRate() {
        spawnRate.start();
    }

    /**
     * Ferma il timer per lo spawn dei nemici.
     */
    public void stopSpawnRate() {
        spawnRate.stop();
    }

    /**
     * Metodo placeholder per gestire lo spawn dei nemici.
     * Implementare la logica di spawn qui.
     */
    public void spawnEnemies() {
    }

    /**
     * Restituisce il pattern del livello.
     *
     * @return matrice 2D che rappresenta il pattern del livello
     */
    public int[][] getPattern() {
        return pattern;
    }

    /**
     * Restituisce il pattern che indica quali blocchi sono solidi.
     *
     * @return matrice 2D di {@code Boolean} che indica se un blocco è solido
     */
    public Boolean[][] getSolidCheckPattern() {
        return solidCheckPattern;
    }

    /**
     * Restituisce l'intero che rappresenta il blocco attuale.
     *
     * @return l'intero del blocco attuale
     */
    public int getBlockInt() {
        return blockInt;
    }

    /**
     * Restituisce la lista dei nemici nel livello.
     *
     * @return lista di oggetti {@code Enemy}
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Restituisce il blocco a una posizione specifica del pattern.
     *
     * @param y coordinata Y del blocco
     * @param x coordinata X del blocco
     * @return l'intero rappresentante il blocco, o 1 se le coordinate sono fuori dai limiti
     */
    public int getBlockInt(int y, int x) {
        if (y >= 0 && y < pattern.length && x >= 0 && x < pattern[0].length) return pattern[y][x];
        else return 1;
    }

    /**
     * Controlla se un blocco a una specifica posizione è solido.
     *
     * @param y coordinata Y del blocco
     * @param x coordinata X del blocco
     * @return {@code true} se il blocco è solido, altrimenti {@code false}
     */
    public boolean isItSolidBlock(int y, int x) {
        if (y >= 0 && y < solidCheckPattern.length && x >= 0 && x < solidCheckPattern[0].length)
            return solidCheckPattern[y][x];
        else return true;
    }

    /**
     * Imposta il blocco a una specifica posizione come solido o non solido.
     *
     * @param y coordinata Y del blocco
     * @param x coordinata X del blocco
     */
    public void setSolidBlock(int y, int x) {
        solidCheckPattern[y][x] = !solidCheckPattern[y][x];
    }

    /**
     * Rimuove il blocco a una specifica posizione, impostandolo a 0.
     *
     * @param y coordinata Y del blocco
     * @param x coordinata X del blocco
     */
    public void removeBlock(int y, int x) {
        pattern[y][x] = 0;
    }

    /**
     * Aggiunge un blocco a una specifica posizione, impostando il valore del blocco.
     *
     * @param y coordinata Y del blocco
     * @param x coordinata X del blocco
     */
    public void addBlock(int y, int x) {
        pattern[y][x] = blockInt;
        solidCheckPattern[y][x] = true;
    }

    /**
     * Restituisce la lista delle viste dei nemici.
     *
     * @return lista di oggetti {@code EnemyView}
     */
    public ArrayList<EnemyView> getEnemyViews() {
        return enemyViews;
    }

    /**
     * Controlla se una bolla si trova vicino a un muro del livello.
     *
     * @param bubble la bolla da controllare
     * @param x      la coordinata X della bolla
     * @return {@code true} se la bolla è vicino a un muro, altrimenti {@code false}
     */
    public boolean isLevelWall(Bubble bubble, int x) {
        int arrayX = x / Block.WIDTH;

        if (arrayX < pattern[0].length && arrayX >= 0) {
            return arrayX == pattern[0].length - 2 || arrayX == 1;
        }
        return false;
    }

    /**
     * Rimuove un nemico dalla lista dei nemici e delle loro viste.
     *
     * @param enemy il nemico da rimuovere
     */
    public void removeEnemy(Enemy enemy) {
        int enemyIDX = enemies.indexOf(enemy);
        if (enemyIDX != -1) {
            enemies.remove(enemyIDX);
            enemyViews.remove(enemyIDX);
        }
    }

    /**
     * Imposta la lista dei nemici nel livello e aggiorna le loro viste.
     *
     * @param enemies lista di nemici
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
        this.enemyViews = enemies.stream().
                map(enemy -> {
                    if (enemy instanceof Benzo) return new BenzoView(enemy);
                    if (enemy instanceof Blubba) return new BlubbaView(enemy);
                    if (enemy instanceof Boris) return new BorisView(enemy);
                    if (enemy instanceof BoaBoa) return new BoaBoaView(enemy);
                    if (enemy instanceof Incendio) return new IncendioView(enemy);
                    if (enemy instanceof Invader) return new InvaderView(enemy);
                    if (enemy instanceof SuperDrunk) return new SuperDrunkView(enemy);
                    return null;
                })
                .collect(Collectors.toCollection(ArrayList<EnemyView>::new));
    }

    /**
     * Aggiunge una nuova bolla alla lista delle bolle attive nel livello.
     *
     * @param bubble la bolla da aggiungere
     */
    public void addBubble(Bubble bubble) {
        this.bubbles.add(bubble);
    }

    /**
     * Rimuove una bolla specificata dalla lista delle bolle attive nel livello,
     * sostituendola con {@code null}.
     *
     * @param bubble la bolla da rimuovere
     */
    public void removeBubble(Bubble bubble) {
        if (bubbles.indexOf(bubble) != -1) {
            this.bubbles.set(bubbles.indexOf(bubble), null);
        }
    }

    /**
     * Restituisce la lista delle bolle attive nel livello.
     *
     * @return una lista di oggetti {@code Bubble}
     */
    public ArrayList<Bubble> getBubbles() {
        return bubbles;
    }

    /**
     * Aggiunge una bolla spawnata alla lista delle bolle generate nel livello.
     *
     * @param bubble la bolla spawnata da aggiungere
     */
    public void addSpawnedBubble(SpawnedBubble bubble) {
        spawnedBubbles.add(bubble);
    }

    /**
     * Spawna tutte le bolle nella lista delle bolle generate nel livello.
     * Aggiunge le bolle spawnate alla lista di bolle attive.
     */
    public void spawnBubbles() {
        bubbles.addAll(spawnedBubbles);
        System.out.println(spawnedBubbles.toString());
    }

    /**
     * Restituisce la lista delle bolle generate nel livello.
     *
     * @return una lista di oggetti {@code SpawnedBubble}
     */
    public ArrayList<SpawnedBubble> getSpawnedBubbles() {
        return spawnedBubbles;
    }

    /**
     * Spawna un potenziamento nel livello e lo aggiunge alla lista dei potenziamenti.
     *
     * @param powerUp il potenziamento da spawnare
     */
    public void spawnPowerUp(PowerUp powerUp) {
        if (currentPowerUpIndex >= 0 && currentPowerUpIndex < powerUps.size()) {
            powerUps.set(currentPowerUpIndex, powerUp);
            currentPowerUpIndex++;
        }
    }

    /**
     * Rimuove un potenziamento dalla lista dei potenziamenti attivi nel livello.
     *
     * @param powerUp il potenziamento da rimuovere
     */
    public void despawnPowerUp(PowerUp powerUp) {
        this.powerUps.set(powerUps.indexOf(powerUp), null);
    }

    /**
     * Restituisce la lista dei potenziamenti attivi nel livello.
     *
     * @return una lista di oggetti {@code PowerUp}
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     * Imposta lo stato del clock (tempo di gioco).
     *
     * @param bool valore booleano per indicare se il clock è attivo o meno
     */
    public void setClock(boolean bool) {
        clock = bool;
    }

    /**
     * Verifica se il clock del livello è attivo.
     *
     * @return {@code true} se il clock è attivo, altrimenti {@code false}
     */
    public boolean isClock() {
        return clock;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del livello.
     *
     * @return una stringa che rappresenta il livello, inclusa la sua posizione nella lista dei livelli
     */
    @Override
    public String toString() {
        return "livello_" + GameStateManager.getInstance().getLevels().indexOf(this);
    }
}