package main.java.com.hoyes.gameoflife.grid;

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

    private int recenterFrequency;
    private int frameCounter;

    public GridView(int width, int height, int recenterFrequency) {
        super();
        this.recenterFrequency = recenterFrequency;
        this.frameCounter = 0;
        this.xCells = width / cellSize;
        this.yCells = height / cellSize;
        this.cells = new Cell[xCells][yCells];
        this.setPreferredSize(new Dimension(width+1, height+1));
        this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        this.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        populateCells(new DefaultCellInitialiser());
    }

    public GridView(int width, int height) {
        this(width, height, 0);
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

    public Rectangle calculateBoundingBox() {
        int minX = cells.length;
        int minY = cells[0].length;
        int maxX = 0;
        int maxY = 0;

        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                if (cells[x][y].isAlive()) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        return new Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    public Point calculateCenterOffset(Rectangle boundingBox) {
        int xOffset = (cells.length - boundingBox.width) / 2 - boundingBox.x;
        int yOffset = (cells[0].length - boundingBox.height) / 2 - boundingBox.y;
        return new Point(xOffset, yOffset);
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

    public void applyChanges(ArrayList<Point> changes) {
        if (recenterFrequency > 0 && frameCounter % recenterFrequency == 0) {
            Rectangle boundingBox = calculateBoundingBox();
            Point offset = calculateCenterOffset(boundingBox);
            applyOffset(changes, offset);
        }

        for (Point p : changes) {
            cells[p.x][p.y].changeAlive();
        }

        frameCounter++;
    }
    //TODO test this
    public List<Point> applyOffset(List<Point> changes, Point offset) {

        for (Point changedCell : changes) {
            changedCell.x += offset.x;
            changedCell.y += offset.y;
        }
        return changes;
    }

    public boolean isChanged(int x, int y) {
        int neighbors = calculateNeighbors(x, y);
        boolean alive = cells[x][y].isAlive();
        //System.out.printf("main.java.com.hoyes.gameoflife.cell.Cell: %s, %s. %s with %d neighbors.%n", x, y, alive?"Alive":"Dead", neighbors);
        if (!alive && neighbors == 3) return true;
        else return alive && (neighbors > 3 || neighbors < 2);
    }

    public int calculateNeighbors(int x, int y) {
        int neighbors = 0;
        for (int x2 = x-1; x2 <= x + 1; x2 ++) {
            for (int y2 = y-1; y2 <= y + 1; y2 ++) {
                //System.out.printf("Neighbor at %s, %s ", x2, y2);
                //Check for negative, out of bounds indexes and same cell.
                if (x2 != -1 && y2 != -1 && x2 < cells.length && y2 < cells.length && (x2 != x || y2 != y)) {
                    if (cells[x2][y2].isAlive()) {
                        neighbors ++;
                    }
                }
                //System.out.printf("is %s%n", cells[x2][y2].isAlive()?"Alive":"Dead");
            }
        }
        return neighbors;
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
