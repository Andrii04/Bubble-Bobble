package Model;

public interface Entity {

    public void updatelocation(int x, int y);
    public void attack();
    public void die();

    public int getX();
    public int getY();

    public int compareLocation(Entity entity);

    public void spawn();



}
