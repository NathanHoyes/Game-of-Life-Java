import java.awt.Rectangle;

public class Cell extends Rectangle {
    private boolean isAlive;

    public Cell(int x, int y, int size, boolean isAlive) {
        super(x, y, size, size);
        setAlive(isAlive);
    }

    /*
     * Accessors
     */

    public boolean isAlive() {
        return isAlive;
    }

    /*
     * Mutators
     */

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /*
     * Helpers
     */
    public void changeAlive() {
        //System.out.printf("Cell at %s, %s was %s and is now %s%n", x/25, y/25, isAlive()?"Alive":"Dead", !isAlive()?"Alive":"Dead");
        setAlive(!isAlive);
    }
}
