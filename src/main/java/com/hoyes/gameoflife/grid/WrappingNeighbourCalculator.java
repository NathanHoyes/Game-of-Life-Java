package main.java.com.hoyes.gameoflife.grid;

import main.java.com.hoyes.gameoflife.cell.Cell;

public class WrappingNeighbourCalculator implements NeighbourCalculator {

    @Override
    public int calculateNeighbours(Cell[][] cells, int x, int y) {
        int neighbors = 0;
        int gridSize = cells.length;

        for (int x2 = x - 1; x2 <= x + 1; x2++) {
            for (int y2 = y - 1; y2 <= y + 1; y2++) {
                int wrappedX = (x2 + gridSize) % gridSize;
                int wrappedY = (y2 + gridSize) % gridSize;

                if ((wrappedX != x || wrappedY != y) && cells[wrappedX][wrappedY].isAlive()) {
                    neighbors++;
                }
            }
        }
        return neighbors;
    }
}
