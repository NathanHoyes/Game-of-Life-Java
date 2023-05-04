package main.java.com.hoyes.gameoflife.grid;

import main.java.com.hoyes.gameoflife.cell.Cell;

public class NonWrappingNeighbourCalculator implements NeighbourCalculator {
    public int calculateNeighbours(Cell[][] cells, int x, int y) {
        int neighbors = 0;

        for (int x2 = x-1; x2 <= x + 1; x2 ++) {
            for (int y2 = y-1; y2 <= y + 1; y2 ++) {
                //Check for negative, out of bounds indexes and same cell.
                if (x2 != -1 && y2 != -1 && x2 < cells.length && y2 < cells.length && (x2 != x || y2 != y)) {
                    if (cells[x2][y2].isAlive()) {
                        neighbors ++;
                    }
                }
            }
        }
        return neighbors;
    }
}
