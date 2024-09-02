package MODEL;


import java.io.Serializable;

public class UserProfile  {

    private String username;
    private int punteggio;
    private int round; // livello
    private static int partiteVinte;
    private static int partitePerse;
    private static int partiteTot;
    private String[]  avatars = {"Bub", "Benzo", " Blubba", "BoaBoa", "BonnieBo", "Boris", "Incendio", "Superdrunk"};
    private String avatarChosen;

    public UserProfile(String username, int punteggio, int round, int avatarIndex) throws IllegalArgumentException{
        if(username == null || username.equals("") || round <= 0 || punteggio < 0){
            throw new IllegalArgumentException("C'è stato un errore nella creazione del profilo utente.");
        }
        this.username = username;
        this.punteggio = punteggio;
        this.round = round;
        this.avatarChosen = avatars[avatarIndex];
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


    public String getUsername() {
        return this.username;
    }
    public int getPunteggio(){
        return this.punteggio;
    }
    public int getRound(){
        return this.round;
    }



    public String getAvatarChosen() {
        return this.avatarChosen;
    }

    //ogni nuova partita dovrebbe essere partiteTot++ ++a o vinte o perse

    public int getPartiteVinte() {
        return this.partiteVinte;
    }

    public void incrementaPartiteVinte() {
        partiteVinte++;
        partiteTot++;
    }


    public int getPartitePerse() {
        return this.partitePerse;
    }

    public void incrementaPartitePerse() {
        partitePerse++;
        partiteTot++;
    }

    public int getPartiteTot() {
        return this.partiteTot;
    }

    public void setAvatarChosen(String avatarChosen) {
        this.avatarChosen = avatarChosen;
    }

    public void setPartiteVinte(int partiteVinte) {
        this.partiteVinte = partiteVinte;
    }

    public void setPartitePerse(int partitePerse) {
        this.partitePerse = partitePerse;
    }

    public void setPartiteTot(int partiteTot) {
        this.partiteTot = partiteTot;
    }

    @Override
    public String toString(){
        return " Avatar : " + avatarChosen +"Username: " + username +  ", Level: " + round + ", Score: " + punteggio +
        ", Wins: " + partiteVinte + ", Losses: " + partitePerse + ", Total Games: " + partiteTot;
    }

}
