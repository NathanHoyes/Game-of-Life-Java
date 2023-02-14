import javax.swing.*;
import java.awt.*;

public abstract class GridView extends JPanel {
    private final int cellSize = 10;
    private final int xCells;
    private final int yCells;
    protected final Cell[][] cells;

    public GridView(int width, int height) {
        super();
        xCells = width / cellSize;
        yCells = height / cellSize;
        cells = new Cell[xCells][yCells];
        this.setPreferredSize(new Dimension(width+1, height+1));
        this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        this.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        populateCells();
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

    /*
     * Helpers
     */

    public void populateCells() {
        for (int x = 0; x < cells.length; x ++) {
            for (int y = 0; y < cells[x].length; y ++) {
                cells[x][y] = new Cell(x * cellSize, y * cellSize, cellSize, false);
            }
        }
    }

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
