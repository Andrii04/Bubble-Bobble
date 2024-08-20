package Model;

public class Player implements Entity {

    private UserProfile profile;
    private int x;
    private int y;
    private int lives;

    public Player(UserProfile profile){
        this.profile=profile;
    }



}
