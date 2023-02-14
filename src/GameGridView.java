import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameGridView extends GridView {
    public GameGridView(int width, int height) {
        super(width, height);
    }

    /*
     * Helpers
     */

    public void populateCellsRandom() {
        Random random = new Random();

        for (int x = 0; x < cells.length; x ++) {
            for (int y = 0; y < cells[x].length; y ++) {
                boolean isAlive = random.nextBoolean();
                int xPos = x * getCellSize();
                int yPos = y * getCellSize();
                cells[x][y] = new Cell(xPos, yPos, getCellSize(), isAlive);
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


}