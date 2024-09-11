package MODEL;
import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private int punteggio;
    private int round; // livello
    private int partiteVinte;
    private int partitePerse;
    private int partiteTot;
    private String[]  avatars = {"Bub", "Benzo", " Blubba", "BoaBoa", "Invader", "Boris", "Incendio", "Superdrunk"};
    private String avatarChosen;
    private String imgPath;
    private int avatarIndex;

    public UserProfile(String username, int punteggio, int round, int avatarIndex) throws IllegalArgumentException{
        if(username == null || username.equals("") || round <= 0 || punteggio < 0){
            throw new IllegalArgumentException("C'è stato un errore nella creazione del profilo utente.");
        }
        this.username = username;
        this.punteggio = punteggio;
        this.round = round;
        this.avatarChosen = avatars[avatarIndex];
        this.imgPath = loadAvatarImage(avatarIndex);
    }

    public void setPartiteVinte(int wins) throws IllegalArgumentException{

        if(wins < 0){
            throw new IllegalArgumentException("Il numero di vittorie non può essere negativo.");
        }
        this.partitePerse = wins;
    }
    public void setPartitePerse(int losses) throws IllegalArgumentException{

        if(losses < 0){
            throw new IllegalArgumentException("Il numero di sconfitte non può essere negativo.");
        }
        this.partitePerse = losses;
    }
    public void setUsername(String username) throws IllegalArgumentException{

        if (username == null ||username.equals("")){
            throw new IllegalArgumentException("Il username non può essere vuoto.");
        }
        this.username = username;
    }
    public void setPunteggio(int punteggio) throws IllegalArgumentException{

        if(punteggio < 0){
            throw new IllegalArgumentException("Il punteggio non può essere negativo.");
        }
        this.punteggio = punteggio;
    }
    public void setRound(int round) throws IllegalArgumentException{

        if(round <= 0){
            throw new IllegalArgumentException("Il round non può essere minore o uguale a 0.");
        }
        this.round = round;}

    public int getPartiteVinte(){
        return this.partiteVinte;
    }
    public int getPartitePerse(){
        return this.partitePerse;
    }
    public String getUsername() {
        return this.username;
    }
    public int getPunteggio(){
        return this.punteggio;
    }
    public int getRound(){
        return this.round;
    }


    //ogni nuova partita dovrebbe essere partiteTot++ ++a o vinte o perse


    public void incrementaPartiteVinte() {
        partiteVinte++;
        partiteTot++;
    }


    public void incrementaPartitePerse() {
        partitePerse++;
        partiteTot++;
    }

    public int getPartiteTot() {
        return this.partiteTot;
    }

    public void setPartiteTot(int partiteTot) {
        this.partiteTot = partiteTot;
    }


    // Metodo per caricare l'immagine avatar
    private String loadAvatarImage(int avatarIndex) {
        String[] avatarPaths = {
                "src/Resources/Bubble Bobble Resources/Character/Run/Run2.png", // bub
                "src/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png", // benzo
                "src/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png", // blubba
                "src/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png", // boaboa
                "src/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle1.png", // invader
                "src/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png", // boris
                "src/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1.png", // incendio
                "src/Resources/Bubble Bobble Resources/Enemies/Superdrunk/Walk/NES - Bubble Bobble - Boss & Final Scene - Super Drunk5.png"};

        if (avatarIndex >= 0 && avatarIndex < avatarPaths.length) {
            String path = avatarPaths[avatarIndex];
            return path;
        }
        return null;
    }

    // Metodo per ottenere il percorso dell'immagine dell'avatar
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

    public String getAvatarImage() {
        return imgPath;
    }

    @Override
    public String toString(){
        String imagePath = loadAvatarImagePath(avatarIndex);
        return   imagePath +"," + username +  "," + round + "," + punteggio +
                "," + partiteVinte + "," + partitePerse + "," + partiteTot;
    }

}

