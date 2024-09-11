package MODEL;
import java.io.Serializable;

/**
 * La classe {@code UserProfile} rappresenta il profilo di un utente nel gioco.
 * Contiene informazioni sul punteggio, il numero di partite giocate, vinte e perse,
 * e l'avatar scelto dall'utente.
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private int punteggio;
    private int round; // livello
    private int partiteVinte;
    private int partitePerse;
    private int partiteTot;
    private String[] avatars = {"Bub", "Benzo", "Blubba", "BoaBoa", "Invader", "Boris", "Incendio", "Superdrunk"};
    private String avatarChosen;
    private String imgPath;
    private int avatarIndex;

    /**
     * Costruisce un nuovo profilo utente con i parametri specificati.
     *
     * @param username il nome utente
     * @param punteggio il punteggio dell'utente
     * @param round il livello corrente
     * @param avatarIndex l'indice dell'avatar scelto
     * @throws IllegalArgumentException se uno dei parametri è invalido
     */
    public UserProfile(String username, int punteggio, int round, int avatarIndex) throws IllegalArgumentException {
        if (username == null || username.equals("") || round <= 0 || punteggio < 0) {
            throw new IllegalArgumentException("C'è stato un errore nella creazione del profilo utente.");
        }
        this.username = username;
        this.punteggio = punteggio;
        this.round = round;
        this.avatarChosen = avatars[avatarIndex];
        this.imgPath = loadAvatarImage(avatarIndex);
    }

    /**
     * Imposta il numero di partite vinte.
     *
     * @param wins il numero di vittorie
     * @throws IllegalArgumentException se il numero di vittorie è negativo
     */
    public void setPartiteVinte(int wins) throws IllegalArgumentException {
        if (wins < 0) {
            throw new IllegalArgumentException("Il numero di vittorie non può essere negativo.");
        }
        this.partiteVinte = wins;
    }

    /**
     * Imposta il numero di partite perse.
     *
     * @param losses il numero di sconfitte
     * @throws IllegalArgumentException se il numero di sconfitte è negativo
     */
    public void setPartitePerse(int losses) throws IllegalArgumentException {
        if (losses < 0) {
            throw new IllegalArgumentException("Il numero di sconfitte non può essere negativo.");
        }
        this.partitePerse = losses;
    }

    /**
     * Imposta il nome utente.
     *
     * @param username il nuovo nome utente
     * @throws IllegalArgumentException se il nome utente è nullo o vuoto
     */
    public void setUsername(String username) throws IllegalArgumentException {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("Il username non può essere vuoto.");
        }
        this.username = username;
    }

    /**
     * Imposta il punteggio dell'utente.
     *
     * @param punteggio il nuovo punteggio
     * @throws IllegalArgumentException se il punteggio è negativo
     */
    public void setPunteggio(int punteggio) throws IllegalArgumentException {
        if (punteggio < 0) {
            throw new IllegalArgumentException("Il punteggio non può essere negativo.");
        }
        this.punteggio = punteggio;
    }

    /**
     * Imposta il livello corrente.
     *
     * @param round il nuovo livello
     * @throws IllegalArgumentException se il livello è minore o uguale a 0
     */
    public void setRound(int round) throws IllegalArgumentException {
        if (round <= 0) {
            throw new IllegalArgumentException("Il round non può essere minore o uguale a 0.");
        }
        this.round = round;
    }

    /**
     * Restituisce il numero di partite vinte.
     *
     * @return il numero di vittorie
     */
    public int getPartiteVinte() {
        return this.partiteVinte;
    }

    /**
     * Restituisce il numero di partite perse.
     *
     * @return il numero di sconfitte
     */
    public int getPartitePerse() {
        return this.partitePerse;
    }

    /**
     * Restituisce il nome utente.
     *
     * @return il nome utente
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Restituisce il punteggio dell'utente.
     *
     * @return il punteggio
     */
    public int getPunteggio() {
        return this.punteggio;
    }

    /**
     * Restituisce il livello corrente.
     *
     * @return il livello corrente
     */
    public int getRound() {
        return this.round;
    }

    /**
     * Incrementa il numero di partite vinte e il totale delle partite giocate.
     */
    public void incrementaPartiteVinte() {
        partiteVinte++;
        partiteTot++;
    }

    /**
     * Incrementa il numero di partite perse e il totale delle partite giocate.
     */
    public void incrementaPartitePerse() {
        partitePerse++;
        partiteTot++;
    }

    /**
     * Restituisce il numero totale di partite giocate.
     *
     * @return il numero totale di partite
     */
    public int getPartiteTot() {
        return this.partiteTot;
    }

    /**
     * Imposta il numero totale di partite giocate.
     *
     * @param partiteTot il numero totale di partite
     */
    public void setPartiteTot(int partiteTot) {
        this.partiteTot = partiteTot;
    }

    /**
     * Carica il percorso dell'immagine dell'avatar in base all'indice specificato.
     *
     * @param avatarIndex l'indice dell'avatar
     * @return il percorso dell'immagine dell'avatar, o {@code null} se l'indice è invalido
     */
    private String loadAvatarImage(int avatarIndex) {
        String[] avatarPaths = {
                "src/Resources/Bubble Bobble Resources/Character/Run/Run2.png", // bub
                "src/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png", // benzo
                "src/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png", // blubba
                "src/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png", // boaboa
                "src/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle1.png", // invader
                "src/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png", // boris
                "src/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1.png", // incendio
                "src/Resources/Bubble Bobble Resources/Enemies/Superdrunk/Walk/NES - Bubble Bobble - Boss & Final Scene - Super Drunk5.png"
        };

        if (avatarIndex >= 0 && avatarIndex < avatarPaths.length) {
            return avatarPaths[avatarIndex];
        }
        return null;
    }

    /**
     * Restituisce il percorso dell'immagine dell'avatar in base all'indice specificato.
     *
     * @param avatarIndex l'indice dell'avatar
     * @return il percorso dell'immagine dell'avatar, o "Avatar non trovato" se l'indice è invalido
     */
    public String loadAvatarImagePath(int avatarIndex) {
        String[] avatarPaths = {
                "src/Resources/Bubble Bobble Resources/Character/Run/Run2.png", // bub
                "src/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png", // benzo
                "src/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png", // blubba
                "src/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png", // boaboa
                "src/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle1.png", // invader
                "src/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png", // boris
                "src/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1.png", // incendio
                "src/Resources/Bubble Bobble Resources/Enemies/Superdrunk/Walk/NES - Bubble Bobble - Boss & Final Scene - Super Drunk5.png"
        };

        if (avatarIndex >= 0 && avatarIndex < avatarPaths.length) {
            return avatarPaths[avatarIndex];
        } else {
            return "Avatar non trovato";
        }
    }

/**
 * Restituisce il percorso dell'immagine dell'avatar scelto.
 *
 * @return il percorso dell'immagine
 */
    public String getAvatarImage() {
        return imgPath;
    }

    /**
     * Restituisce una rappresentazione stringa del profilo utente.
     * La rappresentazione include il percorso dell'immagine dell'avatar,
     * il nome utente, il livello, il punteggio, il numero di partite vinte,
     * perse e il totale delle partite giocate.
     *
     * @return una stringa che rappresenta il profilo utente
     */
    @Override
    public String toString(){
        String imagePath = loadAvatarImagePath(avatarIndex);
        return   imagePath +"," + username +  "," + round + "," + punteggio +
                "," + partiteVinte + "," + partitePerse + "," + partiteTot;
    }

}

