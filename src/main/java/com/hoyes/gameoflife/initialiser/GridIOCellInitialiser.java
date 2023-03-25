package main.java.com.hoyes.gameoflife.initialiser;

public class GridIOCellInitialiser implements CellInitialiser {
    private final boolean[][] initialState;
    private final int xOffset;
    private final int yOffset;

    public GridIOCellInitialiser(boolean[][] initialState, int gridWidth, int gridHeight) {
        this.initialState = initialState;
        this.xOffset = (gridWidth - initialState[0].length) / 2;
        this.yOffset = (gridHeight - initialState.length) / 2;
    }

    @Override
    public boolean shouldInitialiseAlive(int x, int y) {
        int centeredX  = x - xOffset;
        int centeredY = y - yOffset;
        if (centeredY >= 0 && centeredY < initialState.length && centeredX >= 0 && centeredX < initialState[centeredY].length) {
            return initialState[centeredY][centeredX];
        }
        return false;
    }
}
