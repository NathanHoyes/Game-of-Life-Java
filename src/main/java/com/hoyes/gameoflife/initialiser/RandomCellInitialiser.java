package main.java.com.hoyes.gameoflife.initialiser;

import java.util.Random;

public class RandomCellInitialiser implements CellInitialiser {
    private final Random random;

    public RandomCellInitialiser () {
        random = new Random();
    }

    @Override
    public boolean shouldInitialiseAlive(int x, int y) {
        return random.nextBoolean();
    }
}
