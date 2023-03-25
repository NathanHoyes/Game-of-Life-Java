package main.java.com.hoyes.gameoflife.initialiser;

public class DefaultCellInitialiser implements CellInitialiser {
    @Override
    public boolean shouldInitialiseAlive(int x, int y) {
        return false;
    }
}
