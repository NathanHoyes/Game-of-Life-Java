package main.java.com.hoyes.gameoflife.grid;

import main.java.com.hoyes.gameoflife.gui.GliderPreviewPanel;
import main.java.com.hoyes.gameoflife.initialiser.DefaultCellInitialiser;
import main.java.com.hoyes.gameoflife.initialiser.GridIOCellInitialiser;

public class GliderPreviewGrid extends GridView{

    public GliderPreviewGrid(int width, int height, int recenterFrequency) {
        super(width, height, recenterFrequency);
        populateCells(new DefaultCellInitialiser());
    }


    public GliderPreviewGrid(int width, int height, int recenterFrequency, boolean[][] initialState) {
        super(width, height, recenterFrequency);
        populateCells(new GridIOCellInitialiser(initialState, this.getXCells(), this.getYCells()));
    }

}
