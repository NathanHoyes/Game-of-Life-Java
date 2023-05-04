package main.java.com.hoyes.gameoflife.grid;

import main.java.com.hoyes.gameoflife.cell.Cell;
import main.java.com.hoyes.gameoflife.gui.GliderPreviewPanel;
import main.java.com.hoyes.gameoflife.initialiser.DefaultCellInitialiser;
import main.java.com.hoyes.gameoflife.initialiser.GridIOCellInitialiser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GliderPreviewGrid extends GridView{

    boolean[][] storedGlider;

    private int margin = 1;
    public GliderPreviewGrid(int width, int height) {
        super(width, height, new WrappingNeighbourCalculator());
        populateCells(new DefaultCellInitialiser());
    }


    public GliderPreviewGrid(int width, int height, boolean[][] initialState) {
        this(width, height);
        storedGlider = initialState;
        populateCells(new GridIOCellInitialiser(initialState, this.getXCells(), this.getYCells()));
    }

    /*
     * Accessors
     */

    public boolean [][] getStoredGlider() {
        return storedGlider;
    }

    /*
     * Mutators
     */

    public void setStoredGlider(boolean[][] inArray) {
        this.storedGlider = inArray;
    }

    /*
     * Recentering Methods
     */

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

    @Override
    public void applyChanges(List<Point> changes) {
        super.applyChanges(changes);
        Rectangle boundingBox = calculateBoundingBox();
        if (boundingBox.x <= margin || boundingBox.y <= margin ||
            boundingBox.x + boundingBox.width >= cells.length - margin ||
            boundingBox.y + boundingBox.height >= cells[0].length - margin) {

            Point offset = calculateOppositeOffset(boundingBox);
            //shiftGrid(offset);
        }
    }

    public void shiftGrid(Point offset) {
        List<Cell> changes = new ArrayList<>();

        for (int x=0; x < cells.length; x++) {
            for (int y=0; y < cells[x].length; y++) {
                int offsetX = x + offset.x;
                int offsetY = y + offset.y;
                if ( offsetX < cells.length && offsetX >= 0 && offsetY < cells[x].length && offsetY >= 0 &&
                        cells[x][y].isAlive() != cells[offsetX][offsetY].isAlive() ) {
                    changes.add(cells[offsetX][offsetY]);
                    changes.add(cells[x][y]);
                }
            }
        }
        for ( Cell cell : changes ) {
            cell.changeAlive();
        }
    }

    public Point calculateCenterOffset(Rectangle boundingBox) {
        int xOffset = (cells.length - boundingBox.width) / 2 - boundingBox.x;
        int yOffset = (cells[0].length - boundingBox.height) / 2 - boundingBox.y;
        return new Point(xOffset, yOffset);
    }

    public Point calculateOppositeOffset(Rectangle boundingBox) {
        int xOffset = 0;
        int yOffset = 0;
        int margin = 4; // 1 extra cell away from the margin

        if (boundingBox.x <= margin) {
            xOffset = cells.length - boundingBox.width - boundingBox.x - margin;
        }
        if (boundingBox.y <= margin) {
            yOffset = cells[0].length - boundingBox.height - boundingBox.y - margin;
        }
        return new Point(xOffset, yOffset);
    }
}
