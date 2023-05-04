package main.java.com.hoyes.gameoflife.grid;

import main.java.com.hoyes.gameoflife.cell.Cell;

public interface NeighbourCalculator {
    int calculateNeighbours(Cell[][] cells, int x, int y);
}
