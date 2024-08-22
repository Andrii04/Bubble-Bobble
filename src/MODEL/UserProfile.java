package MODEL;

public class UserProfile {
    private String username;
    private int punteggio;
    private int round; // livello

    public UserProfile(String username, int punteggio, int round) throws IllegalArgumentException{
        if(username == null || username.equals("") || round <= 0 || punteggio < 0){
            throw new IllegalArgumentException("C'è stato un errore nella creazione del profilo utente.");
        }
        this.username = username;
        this.punteggio = punteggio;
        this.round = round;
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
    public String getNickname() {
        return this.username;
    }
    public int getPunteggio(){
        return this.punteggio;
    }
    public int getRound(){
        return this.round;
    }

}
