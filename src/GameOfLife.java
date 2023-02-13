import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameOfLife extends JPanel {
    private final int width = 1000, height = 1000;
    private final int cellSize = 10;
    private final int xCells = width/cellSize, yCells = height/cellSize;
    private final Cell[][] cells = new Cell[xCells][yCells];
    public GameOfLife() {
        super();
        this.setPreferredSize(new Dimension(width + 1, height + 1));
        populateCells();
    }

    /*
     * Accessors
     */

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public int getCellSize() {
        return cellSize;
    }

    /*
     * Helpers
     */

    public void populateCells() {
        Random random = new Random();
        for (int x = 0; x < cells.length; x ++) {
            for (int y = 0; y < cells[x].length; y ++) {
                boolean isAlive = random.nextBoolean();
                int xPos = x * getCellSize();
                int yPos = y * getCellSize();

                cells[x][y] = new Cell(xPos, yPos, cellSize, cellSize, isAlive);
            }
        }
    }

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
        for (Point p : changes) {
            cells[p.x][p.y].changeAlive();
        }
    }

    public boolean isChanged(int x, int y) {
        int neighbors = calculateNeighbors(x, y);
        boolean alive = cells[x][y].isAlive();
        //System.out.printf("Cell: %s, %s. %s with %d neighbors.%n", x, y, alive?"Alive":"Dead", neighbors);
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
     * Graphics Helpers
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