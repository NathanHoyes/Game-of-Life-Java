package main.java.com.hoyes.gameoflife.initialiser;

public interface CellInitialiser {
    boolean shouldInitialiseAlive(int x, int y);
}
