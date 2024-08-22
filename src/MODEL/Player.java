package MODEL;

public class Player implements Entity {

    private UserProfile profile;
    private int x;
    private int y;
    private int punteggio;
    private int lives;

    public Player(UserProfile profile){

        this.profile=profile;
        // this.x = ;
        // this.y = ;
        this.lives = 2; // default

    }


    @Override
    public void updatelocation(int x, int y) {

    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int compareLocation(Entity entity) {
        return 0;
    }

    @Override
    public void spawn() {

    }

    public int addPunteggio(int punti){

        return punteggio+punti;
    }

    public int getPunteggio(){

        return this.punteggio;
    }
}
