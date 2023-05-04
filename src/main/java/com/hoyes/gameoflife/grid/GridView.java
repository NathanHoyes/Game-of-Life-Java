package main.java.com.hoyes.gameoflife.grid;

import main.java.com.hoyes.gameoflife.animate.AnimateThread;
import main.java.com.hoyes.gameoflife.cell.Cell;
import main.java.com.hoyes.gameoflife.initialiser.CellInitialiser;
import main.java.com.hoyes.gameoflife.initialiser.DefaultCellInitialiser;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GridView extends JPanel {
    private final int cellSize = 10;
    private final int xCells;
    private final int yCells;
    protected final Cell[][] cells;
    private NeighbourCalculator neighbourCalculator = new NonWrappingNeighbourCalculator();

    private final AnimateThread animateThread;

    public GridView(int width, int height) {
        super();
        this.animateThread = new AnimateThread(this);
        this.xCells = width / cellSize;
        this.yCells = height / cellSize;
        this.cells = new Cell[xCells][yCells];
        this.setPreferredSize(new Dimension(width+1, height+1));
        this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        this.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        populateCells(new DefaultCellInitialiser());
    }

    public GridView(int width, int height, NeighbourCalculator neighbourCalculator) {
        this(width, height);
        this.neighbourCalculator = neighbourCalculator;
    }


    /*
     * Accessors
     */
    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getXCells() {
        return xCells;
    }

    public int getYCells() {
        return yCells;
    }

    /*
     * Helpers
     */

    public void populateCells(CellInitialiser cellInitialiser) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                int xPos = x * getCellSize();
                int yPos = y * getCellSize();
                cells[x][y] = new Cell(xPos, yPos, getCellSize(), cellInitialiser.shouldInitialiseAlive(x, y));
            }
        }
    }

    /*
     * Game Logic
     */

    public void calculateChanges() {
        ArrayList<Point> changes;
        changes = new ArrayList<>();

        for (int x = 0; x < cells.length; x ++) {
            for (int y = 0; y < cells.length; y ++) {
                if (isChanged(x, y)) {
                    changes.add(new Point(x, y));
                }
            }
        }
        applyChanges(changes);
    }

    public void applyChanges(List<Point> changes) {
        for (Point p : changes) {
            cells[p.x][p.y].changeAlive();
        }
    }



    public boolean isChanged(int x, int y) {
        int neighbors = calculateNeighbors(x, y);
        boolean alive = cells[x][y].isAlive();
        //System.out.printf("main.java.com.hoyes.gameoflife.cell.Cell: %s, %s. %s with %d neighbors.%n", x, y, alive?"Alive":"Dead", neighbors);
        if (!alive && neighbors == 3) return true;
        else return alive && (neighbors > 3 || neighbors < 2);
    }

    public int calculateNeighbors(int x, int y) {
        return neighbourCalculator.calculateNeighbours(cells, x, y);
    }


    /*
     * Graphics
     */
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        Color borderColour = new Color(150,150,150);
        Color liveColour = new Color(0, 0, 0);
        Color deadColour = new Color(255,255,255);

        for (Cell[] value : cells) {
            for (Cell cell : value) {
                if (cell.isAlive()) {
                    graphics2D.setColor(liveColour);
                } else {
                    graphics2D.setColor(deadColour);
                }
                graphics2D.fill(cell);
                graphics2D.setColor(borderColour);
                graphics2D.draw(cell);
            }
        }
    }
}
